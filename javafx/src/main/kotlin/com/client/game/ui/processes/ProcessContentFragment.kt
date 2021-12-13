package com.client.game.ui.processes

import com.client.game.model.PreferencesModel
import com.client.game.model.processes.ProcessDataModel
import com.client.javafx.setHideable
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import tornadofx.Fragment

class ProcessContentFragment : Fragment() {

    val preferences: PreferencesModel by inject()

    override val root: Parent by fxml("process-content.fxml")

    val processName: Label by fxid()
    val processProgress: ProgressBar by fxid()
    val completeBtn: Button by fxid()

    fun bind(data: ProcessDataModel) {
        processName.textProperty().setHideable(preferences.MEDIUM_MODE, preferences.HIGH_MODE) { data.name.get() }
    }
}