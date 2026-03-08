plugins {
    id("java")
    id ("com.github.ben-manes.versions") version "0.53.0"
    id("org.sonarqube") version "7.2.2.6593"
    application
    checkstyle
    jacoco
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.assertj:assertj-core:3.22.0")

    // JUnit 5 BOM – задаёт версии всех артефактов JUnit
    testImplementation (platform("org.junit:junit-bom:5.10.0"))

    // Тестовые аннотации (api)
    testImplementation ("org.junit.jupiter:junit-jupiter-api")

    // Движок, который действительно запускает тесты
    testRuntimeOnly  ("org.junit.jupiter:junit-jupiter-engine")

    // Альтернатива: добавьте launcher напрямую
    testRuntimeOnly ("org.junit.platform:junit-platform-launcher")

    implementation("info.picocli:picocli:4.7.7")
    annotationProcessor ("info.picocli:picocli-codegen:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")

}

application {
    mainClass = "hexlet.code.App"
}

tasks.test {
    useJUnitPlatform()
}

sonar {
    properties {
        property("sonar.projectKey", "Xomyakkk_java-project-71")
        property("sonar.organization", "xomyakkk")
    }
}