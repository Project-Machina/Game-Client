package com.client.game.ui.logs.actions

import com.client.game.model.logs.LogDataModel
import com.client.game.model.processes.ProcessesModel
import com.client.game.model.software.SoftwareModel
import com.client.packets.outgoing.VmCommandMessage
import com.client.scripting.Extensions
import javafx.beans.binding.Bindings
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.toObservable

class HideLogFragment : Fragment() {

    val softwareModel: SoftwareModel by inject()

    override val root: AnchorPane by fxml("hiding-log.fxml")

    val data: LogDataModel = params["data"]!! as LogDataModel
    val isRemote: Boolean = params["isRemote"]!! as Boolean

    val hiderSoftware: ChoiceBox<String> by fxid()
    val hideBtn: Button by fxid()
    val cancelBtn: Button by fxid()

    init {

        hiderSoftware.itemsProperty().bind(Bindings.createObjectBinding({
            softwareModel.softwares.values.filter { it.extension.get() == "hdr" && it.pid.get() != -1 }
                .map { it.name.concat(".").concat(it.extension).concat(":").concat(it.version).get() }.toObservable()
        }, softwareModel.softwares))

        hideBtn.setOnAction {
            val hiderSoftware = hiderSoftware.value

            if(hiderSoftware.isNotEmpty()) {
                val version = hiderSoftware.split(":")[1]
                val session = Extensions.session
                session?.sendMessage(VmCommandMessage("hidelg -l ${data.id.get()} --hdr $version", isRemote))
            }
            close()
        }

        cancelBtn.setOnAction { close() }

    }

}