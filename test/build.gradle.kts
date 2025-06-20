plugins {
    kotlin("jvm") version "2.1.0"
    id("cloud.rio.gdprdoc")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("cloud.rio.gdprdoc:core:0.0.1")
    testImplementation("com.diffplug.selfie:selfie-runner-junit5:2.5.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
        vendor = JvmVendorSpec.AMAZON
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    dependsOn(":generateGdprDocumentation")
}
