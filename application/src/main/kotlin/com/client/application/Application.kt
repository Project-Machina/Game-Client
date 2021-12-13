package com.client.application

import com.client.game.model.developer.DeveloperModel
import com.client.game.ui.gameframe.GameFrameView
import com.client.network.NetworkClient
import com.client.network.session.NetworkSession.Companion.toPacket
import com.client.packets.outgoing.VmCommandMessage
import com.client.packets.outgoingPackets
import com.client.scope.GameScope
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import tornadofx.Scope
import kotlin.reflect.KClass

class Application : App(GameFrameView::class) {

    override var scope: Scope = GameScope()

    override fun init() {
        startKoin {
            modules(module {
                single { NetworkClient("127.0.0.1", 43595) }
                single { DeveloperModel() }
            }, outgoingPackets)
        }
        FX.defaultScope = GameScope()
        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T {
                return GlobalContext.get().get(type)
            }
        }
    }

    override fun start(stage: Stage) {
        stage.initStyle(StageStyle.UNDECORATED)
        super.start(stage)
        stage.scene.stylesheets.add(this::class.java.getResource("dark-theme.css").toExternalForm())
    }
}