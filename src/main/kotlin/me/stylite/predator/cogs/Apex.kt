package me.stylite.predator.cogs

import com.google.gson.Gson
import me.devoxin.flight.api.Context
import me.devoxin.flight.api.annotations.Command
import me.devoxin.flight.api.entities.Cog
import me.stylite.predator.models.ApexNews
import me.stylite.predator.models.ApexOauthPC
import me.stylite.predator.models.ApexProfile
import me.stylite.predator.utils.Http
import me.stylite.predator.utils.RandomItems
import net.dv8tion.jda.api.EmbedBuilder


class Apex : Cog {

    val http = Http()
    val gson = Gson()
    @Command(description = "Get stats of a player")
    suspend fun stats(ctx: Context, platform: String, username: String) {
        val stats = http.fetchStats(platform, username)
        val user = gson.fromJson(stats.body(), ApexProfile::class.java)
        val stat = StringBuilder()
        stat.append("Current legend: ${user.legends.selected.LegendName}\n")
        user.legends.selected.data.forEach {
            stat.append("${it.name}: ${it.value}\n")
        }
        ctx.send {
            setTitle("Stats for $username")
            setDescription(stat.toString())
            setThumbnail(user.legends.selected.ImgAssets.icon)
            setFooter("Info provided https://mozambiquehe.re/")
        }
    }


    @Command(description = "Gives a random loadout and legend")
    fun random(ctx: Context) {
       val loadout =  RandomItems.generateLoadout()
        ctx.send {
            setTitle("Here's a random loadout")
            setDescription(loadout)
        }
    }

    @Command(description = "Ranked stats on a player")
    suspend fun ranked(ctx: Context, platform: String, username: String) {
        val stats = http.fetchStats(platform, username)
        val user = gson.fromJson(stats.body(), ApexProfile::class.java)
        val stat = StringBuilder()
        stat.append("Current rank: ${user.global.rank.rankName}\n")
            .append("Ranked score: ${user.global.rank.rankScore}\n")
            .append("Ranked division: ${user.global.rank.rankDiv}")
        ctx.send {
            setTitle("Stats for $username")
            setDescription(stat.toString())
            setThumbnail(user.global.rank.rankImg)
            setFooter("Info provided https://mozambiquehe.re/")
        }
    }

    @Command(description = "Global stats on a player")
    suspend fun global(ctx: Context, platform: String, username: String) {
        val stats = http.fetchStats(platform, username)
        val user = gson.fromJson(stats.body(), ApexProfile::class.java)
        val stat = StringBuilder()
        stat.append("Level: ${user.global.level}\n")
            .append("Rank: ${user.global.rank.rankName}\n")
            .append("Percent to next level: ${user.global.toNextLevelPercent}\n")
            .append("Battlepass level: ${user.global.battlepass.level}\n")
        ctx.send {
            setTitle("Stats for $username")
            setThumbnail(user.legends.selected.ImgAssets.icon)
            setDescription(stat.toString())
            setFooter("Info provided https://mozambiquehe.re/")
        }
    }

    @Command(description = "Get recent Apex news")
    suspend fun news(ctx: Context) {
        val newNews = http.fetchNews()
        val news = gson.fromJson(newNews.body(), ApexNews::class.java).take(3)
        val stringBuilder = StringBuilder()
        for (new in news) {
            stringBuilder.append("[${new.title}](${new.link})\n${new.short_desc}\n")
        }
        val em = EmbedBuilder()
        em.setDescription(stringBuilder.toString())
        em.setTitle("Apex legends news")
        em.build()
        ctx.sendAsync {
            setTitle("Apex legends news")
            setDescription(stringBuilder.toString().replace("&#x27;","'"))
        }
    }
}