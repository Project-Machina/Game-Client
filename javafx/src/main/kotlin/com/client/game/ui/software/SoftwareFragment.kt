package com.client.game.ui.software

import com.client.game.formatSize
import com.client.game.model.PreferencesModel
import com.client.game.model.software.SoftwareData
import com.client.game.model.software.SoftwareDataModel
import com.client.game.model.software.SoftwareModel
import com.client.game.ui.software.cells.SoftwareActionsTableCell
import com.client.game.ui.software.cells.SoftwareVersionTableCell
import com.client.javafx.nodes.GameIcon
import com.client.javafx.setHideable
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.chart.PieChart
import javafx.scene.control.Label
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.AnchorPane
import tornadofx.*

class SoftwareFragment : Fragment("Software") {

    val preferences: PreferencesModel by di()
    val model: SoftwareModel by inject()

    override val root: AnchorPane by fxml("software.fxml")

    val storageCapacity: Label by fxid()

    val softwareTable: TableView<SoftwareDataModel> by fxid()
    val softwareName: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareVersion: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareSize: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareActions: TableColumn<SoftwareDataModel, String> by fxid()
    val iconColumn: TableColumn<SoftwareDataModel, String> by fxid()

    val storageChart: PieChart by fxid()

    val crcIcon: GameIcon by fxid()
    val hashIcon: GameIcon by fxid()
    val avIcon: GameIcon by fxid()

    init {

        storageCapacity.textProperty().setHideable(preferences.HIGH_MODE) { formatSize(model.capacity.get()) }

        iconColumn.setCellValueFactory { SimpleObjectProperty("mock") }
        iconColumn.setCellFactory { object : TableCell<SoftwareDataModel, String>() {
            override fun updateItem(item: String?, empty: Boolean) {
                super.updateItem(item, empty)
                if(item != null && !empty) {
                    graphic = getGameIcon(rowItem.extension.get())
                    text = null
                } else {
                    text = null
                    graphic = null
                }
            }
        } }

        softwareName.setCellValueFactory {
            val prop = SimpleStringProperty()
            prop.setHideable({
                if(preferences.SOFTWARE_EXTENSION_SUB_MODE.get()) {
                    it.value.name.get()
                } else {
                    "${it.value.name.get()}.${it.value.extension.get()}"
                }
            }, preferences.HIGH_MODE, preferences.SOFTWARE_NAME_SUB_MODE, preferences.SOFTWARE_EXTENSION_SUB_MODE)
            prop
        }
        softwareSize.setCellValueFactory {
            val prop = SimpleStringProperty()
            prop.setHideable({
                formatSize(it.value.size.get())
            }, preferences.HIGH_MODE, preferences.SOFTWARE_VERSION_SUB_MODE)
            prop
        }
        softwareVersion.setCellValueFactory { SimpleStringProperty("mock") }
        softwareVersion.setCellFactory { SoftwareVersionTableCell(SoftwareVersionFragment()) }

        softwareActions.setCellValueFactory { SimpleStringProperty("mock") }
        softwareActions.setCellFactory { SoftwareActionsTableCell(SoftwareActionsFragment()) }

        softwareTable.items.onChange {
            //Possible overflow!
            val usage = it.list.sumOf { s -> s.size.get() }
            val capacity = model.capacity.get()
            val freeSpace = capacity - usage

            val usageData = PieChart.Data("Usage ${formatSize(usage)}", usage.toDouble())
            usageData.nameProperty().setHideable(preferences.HIGH_MODE) { "Usage ${formatSize(usage)}" }
            val freeSpaceData = PieChart.Data("Free Space ${formatSize(freeSpace)}", freeSpace.toDouble())
            freeSpaceData.nameProperty().setHideable(preferences.HIGH_MODE) { "Free Space ${formatSize(freeSpace)}" }

            if(storageChart.data.isEmpty()) {
                storageChart.data = observableListOf(usageData, freeSpaceData)
            } else {
                storageChart.data[0] = usageData
                storageChart.data[1] = freeSpaceData
            }
        }
        storageChart.labelsVisible = false

        softwareTable.items.add(SoftwareDataModel(SoftwareData("Cracker", "crc", 1.0, 8500uL)))
        softwareTable.items.add(SoftwareDataModel(SoftwareData("Hasher", "hash", 5.5, 4500uL)))
        softwareTable.items.add(SoftwareDataModel(SoftwareData("Super Anti-Bug", "av", 28.4, 4500uL)))
        softwareTable.items.add(SoftwareDataModel(SoftwareData("Old Man Crack", "crc", 78.6, 4500uL)))
    }

    private fun getGameIcon(ext: String) : GameIcon {
        return when(ext) {
            "crc" -> crcIcon.copy()
            "hash" -> hashIcon.copy()
            "av" -> avIcon.copy()
            else -> GameIcon()
        }
    }

}