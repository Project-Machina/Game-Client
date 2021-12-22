package com.client.game.model.processes

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.ViewModel

class ProcessDialogModel : ViewModel() {

    val isFinished = bind { SimpleBooleanProperty(this, "is_finished", false) }

}