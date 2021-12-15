package com.client.javafx.fields

import javafx.beans.property.SimpleBooleanProperty
import kotlin.reflect.KProperty

class ShowAddressProperty(val control: AddressField) : SimpleBooleanProperty(true) {

    override fun invalidated() {

        val text = control.text

        control.textProperty().set(null)
        control.textProperty().set(text)

    }

    operator fun getValue(ref: Any, property: KProperty<*>): Boolean {
        return get()
    }
}