package com.client.game.ui.gameframe

import com.client.game.formatSize
import com.client.game.model.PreferencesModel
import com.client.game.model.gameframe.GameFrameModel
import com.client.game.model.player.PlayerStatisticsModel
import com.client.game.ui.developer.DeveloperFragment
import com.client.game.ui.hardware.HardwareFragment
import com.client.game.ui.internet.InternetFragment
import com.client.game.ui.login.LoginView
import com.client.game.ui.login.LoginViewModel
import com.client.game.ui.processes.ProcessesFragment
import com.client.game.ui.software.SoftwareFragment
import com.client.javafx.nodes.ExitButton
import com.client.javafx.nodes.combox.HideInfoButtonCell
import com.client.javafx.setHideable
import com.client.network.NetworkClient
import com.client.packets.outgoing.LogoutMessage
import com.client.scope.GameScope
import com.client.scripting.Extensions.dateTimeFormatter
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.control.skin.ComboBoxListViewSkin
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import tornadofx.*
import java.time.Instant
import java.time.ZoneOffset
import kotlin.system.exitProcess

class GameFrameView : View("Project Zero") {

    val client: NetworkClient by di()
    val playerStats: PlayerStatisticsModel by di()

    override val scope: GameScope = super.scope as GameScope

    val model: GameFrameModel by inject()
    val preferences: PreferencesModel by di()
    val loginModel: LoginViewModel by inject()
    val gameframeModel: GameFrameModel by inject()

    override val root: AnchorPane by fxml("gameframe.fxml")
    val mainContainer: AnchorPane by fxid()

    val exitButton: ExitButton by fxid()
    val titleIcon: ImageView by fxid()
    val titleBar: AnchorPane by fxid()
    val softwareBtn: Button by fxid()
    val hardwareBtn: Button by fxid()
    val processesBtn: Button by fxid()
    val internetBtn: Button by fxid()
    val devButton: Button by fxid()
    val hideInfo: ComboBox<Node> by fxid()
    val gameInterface: AnchorPane by fxid()
    val money: Label by fxid()
    val btc: Label by fxid()
    val linkIP: Label by fxid()
    val remoteIP: Label by fxid()
    val rank: Label by fxid()
    val serverTime: Label by fxid()
    val rankProgress: ProgressBar by fxid()

    val lowMode: String by fxid()
    val mediumMode: String by fxid()
    val highMode: String by fxid()
    val off: String by fxid()

    val profileBtn: Button by fxid()
    val notsBtn: Button by fxid()
    val privacyBtn: Button by fxid()
    val infoContainer: HBox by fxid()
    val aboutBtn: Button by fxid()
    val logoutBtn: Button by fxid()

    val detachProcessBtn: MenuItem by fxid()
    val detachDevBtn: MenuItem by fxid()

    val availableRamLabel: Label by fxid()
    val usedRamLabel: Label by fxid()

    val availableSpaceLabel: Label by fxid()
    val usedSpaceLabel: Label by fxid()


    init {
        mainContainer.disableWhen(loginModel.isLoggedIn.not())
        profileBtn.disableWhen(loginModel.isLoggedIn.not())
        notsBtn.disableWhen(loginModel.isLoggedIn.not())
        privacyBtn.disableWhen(loginModel.isLoggedIn.not())
        infoContainer.disableWhen(loginModel.isLoggedIn.not())
        logoutBtn.disableWhen(loginModel.isLoggedIn.not())
        gameInterface.disableWhen(loginModel.isLoggedIn.not())

        detachProcessBtn.setOnAction {
            find<ProcessesFragment> {
                openWindow(owner = null)
            }
        }
        detachDevBtn.setOnAction {
            find<DeveloperFragment> {
                openWindow(owner = null)
            }
        }

        logoutBtn.setOnAction {
            val session = scope.session
            session?.sendMessage(LogoutMessage())
            client.shutdown()
            loginModel.isLoggedIn.set(false)
            session?.shutdownGracefully()
        }

        softwareBtn.setOnAction {
            gameInterface.clear()
            gameInterface.add<SoftwareFragment>()
        }

        processesBtn.setOnAction {
            gameInterface.clear()
            gameInterface.add<ProcessesFragment>()
        }

        hardwareBtn.setOnAction {
            gameInterface.clear()
            gameInterface.add<HardwareFragment>()
        }

        internetBtn.setOnAction {
            gameInterface.clear()
            gameInterface.add<InternetFragment>()
        }

        devButton.setOnAction {
            gameInterface.clear()
            gameInterface.add<DeveloperFragment>()
        }

        titleIcon.contextmenu {
            checkmenuitem("Developer Mode", selected = preferences.devMode)
            checkmenuitem("Bypass Login", selected = preferences.bypassLogin)
        }

        titleBar.setOnMousePressed {
            model.x.set(it.sceneX)
            model.y.set(it.sceneY)
        }

        titleBar.setOnMouseDragged { event ->
            currentStage?.let {
                it.x = event.screenX - model.x.get()
                it.y = event.screenY - model.y.get()
            }
        }

        exitButton.setOnMouseClicked {
            scope.session?.sendMessage(LogoutMessage())
            model.commit()
            preferences.commit()
            if (scope.session != null) {
                scope.session!!.shutdownGracefully()
            }
            Platform.exit()
            exitProcess(0)
        }
        devButton.hiddenWhen(preferences.devMode.not())

        hideInfo.items.addAll(
            Label(off),
            Label(lowMode),
            Label(mediumMode),
            Label(highMode),
            VBox(menubutton("Other Modes") {
                menu("Software") {
                    checkmenuitem("Hide Software Name", selected = preferences.SOFTWARE_NAME_SUB_MODE)
                    checkmenuitem("Hide Software Extension", selected = preferences.SOFTWARE_EXTENSION_SUB_MODE)
                    checkmenuitem("Hide Software Version", selected = preferences.SOFTWARE_VERSION_SUB_MODE)
                }
            })
        )
        val skin = ComboBoxListViewSkin(hideInfo)
        skin.isHideOnClick = false
        hideInfo.valueProperty().onChange { hideInfo.hide() }
        hideInfo.selectionModel.select(0)
        hideInfo.skin = skin
        hideInfo.buttonCell = HideInfoButtonCell()

        preferences.devMode.onChange {
            if (it) {
                hideInfo.tooltip("Order Matters\nIndex 0 = Off\nIndex 1 = Low Mode\nIndex 2 = Medium Mode\nIndex 3 = High Mode")
            } else {
                hideInfo.tooltip = null
            }
        }

        hideInfo.selectionModel.selectedIndexProperty().onChange {
            when (it) {
                0 -> {
                    val confirm = confirmation(
                        title = "Information Visibility",
                        header = "You are about to make sensitive information readable! Are you sure?",
                        buttons = arrayOf(ButtonType.NO, ButtonType.YES)
                    )
                    val result = confirm.showAndWait()
                    if (result.isPresent && result.get() === ButtonType.YES) {
                        preferences.unhideAll()
                    }
                }
                1 -> preferences.setMode("low")
                2 -> preferences.setMode("medium")
                3 -> preferences.setMode("high")
            }
            preferences.commit()
        }

        availableSpaceLabel.textProperty().setHideable(preferences.HIGH_MODE, playerStats.availableDiskSpace) {
            formatSize(playerStats.availableDiskSpace.get())
        }
        usedSpaceLabel.textProperty().setHideable(preferences.HIGH_MODE, playerStats.driveUsage) {
            formatSize(playerStats.driveUsage.get())
        }

        availableRamLabel.textProperty().setHideable(preferences.HIGH_MODE, playerStats.availableRam) {
            formatSize(playerStats.availableRam.get())
        }

        usedRamLabel.textProperty().setHideable(preferences.HIGH_MODE, playerStats.ramUsage) {
            formatSize(playerStats.ramUsage.get())
        }

        money.textProperty().setHideable(
            { "$1000" },
            preferences.LOW_MODE,
            preferences.MEDIUM_MODE,
            preferences.HIGH_MODE
        )

        btc.textProperty().setHideable(
            { "0.00000000" },
            preferences.MEDIUM_MODE,
            preferences.HIGH_MODE
        )

        linkIP.textProperty().setHideable(
            { gameframeModel.linkIP.get() },
            preferences.LOW_MODE,
            preferences.MEDIUM_MODE,
            preferences.HIGH_MODE,
            gameframeModel.linkIP
        )

        remoteIP.textProperty().setHideable(
            { gameframeModel.remoteIP.get() },
            preferences.MEDIUM_MODE,
            preferences.HIGH_MODE,
            gameframeModel.remoteIP
        )

        rank.textProperty().setHideable(
            { gameframeModel.rank.get().toString() },
            preferences.HIGH_MODE,
            gameframeModel.rank
        )

        serverTime.textProperty().setHideable(
            {
                Instant.ofEpochSecond(gameframeModel.time.get()).atOffset(ZoneOffset.UTC).toLocalDateTime()
                    .format(dateTimeFormatter)
            },
            preferences.HIGH_MODE,
            gameframeModel.time
        )

        rankProgress.tooltip("Experience: 0 / 1000")

        loginModel.isLoggedIn.onChange {
            val loginView = find<LoginView>()
            if (it) {
                loginView.removeFromParent()
            } else {
                root.add(loginView)
            }
        }

        preferences.bypassLogin.onChange {
            if (it) {
                loginModel.isLoggedIn.set(true)
            } else {
                loginModel.isLoggedIn.set(false)
            }
        }

        if (loginModel.isLoggedIn.not().get() && preferences.bypassLogin.not().get()) {
            val loginView = find<LoginView>()
            root.add(loginView)
        }
    }
}