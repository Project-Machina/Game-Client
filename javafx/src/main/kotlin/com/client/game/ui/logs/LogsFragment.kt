package com.client.game.ui.logs

import com.client.game.model.logs.LogData
import com.client.game.model.logs.LogDataModel
import com.client.game.model.logs.LogsModel
import com.client.scripting.Extensions.dateTimeFormatter
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.bind
import tornadofx.compareTo
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class LogsFragment : Fragment() {

    val logModel: LogsModel by inject()

    override val root: AnchorPane by fxml("logs.fxml")

    val logTable: TableView<LogDataModel> by fxid()

    val timeColumn: TableColumn<LogDataModel, String> by fxid()
    val sourceColumn: TableColumn<LogDataModel, String> by fxid()
    val messageColumn: TableColumn<LogDataModel, String> by fxid()
    val actionColumn: TableColumn<LogDataModel, String> by fxid()

    init {
        logTable.itemsProperty().bind(logModel.systemLogs)
        logTable.sortOrder.setAll(timeColumn, sourceColumn, messageColumn)
        timeColumn.setCellValueFactory {
            SimpleStringProperty(Instant.ofEpochSecond(it.value.time.get()).atOffset(ZoneOffset.UTC).toLocalDateTime()
                .format(dateTimeFormatter))
        }

        sourceColumn.setCellValueFactory { it.value.source }
        messageColumn.setCellValueFactory { it.value.message }
        sourceColumn.setCellFactory { TextFieldTableCell() }
        messageColumn.setCellFactory { TextFieldTableCell() }
    }

}