package com.client.game.model.logs

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.transformation.SortedList
import tornadofx.ViewModel
import tornadofx.compareTo

class LogsModel : ViewModel() {

    val logs = FXCollections.observableArrayList<LogDataModel>()
    val systemLogs = SimpleObjectProperty(SortedList(logs) { o1, o2 ->
        if (o1.time < o2.time)
            return@SortedList 1
        else if (o1.time > o2.time)
            return@SortedList 0
        return@SortedList -1
    })

}