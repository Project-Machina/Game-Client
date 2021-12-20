package com.client.game.ui.hardware.machines

import com.client.game.formatSize
import com.client.game.model.PreferencesModel
import com.client.game.model.hardware.machines.HardwareModel
import com.client.game.model.hardware.machines.MachineDataModel
import com.client.game.model.player.PlayerStatisticsModel
import com.client.javafx.setHideable
import javafx.beans.binding.Bindings
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import tornadofx.*

class MachinesFragment : Fragment() {

    val hardwareModel: HardwareModel by inject()
    val prefModel: PreferencesModel by di()

    override val root: AnchorPane by fxml("machines.fxml")

    val machineList: ListView<MachineDataModel> by fxid()

    val addressLabel: Label by fxid()
    val linkBtn: Button by fxid()
    val resetBtn: Button by fxid()
    val domainRequestBtn: Button by fxid()
    val domainField: TextField by fxid()

    val domainProtection: ChoiceBox<String> by fxid()

    val coresLabel: Label by fxid()
    val threadsLabel: Label by fxid()
    val cpuWattsLabel: Label by fxid()

    val hddCapLabel: Label by fxid()
    val spaceLabel: Label by fxid()
    val hddWattsLabel: Label by fxid()

    val ramCapLabel: Label by fxid()
    val ramUsageLabel: Label by fxid()
    val ramWattsLabel: Label by fxid()

    val netMbLabel: Label by fxid()
    val netMBLabel: Label by fxid()
    val netWattsLabel: Label by fxid()

    init {

        machineList.setCellFactory { MachineListCell(MachineListCellFragment()) }

        machineList.bindSelected(hardwareModel.selectedVM)

        machineList.items.bind(hardwareModel.virtualMachines) { _, vm -> vm }

        addressLabel.textProperty().setHideable(
            prefModel.LOW_MODE,
            prefModel.MEDIUM_MODE,
            prefModel.HIGH_MODE,
            hardwareModel.selectedVM
        ) { hardwareModel.selectedVM.get()?.address?.get() ?: "" }

        linkBtn.disableWhen {
            Bindings.createBooleanBinding({
                if (hardwareModel.selectedVM.isNotNull.get()) hardwareModel.selectedVM.get().isLinked.get() else false
            }, hardwareModel.selectedVM)
        }

        coresLabel.textProperty().bind(
            Bindings.createStringBinding(
                { if (hardwareModel.selectedVM.isNotNull.get()) (hardwareModel.selectedVM.get().threadCapacity.get() / 2).toString() else "" },
                hardwareModel.selectedVM
            )
        )

        threadsLabel.textProperty().bind(
            Bindings.createStringBinding(
                { if (hardwareModel.selectedVM.isNotNull.get()) (hardwareModel.selectedVM.get().availableThreads.get()).toString() else "" },
                hardwareModel.selectedVM
            )
        )

        cpuWattsLabel.textProperty().bind(
            Bindings.createStringBinding(
                { if (hardwareModel.selectedVM.isNotNull.get()) (hardwareModel.selectedVM.get().mbWatts.get()).toString() else "" },
                hardwareModel.selectedVM
            )
        )
        ramWattsLabel.textProperty().bind(cpuWattsLabel.textProperty())

        hddCapLabel.textProperty().bind(
            Bindings.createStringBinding(
                { if (hardwareModel.selectedVM.isNotNull.get()) formatSize(hardwareModel.selectedVM.get().storageCapacity.get()) else "" },
                hardwareModel.selectedVM
            )
        )

        spaceLabel.textProperty().bind(
            Bindings.createStringBinding(
                { if (hardwareModel.selectedVM.isNotNull.get()) formatSize(hardwareModel.selectedVM.get().availableStorage.get()) else "" },
                hardwareModel.selectedVM
            )
        )

        hddWattsLabel.textProperty().bind(
            Bindings.createStringBinding(
                {
                    if (hardwareModel.selectedVM.isNotNull.get()) hardwareModel.selectedVM.get().rackWatts.get()
                        .toString() else ""
                },
                hardwareModel.selectedVM
            )
        )

        ramCapLabel.textProperty().bind(
            Bindings.createStringBinding(
                { if (hardwareModel.selectedVM.isNotNull.get()) formatSize(hardwareModel.selectedVM.get().ramCapacity.get()) else "" },
                hardwareModel.selectedVM
            )
        )

        ramUsageLabel.textProperty().bind(
            Bindings.createStringBinding(
                { if (hardwareModel.selectedVM.isNotNull.get()) formatSize(hardwareModel.selectedVM.get().availableRam.get()) else "" },
                hardwareModel.selectedVM
            )
        )

        netMbLabel.textProperty().bind(Bindings.createStringBinding({
            if (hardwareModel.selectedVM.isNotNull.get()) "${hardwareModel.selectedVM.get().networkCardCapacity.get()} Mbit/s" else ""
        }, hardwareModel.selectedVM))

        netMBLabel.textProperty().bind(Bindings.createStringBinding({
            if (hardwareModel.selectedVM.isNotNull.get()) "${formatSize((hardwareModel.selectedVM.get().networkCardCapacity.get() * 0.125).toLong())}/s" else ""
        }, hardwareModel.selectedVM))

        netWattsLabel.textProperty().bind(
            Bindings.createStringBinding(
                { if (hardwareModel.selectedVM.isNotNull.get()) hardwareModel.selectedVM.get().networkWatts.get().toString() else "" },
                hardwareModel.selectedVM
            )
        )

        hardwareModel.selectedVM.onChange {
            if(it != null) {
                domainField.text = it.domain.get()
            }
        }

    }

}