package com.client.game.model.processes

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class ProcessDataModel(data: ProcessData) : ItemViewModel<ProcessData>(data) {

    val name = bind { SimpleStringProperty(this, "name", data.name) }
    val timeElapsed = bind { SimpleLongProperty(this, "time_elapsed", data.elapsedTime) }
    val time = bind { SimpleLongProperty(this, "time", data.time) }
    val pid = bind { SimpleIntegerProperty(this, "index", data.pid) }

    override fun onCommit() {
        this.item = ProcessData(name.get(), pid.get(), timeElapsed.get(), time.get())
    }
}