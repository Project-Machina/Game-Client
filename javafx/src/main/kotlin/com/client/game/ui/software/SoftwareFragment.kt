package com.client.game.ui.software

import com.client.game.formatSize
import com.client.game.model.PreferencesModel
import com.client.game.model.player.PlayerStatisticsModel
import com.client.game.model.software.SoftwareData
import com.client.game.model.software.SoftwareDataModel
import com.client.game.model.software.SoftwareModel
import com.client.game.ui.gameframe.GameFrameView
import com.client.game.ui.software.cells.SoftwareActionsTableCell
import com.client.game.ui.software.cells.SoftwareSizeTableCell
import com.client.game.ui.software.cells.SoftwareTableRowCell
import com.client.game.ui.software.cells.SoftwareVersionTableCell
import com.client.javafx.nodes.GameIcon
import com.client.javafx.setHideable
import io.ktor.util.*
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import javafx.scene.chart.PieChart
import javafx.scene.control.Label
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.AnchorPane
import tornadofx.*

class SoftwareFragment : Fragment("Software") {

    val preferences: PreferencesModel by di()
    val playerStats: PlayerStatisticsModel by di()
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
    val bugIcon: GameIcon by fxid()
    val fwlIcon: GameIcon by fxid()
    val fhwlIcon: GameIcon by fxid()
    val installedBugIcon: GameIcon by fxid()
    val seekIcon: GameIcon by fxid()
    val hideIcon: GameIcon by fxid()
    val exploitIcon: GameIcon by fxid()
    val elog: GameIcon by fxid()
    val dlog: GameIcon by fxid()

    init {
        storageCapacity.textProperty()
            .setHideable(preferences.HIGH_MODE) { formatSize(playerStats.availableDiskSpace.get()) }

        softwareTable.setRowFactory { SoftwareTableRowCell() }
        softwareTable.items.bind(model.softwares) { _, v -> v }

        softwareTable.sortOrder.setAll(softwareName, softwareSize, softwareVersion)

        iconColumn.setCellValueFactory { SimpleObjectProperty("mock") }
        iconColumn.setCellFactory {
            object : TableCell<SoftwareDataModel, String>() {
                override fun updateItem(item: String?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (item != null && !empty) {
                        graphic = getGameIcon(rowItem.extension.get(), rowItem.installed)
                        text = null
                    } else {
                        text = null
                        graphic = null
                    }
                }
            }
        }

        softwareName.setCellValueFactory {
            val prop = SimpleStringProperty()
            prop.setHideable({
                if (preferences.SOFTWARE_EXTENSION_SUB_MODE.get()) {
                    it.value.name.get()
                } else {
                    "${it.value.name.get()}.${it.value.extension.get()}"
                }
            }, preferences.HIGH_MODE, preferences.SOFTWARE_NAME_SUB_MODE, preferences.SOFTWARE_EXTENSION_SUB_MODE)
            prop
        }
        softwareSize.setCellValueFactory { SimpleStringProperty(formatSize(it.value.size.get())) }
        softwareSize.setCellFactory { SoftwareSizeTableCell(SoftwareSizeFragment()) }

        softwareVersion.setCellValueFactory { SimpleStringProperty(String.format("%.1f", it.value.version.get())) }
        softwareVersion.setCellFactory { SoftwareVersionTableCell(SoftwareVersionFragment()) }

        softwareActions.setCellValueFactory { SimpleStringProperty("mock") }
        softwareActions.setCellFactory { SoftwareActionsTableCell(SoftwareActionsFragment()) }

        calculateStoragePieChart(softwareTable.items)
        softwareTable.items.onChange {
            calculateStoragePieChart(it.list)
        }
        storageChart.labelsVisible = false
    }

    private fun calculateStoragePieChart(list: List<SoftwareDataModel>) {
        //Possible overflow!
        val usage = list.sumOf { s -> s.size.get() }
        val capacity = playerStats.availableDiskSpace.get()
        val freeSpace = capacity - usage

        val usageData = PieChart.Data("Usage ${formatSize(usage)}", usage.toDouble())
        usageData.nameProperty().setHideable(preferences.HIGH_MODE) { "Usage ${formatSize(usage)}" }
        val freeSpaceData = PieChart.Data("Free Space ${formatSize(freeSpace)}", freeSpace.toDouble())
        freeSpaceData.nameProperty().setHideable(preferences.HIGH_MODE) { "Free Space ${formatSize(freeSpace)}" }

        if (storageChart.data.isEmpty()) {
            storageChart.data = observableListOf(usageData, freeSpaceData)
        } else {
            storageChart.data[0] = usageData
            storageChart.data[1] = freeSpaceData
        }
    }

    fun getGameIcon(ext: String, installed: Boolean = false): GameIcon {
        if(ext[0] == 'v' && installed) {
            return installedBugIcon.copy()
        }
        if (ext[0] == 'v') {
            return bugIcon.copy()
        }
        if(ext[0] == 'x') {
            return exploitIcon.copy()
        }
        return when (ext) {
            "crc" -> crcIcon.copy()
            "hash" -> hashIcon.copy()
            "av" -> avIcon.copy()
            "fhwl" -> fhwlIcon.copy()
            "fwl" -> fwlIcon.copy()
            "skr" -> seekIcon.copy()
            "hdr" -> hideIcon.copy()
            "elog" -> elog.copy()
            "dlog" -> dlog.copy()
            else -> GameIcon()
        }
    }

}