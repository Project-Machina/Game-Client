package com.client.javafx.nodes.combox

import javafx.scene.Node
import javafx.scene.control.Labeled
import javafx.scene.control.ListCell

class HideInfoButtonCell : ListCell<Node>() {
    override fun updateItem(item: Node?, empty: Boolean) {
        super.updateItem(item, empty)
        if(item != null && !empty) {
            if(item is Labeled) {
                text = item.text
                graphic = null
            } else {
                text = null
                graphic = item
            }
        } else {
            text = null
            graphic = null
        }
    }
}