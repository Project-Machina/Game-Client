package com.client.game.ui.gameframe

import com.client.game.ui.developer.DeveloperFragment
import com.client.javafx.nodes.ExitButton
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import tornadofx.View
import tornadofx.clear
import tornadofx.replaceWith

class GameFrameView : View() {

    val model: GameFrameModel by inject()

    override val root: AnchorPane by fxml("gameframe.fxml")

    val exitButton: ExitButton by fxid()

    val titleIcon: ImageView by fxid()
    val titleBar: AnchorPane by fxid()

    val content: AnchorPane by fxid()

    val devButton: Button by fxid()

    val gameInterface: AnchorPane by fxid()

    init {

        devButton.setOnAction {
            gameInterface.clear()
            gameInterface.add<DeveloperFragment>()
        }

        titleIcon.isMouseTransparent = true

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
            Platform.exit()
        }
    }

}