import io.gitlab.arturbosch.detekt.Detekt
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring") version "2.0.21"
    kotlin("plugin.jpa") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"

    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "7.17.0"
    id("org.liquibase.gradle") version "2.2.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
}

group = "ru.bardinpetr.itmo"
version = "0.3.0"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.fusesource.mqtt-client:mqtt-client:1.15")

    // data
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // mappers
    implementation("org.modelmapper:modelmapper:3.2.0")
    implementation("org.modelmapper.extensions:modelmapper-spring:3.0.0")
    kapt("org.mapstruct:mapstruct-processor:1.6.0")

    // audit
    implementation("org.hibernate:hibernate-envers:7.0.0.Beta1")
    implementation("org.springframework.data:spring-data-envers")

    // driver
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.liquibase:liquibase-core")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // docs
    implementation("org.springdoc:springdoc-openapi-starter-common:2.2.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // geo
    implementation("io.github.dellisd.spatialk:geojson:0.3.0")

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // misc
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.mapstruct:mapstruct:1.6.0")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.8")
}

kotlin {
}

allOpen {
    annotations("jakarta.persistence.Entity", "jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<BootJar>("bootJar") {
    this.archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}

openApiGenerate {
    generatorName = "typescript-angular"
    inputSpec = "$rootDir/openapi.yaml"
    outputDir = layout.buildDirectory.dir("fe-lib").get().toString()
    skipValidateSpec = true
    additionalProperties.apply {
        put("packageName", "medicine-drone-delivery-fe-lib")
    }
    configOptions.apply {
        put("npmName", "medicine-drone-delivery-fe-lib")
        put("withInterfaces", "true")
        put("ngVersion", "18")
    }
}

dependencyAnalysis {
    issues {
        all {
            onAny {
                severity("fail")
            }
        }
    }
}

detekt {
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        sarif.required.set(true)
    }
}
