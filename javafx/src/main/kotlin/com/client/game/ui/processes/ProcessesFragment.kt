package com.client.game.ui.processes

import com.client.game.model.processes.ProcessData
import com.client.game.model.processes.ProcessDataModel
import com.client.game.model.processes.ProcessesModel
import com.client.game.ui.processes.cells.ProcessActionsColumnCell
import com.client.game.ui.processes.cells.ProcessNameColumnCell
import com.client.game.ui.processes.cells.ProcessProgressColumnCell
import com.client.game.ui.processes.cells.ProcessRunningTimeColumnCell
import com.client.scripting.Extensions.dateTimeFormatter
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.ListView
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import tornadofx.*
import java.time.Instant
import java.time.ZoneOffset

class ProcessesFragment : Fragment("Processes") {

    val model: ProcessesModel by di()

    override val root: AnchorPane by fxml("processes.fxml")

    val processTable: TableView<ProcessDataModel> by fxid()

    val processName: TableColumn<ProcessDataModel, String> by fxid()

    val processBar: TableColumn<ProcessDataModel, String> by fxid()

    val processRunningTime: TableColumn<ProcessDataModel, String> by fxid()

    val processActions: TableColumn<ProcessDataModel, String> by fxid()

    init {

        processTable.items.bind(model.processes) { _, pc -> pc }

        processName.setCellValueFactory { it.value.name }
        processName.setCellFactory { ProcessNameColumnCell(ProcessNameColumnFragment()) }

        processBar.setCellValueFactory { SimpleStringProperty("mock") }
        processBar.setCellFactory { ProcessProgressColumnCell(ProcessProgressColumnFragment()) }

        processRunningTime.setCellValueFactory {
            SimpleStringProperty(
                Instant.ofEpochMilli(it.value.time.get()).atOffset(ZoneOffset.UTC).toLocalDateTime()
                    .format(dateTimeFormatter)
            )
        }
        processRunningTime.setCellFactory { ProcessRunningTimeColumnCell(ProcessRunningTimeColumnFragment()) }

        processActions.setCellValueFactory { SimpleStringProperty("mock") }
        processActions.setCellFactory { ProcessActionsColumnCell(ProcessActionsColumnFragment()) }

    }

}