package com.client.game.model.software

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleMapProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class SoftwareModel : ViewModel() {

    val softwares = bind { SimpleMapProperty<String, SoftwareDataModel>(this, "softwares", FXCollections.observableHashMap()) }

    val isRemote = bind { SimpleBooleanProperty(this, "is_remote", false) }

}