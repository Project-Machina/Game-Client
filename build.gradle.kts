plugins {
    kotlin("jvm") version "1.6.0" apply false
    id("org.openjfx.javafxplugin") version "0.0.10" apply false
    kotlin("plugin.serialization") version "1.6.0" apply false
}

group = "com.client"
version = "1.0-SNAPSHOT"

project("javafx") {
    apply {
        plugin("org.openjfx.javafxplugin")
    }
    dependencies {
        "implementation"("no.tornado:tornadofx2:2.0.0-SNAPSHOT")
    }
    configure<org.openjfx.gradle.JavaFXOptions> {
        modules("javafx.base", "javafx.graphics", "javafx.fxml", "javafx.web")
    }
}

project("application") {
    apply {
        plugin("org.openjfx.javafxplugin")
    }
    dependencies {
        "implementation"("no.tornado:tornadofx2:2.0.0-SNAPSHOT")
    }
    configure<org.openjfx.gradle.JavaFXOptions> {
        version = "17"
        modules("javafx.base", "javafx.graphics", "javafx.fxml", "javafx.web")
    }
}

subprojects {
    group = "com.client"
    apply {
        plugin("kotlin")
        plugin("kotlinx-serialization")
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        "implementation"(kotlin("stdlib"))
        "implementation"("io.insert-koin:koin-core:3.1.2")
        "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
        "implementation"("io.ktor:ktor-client-serialization-jvm:1.6.5")
        "implementation"("io.ktor:ktor-client-json-jvm:1.6.5")
        "implementation"("io.ktor:ktor-client-cio-jvm:1.6.5")
        "implementation"("io.ktor:ktor-client-core-jvm:1.6.5")
        "implementation"("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
        "implementation"("org.jetbrains.kotlin:kotlin-script-runtime:1.6.0")
        "implementation"("org.jetbrains.kotlin:kotlin-script-util:1.6.0")
        "implementation"("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.6.0")
        "implementation"("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:1.6.0")
        "implementation"("org.openjdk.nashorn:nashorn-core:15.3")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        targetCompatibility = "12"
        kotlinOptions {
            jvmTarget = "12"
        }
    }

    tasks.withType<JavaCompile> {
        options.release.set(12)
    }

}

