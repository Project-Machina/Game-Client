package com.client.game.model.processes

import javafx.beans.property.SimpleMapProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class ProcessesModel : ViewModel() {

    val processes = bind { SimpleMapProperty<Int, ProcessDataModel>(this, "processes", FXCollections.observableHashMap()) }

}