package com.client.game.ui.processes

import com.client.game.model.processes.ProcessDataModel
import com.client.game.model.processes.ProcessesModel
import javafx.scene.Node
import javafx.scene.control.ListView
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment

class ProcessesFragment : Fragment("Processes") {

    val model: ProcessesModel by inject()

    override val root: AnchorPane by fxml("processes.fxml")

    val processList: ListView<ProcessDataModel> by fxid()

    init {

        processList.setCellFactory { ProcessListCell(ProcessContentFragment()) }

        processList.items.add(ProcessDataModel("Test", 1))
        processList.items.add(ProcessDataModel("Test", 1))
        processList.items.add(ProcessDataModel("Test", 1))
        processList.items.add(ProcessDataModel("Test", 1))
        processList.items.add(ProcessDataModel("Test", 1))

    }

}