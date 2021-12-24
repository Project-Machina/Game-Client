package com.client.game.model.animations

import javafx.animation.Timeline
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class AnimationModel : ViewModel() {

    val currentAnim = bind { SimpleObjectProperty<Timeline>(this, "current_anim") }

}