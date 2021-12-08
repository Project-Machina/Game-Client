package com.client.game.ui.components

import com.client.scripting.ScriptAPI
import javafx.beans.property.ObjectProperty
import javafx.scene.Node
import javafx.scene.layout.VBox
import tornadofx.*

class InformationBox : VBox() {

    val header: ObjectProperty<Node> by lazy { InformationPositionProperty("header") }
    val content: ObjectProperty<Node> by lazy { InformationPositionProperty("content") }

    fun setHeader(node: Node) {
        header.set(node)
    }

    fun setContent(node: Node) {
        content.set(node)
    }

    fun getHeader() = header.get()

    fun getContent() = content.get()

    init {
        ScriptAPI.importStylesheet<InformationBox>("infobox.css")
        addClass("full-border", "back")

        add(hbox {
            maxWidthProperty().bind(this@InformationBox.widthProperty())
            prefHeight = 120.0
            maxHeight = 120.0
            styleClass.add("info-header")
            dynamicContent(header) {
                if (header.get() != null) {
                    children.setAll(header.get())
                }
            }
        })

        add(anchorpane {
            prefWidthProperty().bind(this@InformationBox.widthProperty())
            prefHeightProperty().bind(this@InformationBox.heightProperty())
            dynamicContent(content) {
                if (content.get() != null) {
                    children.setAll(content.get())
                }
            }
        })

    }
}
