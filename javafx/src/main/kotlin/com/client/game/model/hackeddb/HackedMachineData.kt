package com.client.game.model.hackeddb

import com.client.packets.incoming.SystemAccount

class HackedMachineData(val address: String, val accounts: List<SystemAccount>, val malware: List<MalwareData>) {
}