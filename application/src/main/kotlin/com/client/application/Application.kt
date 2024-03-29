package com.client.application

import com.client.game.model.ParameterModel
import com.client.game.model.PreferencesModel
import com.client.game.model.animations.AnimationModel
import com.client.game.model.developer.DeveloperModel
import com.client.game.model.internet.InternetModel
import com.client.game.model.player.PlayerStatisticsModel
import com.client.game.model.processes.ProcessesModel
import com.client.game.model.remote.SystemAccountModel
import com.client.game.model.software.SoftwareModel
import com.client.game.ui.gameframe.GameFrameView
import com.client.network.NetworkClient
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
import kotlin.math.sin
import kotlin.reflect.KClass

class Application : App(GameFrameView::class) {

    override var scope: Scope = GameScope()

    override fun init() {
        startKoin {
            modules(module {
                single { NetworkClient("127.0.0.1", 43595) }
                single { DeveloperModel() }
                single { PreferencesModel() }
                single { ProcessesModel() }
                single { PlayerStatisticsModel() }
                single { InternetModel() }
                single { SoftwareModel() }
                single { SystemAccountModel() }
                single { AnimationModel() }
                single { ParameterModel() }
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