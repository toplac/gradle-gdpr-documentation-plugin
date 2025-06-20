plugins {
    kotlin("jvm") version "2.1.0"
    `java-gradle-plugin`
    `maven-publish`
}

group = "cloud.rio.gdprdoc"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
    implementation("io.github.classgraph:classgraph:4.8.162")

    api("cloud.rio.gdprdoc:core:0.0.1")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
        vendor = JvmVendorSpec.AMAZON
    }
}

gradlePlugin {
    plugins {
        create("rio-gdpr-documentation-plugin") {
            id = "cloud.rio.gdprdoc"
            implementationClass = "cloud.rio.gdprdoc.GdprDocumentationPlugin"
            version = "0.0.1"
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    repositories {
        mavenLocal()
    }
}
