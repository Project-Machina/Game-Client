package com.client.game.ui.processes

import com.client.game.model.processes.ProcessDataModel
import com.client.game.model.processes.ProcessesModel
import javafx.scene.control.ListView
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.bind

class ProcessesFragment : Fragment("Processes") {

    val model: ProcessesModel by di()

    override val root: AnchorPane by fxml("processes.fxml")

    val processList: ListView<ProcessDataModel> by fxid()

    init {

        processList.setCellFactory { ProcessListCell(ProcessContentFragment()) }

        processList.items.bind(model.processes) { _, pc -> pc }
    }

}