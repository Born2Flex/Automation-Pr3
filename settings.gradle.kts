rootProject.name = "practice3"
include("props")
include("core")
include("cli")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("logback", "ch.qos.logback:logback-classic:1.4.12")
        }
    }
}
