plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.serialization") version "1.8.0"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator").version("7.10.0")
}

group = "com.valrTechassessment"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.2.0")
    implementation("commons-io:commons-io:2.18.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.h2database:h2")

    //Swagger
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.28")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.openapitools:jackson-databind-nullable:+")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generate_stub") {
    generatorName.set("spring")
    library.set("spring-cloud")
    inputSpec.set("$rootDir/src/main/resources/api-spec.yaml")
    outputDir.set("${layout.buildDirectory.get().asFile}/generated/spec")
    modelPackage.set("com.valrTechassessment")
    apiPackage.set("com.valrTechassessment")
    invokerPackage.set("com.valrTechassessment")
}

tasks.compileKotlin {
    dependsOn(tasks.named("generate_stub"))
}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("${layout.buildDirectory.get().asFile}/generated/spec/src/main/java")
    }
}