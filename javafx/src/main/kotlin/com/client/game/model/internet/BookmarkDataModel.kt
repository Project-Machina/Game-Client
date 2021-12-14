package com.client.game.model.internet

import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class BookmarkDataModel(name: String, address: String) : ItemViewModel<BookmarkData>(BookmarkData(name, address)) {

    val name = bind { SimpleStringProperty(this, "name", name) }
    val address = bind { SimpleStringProperty(this, "address", address) }

}