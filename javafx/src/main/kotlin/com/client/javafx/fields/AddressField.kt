package com.client.javafx.fields

import javafx.scene.control.Skin
import javafx.scene.control.TextField

class AddressField(text: String = "") : TextField(text) {

    val shownProperty = ShowAddressProperty(this)
    val isShown: Boolean by shownProperty

    override fun createDefaultSkin(): Skin<*> {
        return AddressFieldSkin(this)
    }
}