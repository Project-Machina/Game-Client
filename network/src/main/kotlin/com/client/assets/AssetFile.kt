package com.client.assets

import kotlinx.serialization.Serializable

@Serializable
data class AssetFile(val crc: String, val file: String)