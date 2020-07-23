import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("maven-publish")
    id("org.jetbrains.dokka")
//    id("org.springframework.boot") version "2.3.1.RELEASE"
//    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm")
}

base {
    archivesBaseName = "logistics-express-client"
}

//extra["springCloudVersion"] = "Hoxton.SR6"

dependencies {
    api("org.springframework.cloud:spring-cloud-starter-openfeign")
    api("io.github.openfeign:feign-httpclient")
    implementation("com.zy.mylib:mylib-webmvc")
}

//dependencyManagement {
//    imports {
//        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
//    }
//}

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
