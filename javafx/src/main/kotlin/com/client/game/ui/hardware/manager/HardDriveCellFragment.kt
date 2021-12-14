package com.client.game.ui.hardware.manager

import com.client.game.model.hardware.manager.HardDriveDataModel
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.Fieldset
import tornadofx.Fragment

class HardDriveCellFragment : Fragment() {

    override val root: VBox by fxml("hddcell.fxml")

    val driveName: Fieldset by fxid()
    val sizeDesc: Label by fxid()

    fun bind(model: HardDriveDataModel) {
        driveName.textProperty.bind(model.driveName)
        sizeDesc.textProperty().bind(model.sizeDesc)
    }

}