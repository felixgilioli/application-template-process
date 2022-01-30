import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.spring") version "1.6.10"
}

tasks.getByName<BootJar>("bootJar") {
    mainClass.set("br.com.felixgilioli.templateprocessor.adapter.ApplicationKt")
}

dependencies {
    implementation(project(":application:core"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.5")
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.0.0.202111291000-r")
}