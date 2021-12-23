package com.client.scripting

import com.client.game.npcs.NpcPageLoader
import com.client.network.session.NetworkSession
import com.client.packets.incoming.*
import javafx.beans.property.SimpleObjectProperty
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools
import java.time.format.DateTimeFormatter

/**
 * Note to self, annotate anything being called from JavaScript with JvmStatic!
 */

object Extensions {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    val sessionProperty = SimpleObjectProperty<NetworkSession>()
    val session: NetworkSession? get() = sessionProperty.get()

    fun setSession(session: NetworkSession) {
        session.handlePacket(VirtualInformationUpdate())
        session.handlePacket(PlayerStatistics())
        session.handlePacket(VirtualProcessUpdate())
        session.handlePacket(VirtualSoftwareUpdate())
        session.handlePacket(VirtualMachineUpdate())
        session.handlePacket(SystemLogUpdate())
        session.handlePacket(VirtualProcessCreate())
        session.handlePacket(NpcPageUpdate())
        session.handlePacket(SystemAccountUpdate())
        sessionProperty.set(session)
    }

    inline fun <reified T : Any> inject(
        qualifier: Qualifier? = null,
        mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
        noinline parameters: ParametersDefinition? = null
    ) = GlobalContext.get().inject<T>(qualifier, mode, parameters)

    inline fun <reified T : Any> get(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null
    ) = GlobalContext.get().get<T>(qualifier, parameters)
}