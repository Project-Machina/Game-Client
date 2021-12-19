package com.client.game

fun formatSize(size: Long) : String {
    val GB = (size / 1024)
    val TB = (GB / 1024)
    val PB = (TB / 1024)
    val EB = (PB / 1024)
    val ZB = (EB / 1024)
    val YB = (ZB / 1024)

    if(YB > 0) {
        return "$YB YB"
    } else if(ZB > 0) {
        return "$ZB ZB"
    } else if(EB > 0) {
        return "$EB EB"
    } else if(PB > 0) {
        return "$PB PB"
    } else if(TB > 0) {
        return "$TB TB"
    } else if(GB > 0) {
        return "$GB GB"
    }

    return "$size MB"
}