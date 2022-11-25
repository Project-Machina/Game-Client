package com.client.game.model

import javafx.beans.property.*
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import org.jetbrains.kotlin.utils.addToStdlib.getOrPut
import tornadofx.ViewModel
import tornadofx.onChange

class ParameterModel : ViewModel() {
    val integerParams = bind { SimpleMapProperty<String, IntegerProperty>(this, "integer_params", FXCollections.observableHashMap()) }
    val stringParams = bind { SimpleMapProperty<String, StringProperty>(this, "string_params", FXCollections.observableHashMap()) }
    val booleanParams = bind { SimpleMapProperty<String, BooleanProperty>(this, "boolean_params", FXCollections.observableHashMap()) }

    fun string(key: String) : String = stringParams[key]?.value ?: ""
    fun int(key: String) : Int = integerParams[key]?.value ?: 0
    fun bool(key: String): Boolean = booleanParams[key]?.value ?: false

    operator fun set(key: String, value: Int) {
        if(integerParams.containsKey(key)) {
            integerParams[key]!!.set(value)
        } else {
            integerParams[key] = SimpleIntegerProperty(value)
        }
    }

    operator fun set(key: String, value: String) {
        if(stringParams.containsKey(key)) {
            stringParams[key]!!.set(value)
        } else {
            stringParams[key] = SimpleStringProperty(value)
        }
    }

    operator fun set(key: String, value: Boolean) {
        if(booleanParams.containsKey(key)) {
            booleanParams[key]!!.set(value)
        } else {
            booleanParams[key] = SimpleBooleanProperty(value)
        }
    }

    fun stringProperty(key: String) : StringProperty {
        return stringParams.getOrPut(key) { SimpleStringProperty("") }
    }

    fun intProperty(key: String) : IntegerProperty {
        return integerParams.getOrPut(key) { SimpleIntegerProperty(0) }
    }

    fun booleanProperty(key: String) : BooleanProperty {
        return booleanParams.getOrPut(key) { SimpleBooleanProperty(false) }
    }

    @JvmName("onBooleanChange")
    fun onChange(key: String, action: (Boolean) -> Unit) {
        booleanParams.getOrPut(key) { SimpleBooleanProperty(false) }
            .onChange(action)
    }

    @JvmName("onStringChange")
    fun onChange(key: String, action: (String?) -> Unit) {
        stringParams.getOrPut(key) { SimpleStringProperty() }
            .onChange(action)
    }

    @JvmName("onIntChange")
    fun onChange(key: String, action: (Int) -> Unit) {
        integerParams.getOrPut(key) { SimpleIntegerProperty(0) }
            .onChange(action)
    }
}