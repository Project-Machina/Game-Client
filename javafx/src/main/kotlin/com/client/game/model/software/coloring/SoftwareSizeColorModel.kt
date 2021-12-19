package com.client.game.model.software.coloring

import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty

class SoftwareSizeColorModel {

    private val minValue = SimpleLongProperty(1)
    private val maxValue = SimpleLongProperty(1)
    private val color = SimpleStringProperty("#90EE90FF")

    fun minValueProperty() = minValue
    fun getMinValue() = minValue.get()
    fun setMinValue(value: Long) {
        minValue.set(value)
    }

    fun maxValueProperty() = maxValue
    fun getMaxValue() = maxValue.get()
    fun setMaxValue(value: Long) {
        maxValue.set(value)
    }

    fun colorProperty() = color
    fun getColor() = color.get()
    fun setColor(color: String) {
        this.color.set(color)
    }
}