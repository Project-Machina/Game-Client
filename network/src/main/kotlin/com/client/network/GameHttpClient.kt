package com.client.network

import com.client.assets.AssetMeta
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GameHttpClient(val baseURL: String) {

    private val client = HttpClient(CIO)

    fun getAssetsMeta(): Flow<List<AssetMeta>> = flow {
        emit(client.get("$baseURL/updates"))
    }

}