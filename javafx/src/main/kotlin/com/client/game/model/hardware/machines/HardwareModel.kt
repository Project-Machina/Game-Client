package com.client.game.model.hardware.machines

import javafx.beans.property.SimpleMapProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class HardwareModel : ViewModel() {

    val virtualMachines = bind { SimpleMapProperty<String, MachineDataModel>(this, "virtual_machines", FXCollections.observableHashMap()) }
    val selectedVM = bind { SimpleObjectProperty<MachineDataModel>(this, "selected_vm") }

}