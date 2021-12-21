package com.client.game.model.software

import javafx.beans.property.*
import tornadofx.ItemViewModel

class SoftwareDataModel(val data: SoftwareData) : ItemViewModel<SoftwareData>(data) {

    val id = bind { SimpleStringProperty(this, "id", data.id) }
    val name = bind { SimpleStringProperty(this, "name", data.name) }
    val extension = bind { SimpleStringProperty(this, "extension", data.extension) }
    val version = bind { SimpleDoubleProperty(this, "version", data.version) }
    val size = bind { SimpleLongProperty(this, "size", data.size) }
    val pid = bind { SimpleIntegerProperty(this, "pid", data.pid) }
    val isHidden = bind { SimpleBooleanProperty(this, "is_hidden", data.isHidden) }
    val installed: Boolean get() = pid.get() != -1


    override fun onCommit() {

        item = SoftwareData(id.get(), name.get(), extension.get(), version.get(), size.get(), pid.get(), isHidden = isHidden.get())

    }
}