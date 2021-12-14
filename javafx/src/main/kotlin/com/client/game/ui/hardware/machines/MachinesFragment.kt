package com.client.game.ui.hardware.machines

import com.client.game.model.hardware.machines.MachineDataModel
import javafx.scene.control.ListView
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment

class MachinesFragment : Fragment() {

    override val root: AnchorPane by fxml("machines.fxml")

    val machineList: ListView<MachineDataModel> by fxid()

    init {

        machineList.setCellFactory { MachineListCell(MachineListCellFragment()) }

        machineList.items.add(MachineDataModel("Test", "127.0.0.1"))
    }

}