package com.client.game.model.software

import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class SoftwareModel : ViewModel() {

    val capacity = bind { SimpleObjectProperty(500000uL) }

}