package com.client.game.ui

import javafx.fxml.FXML
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import tornadofx.ChangeListener
import tornadofx.Controller

class ContentController : Controller() {
    @FXML
    lateinit var contentPane: AnchorPane

    @FXML
    fun clickTest() {
        contentPane.children.forEach {
            println(it)
        }

        contentPane.children.setAll()

        val l = javafx.scene.control.Label()

        l.textProperty().addListener(ChangeListener { observable, oldValue, newValue ->

        })

        l.styleClass

    }

}