package com.client.game.model

import javafx.beans.property.*
import javafx.collections.FXCollections
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

    inline fun <reified C> property(key: String) : C {
       return when(C::class) {
           Int::class -> integerParams.getOrPut(key) { SimpleIntegerProperty(0) } as C
           String::class -> stringParams.getOrPut(key) { SimpleStringProperty("") } as C
           Boolean::class -> booleanParams.getOrPut(key) { SimpleBooleanProperty(false) } as C
           else -> error("Invalid type ${C::class.simpleName}")
       }
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