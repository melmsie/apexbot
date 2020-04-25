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
        val stats = StringBuilder()
        stats.append("```ini\nJDA: ${JDAInfo.VERSION}\n")
            .append("Version: 1.0\n")
            .append("Commands loaded: ${Predator.commandHandler.commands.size}\n")
            .append("Guild Count: ${Predator.shardManager.guilds.size}\n")
            .append("Shard Count: ${Predator.shardManager.shards.size}\n")
            .append("Ping: ${Predator.shardManager.averageGatewayPing}ms\n")
            .append("```")
        ctx.send(stats.toString())
    }

    @Command(description = "FAQ")
    fun faq(ctx: Context) {
        ctx.send {
            setTitle("Frequently asked questions")
            setDescription("**If your battlepass level shows as -1**\nDisplay the battlepass badge at least once on your character card and rerun the command")
        }
    }

    @Command(description = "Get links for the bot")
    fun invite(ctx: Context) {
        val inviteLink = Predator.shardManager.retrieveApplicationInfo().complete().getInviteUrl()
        ctx.send("Invite me: <$inviteLink>\nJoin my support server: https://discord.gg/sSzByu")
    }
}