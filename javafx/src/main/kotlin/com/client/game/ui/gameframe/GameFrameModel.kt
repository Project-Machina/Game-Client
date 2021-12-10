package com.client.game.ui.gameframe

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import tornadofx.ViewModel

class GameFrameModel : ViewModel() {

    val x = bind { SimpleDoubleProperty(this, "x", 0.0) }
    val y = bind { SimpleDoubleProperty(this, "y", 0.0) }
    val isMovingWindow = bind { SimpleBooleanProperty(this, "is_moving_window", false) }

}