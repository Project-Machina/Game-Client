package com.client.game.model.hackeddb

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ItemViewModel

class HackedMachineDataModel(val data: HackedMachineData) : ItemViewModel<HackedMachineData>(data) {

    val address = bind { SimpleStringProperty(this, "address", data.address) }

    val accounts = bind { SimpleListProperty(this, "accounts", FXCollections.observableArrayList(data.accounts)) }

    val malware = bind { SimpleListProperty(this, "malware", FXCollections.observableArrayList(data.malware)) }

}