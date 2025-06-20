package cloud.rio.example.adapter.kafka

import cloud.rio.example.adapter.rest.DriverEventRest
import cloud.rio.gdprdoc.annotations.GdprData
import cloud.rio.gdprdoc.model.PiiLevel
import java.time.Instant

@GdprData.Incoming(
    whereFrom = "Kafka topic rio.iot-events",
    whatToDo = "Generate aggregated events",
)
data class IotDataKafka(
    @GdprData.Field(level = PiiLevel.PSEUDONYM)
    val assetId: String,
    @GdprData.Field(level = PiiLevel.NON_PII)
    val timestamp: Instant,
    @GdprData.Field(level = PiiLevel.NON_PII)
    val position: PositionKafka,
    @GdprData.Field(level = PiiLevel.PSEUDONYM)
    val driverCardNumber: String,
)

data class PositionKafka(
    val latitude: Double,
    val longitude: Double,
)

@GdprData.ReadModel(
    whereFrom = "Kafka topic rio.iot-events",
    whatToDo = "Enrich events during aggregation with driver card number",
    links = [DriverEventRest::class],
)
data class DriverKafka(
    @GdprData.Field(level = PiiLevel.PSEUDONYM)
    val driverCardNumber: String,
    @GdprData.Field(level = PiiLevel.PII)
    val name: String,
)

@GdprData.ReadModel(
    whereFrom = "Kafka topic rio.permissions",
    whatToDo = "Implement Access Control for API",
)
data class PermissionsKafka(
    @GdprData.Field(level = PiiLevel.PSEUDONYM)
    val userId: String,
    @GdprData.Field(level = PiiLevel.NON_PII)
    val hasAccess: Boolean,
)
