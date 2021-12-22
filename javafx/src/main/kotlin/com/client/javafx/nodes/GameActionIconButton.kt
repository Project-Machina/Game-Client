package com.client.javafx.nodes

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Cursor
import javafx.scene.control.Button
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.paint.Color
import tornadofx.onChange
import tornadofx.onHover
import tornadofx.style

class GameActionIconButton : Button() {

    private val icon = SimpleObjectProperty<GameIcon>()
    fun iconProperty() = icon
    fun getIcon() = icon.get()
    fun setIcon(icon: GameIcon) {
        this.icon.set(icon)
    }

    private val secondaryIcon = SimpleObjectProperty<GameIcon>()
    fun secondaryIconProperty() = secondaryIcon
    fun getSecondaryIcon() = secondaryIcon.get()
    fun setSecondaryIcon(icon: GameIcon) {
        this.secondaryIcon.set(icon)
    }

    private val iconToggle = SimpleBooleanProperty()
    fun iconToggleProperty() = iconToggle
    fun getIconToggle() = iconToggle.get()
    fun setIconToggle(value: Boolean) {
        iconToggle.set(value)
    }

    init {
        styleClass.remove("button")
        style {
            cursor = Cursor.HAND
            onHover {
                background = if (it) {
                    Background(BackgroundFill(Color.rgb(255, 255, 255, 0.25), null, null))
                } else {
                    null
                }
            }
        }
        prefWidth = 16.0
        prefHeight = 16.0
        graphicProperty().bind(Bindings.createObjectBinding({
            if(iconToggle.get()) secondaryIcon.get() else icon.get()
        }, iconToggle, icon, secondaryIcon))
    }

}