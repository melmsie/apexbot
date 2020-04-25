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
            Guild Count: ${Predator.shardManager.guilds.size}
            Ping: ${Predator.shardManager.averageGatewayPing}ms
            ```
        """.trimIndent())
    }

    @Command(description = "FAQ")
    fun faq(ctx: Context) {
        ctx.send {
            setTitle("Frequently Asked Questions")
            setDescription("**If your battlepass level shows as -1**\nDisplay the battlepass badge at least once on your character card and rerun the command")
        }
    }

    @Command(description = "Get links for the bot")
    fun invite(ctx: Context) {
        val inviteLink = ctx.jda.getInviteUrl()
        ctx.send("Invite me: <$inviteLink>\nJoin my support server: https://discord.gg/sSzByu")
    }
}