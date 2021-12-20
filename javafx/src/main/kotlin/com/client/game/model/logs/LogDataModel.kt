package com.client.game.model.logs

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class LogDataModel(val data: LogData) : ItemViewModel<LogData>(data) {

    val id = bind { SimpleIntegerProperty(this, "id", data.id) }
    val time = bind { SimpleLongProperty(this, "time", data.time) }
    val source = bind { SimpleStringProperty(this, "source", data.source) }
    val message = bind { SimpleStringProperty(this, "message", data.message) }

}