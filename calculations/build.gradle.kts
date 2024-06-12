plugins {
    id("java-library")
}

group = "ua.edu.ukma"
version = "1.0.0"

repositories {
    mavenCentral()
}

val guavaVersion = "33.2.1-jre"
val jacksonVersion = "2.17.1"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.google.guava:guava:$guavaVersion")
    api("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
}

tasks.test {
    useJUnitPlatform()
}