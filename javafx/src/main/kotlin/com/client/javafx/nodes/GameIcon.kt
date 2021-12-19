package com.client.javafx.nodes

import javafx.beans.property.SimpleStringProperty
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import tornadofx.onChange

class GameIcon : ImageView() {

    private val name = SimpleStringProperty()
    fun nameProperty() = name
    fun getName() = name.get()
    fun setName(path: String) {
        this.name.set(path)
    }

    init {
        prefWidth(16.0)
        prefHeight(16.0)
        nameProperty().onChange {
            if (it != null && it.isNotEmpty()) {
                image = Image(GameIcon::class.java.getResourceAsStream("/com/client/images/$it"))
            }
        }
    }

    fun copy() : GameIcon {
        val icon = GameIcon()
        icon.name.set(getName())
        return icon
    }

}