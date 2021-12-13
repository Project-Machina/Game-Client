package com.client.game

fun formatSize(size: ULong) : String {
    val GB = (size / 1024uL)
    val TB = (GB / 1024uL)
    val PB = (TB / 1024uL)
    val EB = (PB / 1024uL)
    val ZB = (EB / 1024uL)
    val YB = (ZB / 1024uL)

    if(YB > 0uL) {
        return "$YB YB"
    } else if(ZB > 0uL) {
        return "$ZB ZB"
    } else if(EB > 0uL) {
        return "$EB EB"
    } else if(PB > 0uL) {
        return "$PB PB"
    } else if(TB > 0uL) {
        return "$TB TB"
    } else if(GB > 0uL) {
        return "$GB GB"
    }

    return "$size MB"
}