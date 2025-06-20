package cloud.rio.example.adapter.db

import cloud.rio.example.adapter.kafka.IotDataKafka
import cloud.rio.gdprdoc.annotations.GdprData
import cloud.rio.gdprdoc.model.PiiLevel
import java.time.Instant

@GdprData.Persisted(
    retention = "Kept for 30 days",
    responsibleForDeletion = "Dev team",
    links = [IotDataKafka::class],
)
data class DriverEventDb(
    @GdprData.Field(level = PiiLevel.PSEUDONYM)
    val assetId: String,
    @GdprData.Field(level = PiiLevel.NON_PII)
    val timestamp: Instant,
    @GdprData.Field(level = PiiLevel.NON_PII)
    val position: PositionDb,
    @GdprData.Field(level = PiiLevel.PSEUDONYM)
    val driverCardNumber: String,
)

data class PositionDb(
    val latitude: Double,
    val longitude: Double,
)
