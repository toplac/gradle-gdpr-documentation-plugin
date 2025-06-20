package cloud.rio.gdprdoc

import com.diffplug.selfie.Selfie.expectSelfie
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class GdprDocumentationSnapshotTest {

    @Test
    fun `gdpr documentation matches snapshot`() {
        val path = Paths.get("build/reports/gdpr-documentation.md")
        val content = Files.readString(path)
        expectSelfie(content).toMatchDisk()
    }

}
