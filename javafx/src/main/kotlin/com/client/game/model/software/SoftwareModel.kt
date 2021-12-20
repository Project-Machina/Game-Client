package com.client.game.model.software

import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleMapProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class SoftwareModel : ViewModel() {

    val softwares = bind { SimpleMapProperty<String, SoftwareDataModel>(this, "softwares", FXCollections.observableHashMap()) }

    val capacity = bind { SimpleLongProperty(500000) }

}