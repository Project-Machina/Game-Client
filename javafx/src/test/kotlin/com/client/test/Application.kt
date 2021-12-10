package com.client.test

import com.client.game.ui.gameframe.GameFrameView
import tornadofx.App
import tornadofx.launch

object ApplicationTest {

    @JvmStatic
    fun main(args: Array<String>) {

        launch<TestApp>()

    }

    class TestApp : App(GameFrameView::class)

}