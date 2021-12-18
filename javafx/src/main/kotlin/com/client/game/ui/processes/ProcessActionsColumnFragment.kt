package com.client.game.ui.processes

import com.client.game.model.processes.ProcessDataModel
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment

class ProcessActionsColumnFragment : Fragment() {

    override val root: AnchorPane by fxml("process-actions-column.fxml")

    fun bind(dataModel: ProcessDataModel) {

    }

}