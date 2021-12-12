package com.client.game.ui.gameframe

import com.client.game.model.PreferencesModel
import com.client.game.ui.developer.DeveloperFragment
import com.client.javafx.nodes.ExitButton
import com.client.javafx.setHideable
import javafx.application.Platform
import javafx.beans.binding.Bindings
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import tornadofx.*
import tornadofx.Stylesheet.Companion.tooltip

class GameFrameView : View() {

    val model: GameFrameModel by inject()
    val preferences: PreferencesModel by inject()

    override val root: AnchorPane by fxml("gameframe.fxml")
    val exitButton: ExitButton by fxid()
    val titleIcon: ImageView by fxid()
    val titleBar: AnchorPane by fxid()
    val devButton: Button by fxid()
    val gameInterface: AnchorPane by fxid()
    val hideInfo: ChoiceBox<String> by fxid()
    val money: Label by fxid()
    val btc: Label by fxid()
    val address: Label by fxid()
    val connected: Label by fxid()
    val rank: Label by fxid()
    val rankProgress: ProgressBar by fxid()

    init {

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
        preferences.devMode.onChange {
            if (it) {
                hideInfo.tooltip("Order Matters\nIndex 0 = Off\nIndex 1 = Low Mode\nIndex 2 = Medium Mode\nIndex 3 = High Mode")
            } else {
                hideInfo.tooltip = null
            }
        }

        hideInfo.value = hideInfo.items[0]
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