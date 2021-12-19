package com.client.game.model.software

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class SoftwareDataModel(val data: SoftwareData) : ItemViewModel<SoftwareData>(data) {

    val name = bind { SimpleStringProperty(this, "name", data.name) }
    val extension = bind { SimpleStringProperty(this, "extension", data.extension) }
    val version = bind { SimpleDoubleProperty(this, "version", data.version) }
    val size = bind { SimpleObjectProperty<ULong>(this, "size", data.size) }

    override fun onCommit() {

        item = SoftwareData(name.get(), extension.get(), version.get(), size.get())

    }
}