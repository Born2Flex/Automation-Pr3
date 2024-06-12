plugins {
    id("java")
}

version = "1.0.0"

abstract class CheckFileExistsTask : DefaultTask() {
    @Input
    lateinit var filePath: String

    @TaskAction
    fun checkFileExists() {
        val file = project.file(filePath)
        if (file.exists()) {
            println("File exists: $filePath")
        } else {
            println("File does not exist: $filePath")
            throw GradleException("File not found: $filePath")
        }
    }
}

abstract class IncrementVersionTask : DefaultTask() {
    @Input
    @Optional
    var incrementType: String? = null

    @TaskAction
    fun incrementVersion() {
        val versionText = project.version
        val versionRegex = """(\d+)\.(\d+)\.(\d+)""".toRegex()
        val matchResult = versionRegex.matchEntire(versionText.toString()) ?: throw GradleException("Invalid version format. Expected format: x.y.z")

        val (major, minor, patch) = matchResult.destructured.toList().map { it.toInt() }
        val newVersion = when (incrementType) {
            "major" -> "${major + 1}.0.0"
            "minor" -> "$major.${minor + 1}.0"
            else -> "$major.$minor.${patch + 1}"
        }
        updateBuildFileVersion(project.rootProject.file("build.gradle.kts"), newVersion)
        project.subprojects.forEach { subproject ->
            val gradleBuildFile = subproject.file("build.gradle.kts")
            updateBuildFileVersion(gradleBuildFile, newVersion)
        }
        println("Updated version to $newVersion")
    }

    private fun updateBuildFileVersion(gradleBuildFile: File, newVersion: String) {
        var versionChanged = false
        val updatedBuildFile = gradleBuildFile.readLines().map { line ->
            if (!versionChanged && line.contains("version = ")) {
                versionChanged = true
                """version = "$newVersion""""
            } else {
                line
            }
        }.joinToString("\n")
        gradleBuildFile.writeText(updatedBuildFile)
        println("Updated version in ${gradleBuildFile.path} to $newVersion")
    }
}

tasks.register<CheckFileExistsTask>("checkFileExists") {
    group = "custom"
    description = "Task checks if specified file exists"
    filePath = (project.findProperty("fileName")?: "cli/src/main/resources/application.properties").toString()
}

tasks.register<IncrementVersionTask>("incrementVersion") {
    group = "versioning"
    description = "Increments the project version based on the given type (major, minor, patch)."
    incrementType = project.findProperty("increment") as String?
}

tasks.jar {
    dependsOn("incrementVersion")
}