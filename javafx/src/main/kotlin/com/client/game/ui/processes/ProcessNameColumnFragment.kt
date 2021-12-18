package com.client.game.ui.processes

import com.client.game.model.processes.ProcessDataModel
import javafx.scene.control.Labeled
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment

class ProcessNameColumnFragment : Fragment() {

    override val root: AnchorPane by fxml("process-name-column.fxml")

    val processName: Labeled by fxid()

    fun bind(data: ProcessDataModel) {
        processName.textProperty().bind(data.name)
    }

}