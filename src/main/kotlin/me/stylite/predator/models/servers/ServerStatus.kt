package me.stylite.predator.models.servers

import me.stylite.predator.models.stats.ApexOauthPC
import me.stylite.predator.models.stats.ApexOauthPS4
import me.stylite.predator.models.stats.ApexOauthX1

data class ServerStatus(
    // EA_novafusion
    // Origin_login
    // EA_accounts

    val ApexOauth_PC: ApexOauthPC,
    val ApexOauth_PS4: ApexOauthPS4,
    val ApexOauth_X1: ApexOauthX1
)