package com.client.game.model

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.ViewModel

class PreferencesModel : ViewModel() {

    val LOW_MODE = bind { SimpleBooleanProperty(this, "low_mode", false) }
    val MEDIUM_MODE = bind { SimpleBooleanProperty(this, "medium_mode", false) }
    val HIGH_MODE = bind { SimpleBooleanProperty(this, "high_mode", false) }

    val SOFTWARE_NAME_SUB_MODE = bind { SimpleBooleanProperty(this, "software_name_sub_mode", config.boolean("hide_soft_name") ?: false) }
    val SOFTWARE_EXTENSION_SUB_MODE = bind { SimpleBooleanProperty(this, "software_extension_sub_mode", config.boolean("hide_soft_ext") ?: false) }
    val SOFTWARE_VERSION_SUB_MODE = bind { SimpleBooleanProperty(this, "software_version_sub_mode", config.boolean("hide_soft_version") ?: false) }

    val devMode = bind { SimpleBooleanProperty(this, "dev_mode", config.boolean("devMode") ?: false) }

    init {

        with(config) {
            if (containsKey("hiddenMode")) {
                when (get("hiddenMode") as String) {
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
        when (value) {
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

            if (LOW_MODE.get()) {
                put("hiddenMode", "low")
            } else if (MEDIUM_MODE.get()) {
                put("hiddenMode", "medium")
            } else {
                put("hiddenMode", "high")
            }

            put("hide_soft_name", SOFTWARE_NAME_SUB_MODE.get())
            put("hide_soft_ext", SOFTWARE_EXTENSION_SUB_MODE.get())
            put("hide_soft_version", SOFTWARE_VERSION_SUB_MODE.get())

            put("devMode", devMode.get())

        }

    }
}