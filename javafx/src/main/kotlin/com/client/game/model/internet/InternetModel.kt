package com.client.game.model.internet

import com.client.game.model.logs.LogDataModel
import com.client.game.model.software.SoftwareDataModel
import javafx.beans.property.SimpleMapProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.transformation.SortedList
import javafx.scene.Node
import javafx.scene.web.WebView
import tornadofx.ViewModel
import tornadofx.compareTo

class InternetModel : ViewModel() {

    val webview = SimpleObjectProperty(WebView())
    val logs = FXCollections.observableArrayList<LogDataModel>()
    val systemLogs = SimpleObjectProperty(SortedList(logs) { o1, o2 ->
        if (o1.time < o2.time)
            return@SortedList 1
        else if (o1.time > o2.time)
            return@SortedList 0
        return@SortedList -1
    })

    val softwares = bind { SimpleMapProperty<String, SoftwareDataModel>(this, "softwares", FXCollections.observableHashMap()) }

    val remoteHomePageNode = bind { SimpleObjectProperty<Node>(this, "remote_home_page_node") }

    val username = bind { SimpleStringProperty(this, "username") }
    val password = bind { SimpleStringProperty(this, "password") }

    fun loadPage(path: String) {
        val engine = webview.get().engine
        println("Loading npc page.")
        engine.load("http://localhost:8080/npcs/$path")
    }

}