package com.client.packets.incoming

class SystemAccount(val username: String, val perms: List<String>, val logout: Boolean = false)