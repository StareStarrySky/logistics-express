import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenLocal()
        maven("http://dev.ystfin.com/nexus/repository/maven-public/")
        mavenCentral()
    }

    val kotlinVersion = "1.3.72"

    dependencies {
        classpath(kotlin("allopen", kotlinVersion))
        classpath(kotlin("noarg", kotlinVersion))
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:0.10.1")
        classpath("com.bmuschko:gradle-docker-plugin:6.1.3")
    }
}

allprojects {
    group = "com.dduptop"
    version = "1.0.1"

    val mylibVersion = "1.12.2"

    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenLocal()
        maven("http://dev.ystfin.com/nexus/repository/maven-public/")
        mavenCentral()
    }

    dependencies {
        val implementation by configurations

        implementation((kotlin("reflect")))
        implementation((kotlin("stdlib")))
        implementation(platform("com.zy.mylib:mylib-dependencies:$mylibVersion"))
    }

    configure<SourceSetContainer> {
        named("main") {
            java.srcDir("src/main/kotlin")
        }
        named("test") {
            java.srcDir("src/test/kotlin")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "6.4.1"
    distributionType = Wrapper.DistributionType.BIN
}
