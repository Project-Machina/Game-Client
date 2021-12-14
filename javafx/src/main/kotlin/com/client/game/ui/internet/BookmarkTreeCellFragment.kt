package com.client.game.ui.internet

import com.client.game.model.PreferencesModel
import com.client.game.model.internet.BookmarkDataModel
import com.client.javafx.setHideable
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import tornadofx.Fragment

class BookmarkTreeCellFragment : Fragment() {

    val preferences: PreferencesModel by inject()

    override val root: HBox by fxml("bookmark-cell.fxml")

    val name: Label by fxid()
    val address: Label by fxid()

    fun bind(model: BookmarkDataModel) {
        name.textProperty().setHideable(
            preferences.HIGH_MODE
        ) { model.name.get() }
        address.textProperty().setHideable(
            preferences.MEDIUM_MODE,
            preferences.HIGH_MODE
        ) { model.address.get() }
    }

}