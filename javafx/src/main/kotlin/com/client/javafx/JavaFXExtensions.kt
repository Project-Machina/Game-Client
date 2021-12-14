package com.client.javafx

import com.client.game.model.PreferencesModel
import javafx.beans.binding.Bindings
import javafx.beans.binding.StringBinding
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.StringProperty
import javafx.beans.value.ObservableValue
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

fun StringProperty.setHideable(supplier: () -> String, vararg modes: ObservableValue<*>) {
    bind(Bindings.createStringBinding({
        val bools = modes.filterIsInstance<BooleanProperty>()
        if(bools.any { it.get() }) {
            "Hidden"
        } else supplier.invoke()
    }, *modes))
}

fun StringProperty.setHideable(vararg modes: ObservableValue<*>, supplier: () -> String) {
    bind(Bindings.createStringBinding({
        val bools = modes.filterIsInstance<BooleanProperty>()
        if(bools.any { it.get() }) {
            "Hidden"
        } else supplier.invoke()
    }, *modes))
}