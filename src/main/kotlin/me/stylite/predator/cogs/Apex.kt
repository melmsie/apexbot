package me.stylite.predator.cogs

import com.google.gson.Gson
import me.devoxin.flight.api.Context
import me.devoxin.flight.api.annotations.Command
import me.devoxin.flight.api.annotations.Greedy
import me.devoxin.flight.api.entities.Cog
import me.stylite.predator.models.ApexNews
import me.stylite.predator.models.ApexOauthPC
import me.stylite.predator.models.ApexProfile
import me.stylite.predator.utils.Http
import me.stylite.predator.utils.RandomItems
import net.dv8tion.jda.api.EmbedBuilder

val locations = mapOf(
    "we" to listOf("SKYHOOK", "SURVEY CAMP", "REFINERY", "THE EPICENTER", "DRILL SITE", "FRAGMENT WEST", "FRAGMENT EAST", "OVERLOOK", "LAVA FISSURE", "THE TRAIN YARD", "MIRAGE VOYAGE", "HARVESTER", "THE GEYSER", "THERMAL STATION", "SORTING FACTORY", "THE TREE", "LAVA CITY", "THE DOME"),
    "kc" to listOf("ARTILLERY", "SLUM LAKES", "RELAY", "CONTAINMENT", "THE PIT", "WETLANDS", "RUNOFF", "BUNKER", "LABS", "SWAMPS", "AIRBASE", "THE CAGE", "HYDRO DAM", "MARKET", "SKULL TOWN", "REPULSOR", "THUNDERDOME", "WATER TREATMENT")
)
val aliases = mapOf("worlds edge" to "we", "kings canyon" to "kc")
val colors = mapOf("Bangalore" to 0x7c635f, "Bloodhound" to 0xc14340, "Caustic" to 0xcaa757, "Crypto" to 0xbbd266, "Gibraltar" to 0xe5e0da, "Lifeline" to 0x8eb5e0, "Mirage" to 0xe49419, "Octane" to 0x999a54, "Pathfinder" to 0xfafd69, "Revenant" to 0x9c5052, "Wattson" to 0xe2914f, "Wraith" to 0x545ca2)

class Apex : Cog {

    val http = Http()
    val gson = Gson()
    @Command(description = "Get stats of a player")
    suspend fun stats(ctx: Context, platform: String, username: String) {
        val stats = http.fetchStats(platform, username)
        val user = gson.fromJson(stats.body(), ApexProfile::class.java)
        val stat = StringBuilder()
        stat.append("**Current legend**: ${user.legends.selected.LegendName}\n")
        user.legends.selected.data.forEach {
            if (it.name == null) return@forEach
            stat.append("**${it.name}**: ${it.value}\n")
        }
        ctx.send {
            setTitle("Stats for ${user.global.name} [$platform]")
            setColor(colors[user.legends.selected.LegendName] ?: 0xffffff)
            setDescription(stat.toString())
            setThumbnail(user.legends.selected.ImgAssets.icon)
            setFooter("Info provided https://mozambiquehe.re/")
        }
    }


    @Command(description = "Gives a random loadout and legend for you to drop with as a challenge")
    fun random(ctx: Context) {
       val loadout =  RandomItems.generateLoadout()
        ctx.send {
            setTitle("Here's a random loadout")
            setDescription(loadout)
        }
    }

    @Command(description = "Gives a random location for you to drop in!")
    fun drop(ctx: Context, @Greedy map: String) {
        val lowered = map.toLowerCase()
        val mapName = aliases[lowered] ?: lowered
        val location = locations[mapName]?.random()
            ?: return ctx.send("Valid map choices are `\"we\"` (world's edge) and `\"kc\"` (king's canyon)")

        ctx.send {
            setTitle("Here's a random location")
            setDescription("You should drop at **$location** for an EZ win")
        }
    }

    @Command(description = "Ranked stats on a player")
    suspend fun ranked(ctx: Context, platform: String, username: String) {
        val stats = http.fetchStats(platform, username)
        val user = gson.fromJson(stats.body(), ApexProfile::class.java)
        val stat = StringBuilder()
        stat.append("**Current rank**: ${user.global.rank.rankName}\n")
            .append("**Ranked score**: ${user.global.rank.rankScore}\n")
            .append("**Ranked division**: ${user.global.rank.rankDiv}")
        ctx.send {
            setTitle("Stats for ${user.global.name} [$platform]")
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
        stat.append("**Level**: ${user.global.level}\n")
            .append("**Rank**: ${user.global.rank.rankName}\n")
            .append("**Percent to next level**: ${user.global.toNextLevelPercent}\n")
            .append("**Battlepass level**: ${user.global.battlepass.level}\n")
        ctx.send {
            setTitle("Stats for ${user.global.name} [$platform]")
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