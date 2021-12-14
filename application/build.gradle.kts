plugins {
    application
}

application {
    mainClass.set("com.client.application.Application")
    applicationDefaultJvmArgs = listOf(
        "--add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED",
        "--add-opens=javafx.graphics/com.sun.javafx.scene.traversal=ALL-UNNAMED",
        "--add-opens=javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED",
        "--add-opens=javafx.controls/javafx.scene.control=ALL-UNNAMED",
        "--add-exports=javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED"
    )
}

dependencies { implementation(project(":javafx"))
    implementation(project(":network"))
}
