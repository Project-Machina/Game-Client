package com.client.game.model.hardware.machines

import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class HardwareModel : ViewModel() {

    val selectedVM = bind { SimpleObjectProperty<MachineDataModel>(this, "selected_vm") }

}