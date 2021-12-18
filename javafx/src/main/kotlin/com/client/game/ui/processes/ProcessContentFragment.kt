package com.client.game.ui.processes

import com.client.game.model.PreferencesModel
import com.client.game.model.processes.ProcessDataModel
import com.client.javafx.setHideable
import com.client.scripting.Extensions
import javafx.beans.binding.Bindings
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import tornadofx.Fragment
import java.time.Instant
import java.time.ZoneOffset

class ProcessContentFragment : Fragment() {

    private val preferences: PreferencesModel by di()

    override val root: Parent by fxml("process-content.fxml")

    val processName: Label by fxid()
    val progressTime: Label by fxid()
    val processProgress: ProgressBar by fxid()
    val completeBtn: Button by fxid()

    fun bind(data: ProcessDataModel) {
        processName.textProperty().setHideable(preferences.MEDIUM_MODE, preferences.HIGH_MODE) { data.name.get() }
        processProgress.progressProperty().bind(Bindings.createDoubleBinding(
            { (data.timeElapsed.get() / data.time.get().toDouble()) },
            data.timeElapsed, data.time
        ))
        progressTime.textProperty().bind(Bindings.createStringBinding({
            Instant.ofEpochMilli((data.time.get() - data.timeElapsed.get())).atOffset(ZoneOffset.UTC).toLocalDateTime()
                .format(Extensions.dateTimeFormatter)
        }, data.timeElapsed, data.time))
    }
}