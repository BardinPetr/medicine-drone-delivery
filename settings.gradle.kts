rootProject.name = "meddelivery"

pluginManagement {
    repositories {
        mavenCentral()
        maven(url = "https://central.sonatype.com/repository/maven-snapshots/")
        gradlePluginPortal()
    }
}

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.20" apply false
    id("org.jetbrains.kotlin.kapt") version "2.2.20" apply false
    id("com.autonomousapps.build-health") version "3.5.1"
}
