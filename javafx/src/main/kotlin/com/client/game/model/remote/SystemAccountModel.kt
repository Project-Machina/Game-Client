package com.client.game.model.remote

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class SystemAccountModel : ViewModel() {

    val username = bind { SimpleStringProperty(this, "username") }
    val permissions = bind { SimpleListProperty<String>(this, "permissions", FXCollections.observableArrayList()) }

}