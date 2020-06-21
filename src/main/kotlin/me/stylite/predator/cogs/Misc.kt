package me.stylite.predator.cogs

import me.devoxin.flight.api.Context
import me.devoxin.flight.api.annotations.Command
import me.devoxin.flight.api.entities.Cog
import me.stylite.predator.Predator
import net.dv8tion.jda.api.JDAInfo

class Misc : Cog {

    @Command(description = "Ping!")
    fun ping(ctx: Context) {
        ctx.send("Pong!")
    }



    @Command(description = "Info on the bot")
    fun info(ctx: Context) {
        ctx.send("""
            ```ini
            JDA: ${JDAInfo.VERSION}
            Version: 1.0
            Commands Loaded: ${Predator.commandHandler.commands.size}
            Shards: ${Predator.shardManager.shards.size}
            Guild Count: ${Predator.shardManager.guilds.size}
            Ping: ${Predator.shardManager.averageGatewayPing}ms
            ```
        """.trimIndent())
    }

    @Command(description = "Post stats to DBL", developerOnly = true)
    fun posttodbl(ctx: Context) {
        val guildCount = Predator.shardManager.guilds.size
        Predator.dbl.setStats(guildCount)
        ctx.send("Posted guild count")
    }

    @Command(description = "FAQ")
    fun faq(ctx: Context) {
        ctx.send {
            setTitle("Frequently Asked Questions")
            setDescription("""
                **If your battlepass level shows as -1**
                Display the battlepass badge at least once on your character card and rerun the command.
                **If your battlepass level shows as the last seasons battlepass level**
                Redisplay your new battlepass level badge on your character card and rerun the command.
            """.trimIndent()
            )
        }
    }

    @Command(description = "Get links for the bot")
    fun invite(ctx: Context) {
        val inviteLink = ctx.jda.getInviteUrl()
        ctx.send("Invite me: <$inviteLink>\nJoin my support server: https://discord.gg/HZP7fUs")
    }
}