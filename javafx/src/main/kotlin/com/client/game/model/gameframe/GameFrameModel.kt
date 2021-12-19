package com.client.game.model.gameframe

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class GameFrameModel : ViewModel() {

    val x = bind { SimpleDoubleProperty(this, "x", 0.0) }
    val y = bind { SimpleDoubleProperty(this, "y", 0.0) }

    val linkIP = bind { SimpleStringProperty(this, "link_ip", "127.0.0.1") }
    val remoteIP = bind { SimpleStringProperty(this, "remote_ip", "localhost") }
    val time = bind { SimpleLongProperty(this, "time", 0L) }
    val rank = bind { SimpleIntegerProperty(this, "rank", 1) }



}