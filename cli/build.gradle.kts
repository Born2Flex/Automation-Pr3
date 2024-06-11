plugins {
    id("java")
}

group = "ua.edu.ukma"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(libs.logback)
    implementation(project(":core"))
    implementation(project(":props"))
}

tasks.test {
    useJUnitPlatform()
}