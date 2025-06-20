package cloud.rio.example.adapter.rest

import cloud.rio.example.adapter.db.DriverEventDb
import cloud.rio.example.adapter.kafka.DriverKafka
import cloud.rio.gdprdoc.annotations.GdprData
import cloud.rio.gdprdoc.model.PiiLevel
import java.time.Instant

@GdprData.Outgoing(
    sharedWith = "Logged in user via frontend / API call",
    why = "To show the driver event on the live monitor",
    links = [DriverEventDb::class],
)
data class DriverEventRest(
    @GdprData.Field(level = PiiLevel.PSEUDONYM)
    val assetId: String,
    @GdprData.Field(level = PiiLevel.NON_PII)
    val timestamp: Instant,
    @GdprData.Field(level = PiiLevel.NON_PII)
    val address: String,
    @GdprData.Field(level = PiiLevel.PII)
    val driverName: String,
)
