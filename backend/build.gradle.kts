plugins {
    java
    jacoco
    checkstyle
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "7.3.0.8198"
    id("org.owasp.dependencycheck") version "12.1.8"
}

group = "de.docfaust"
version = "0.1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

checkstyle {
    toolVersion = "10.26.1"
    configFile = file("$rootDir/checkstyle.xml")
    isIgnoreFailures = false
}

tasks.withType<Checkstyle> {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

dependencyCheck {
    failBuildOnCVSS = 7.0F
    formats = listOf("HTML", "JSON")
}

sonar {
    properties {
        property("sonar.projectKey", "DocFaust_vbb_classic")
        property("sonar.organization", "docfaust")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.sources", "src/main/java,../frontend/src")
        property("sonar.tests", "src/test/java,../frontend/src")
        property("sonar.test.inclusions", "src/test/java/**,../frontend/src/**/*.test.ts,../frontend/src/**/*.test.tsx")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.javascript.lcov.reportPaths", "../frontend/coverage/lcov.info")
    }
}
