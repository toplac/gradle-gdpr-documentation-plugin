tasks.register("clean") {
    dependsOn(gradle.includedBuilds.map { it.task(":clean") })
}

tasks.register("build") {
    dependsOn(gradle.includedBuilds.map { it.task(":build") })
}

tasks.register("publishToMavenLocal") {
    dependsOn(gradle.includedBuilds.filter { it.name != "test" }.map { it.task(":publishToMavenLocal") })
}
