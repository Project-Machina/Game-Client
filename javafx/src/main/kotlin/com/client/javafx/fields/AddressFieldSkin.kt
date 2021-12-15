package com.client.javafx.fields

import javafx.scene.control.skin.TextFieldSkin

class AddressFieldSkin(val control: AddressField) : TextFieldSkin(control) {
    private val BULLET = '\u25cf'
    override fun maskText(txt: String): String {
        if(skinnable is AddressField && (skinnable as AddressField).isShown) {
            val n = txt.length
            val build = StringBuilder(n)
            for (i in 0 until n) {
                build.append(BULLET)
            }
            return build.toString()
        }
        return super.maskText(txt)
    }


}