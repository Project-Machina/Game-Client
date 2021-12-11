package com.client.javafx

import javafx.fxml.FXMLLoader
import javafx.scene.Node

inline fun <reified T : Node> T.setCustomComponentFxml(name: String) {
    val loader = FXMLLoader(T::class.java.getResource(name))
    loader.setRoot(this)
    loader.setController(this)
    try {
        loader.load()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}