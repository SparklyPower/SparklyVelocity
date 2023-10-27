plugins {
    java
    `maven-publish`
}

extensions.configure<PublishingExtension> {
    repositories {
        maven {
            credentials(PasswordCredentials::class.java)

            // name = "paper"
            // val base = "https://papermc.io/repo/repository/maven"
            // val releasesRepoUrl = "$base-releases/"
            // val snapshotsRepoUrl = "$base-snapshots/"
            // setUrl(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            // Publish to PerfectDreams
            name = "PerfectDreams"
            url = uri("https://repo.perfectdreams.net/")
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("SparklyVelocity")
                description.set("The modern, next-generation Minecraft server proxy")
                url.set("https://papermc.io/software/velocity")
                scm {
                    url.set("https://github.com/SparklyPower/SparklyVelocity")
                    connection.set("scm:git:https://github.com/SparklyPower/SparklyVelocity.git")
                    developerConnection.set("scm:git:https://github.com/SparklyPower/SparklyVelocity.git")
                }
            }
        }
    }
}
