package com.client.test

import com.client.game.ui.developer.DeveloperFragment
import com.client.game.ui.gameframe.GameFrameView
import javafx.scene.Parent
import javafx.scene.layout.Pane
import tornadofx.App
import tornadofx.View
import tornadofx.launch

object ApplicationTest {

    @JvmStatic
    fun main(args: Array<String>) {

        launch<TestApp>()

    }

    class TestApp : App(DeveloperFragment::class)

}