package com.client.javafx.models.dialogs

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class AlertDialogModel(title: String, message: String) : ViewModel() {

    val title = bind { SimpleStringProperty(this, "title", title) }
    val message = bind { SimpleStringProperty(this, "message", message) }

}