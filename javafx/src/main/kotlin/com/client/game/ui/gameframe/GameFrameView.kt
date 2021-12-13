package com.client.game.ui.gameframe

import com.client.game.model.PreferencesModel
import com.client.game.ui.developer.DeveloperFragment
import com.client.game.ui.processes.ProcessesFragment
import com.client.game.ui.software.SoftwareFragment
import com.client.javafx.nodes.ExitButton
import com.client.javafx.nodes.combox.HideInfoButtonCell
import com.client.javafx.setHideable
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.control.skin.ComboBoxListViewSkin
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import tornadofx.*

class GameFrameView : View() {

    val model: GameFrameModel by inject()
    val preferences: PreferencesModel by inject()

    override val root: AnchorPane by fxml("gameframe.fxml")
    val exitButton: ExitButton by fxid()
    val titleIcon: ImageView by fxid()
    val titleBar: AnchorPane by fxid()
    val softwareBtn: Button by fxid()
    val hardwareBtn: Button by fxid()
    val processesBtn: Button by fxid()
    val devButton: Button by fxid()
    val hideInfo: ComboBox<Node> by fxid()
    val gameInterface: AnchorPane by fxid()
    val money: Label by fxid()
    val btc: Label by fxid()
    val address: Label by fxid()
    val connected: Label by fxid()
    val rank: Label by fxid()
    val rankProgress: ProgressBar by fxid()

    val lowMode: String by fxid()
    val mediumMode: String by fxid()
    val highMode: String by fxid()
    val off: String by fxid()

    init {

        softwareBtn.setOnAction {
            gameInterface.clear()
            gameInterface.add<SoftwareFragment>()
        }

        processesBtn.setOnAction {
            gameInterface.clear()
            gameInterface.add<ProcessesFragment>()
        }

        devButton.setOnAction {
            gameInterface.clear()
            gameInterface.add<DeveloperFragment>()
        }

        titleIcon.contextmenu {
            checkmenuitem("Developer Mode", selected = preferences.devMode)
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
            model.commit()
            preferences.commit()
            Platform.exit()
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

        address.textProperty().setHideable(
            { "127.0.0.1" },
            preferences.LOW_MODE,
            preferences.MEDIUM_MODE,
            preferences.HIGH_MODE
        )

        connected.textProperty().setHideable(
            { "Disconnected" },
            preferences.MEDIUM_MODE,
            preferences.HIGH_MODE
        )

        rank.textProperty().setHideable(
            { "1" },
            preferences.HIGH_MODE
        )

        rankProgress.tooltip("Experience: 0 / 1000")
    }

}