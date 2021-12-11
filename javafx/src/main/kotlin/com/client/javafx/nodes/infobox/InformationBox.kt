package com.client.javafx.nodes.infobox

import javafx.beans.DefaultProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.control.Control
import javafx.scene.control.Skin

@DefaultProperty("content")
class InformationBox : Control() {

    private val title = SimpleStringProperty(this, "title", null)
    private val content = SimpleListProperty<Node>(this, "content", FXCollections.observableArrayList())

    fun titleProperty() = title
    fun getTitle() = title.get()
    fun setTitle(title: String) {
        this.title.set(title)
    }

    fun getContent(): ObservableList<Node> = content
    fun setContent(content: ObservableList<Node>) {
        content.setAll(content)
    }

    override fun createDefaultSkin(): Skin<InformationBox> {
        return InformationBoxSkin(this)
    }
}