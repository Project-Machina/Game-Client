package com.client.game.ui.processes

import com.client.game.model.processes.ProcessDataModel
import com.client.game.model.processes.ProcessDialogModel
import com.client.packets.outgoing.VmCommandMessage
import com.client.scripting.Extensions
import javafx.beans.binding.Bindings
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.layout.AnchorPane
import javafx.stage.StageStyle
import tornadofx.Fragment
import tornadofx.onChange
import java.time.Instant
import java.time.ZoneOffset

class ProcessDialogFragment(val onClose: () -> Unit = {}) : Fragment() {

    private val model = ProcessDialogModel()

    override val root: AnchorPane by fxml("process-dialog.fxml")

    val nameLabel: Label by fxid()
    val processBar: ProgressBar by fxid()
    val timeLabel: Label by fxid()
    val cancelBtn: Button by fxid()

    fun bind(data: ProcessDataModel) {
        data.isAutoComplete.set(true)
        nameLabel.textProperty().bind(data.name)

        model.isFinished.bind(data.isComplete)

        processBar.progressProperty().bind(Bindings.createDoubleBinding({
            if(data.timeElapsed.get() < data.time.get() && data.isIndeterminate.get()) {
                (data.time.get().toDouble() / data.timeElapsed.get())
            } else -1.0
        }, data.time, data.timeElapsed, data.isIndeterminate))

        timeLabel.textProperty().bind(Bindings.createStringBinding({
            if(data.timeElapsed.get() < data.time.get() && !data.isIndeterminate.get()) {
                Instant.ofEpochMilli((data.time.get() - data.timeElapsed.get())).atOffset(ZoneOffset.UTC).toLocalDateTime()
                    .format(Extensions.dateTimeFormatter)
            } else {
                if (data.isIndeterminate.get()) "Indefinite" else "Done"
            }
        }, data.time, data.timeElapsed, data.isIndeterminate))

        cancelBtn.setOnAction {
            onClose.invoke()
            data.isAutoComplete.set(false)
            close()
        }

        model.isFinished.onChange {
            if(it && data.isAutoComplete.get()) {
                close()
                val session = Extensions.session
                session?.sendMessage(VmCommandMessage("fproc ${data.pid.get()}", false))
                data.isAutoComplete.set(false)
            }
        }
    }

    companion object {

        fun ProcessDataModel.showProcess(onClose : () -> Unit = {}) {
            val dialog = ProcessDialogFragment(onClose)
            dialog.bind(this)
            dialog.openModal(StageStyle.UNDECORATED, escapeClosesWindow = false, block = true)
        }

    }

}