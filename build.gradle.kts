plugins {
    kotlin("jvm") version "1.5.31"
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "com.client"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}