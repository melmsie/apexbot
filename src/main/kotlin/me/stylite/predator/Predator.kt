package me.stylite.predator

import me.devoxin.flight.api.CommandClient
import java.io.FileInputStream
import java.util.Properties
import me.devoxin.flight.api.CommandClientBuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.sharding.DefaultShardManager
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import me.stylite.predator.Config
import me.stylite.predator.utils.Resources

object Predator {
    lateinit var shardManager: ShardManager
    lateinit var commandHandler: CommandClient


    @ExperimentalStdlibApi
    @JvmStatic
    fun main(args: Array<String>) {

        val conf = Config
        commandHandler = CommandClientBuilder()
            .setPrefixes(conf.prefix)
            .registerDefaultParsers()
            .setOwnerIds(172571295077105664L, 218468138709155841L)
            .addEventListeners(EventHook())
            .build()

        shardManager = DefaultShardManagerBuilder.createDefault(conf.token)
            .addEventListeners(commandHandler)
            .setActivity(Activity.watching("the kill leader || ${conf.prefix.first()}help"))
            .build()

        commandHandler.registerCommands("me.stylite.predator.cogs")
    }
}