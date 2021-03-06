import com.bmuschko.gradle.docker.tasks.image.*

plugins {
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("com.bmuschko.docker-remote-api")
    kotlin("jvm")
    kotlin("plugin.spring")
}

base {
    archivesBaseName = "logistics-express-server"
}

dependencies {
//    implementation("com.zy.mylib:mylib-utils")
//    implementation("com.zy.mylib:mylib-webmvc-data-jpa")
    implementation("com.zy.mylib:mylib-mvc-logger")
    implementation("com.zy.mylib:mylib-webmvc-security")
    implementation("com.zy.mylib:mylib-security-casbin-jwt")
    implementation("com.dduptop:document-service-client:1.0.34")
    implementation("com.dduptop:auth-client:1.11.43")
    implementation("com.alibaba.csb.sdk:http-client:1.1.5.2")

    implementation("com.google.zxing:core:3.4.0")
    implementation("com.google.zxing:javase:3.4.0")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
//    implementation("org.flywaydb:flyway-core:6.5.0")
//    implementation("mysql:mysql-connector-java:8.0.20")
//    implementation(project(":logistics-express-client"))

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.openfeign:feign-httpclient")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks {
    jar {
        enabled = true
    }

    bootJar {
        archiveFileName.value("app.jar")
        archiveClassifier.value("boot")
    }

    docker {
        url.value("tcp://192.168.114.20:2375")
        registryCredentials {
            username.value("100009702943")
            password.value("zzyy1978")
        }
    }
}

tasks.register<DockerBuildImage>("buildImage") {
    dependsOn("bootJar")
    inputDir.set(file("."))
    images.add("ccr.ccs.tencentyun.com/logistics-express-platform/logistics-express:${project.version}")
}

tasks.register<DockerPushImage>("pushImage") {
    dependsOn("buildImage")
    images.add("ccr.ccs.tencentyun.com/logistics-express-platform/logistics-express:${project.version}")
}
