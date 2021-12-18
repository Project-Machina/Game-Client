package com.client.game.ui.processes

import com.client.game.model.processes.ProcessDataModel
import com.client.scripting.Extensions
import javafx.beans.binding.Bindings
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.compareTo
import java.time.Instant
import java.time.ZoneOffset

class ProcessProgressColumnFragment : Fragment() {

    override val root: AnchorPane by fxml("process-progress-column.fxml")

    val processBar: ProgressBar by fxid()
    val progressTime: Label by fxid()

    fun bind(data: ProcessDataModel) {

        processBar.progressProperty().bind(Bindings.createDoubleBinding({
            if(data.timeElapsed.get() < data.time.get()) {
                (data.time.get().toDouble() / data.timeElapsed.get())
            } else -1.0
        }, data.time, data.timeElapsed))
        progressTime.textProperty().bind(Bindings.createStringBinding({
            if(data.timeElapsed.get() < data.time.get()) {
                Instant.ofEpochMilli((data.time.get() - data.timeElapsed.get())).atOffset(ZoneOffset.UTC).toLocalDateTime()
                    .format(Extensions.dateTimeFormatter)
            } else {
                "Done"
            }
        }, data.time, data.timeElapsed))

    }

}