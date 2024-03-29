package com.client.game.ui.logs

import com.client.game.model.logs.LogDataModel
import com.client.game.model.logs.LogsModel
import com.client.game.ui.logs.cells.LogActionsTableCell
import com.client.packets.outgoing.VmCommandMessage
import com.client.scripting.Extensions
import com.client.scripting.Extensions.dateTimeFormatter
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import java.time.Instant
import java.time.ZoneOffset

class LogsFragment : Fragment() {

    val logModel: LogsModel by inject()

    override val root: AnchorPane by fxml("logs.fxml")

    val logTable: TableView<LogDataModel> by fxid()

    val timeColumn: TableColumn<LogDataModel, String> by fxid()
    val sourceColumn: TableColumn<LogDataModel, String> by fxid()
    val messageColumn: TableColumn<LogDataModel, String> by fxid()
    val actionColumn: TableColumn<LogDataModel, String> by fxid()
    val clearLogsBtn: Button by fxid()

    init {
        logModel.systemLogs.get().comparatorProperty().bind(logTable.comparatorProperty())
        logTable.itemsProperty().bind(logModel.systemLogs)
        logTable.sortOrder.setAll(timeColumn, sourceColumn, messageColumn)
        timeColumn.setCellValueFactory {
            SimpleStringProperty(Instant.ofEpochSecond(it.value.time.get()).atOffset(ZoneOffset.UTC).toLocalDateTime()
                .format(dateTimeFormatter))
        }

        sourceColumn.setCellValueFactory { it.value.source }
        messageColumn.setCellValueFactory { it.value.message }

        actionColumn.setCellFactory { LogActionsTableCell(LogActionsFragment()) }
        actionColumn.setCellValueFactory { SimpleStringProperty("mock") }

        clearLogsBtn.setOnAction {
            val session = Extensions.session
            session?.sendMessage(VmCommandMessage("lgcls", false))
        }
    }

}