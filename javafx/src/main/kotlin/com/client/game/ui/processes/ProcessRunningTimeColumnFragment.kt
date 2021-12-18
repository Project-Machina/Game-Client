package com.client.game.ui.processes

import com.client.game.model.processes.ProcessDataModel
import com.client.scripting.Extensions
import javafx.beans.binding.Bindings
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import java.time.Instant
import java.time.ZoneOffset

class ProcessRunningTimeColumnFragment : Fragment() {

    override val root: AnchorPane by fxml("process-time-column.fxml")

    val timeLabel: Label by fxid()

    fun bind(data: ProcessDataModel) {
        timeLabel.textProperty().bind(Bindings.createStringBinding({
            Instant.ofEpochMilli(data.time.get()).atOffset(ZoneOffset.UTC).toLocalDateTime()
                .format(Extensions.dateTimeFormatter)
        }, data.time))
    }

}