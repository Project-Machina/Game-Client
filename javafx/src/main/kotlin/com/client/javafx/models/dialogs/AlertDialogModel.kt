package com.client.javafx.models.dialogs

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import tornadofx.ViewModel

class AlertDialogModel(title: String, message: String, color: Color = Color.WHITE) : ViewModel() {

    val title = bind { SimpleStringProperty(this, "title", title) }
    val message = bind { SimpleStringProperty(this, "message", message) }
    val color = bind { SimpleObjectProperty(this, "color", color) }

}