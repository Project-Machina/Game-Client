package com.client.game.ui.internet

import com.client.game.formatSize
import com.client.game.model.PreferencesModel
import com.client.game.model.internet.BookmarkDataModel
import com.client.game.model.internet.InternetModel
import com.client.game.model.logs.LogDataModel
import com.client.game.model.software.SoftwareDataModel
import com.client.game.ui.logs.LogActionsFragment
import com.client.game.ui.logs.cells.LogActionsTableCell
import com.client.game.ui.software.SoftwareActionsFragment
import com.client.game.ui.software.SoftwareFragment
import com.client.game.ui.software.SoftwareSizeFragment
import com.client.game.ui.software.SoftwareVersionFragment
import com.client.game.ui.software.cells.SoftwareActionsTableCell
import com.client.game.ui.software.cells.SoftwareSizeTableCell
import com.client.game.ui.software.cells.SoftwareTableRowCell
import com.client.game.ui.software.cells.SoftwareVersionTableCell
import com.client.javafx.fields.AddressField
import com.client.javafx.setHideable
import com.client.packets.outgoing.VmCommandMessage
import com.client.scope.GameScope
import com.client.scripting.Extensions
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXMLLoader
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.bind
import tornadofx.disableWhen
import tornadofx.rowItem
import java.time.Instant
import java.time.ZoneOffset

class InternetFragment : Fragment("Internet") {

    override val scope: GameScope = super.scope as GameScope
    override val root: AnchorPane by fxml("internet.fxml")

    val preferences: PreferencesModel by di()
    val internetModel: InternetModel by di()

    val prefModel: PreferencesModel by di()

    val bookmarks: TreeView<BookmarkDataModel> by fxid()

    val homeTab: Tab by fxid()

    val addressField: AddressField by fxid()

    val connectBtn: Button by fxid()

    val remoteContainer: AnchorPane by fxid()

    /**
     * Remote Login Tab
     */
    val loginTab: Tab by fxid()
    val userField: TextField by fxid()
    val passField: PasswordField by fxid()
    val loginBtn: Button by fxid()

    /**
     * Remote Logs Tab
     */
    val logsTab: Tab by fxid()
    val logsTable: TableView<LogDataModel> by fxid()
    val timeColumn: TableColumn<LogDataModel, String> by fxid()
    val sourceColumn: TableColumn<LogDataModel, String> by fxid()
    val messageColumn: TableColumn<LogDataModel, String> by fxid()
    val actionsColumn: TableColumn<LogDataModel, String> by fxid()

    /**
     * Remote Software Tab
     */
    val softwareTable: TableView<SoftwareDataModel> by fxid()
    val iconColumn: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareName: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareVersion: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareSize: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareActions: TableColumn<SoftwareDataModel, String> by fxid()

    init {

        val bookmarksRoot = TreeItem(BookmarkDataModel("Bookmarks", ""))
        val favorite = TreeItem(BookmarkDataModel("Favorites", ""))
        val history = TreeItem(BookmarkDataModel("History", ""))

        homeTab.contentProperty().bind(internetModel.remoteHomePageNode)
        remoteContainer.disableWhen(internetModel.remoteHomePageNode.isNull)

        bookmarksRoot.isExpanded = true
        bookmarksRoot.children.addAll(favorite, history)

        bookmarks.setCellFactory { BookmarkTreeCell(BookmarkTreeCellFragment()) }
        bookmarks.root = bookmarksRoot

        history.children.add(TreeItem(BookmarkDataModel("Bank", "56.54.11.167")))

        addressField.shownProperty.bind(prefModel.MEDIUM_MODE.or(prefModel.HIGH_MODE))

        connectBtn.setOnAction {
            val address = addressField.text
            scope.session?.sendMessage(VmCommandMessage("connect $address", false))

            history.children.add(0, TreeItem(BookmarkDataModel(address, address)))
        }

        initRemoteLogin()
        initRemoteLogs()
        initRemoteSoftware()
    }

    private fun initRemoteLogin() {

    }

    private fun initRemoteSoftware() {
        val softFrag = find<SoftwareFragment>()
        softwareTable.setRowFactory { SoftwareTableRowCell() }
        softwareTable.items.bind(internetModel.softwares) { _, v -> v }

        softwareTable.sortOrder.setAll(softwareName, softwareSize, softwareVersion)

        iconColumn.setCellValueFactory { SimpleObjectProperty("mock") }
        iconColumn.setCellFactory {
            object : TableCell<SoftwareDataModel, String>() {
                override fun updateItem(item: String?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (item != null && !empty) {
                        graphic = softFrag.getGameIcon(rowItem.extension.get(), rowItem.installed)
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
    }


    private fun initRemoteLogs() {

        logsTable.itemsProperty().bind(internetModel.systemLogs)
        logsTable.sortOrder.setAll(timeColumn, sourceColumn, messageColumn)
        timeColumn.setCellValueFactory {
            SimpleStringProperty(
                Instant.ofEpochSecond(it.value.time.get()).atOffset(ZoneOffset.UTC).toLocalDateTime()
                .format(Extensions.dateTimeFormatter))
        }

        sourceColumn.setCellValueFactory { it.value.source }
        messageColumn.setCellValueFactory { it.value.message }

        actionsColumn.setCellFactory { LogActionsTableCell(LogActionsFragment()) }
        actionsColumn.setCellValueFactory { SimpleStringProperty("mock") }

    }
}