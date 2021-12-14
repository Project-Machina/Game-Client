package com.client.scripting

import com.client.network.session.NetworkSession
import com.client.packets.incoming.PlayerStatistics
import com.client.packets.incoming.VmCommandOutput
import javafx.beans.property.SimpleObjectProperty
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools

/**
 * Note to self, annotate anything being called from JavaScript with JvmStatic!
 */

object Extensions {
    val sessionProperty = SimpleObjectProperty<NetworkSession>()
    val session: NetworkSession? get() = sessionProperty.get()

    fun setSession(session: NetworkSession) {
        session.handlePacket(VmCommandOutput())
        session.handlePacket(PlayerStatistics())
        sessionProperty.set(session)
    }

    inline fun <reified T : Any> inject(
        qualifier: Qualifier? = null,
        mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
        noinline parameters: ParametersDefinition? = null
    ) = GlobalContext.get().inject<T>()
}