plugins {
    id("java")
}

group = "ua.edu.ukma"
version = "1.0.0"

repositories {
    mavenCentral()
}

val postgresDriverVersion = "42.7.3"
val mybatisVersion = "3.5.15"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(libs.logback)
    implementation(project(":calculations"))
    implementation("org.postgresql:postgresql:$postgresDriverVersion")
    implementation("org.mybatis:mybatis:$mybatisVersion")
}

tasks.test {
    useJUnitPlatform()
}