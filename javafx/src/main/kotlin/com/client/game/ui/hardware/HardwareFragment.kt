package com.client.game.ui.hardware

import com.client.game.model.hardware.machines.HardwareModel
import com.client.game.ui.hardware.engineering.HardwareEngineerFragment
import com.client.game.ui.hardware.machines.MachinesFragment
import com.client.game.ui.hardware.manager.VirtualMachineManagerFragment
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.disableWhen

class HardwareFragment : Fragment("Hardware") {

    val model: HardwareModel by inject()

    override val root: AnchorPane by fxml("hardware.fxml")

    val hardwareTabs: TabPane by fxid()
    val machinesTab: Tab by fxid()
    val machineManagerTab: Tab by fxid()
    val engineerTab: Tab by fxid()


    init {

        //machineManagerTab.disableWhen(model.selectedVM.isNull)

        machinesTab.contentProperty().set(MachinesFragment().root)
        machineManagerTab.contentProperty().set(VirtualMachineManagerFragment().root)
        engineerTab.contentProperty().set(HardwareEngineerFragment().root)

    }
}