package com.client.javafx

import com.client.game.model.PreferencesModel
import javafx.beans.binding.Bindings
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.StringProperty
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

fun StringProperty.setHideable(supplier: () -> String, vararg modes: BooleanProperty) {
    bind(Bindings.createStringBinding({
        if(modes.any { it.get() }) {
            "Hidden"
        } else supplier.invoke()
    }, *modes))
}