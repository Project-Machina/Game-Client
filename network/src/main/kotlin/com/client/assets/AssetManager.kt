package com.client.assets

import com.client.network.GameHttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

object AssetManager {

    fun requestAssets(client: GameHttpClient) {
        client.getAssetsMeta()
            .onEach {

                it.forEach {

                }

            }
            .launchIn(CoroutineScope(Dispatchers.IO))
    }

}