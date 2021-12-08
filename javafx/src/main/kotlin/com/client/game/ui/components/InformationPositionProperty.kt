package com.client.game.ui.components

import javafx.beans.property.ObjectPropertyBase
import javafx.scene.Node

class InformationPositionProperty(val propertyName: String = "") : ObjectPropertyBase<Node>() {
    override fun getBean(): Any {
        return this
    }

    override fun getName(): String {
        return this.propertyName
    }
}