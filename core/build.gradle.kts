plugins {
    kotlin("jvm") version "2.1.0"
    `java-library`
    `maven-publish`
}

group = "cloud.rio.gdprdoc"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
        vendor = JvmVendorSpec.AMAZON
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("libraryMaven") {
            from(components["java"])
            artifactId = "core"
            version = "0.0.1"
        }
    }
    repositories {
        mavenLocal()
    }
}
