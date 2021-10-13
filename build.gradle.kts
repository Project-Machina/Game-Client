plugins {
    kotlin("jvm") version "1.5.31" apply false
    id("org.openjfx.javafxplugin") version "0.0.10" apply false
}

group = "com.client"
version = "1.0-SNAPSHOT"

project("javafx") {
    apply {
        plugin("org.openjfx.javafxplugin")
    }
    dependencies {
        "implementation"("no.tornado:tornadofx:2.0.0-SNAPSHOT")
    }
    configure<org.openjfx.gradle.JavaFXOptions> {
        modules("javafx.base", "javafx.graphics", "javafx.fxml", "javafx.web")
    }
}

subprojects {
    group = "com.client"
    apply {
        plugin("kotlin")
    }

    repositories {
        mavenCentral()
        maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots/") }
    }

    dependencies {
        "implementation"(kotlin("stdlib"))
    }

}

