plugins {
    application
}

application {
    mainClass.set("com.client.application.Application")
    applicationDefaultJvmArgs = listOf(
        "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
        "--add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED",
        "--add-opens=javafx.graphics/com.sun.javafx.scene.traversal=ALL-UNNAMED",
        "--add-opens=javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED",
        "--add-opens=javafx.controls/javafx.scene.control=ALL-UNNAMED",
        "--add-exports=javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED",
        "--add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED",
        "--add-exports=javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED",
        "--add-exports=javafx.base/com.sun.javafx.binding=ALL-UNNAMED",
        "--add-exports=javafx.base/com.sun.javafx.event=ALL-UNNAMED",
        "--add-exports=javafx.web/com.sun.javafx.webkit=ALL-UNNAMED"
    )
}

dependencies { implementation(project(":javafx"))
    implementation(project(":network"))
}
