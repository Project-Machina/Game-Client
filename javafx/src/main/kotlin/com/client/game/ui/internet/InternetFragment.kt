package com.client.game.ui.internet

import com.client.game.model.internet.BookmarkDataModel
import com.client.packets.outgoing.VmCommandMessage
import com.client.scope.GameScope
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment

class InternetFragment : Fragment("Internet") {

    override val scope: GameScope = super.scope as GameScope

    override val root: AnchorPane by fxml("internet.fxml")

    val bookmarks: TreeView<BookmarkDataModel> by fxid()

    val npcContainer: AnchorPane by fxid()

    val addressField: TextField by fxid()

    val connectBtn: Button by fxid()

    init {

        val bookmarksRoot = TreeItem(BookmarkDataModel("Bookmarks", ""))
        val favorite = TreeItem(BookmarkDataModel("Favorites", ""))
        val history = TreeItem(BookmarkDataModel("History", ""))

        bookmarksRoot.isExpanded = true
        bookmarksRoot.children.addAll(favorite, history)

        bookmarks.setCellFactory { BookmarkTreeCell(BookmarkTreeCellFragment()) }
        bookmarks.root = bookmarksRoot

        history.children.add(TreeItem(BookmarkDataModel("Bank", "56.54.11.167")))

        connectBtn.setOnAction {
            val address = addressField.text
            scope.session?.sendMessage(VmCommandMessage("connect $address", false))
        }

        val npc = InternetFragment::class.java.getResource("npcs/default.fxml")
        val loader = FXMLLoader(npc)
        val con = loader.load<AnchorPane>()
        npcContainer.children.setAll(con)
    }

}