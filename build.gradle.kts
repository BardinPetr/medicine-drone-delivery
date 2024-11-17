import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "1.9.25"

    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"

    id("org.openapi.generator") version "7.1.0"
    id("org.liquibase.gradle") version "2.2.0"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("plugin.allopen") version "2.0.21"
    kotlin("kapt") version "2.0.21"
}

group = "ru.bardinpetr.itmo"
version = "0.2.0"

val fePath: String by project

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
//    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.session:spring-session-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // data
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
//    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.hibernate:hibernate-envers:7.0.0.Beta1")
    implementation("org.springframework.data:spring-data-envers")
    implementation("org.modelmapper.extensions:modelmapper-spring:3.0.0")
    implementation("org.modelmapper:modelmapper:3.2.0")


    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-data-ldap")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.security:spring-security-messaging")
    implementation("org.keycloak.bom:keycloak-adapter-bom:22.0.5")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
    implementation("org.fusesource.mqtt-client:mqtt-client:1.12")

    // docs
    implementation("org.springdoc:springdoc-openapi-starter-common:2.2.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // misc
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // geo
//    implementation("org.locationtech.spatial4j:spatial4j:0.8")
    implementation("io.github.dellisd.spatialk:geojson:0.3.0")
    implementation("io.github.dellisd.spatialk:turf:0.3.0")

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    kapt("org.mapstruct:mapstruct-processor:1.6.0")
    compileOnly("org.mapstruct:mapstruct:1.6.0")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
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
    outputDir = "$fePath/src/lib"
    skipValidateSpec = true
    additionalProperties.apply {
        put("packageName", "lab1api")
    }
    configOptions.apply {
        put("npmName", "itmo-is-lab1")
        put("npmRepository", "https://github.com/BardinPetr/itmo-is-lab-1-server")
        put("withInterfaces", "true")
        put("ngVersion", "17")
    }
}
