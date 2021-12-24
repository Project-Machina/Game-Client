package com.client.game.model

import javafx.beans.property.SimpleMapProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableMap
import tornadofx.ViewModel
import tornadofx.onChange
import tornadofx.wasUpdated

class ParameterModel : ViewModel() {
    val integerParams = bind { SimpleMapProperty<String, Int>(this, "integer_params", FXCollections.observableHashMap()) }
    val stringParams = bind { SimpleMapProperty<String, String>(this, "string_params", FXCollections.observableHashMap()) }
    fun string(key: String) : String = stringParams[key] ?: ""
    fun int(key: String) : Int = integerParams[key] ?: 0

    fun onStringChange(key: String, onChange : (String) -> Unit) {
        stringParams.value.onChange {
            println("Changed ${it.key}")
            if(it.key == key && it.map.containsKey(it.key)) {
                onChange(it.map[it.key]!!)
            }
        }
    }

    fun onIntChange(key: String, onChange : (Int) -> Unit) {
        integerParams.value.onChange {
            if(it.key == key && it.map.containsKey(it.key)) {
                onChange(it.map[it.key]!!)
            }
        }
    }

}