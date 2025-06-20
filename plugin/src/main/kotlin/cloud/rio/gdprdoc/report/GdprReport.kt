package cloud.rio.gdprdoc.report

import cloud.rio.gdprdoc.model.PiiLevel

@JvmInline
value class GdprItemId(val value: String): Comparable<GdprItemId> {
    override fun toString(): String = value
    override fun compareTo(other: GdprItemId): Int = value.compareTo(other.value)
}

data class GdprItemRelation(
    val from: GdprItemId,
    val to: GdprItemId,
    val type: Type,
) {
    enum class Type {
        FLOW,
        RELATED_TO,
    }

    companion object {
        fun flow(from: GdprItemId, to: GdprItemId): GdprItemRelation {
            return GdprItemRelation(from, to, Type.FLOW)
        }

        fun relatedTo(from: GdprItemId, to: GdprItemId): GdprItemRelation {
            val sortedIds = listOf(from, to).sorted()
            return GdprItemRelation(sortedIds[0], sortedIds[1], Type.RELATED_TO)
        }
    }
}

data class GdprLinkToItem(
    val id: GdprItemId,
    val name: String,
)


data class GdprReport(
    val serviceName: String,
    val data: List<GdprDataItem>,
    val relations: Set<GdprItemRelation>,
) {
    fun get(id: GdprItemId): GdprDataItem? {
        return data.find { it.id == id }
    }

    fun linksOf(id: GdprItemId): List<GdprLinkToItem> {
        return relations.filter { it.from == id || it.to == id }
            .map { relation ->
                val linkedId = if (relation.from == id) relation.to else relation.from
                val linkedItem = get(linkedId)
                GdprLinkToItem(linkedId, linkedItem?.name ?: linkedId.value)
            }
    }
}

sealed class GdprDataItem {
    abstract val id: GdprItemId
    abstract val name: String
    abstract val fields: List<Field>

    data class Incoming(
        override val id: GdprItemId,
        override val name: String,
        val whereFrom: String,
        val whatToDo: String,
        override val fields: List<Field> = emptyList(),
    ) : GdprDataItem()

    data class Outgoing(
        override val id: GdprItemId,
        override val name: String,
        val sharedWith: String,
        val why: String,
        override val fields: List<Field> = emptyList(),
    ) : GdprDataItem()

    data class Persisted(
        override val id: GdprItemId,
        override val name: String,
        val retention: String,
        val responsibleForDeletion: String,
        override val fields: List<Field> = emptyList(),
    ) : GdprDataItem()

    data class Field(
        val name: String,
        val type: String,
        val level: PiiLevel,
    )
}
