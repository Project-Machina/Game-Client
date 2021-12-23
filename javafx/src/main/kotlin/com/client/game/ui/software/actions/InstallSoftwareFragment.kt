package com.client.game.ui.software.actions

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

class InstallSoftwareFragment : Fragment() {

    val softwareModel: SoftwareModel by inject()

    override val root: AnchorPane by fxml("install-software.fxml")

    val data: SoftwareDataModel = params["data"]!! as SoftwareDataModel
    val isRemote: Boolean = params["isRemote"]!! as Boolean

    val installBtn: Button by fxid()
    val cancelBtn: Button by fxid()

    val headerLabel: Label by fxid()

    val remSoftwares: ChoiceBox<String> by fxid()

    init {

        installBtn.textProperty().bind(Bindings.format("Install %s", data.name.concat(".").concat(data.extension)))
        headerLabel.textProperty().bind(Bindings.format("Install %s", data.name.concat(".").concat(data.extension)))

        cancelBtn.setOnAction { close() }

        installBtn.setOnAction {
            val session = Extensions.session
            val softwareName = data.name.concat(".").concat(data.extension).get().replace(' ', '_')

            val remSoft = softwareModel.softwares.values.find {
                remSoftwares.value == it.name.concat(".").concat(it.extension).get()
            }
            if(remSoft != null) {
                val remSoftName = remSoft.name.concat(".").concat(remSoft.extension).get().replace(' ', '_')
                session?.sendMessage(VmCommandMessage("install -n $softwareName -v ${String.format("%.1f", data.version.get())} -e $remSoftName -V ${remSoft.version.get()}", isRemote))
                close()
            } else {
                session?.sendMessage(VmCommandMessage("install -n $softwareName -v ${String.format("%.1f", data.version.get())}", isRemote))
                close()
            }
        }

    }

}