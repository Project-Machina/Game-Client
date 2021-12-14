package com.client.game.ui.hardware.manager

import javafx.scene.layout.AnchorPane
import tornadofx.Fragment

class VirtualMachineManagerFragment : Fragment() {

    override val root: AnchorPane by fxml("manager.fxml")

    init {

        //hddList.setCellFactory { HardDriveListCell(HardDriveCellFragment()) }

        //hddList.items.add(HardDriveDataModel("Zero-I", "10 MB"))

    }

}