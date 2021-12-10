package com.client.javafx.nodes

import javafx.scene.control.Button

class ExitButton : Button() {

    init {
        styleClass.remove("button")
    }

}