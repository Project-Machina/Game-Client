package com.client.scripting

import com.client.network.session.NetworkSession
import com.client.packets.outgoing.PingMessage
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.css.Styleable
import tornadofx.FX
import tornadofx.onChange

/**
 * Note to self, annotated anything being called from JavaScript with JvmStatic!
 */

object ScriptAPI {
    val sessionProperty = SimpleObjectProperty<NetworkSession>()
    val session: NetworkSession? get() = sessionProperty.get()

    @JvmStatic
    fun message() = "Hello, From Script API"

    @JvmStatic
    fun sendPing() {
        session?.let {
            val ping = PingMessage.encode(PingMessage("Hey"))
            it.sendPacket(ping)
        }
        println("Sending Ping Packet!")
    }

    @JvmStatic
    fun onStringChange(node: ObservableValue<String>, func : (String?) -> Unit) {
        node.onChange {
            func(it)
        }
        func("working!")
    }

    @JvmStatic
    fun onIntChange(node: ObservableValue<Int>, func: (Int?) -> Unit) {
        node.onChange {
            func(it)
        }
    }

    @JvmStatic
    fun replaceStyleClass(styleable: Styleable, find: String, replace: String) {
        if(styleable.styleClass.remove(find)) {
            styleable.styleClass.add(replace)
        }
    }

    inline fun <reified T> importStylesheet(stylesheet: String) {
        val css = T::class.java.getResource(stylesheet)
        if (css != null) {
            FX.stylesheets.add(css.toExternalForm())
        }
    }
}