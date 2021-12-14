package com.client.game.ui.hardware.machines

import com.client.game.model.hardware.machines.MachineDataModel
import javafx.scene.control.Label
import tornadofx.Form
import tornadofx.Fragment

class MachineListCellFragment : Fragment() {

    override val root: Form by fxml("machine-cell.fxml")

    val name: Label by fxid()
    val address: Label by fxid()

    fun bind(model: MachineDataModel) {
        name.textProperty().bind(model.name)
        address.textProperty().bind(model.address)
    }

}