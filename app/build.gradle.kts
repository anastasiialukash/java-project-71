plugins {
    id("java")
    id ("application")
    id ("checkstyle")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("info.picocli:picocli:4.7.6")
    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.1")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "hexlet.code.App"
}

tasks.getByName("run", JavaExec::class) {
    standardInput = System.`in`
}