package com.client.application

import com.client.game.ui.GameFrameView
import com.client.network.NetworkClient
import com.client.packets.outgoingPackets
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import javax.script.ScriptEngineManager
import kotlin.reflect.KClass

class Application : App(GameFrameView::class) {

    override fun init() {
        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T {
                return GlobalContext.get().get(type)
            }
        }
        startKoin {
            modules(module {
                single { NetworkClient("127.0.0.1", 43595) }
            }, outgoingPackets)
        }

        val manager = ScriptEngineManager()
        val factories = manager.engineFactories

        for (factory in factories) {
            println(factory.engineName)
        }

    }
}