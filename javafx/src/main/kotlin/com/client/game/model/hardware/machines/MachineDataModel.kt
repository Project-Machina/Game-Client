package com.client.game.model.hardware.machines

import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class MachineDataModel(name: String, address: String) : ItemViewModel<VirtualMachineData>(VirtualMachineData(name, address)) {

    val name = bind { SimpleStringProperty(this, "name", name) }
    val address = bind { SimpleStringProperty(this, "address", address) }

    override fun onCommit() {
        item = VirtualMachineData(name.get(), address.get())
    }
}