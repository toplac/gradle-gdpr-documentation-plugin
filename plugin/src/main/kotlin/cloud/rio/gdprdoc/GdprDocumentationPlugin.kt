package cloud.rio.gdprdoc


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

class GdprDocumentationPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("generateGdprDocumentation", GenerateGdprDocumentationTask::class.java) {
            it.group = "annotation processing"
            it.description = "Processes annotations in the source set."
            val mainSourceSet = project.extensions.getByType(SourceSetContainer::class.java).getByName("main")
            it.serviceName.set(project.name)
            it.classpath.from(mainSourceSet.output)
            it.markdownReport.set(
                project.layout.buildDirectory.file("reports/gdpr-documentation.md")
            )
        }
    }
}
