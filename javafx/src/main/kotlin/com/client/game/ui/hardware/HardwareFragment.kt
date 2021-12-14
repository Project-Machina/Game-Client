package com.client.game.ui.hardware

import com.client.game.ui.hardware.machines.MachinesFragment
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment

class HardwareFragment : Fragment("Hardware") {
    override val root: AnchorPane by fxml("hardware.fxml")

    val hardwareTabs: TabPane by fxid()
    val machinesTab: Tab by fxid()
    val machineManagerTab: Tab by fxid()
    val internetTab: Tab by fxid()


    init {

        machinesTab.content = MachinesFragment().root

    }
}