package com.client.assets

import kotlinx.serialization.Serializable

@Serializable
data class AssetMeta(val name: String, val files: Array<AssetFile>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AssetMeta

        if (name != other.name) return false
        if (!files.contentEquals(other.files)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + files.contentHashCode()
        return result
    }
}