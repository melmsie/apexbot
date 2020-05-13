package me.stylite.predator.utils

import me.stylite.predator.Config
import me.stylite.predator.Predator
import org.discordbots.api.client.DiscordBotListAPI
import java.util.*


object DblTimer {

    val api: DiscordBotListAPI = DiscordBotListAPI.Builder()
        .token(Config.dblKey)
        .botId("702604525529202749")
        .build()
    fun postStats() {
        val timer = Timer()
        val guildCount = Predator.shardManager.guilds.size
        val post = object : TimerTask() {
            override fun run() {
                api.setStats(guildCount)
                Predator.log.info("Posted stats to DBL")
            }

        }
        timer.schedule(post, 0, 1800*1000)


    }

}