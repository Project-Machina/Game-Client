package com.client.scripting

import com.client.network.session.NetworkSession
import com.client.packets.outgoing.PingMessage
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.css.Styleable
import javafx.scene.control.Label
import tornadofx.onChange
import java.util.*
import java.util.function.Consumer
import java.util.function.Function

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

}