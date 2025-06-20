package cloud.rio.example.adapter.publisher

import cloud.rio.example.adapter.kafka.IotDataKafka
import cloud.rio.example.adapter.rest.DriverEventRest
import cloud.rio.gdprdoc.annotations.GdprData
import cloud.rio.gdprdoc.model.PiiLevel
import java.time.Instant

@GdprData.Outgoing(
    sharedWith = "Published to kafka topic rio.driver-events",
    why = "To provide driver events to other services",
    links = [IotDataKafka::class, DriverEventRest::class],
)
data class DriverEventKafka(
    @GdprData.Field(level = PiiLevel.PSEUDONYM)
    val assetId: String,
    @GdprData.Field(level = PiiLevel.NON_PII)
    val timestamp: Instant,
    @GdprData.Field(level = PiiLevel.PSEUDONYM)
    val driverCardNumber: String,
)
