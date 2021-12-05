plugins {
    application
}

application {
    mainClass.set("com.client.application.Application")
}

dependencies {
    implementation(project(":javafx"))
    implementation(project(":network"))
}