package com.client.game.model.processes

import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class ProcessDataModel(name: String, time: Long) : ItemViewModel<ProcessData>(ProcessData(name, 0L, time)) {

    val name = bind { SimpleStringProperty(this, "name", name) }
    val timeElapsed = bind { SimpleLongProperty(this, "time_elapsed", 0L) }
    val time = bind { SimpleLongProperty(this, "time", time) }

    override fun onCommit() {
        this.item = ProcessData(name.get(), timeElapsed.get(), time.get())
    }
}