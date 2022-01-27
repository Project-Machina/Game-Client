package com.client.game.ui.software.actions

import com.client.game.model.processes.ProcessesModel
import com.client.game.model.software.SoftwareDataModel
import com.client.game.model.software.SoftwareModel
import com.client.packets.outgoing.VmCommandMessage
import com.client.scripting.Extensions
import javafx.beans.binding.Bindings
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.onChange

class HideSoftwareFragment : Fragment() {

    val softwareModel: SoftwareModel by inject()

    override val root: AnchorPane by fxml("hiding-software.fxml")

    val data: SoftwareDataModel = params["data"]!! as SoftwareDataModel
    val isRemote: Boolean = params["isRemote"]!! as Boolean

    val hideBtn: Button by fxid()
    val cancelBtn: Button by fxid()

    val headerLabel: Label by fxid()

    val hiderSoftwares: ChoiceBox<String> by fxid()

    init {

        softwareModel.softwares.value.onChange {
            val items = it.map.values.filter { s -> s.extension.get() == "hdr" && s.pid.get() != -1 }
                .map { s -> s.name.concat(".").concat(s.extension).concat(":").concat(s.version).get() }
            hiderSoftwares.items.setAll(items)
        }

        val items = softwareModel.softwares.values.filter { s -> s.extension.get() == "hdr" && s.pid.get() != -1 }
            .map { s -> s.name.concat(".").concat(s.extension).concat(":").concat(s.version).get() }
        hiderSoftwares.items.setAll(items)

        hideBtn.textProperty().bind(Bindings.format("Hide %s", data.name.concat(".").concat(data.extension)))
        headerLabel.textProperty().bind(Bindings.format("Hide %s", data.name.concat(".").concat(data.extension)))

        cancelBtn.setOnAction { close() }

        hideBtn.setOnAction {
            val session = Extensions.session
            val softwareName = data.name.concat(".").concat(data.extension).get().replace(' ', '_')

            val version = String.format("%.1f", data.version.get())
            val hidderVersion = if (hiderSoftwares.value.isNotEmpty()) {
                hiderSoftwares.value.split(":")[1]
            } else "0.0"
            session?.sendMessage(
                VmCommandMessage(
                    "hide -n $softwareName -v $version --hdr $hidderVersion",
                    isRemote
                )
            )
            close()
        }

    }

}