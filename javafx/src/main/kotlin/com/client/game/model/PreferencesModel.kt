package com.client.game.model

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.ViewModel

class PreferencesModel : ViewModel() {

    val LOW_MODE = bind { SimpleBooleanProperty(this, "low_mode", false) }
    val MEDIUM_MODE = bind { SimpleBooleanProperty(this, "medium_mode", false) }
    val HIGH_MODE = bind { SimpleBooleanProperty(this, "high_mode", false) }

    val devMode = bind { SimpleBooleanProperty(this, "dev_mode", config.boolean("devMode") ?: false) }

    init {

        with(config) {
            if (containsKey("hiddenMode")) {
                when(get("hiddenMode") as String) {
                    "low" -> {
                        LOW_MODE.set(true)
                        MEDIUM_MODE.set(false)
                        HIGH_MODE.set(false)
                    }
                    "medium" -> {
                        LOW_MODE.set(false)
                        MEDIUM_MODE.set(true)
                        HIGH_MODE.set(false)
                    }
                    else -> {
                        LOW_MODE.set(false)
                        MEDIUM_MODE.set(false)
                        HIGH_MODE.set(true)
                    }
                }
            }
        }

    }

    fun setMode(value: String) {
        when(value) {
            "low" -> {
                LOW_MODE.set(true)
                MEDIUM_MODE.set(false)
                HIGH_MODE.set(false)
            }
            "medium" -> {
                LOW_MODE.set(false)
                MEDIUM_MODE.set(true)
                HIGH_MODE.set(false)
            }
            else -> {
                LOW_MODE.set(false)
                MEDIUM_MODE.set(false)
                HIGH_MODE.set(true)
            }
        }
    }

    fun unhideAll() {
        LOW_MODE.set(false)
        MEDIUM_MODE.set(false)
        HIGH_MODE.set(false)
    }

    override fun onCommit() {

        with(config) {

            if(LOW_MODE.get()) {
                put("hiddenMode", "low")
            } else if(MEDIUM_MODE.get()) {
                put("hiddenMode", "medium")
            } else {
                put("hiddenMode", "high")
            }

            put("devMode", devMode.get())

        }

    }
}