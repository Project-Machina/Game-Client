package com.client.game.model.developer

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class DeveloperModel : ViewModel() {

    val output = bind { SimpleStringProperty(this, "output", "") }

}