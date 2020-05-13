package me.stylite.predator

import me.devoxin.flight.api.CommandClient
import java.io.FileInputStream
import me.devoxin.flight.api.CommandClientBuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.sharding.DefaultShardManager
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import me.stylite.predator.Config
import org.slf4j.Logger
import me.stylite.predator.utils.Resources
import org.discordbots.api.client.DiscordBotListAPI
import org.slf4j.LoggerFactory
import java.util.*

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
            .token(Config.dblKey)
            .botId("702604525529202749")
            .build()

        log = LoggerFactory.getLogger("Predator")
        shardManager = DefaultShardManagerBuilder.createDefault(conf.token)
            .addEventListeners(commandHandler, JdaEvents)
            .setActivity(Activity.watching("the kill leader || ${conf.prefix.first()}help"))
            .build()

        commandHandler.registerCommands("me.stylite.predator.cogs")
    }
}