package com.client.game.model.processes

import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.ViewModel

class ProcessesModel : ViewModel() {

    val processes = bind { SimpleListProperty<ObservableList<ProcessDataModel>>(this, "processes", FXCollections.observableArrayList()) }

}