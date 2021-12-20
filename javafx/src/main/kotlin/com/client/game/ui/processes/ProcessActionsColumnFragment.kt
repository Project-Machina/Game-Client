package com.client.game.ui.processes

import com.client.game.model.processes.ProcessDataModel
import com.client.packets.outgoing.VmCommandMessage
import com.client.scripting.Extensions
import javafx.beans.binding.Bindings
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.disableWhen

class ProcessActionsColumnFragment : Fragment() {

    override val root: AnchorPane by fxml("process-actions-column.fxml")

    val completeBtn: Button by fxid()
    val pauseBtn: Button by fxid()
    val killBtn: Button by fxid()

    fun bind(dataModel: ProcessDataModel) {

        pauseBtn.textProperty().bind(Bindings.createStringBinding({
            if (dataModel.isPaused.get()) "Resume" else "Pause"
        }, dataModel.isPaused))

        completeBtn.disableWhen(Bindings.createBooleanBinding({
            dataModel.timeElapsed.get() < dataModel.time.get() || dataModel.isIndeterminate.get() || dataModel.isPaused.get()
        }, dataModel.time, dataModel.timeElapsed, dataModel.isIndeterminate, dataModel.isPaused))

        pauseBtn.disableWhen(completeBtn.disableProperty().not())

        pauseBtn.setOnAction {
            val session = Extensions.session
            session?.sendMessage(VmCommandMessage("pproc ${dataModel.pid.get()}", false))
        }
        completeBtn.setOnAction {
            val session = Extensions.session
            session?.sendMessage(VmCommandMessage("fproc ${dataModel.pid.get()}", false))
        }
        killBtn.setOnAction {
            val session = Extensions.session
            session?.sendMessage(VmCommandMessage("killproc ${dataModel.pid.get()}", false))
        }
    }

}