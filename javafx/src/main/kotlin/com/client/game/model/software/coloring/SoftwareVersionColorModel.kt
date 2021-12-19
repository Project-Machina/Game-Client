package com.client.game.model.software.coloring

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty

class SoftwareVersionColorModel {

    private val minValue = SimpleDoubleProperty(1.0)
    private val maxValue = SimpleDoubleProperty(1.0)
    private val color = SimpleStringProperty("#90EE90FF")

    fun minValueProperty() = minValue
    fun getMinValue() = minValue.get()
    fun setMinValue(value: Double) {
        minValue.set(value)
    }

    fun maxValueProperty() = maxValue
    fun getMaxValue() = maxValue.get()
    fun setMaxValue(value: Double) {
        maxValue.set(value)
    }

    fun colorProperty() = color
    fun getColor() = color.get()
    fun setColor(color: String) {
        this.color.set(color)
    }

}