package cloud.rio.gdprdoc.report

class PlantUmlReporter {
    fun generateReport(report: GdprReport): String {
        val sb = StringBuilder()
        sb.appendLine("digraph G {")
        sb.appendLine("  rankdir=LR;")
        sb.appendLine("  nodesep=0.6;")

        // Legend subgraph (with margin and darkgray outline)
        sb.appendLine("    subgraph cluster_legend {")
        sb.appendLine("      label=\"Legend\";")
        sb.appendLine("      color=darkgray;")
        sb.appendLine("      margin=10;")
        sb.appendLine("      key_in [label=\"Incoming\", shape=box];")
        sb.appendLine("      key_db [label=\"Persisted\", shape=cylinder];")
        sb.appendLine("      key_out [label=\"Outgoing\", shape=hexagon];")
        sb.appendLine("      key_in -> key_db [label=\"data flow\", style=solid];")
        sb.appendLine("      key_db -> key_out [label=\"data flow\", style=solid];")
        sb.appendLine("      key_in -> key_out [label=\"related to\", style=dotted, dir=none];")
        sb.appendLine("      key_in [style=filled, fillcolor=white];")
        sb.appendLine("      key_db [style=filled, fillcolor=white];")
        sb.appendLine("      key_out [style=filled, fillcolor=white];")
        sb.appendLine("    }")

        // Main diagram cluster (no border)
        sb.appendLine("  subgraph cluster_main {")
        sb.appendLine("    label=\"\";")
        sb.appendLine("    margin=40;")
        sb.appendLine("    color=white;")

        // Map GdprDataItem type to Graphviz shape
        fun shapeForItem(item: GdprDataItem): String = when (item) {
            is GdprDataItem.Incoming -> "box"
            is GdprDataItem.Persisted -> "cylinder"
            is GdprDataItem.Outgoing -> "hexagon"
        }

        // Helper to quote IDs for Graphviz
        fun quoteId(id: GdprItemId): String = "\"${id.value}\""

        // Group items by type for rank
        val incoming = report.data.filterIsInstance<GdprDataItem.Incoming>()
        val persisted = report.data.filterIsInstance<GdprDataItem.Persisted>()
        val outgoing = report.data.filterIsInstance<GdprDataItem.Outgoing>()

        // Add nodes for each item, using the name as the label
        for (item in report.data) {
            val shape = shapeForItem(item)
            val label = item.name.replace("\"", "\\\"")
            sb.appendLine("    ${quoteId(item.id)} [label=\"$label\", shape=$shape];")
        }

        // Add rank groups for columns
        if (incoming.isNotEmpty()) {
            sb.append("    { rank=same; ")
            incoming.forEach { sb.append("${quoteId(it.id)}; ") }
            sb.appendLine("}")
        }
        if (persisted.isNotEmpty()) {
            sb.append("    { rank=same; ")
            persisted.forEach { sb.append("${quoteId(it.id)}; ") }
            sb.appendLine("}")
        }
        if (outgoing.isNotEmpty()) {
            sb.append("    { rank=same; ")
            outgoing.forEach { sb.append("${quoteId(it.id)}; ") }
            sb.appendLine("}")
        }

        // Add edges for each relation
        for (relation in report.relations) {
            val from = quoteId(relation.from)
            val to = quoteId(relation.to)
            when (relation.type) {
                GdprItemRelation.Type.FLOW ->
                    sb.appendLine("    $from -> $to;")
                GdprItemRelation.Type.RELATED_TO ->
                    sb.appendLine("    $from -> $to [dir=none, style=dotted];")
            }
        }

        sb.appendLine("  }") // end cluster_main

        sb.appendLine("}")
        return sb.toString()
    }
}
