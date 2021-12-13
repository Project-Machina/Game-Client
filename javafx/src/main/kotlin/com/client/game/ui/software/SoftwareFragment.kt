package com.client.game.ui.software

import com.client.game.formatSize
import com.client.game.model.PreferencesModel
import com.client.game.model.software.SoftwareData
import com.client.game.model.software.SoftwareModel
import com.client.javafx.setHideable
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.Node
import javafx.scene.chart.PieChart
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.observableListOf
import tornadofx.onChange

class SoftwareFragment : Fragment("Software") {

    val preferences: PreferencesModel by inject()
    val model: SoftwareModel by inject()

    override val root: AnchorPane by fxml("software.fxml")

    val storageCapacity: Label by fxid()

    val softwareTable: TableView<SoftwareData> by fxid()
    val softwareName: TableColumn<SoftwareData, String> by fxid()
    val softwareExtension: TableColumn<SoftwareData, String> by fxid()
    val softwareVersion: TableColumn<SoftwareData, String> by fxid()
    val softwareSize: TableColumn<SoftwareData, String> by fxid()
    val softwareActions: TableColumn<SoftwareData, Node> by fxid()

    val storageChart: PieChart by fxid()

    init {

        storageCapacity.textProperty().setHideable(preferences.HIGH_MODE) { formatSize(model.capacity.get()) }

        softwareName.setCellValueFactory {
            val prop = SimpleStringProperty()
            prop.setHideable({
                it.value.name
            }, preferences.HIGH_MODE, preferences.SOFTWARE_NAME_SUB_MODE)
            prop
        }
        softwareExtension.setCellValueFactory {
            val prop = SimpleStringProperty()
            prop.setHideable({
                it.value.extension
            }, preferences.HIGH_MODE, preferences.SOFTWARE_EXTENSION_SUB_MODE)
            prop
        }
        softwareSize.setCellValueFactory {
            val prop = SimpleStringProperty()
            prop.setHideable({
                formatSize(it.value.size)
            }, preferences.HIGH_MODE, preferences.SOFTWARE_VERSION_SUB_MODE)
            prop
        }
        softwareVersion.setCellValueFactory {
            val prop = SimpleStringProperty()
            prop.setHideable({
                it.value.version.toString()
            }, preferences.HIGH_MODE, preferences.SOFTWARE_VERSION_SUB_MODE)
            prop
        }
        softwareActions.setCellValueFactory { SimpleObjectProperty(Label("test")) }

        softwareTable.items.onChange {
            //Possible overflow!
            val usage = it.list.sumOf { s -> s.size }
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

        softwareTable.items.add(SoftwareData("Cracker", "crc", 1.0, 8500uL))
        softwareTable.items.add(SoftwareData("Hasher", "hash", 1.0, 4500uL))
    }

}