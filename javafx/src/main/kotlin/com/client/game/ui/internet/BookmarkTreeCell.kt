package com.client.game.ui.internet

import com.client.game.model.internet.BookmarkDataModel
import javafx.scene.control.ContentDisplay
import javafx.scene.control.TreeCell

class BookmarkTreeCell(val fragment: BookmarkTreeCellFragment) : TreeCell<BookmarkDataModel>() {

    init {
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
    }

    override fun updateItem(item: BookmarkDataModel?, empty: Boolean) {
        super.updateItem(item, empty)
        graphic = if(item != null && !empty) {
            fragment.bind(item)
            fragment.root
        } else null
    }
}