package com.client.game.model.logs

import javafx.beans.property.SimpleMapProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class LogsModel : ViewModel() {

    val logs = bind { SimpleMapProperty<Int, LogDataModel>(this, "logs", FXCollections.observableHashMap()) }

}