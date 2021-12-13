package com.client.scope

import com.client.network.session.NetworkSession
import com.client.scripting.Extensions
import tornadofx.Scope

class GameScope : Scope() {
    val session: NetworkSession? get() = Extensions.session
}