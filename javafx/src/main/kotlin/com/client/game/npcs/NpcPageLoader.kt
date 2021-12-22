package com.client.game.npcs

import javafx.fxml.FXMLLoader
import javafx.scene.layout.AnchorPane
import java.io.ByteArrayInputStream
import java.net.URL

object NpcPageLoader {

    fun loadNpcPage(data: ByteArray): AnchorPane {
        val inputStream = ByteArrayInputStream(data)
        val loader = FXMLLoader()
        loader.location = URL("file:")
        return loader.load(inputStream)
    }

}