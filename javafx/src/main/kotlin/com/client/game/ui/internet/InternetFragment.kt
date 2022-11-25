package com.client.game.ui.internet

import com.client.game.formatSize
import com.client.game.model.ParameterModel
import com.client.game.model.PreferencesModel
import com.client.game.model.animations.AnimationModel
import com.client.game.model.gameframe.GameFrameModel
import com.client.game.model.internet.BookmarkDataModel
import com.client.game.model.internet.InternetModel
import com.client.game.model.logs.LogDataModel
import com.client.game.model.remote.SystemAccountModel
import com.client.game.model.software.SoftwareDataModel
import com.client.game.model.software.SoftwareModel
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
import com.client.scripting.GameScriptActions
import com.client.scripting.generatePassword
import com.sun.javafx.webkit.WebConsoleListener
import javafx.animation.Animation
import javafx.animation.Timeline
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.concurrent.Worker
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.scene.web.WebView
import javafx.util.Duration
import netscape.javascript.JSObject
import tornadofx.*
import java.time.Instant
import java.time.ZoneOffset


class InternetFragment : Fragment("Internet") {

    override val scope: GameScope = super.scope as GameScope
    override val root: AnchorPane by fxml("internet.fxml")

    val preferences: PreferencesModel by di()
    val gameFrameModel: GameFrameModel by inject()
    val accountModel: SystemAccountModel by di()
    val internetModel: InternetModel by di()
    val animModel: AnimationModel by di()
    val paramModel: ParameterModel by di()
    val softwareModel: SoftwareModel by di()

    val prefModel: PreferencesModel by di()

    val bookmarks: TreeView<BookmarkDataModel> by fxid()

    val homeTab: Tab by fxid()

    val addressField: AddressField by fxid()

    val connectBtn: Button by fxid()

    val remoteContainer: AnchorPane by fxid()

    val uploadBox: ComboBox<SoftwareDataModel> by fxid()

    val uploadBtn: Button by fxid()

    /**
     * Remote Login Tab
     */
    val loginTab: Tab by fxid()
    val userField: TextField by fxid()
    val passField: PasswordField by fxid()
    val loginBtn: Button by fxid()
    val bruteBtn: Button by fxid()
    val exploitBtn: Button by fxid()
    val logoutBtn: Button by fxid()

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
    val softTab: Tab by fxid()
    val softwareTable: TableView<SoftwareDataModel> by fxid()
    val iconColumn: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareName: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareVersion: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareSize: TableColumn<SoftwareDataModel, String> by fxid()
    val softwareActions: TableColumn<SoftwareDataModel, String> by fxid()

    init {

        val ftpBinding = Bindings.createBooleanBinding({
            accountModel.permissions.contains("ftp")
        }, accountModel.permissions)

        uploadBox.enableWhen(ftpBinding)
        uploadBtn.enableWhen(ftpBinding.and(uploadBox.selectionModel.selectedItemProperty().isNotNull))
        uploadBox.cellFormat {
            text = it.name
                .concat(".")
                .concat(it.extension)
                .concat(" - ")
                .concat(it.version)
                .get()
        }
        uploadBox.items.bind(softwareModel.softwares) { _, v -> v }

        uploadBtn.setOnAction {
            val soft = uploadBox.selectedItem
            if(soft != null) {
                val name = soft.name.concat(".").concat(soft.extension).get().replace(' ', '_')
                Extensions.session?.sendMessage(
                    VmCommandMessage("upload -n $name -v ${soft.version.get()}", true)
                )
            }
        }

        val bookmarksRoot = TreeItem(BookmarkDataModel("Bookmarks", ""))
        val favorite = TreeItem(BookmarkDataModel("Favorites", ""))
        val history = TreeItem(BookmarkDataModel("History", ""))

        internetModel.webview.get().apply {
            engine.setOnError {
                println(it.message)
            }
            engine.setOnAlert {
                println(it.data)
            }
            engine.locationProperty().onChange {
                println(it)
            }
        }
        WebConsoleListener.setDefaultListener { webView, message, lineNumber, sourceId ->
            println("[$lineNumber]: $message")
        }
        val webView = internetModel.webview.get()
        webView.isContextMenuEnabled = false
        homeTab.content = internetModel.webview.get()
        homeTab.setOnSelectionChanged {
            webView.engine.reload()
        }
        //webView.engine.load("http://google.com/")

        webView.engine.userStyleSheetLocation = null
        webView.engine.loadWorker.stateProperty().addListener { _, _, newValue ->
            if (newValue === Worker.State.SUCCEEDED) {
                val jso: JSObject = webView.engine.executeScript("window") as JSObject
                jso.setMember("game", GameScriptActions)
            }
        }


        //remoteContainer.disableWhen(internetModel.remoteHomePageNode.isNull.and(prefModel.devMode.not()))

        bookmarksRoot.isExpanded = true
        bookmarksRoot.children.addAll(favorite, history)

        bookmarks.setCellFactory { BookmarkTreeCell(BookmarkTreeCellFragment()) }
        bookmarks.root = bookmarksRoot

        history.children.add(TreeItem(BookmarkDataModel("Bank", "56.54.11.167")))

        addressField.shownProperty.bind(prefModel.MEDIUM_MODE.or(prefModel.HIGH_MODE))

        connectBtn.setOnAction {
            val address = addressField.text
            scope.session?.sendMessage(VmCommandMessage("connect $address", false))
            var found = false
            for (child in history.children) {
                val data = child.value
                if(data.address.get().equals(address, true)) {
                    found = true
                    break
                }
            }
            if (!found) {
                history.children.add(0, TreeItem(BookmarkDataModel("none", address)))
            }
        }

        initRemoteLogin()
        initRemoteLogs()
        initRemoteSoftware()
    }

    private fun initRemoteLogin() {
        loginTab.enableWhen(accountModel.username.isNull.or(prefModel.devMode.not()))
        logoutBtn.visibleWhen(accountModel.username.isNotNull)
        logoutBtn.setOnAction {
            val session = Extensions.session
            session?.sendMessage(VmCommandMessage("logout", true))
        }

        internetModel.username.bind(userField.textProperty())
        internetModel.password.bind(passField.textProperty())
        userField.text = "root"
        passField.textProperty().bindBidirectional(paramModel.stringProperty("remote-pass"))

        loginBtn.setOnAction {
            val user = userField.text
            val pass = passField.text
            val ip = gameFrameModel.remoteIP.get().replace(' ', '_')
            val session = Extensions.session
            session?.sendMessage(VmCommandMessage("login -i $ip -u $user -p $pass", false))
        }

        bruteBtn.setOnAction {
            val curAnim = animModel.currentAnim.get()
            if (curAnim != null && curAnim.status === Animation.Status.RUNNING) {
                curAnim.stop()
            }
            animModel.currentAnim.set(timeline(false) {
                cycleCount = Timeline.INDEFINITE
                //isAutoReverse = true
                keyframe(Duration.millis(100.0)) {
                    setOnFinished {
                        passField.promptText = generatePassword()
                    }
                }
            })
            val session = Extensions.session
            session?.sendMessage(VmCommandMessage("bf -u ${userField.text}", true))
        }

    }

    private fun initRemoteSoftware() {
        softTab.disableWhen(accountModel.username.isNull)
        val softFrag = find<SoftwareFragment>()
        softwareTable.setRowFactory { SoftwareTableRowCell() }
        softwareTable.items.bind(internetModel.softwares) { _, v -> v }
        softwareTable.columnResizePolicy = SmartResize.POLICY

        softwareTable.sortOrder.setAll(softwareName, softwareSize, softwareVersion, softwareActions)

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
        softwareActions.setCellFactory { SoftwareActionsTableCell(SoftwareActionsFragment(true)) }
    }


    private fun initRemoteLogs() {

        logsTab.disableWhen(accountModel.username.isNull)

        internetModel.systemLogs.get().comparatorProperty().bind(logsTable.comparatorProperty())
        logsTable.itemsProperty().bind(internetModel.systemLogs)
        logsTable.sortOrder.setAll(timeColumn, sourceColumn, messageColumn)
        timeColumn.setCellValueFactory {
            SimpleStringProperty(
                Instant.ofEpochSecond(it.value.time.get()).atOffset(ZoneOffset.UTC).toLocalDateTime()
                    .format(Extensions.dateTimeFormatter)
            )
        }

        sourceColumn.setCellValueFactory { it.value.source }
        messageColumn.setCellValueFactory { it.value.message }

        actionsColumn.setCellFactory { LogActionsTableCell(LogActionsFragment(true)) }
        actionsColumn.setCellValueFactory { SimpleStringProperty("mock") }

    }
}