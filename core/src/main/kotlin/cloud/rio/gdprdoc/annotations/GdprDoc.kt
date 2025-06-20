package cloud.rio.gdprdoc.annotations

import cloud.rio.gdprdoc.model.PiiLevel
import kotlin.reflect.KClass

const val READ_MODEL_DEFAULT_RETENTION = "Kept until data is deleted upstream"
const val READ_MODEL_DEFAULT_RESPONSIBLE_FOR_DELETION = "Owner of the upstream data source"


@Target(AnnotationTarget.CLASS)
annotation class GdprData {

    @GdprData
    @Target(AnnotationTarget.CLASS)
    annotation class Incoming(
        val whereFrom: String,
        val whatToDo: String,
        val links: Array<KClass<*>> = [],
    )

    @GdprData
    @Target(AnnotationTarget.CLASS)
    annotation class Outgoing(
        val sharedWith: String,
        val why: String,
        val links: Array<KClass<*>> = [],
    )

    @GdprData
    @Target(AnnotationTarget.CLASS)
    annotation class Persisted(
        val retention: String,
        val responsibleForDeletion: String,
        val links: Array<KClass<*>> = [],
    )

    @GdprData
    @Target(AnnotationTarget.CLASS)
    annotation class ReadModel(
        val whereFrom: String,
        val whatToDo: String,
        val retention: String = READ_MODEL_DEFAULT_RETENTION,
        val responsibleForDeletion: String = READ_MODEL_DEFAULT_RESPONSIBLE_FOR_DELETION,
        val links: Array<KClass<*>> = [],
    )

    @Target(AnnotationTarget.FIELD)
    annotation class Field(
        val level: PiiLevel,
    )
}
