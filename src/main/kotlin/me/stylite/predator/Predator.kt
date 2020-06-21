package me.stylite.predator

import me.devoxin.flight.api.CommandClient
import me.devoxin.flight.api.CommandClientBuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import org.slf4j.Logger
import org.discordbots.api.client.DiscordBotListAPI
import org.slf4j.LoggerFactory

object Predator {
    lateinit var shardManager: ShardManager
    lateinit var commandHandler: CommandClient
    lateinit var log: Logger
    lateinit var dbl: DiscordBotListAPI

    @ExperimentalStdlibApi
    @JvmStatic
    fun main(args: Array<String>) {
        val conf = Config

        commandHandler = CommandClientBuilder()
            .setPrefixes(conf.prefix)
            .registerDefaultParsers()
            .setOwnerIds(172571295077105664L, 218468138709155841L, 180093157554388993L)
            .addEventListeners(EventHook())
            .build()

        dbl = DiscordBotListAPI.Builder()
            .token("conf.dblKey")
            .botId("702604525529202749")
            .build()

        log = LoggerFactory.getLogger("Predator")
        shardManager = DefaultShardManagerBuilder.createDefault(conf.token)
            .addEventListeners(commandHandler)
            .setActivity(Activity.watching("the kill leader || ${conf.prefix.first()}help"))
            .build()

        commandHandler.registerCommands("me.stylite.predator.cogs")
    }
}
