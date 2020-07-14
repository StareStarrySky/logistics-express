import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("maven-publish")
    id("org.jetbrains.dokka")
    kotlin("jvm")
}

base {
    archivesBaseName = "logistics-express-client"
}

dependencies {
    api("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("com.zy.mylib:mylib-webmvc")
}

val generateSourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().java.srcDirs)
}

tasks.register<DokkaTask>("dokkaJavadoc") {
    outputFormat = "javadoc"
    outputDirectory = "$buildDir" + File.separator + "javadoc"
}

val generateJavadoc by tasks.creating(Jar::class) {
    dependsOn("dokkaJavadoc")
    group = "jar"
    archiveClassifier.set("javadoc")
    from(tasks["dokkaJavadoc"].property("outputDirectory"))
}

tasks {
    publishing {
        repositories {
            maven {
                name = "nexus-releases"
                url = uri("http://dev.ystfin.com/nexus/repository/maven-releases")
                credentials {
                    username = "deploy"
                    password = "ystfin!@#\$"
                }
            }
        }
        publications {
            create<MavenPublication>("maven") {
                artifact(generateJavadoc)
                artifact(generateSourcesJar)

//                afterEvaluate {
//                    artifactId = tasks.jar.get().archiveBaseName.get()
//                }
                from(getComponents()["java"])
            }
        }
    }
}
