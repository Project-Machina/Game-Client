package com.client.game.ui.gameframe

import com.client.javafx.nodes.ExitButton
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import tornadofx.View

class GameFrameView : View() {

    val model: GameFrameModel by inject()

    override val root: AnchorPane by fxml("gameframe.fxml")

    val exitButton: ExitButton by fxid()

    val titleIcon: ImageView by fxid()
    val titleBar: AnchorPane by fxid()

    init {

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
            Platform.exit()
        }
    }

}