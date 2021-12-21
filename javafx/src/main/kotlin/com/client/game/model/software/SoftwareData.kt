package com.client.game.model.software

data class SoftwareData(
    val id: String,
    val name: String,
    val extension: String,
    val version: Double,
    val size: Long,
    val pid: Int = -1,
    val installed: Boolean = false,
    val remote: Boolean = false,
    val isHidden: Boolean = false
)