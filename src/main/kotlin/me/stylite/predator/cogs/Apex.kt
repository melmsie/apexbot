package me.stylite.predator.cogs

import com.google.gson.Gson
import me.devoxin.flight.api.Context
import me.devoxin.flight.api.annotations.Command
import me.devoxin.flight.api.annotations.Greedy
import me.devoxin.flight.api.entities.Cog
import me.stylite.predator.models.APIException
import me.stylite.predator.models.ApexNews
import me.stylite.predator.models.ApexOauthPC
import me.stylite.predator.models.ApexProfile
import me.stylite.predator.utils.Http
import me.stylite.predator.utils.RandomItems
import net.dv8tion.jda.api.EmbedBuilder
import java.net.http.HttpResponse

class Apex : Cog {
    private val http = Http()
    private val gson = Gson()

    private val locations = mapOf(
        "we" to listOf("SKYHOOK", "SURVEY CAMP", "REFINERY", "THE EPICENTER", "DRILL SITE", "FRAGMENT WEST", "FRAGMENT EAST", "OVERLOOK", "LAVA FISSURE", "THE TRAIN YARD", "MIRAGE VOYAGE", "HARVESTER", "THE GEYSER", "THERMAL STATION", "SORTING FACTORY", "THE TREE", "LAVA CITY", "THE DOME"),
        "kc" to listOf("ARTILLERY", "SLUM LAKES", "RELAY", "CONTAINMENT", "THE PIT", "WETLANDS", "RUNOFF", "BUNKER", "LABS", "SWAMPS", "AIRBASE", "THE CAGE", "HYDRO DAM", "MARKET", "SKULL TOWN", "REPULSOR", "THUNDERDOME", "WATER TREATMENT")
    )
    private val validPlatforms = setOf("X1", "PS4", "PC")
    private val aliases = mapOf(
        "worlds edge" to "we",
        "kings canyon" to "kc"
    )
    private val realMapName = mapOf(
        "we" to "World's edge",
        "kc" to "King's canyon"
    )
    private val mapImages = mapOf(
        "we" to "https://cdn.discordapp.com/attachments/703277951277596823/703751520180633640/Screen_Shot_2020-04-25_at_7.37.03_PM.png",
        "kc" to "https://i.imgur.com/xTvittM.png"
    )
    private val colors = mapOf(
        "Bangalore" to 0x7c635f,
        "Bloodhound" to 0xc14340,
        "Caustic" to 0xcaa757,
        "Crypto" to 0xbbd266,
        "Gibraltar" to 0xe5e0da,
        "Lifeline" to 0x8eb5e0,
        "Mirage" to 0xe49419,
        "Octane" to 0x999a54,
        "Pathfinder" to 0xfafd69,
        "Revenant" to 0x9c5052,
        "Wattson" to 0xe2914f,
        "Wraith" to 0x545ca2
    )

    suspend fun apiCommand(ctx: Context, platform: String, username: String, transform: ApexProfile.() -> Unit) {
        val platformUpper = platform.toUpperCase()

        if (platformUpper !in validPlatforms) {
            return ctx.send("Invalid platform. ${validPlatforms.joinToString("`, `", prefix = "`", postfix = "`")}")
        }

        val stats = http.fetchStats(platformUpper, username)

        if (stats.statusCode() != 200) {
            return ctx.send("No Apex Legends player found with that name. Check for spelling errors and try again.")
        }

        val response = stats.body()

        if (response.contains("Error")) {
            val err = gson.fromJson(response, APIException::class.java)
            return ctx.send(err.message)
        }

        val user = gson.fromJson(response, ApexProfile::class.java)
        transform(user)
    }

    @Command(description = "Get stats of a player")
    suspend fun stats(ctx: Context, platform: String, username: String) = apiCommand(ctx, platform, username) {
        val stat = StringBuilder("**Current legend**: ${legends.selected.LegendName}\n")

        for (legend in legends.selected.data) {
            stat.append("**").append(legend.name).append("**: ").append(legend.value).append("\n")
        }

        ctx.send {
            setColor(colors[legends.selected.LegendName] ?: 0xffffff)
            setTitle("Stats for ${global.name} [${platform.toUpperCase()}]")
            setDescription(stat.toString())
            setThumbnail(legends.selected.ImgAssets.icon)
            setFooter("Info provided https://mozambiquehe.re/")
        }
    }

    @Command(description = "Gives a random loadout and legend for you to drop with as a challenge")
    fun random(ctx: Context) {
       val loadout = RandomItems.generateLoadout()
        ctx.send {
            setTitle("Here's a random loadout")
            setDescription(loadout)
        }
    }

    @Command(description = "Gives a random location for you to drop in!")
    fun drop(ctx: Context, @Greedy map: String?) {
        if (map == null) {
            return ctx.send("Usage: `${ctx.trigger}drop <we/kc>`\nExample: `${ctx.trigger}drop we`")
        }

        val lowered = map.toLowerCase()
        val mapName = aliases[lowered] ?: lowered
        val location = locations[mapName]?.random()
            ?: return ctx.send("Valid map choices are `\"we\"` (world's edge) and `\"kc\"` (king's canyon)")

        ctx.send {
            setTitle("Here's a random location")
            setDescription("You should drop at **$location** for an EZ win")
        }
    }

    @Command(description = "Gives an image of a selected map!")
    fun map(ctx: Context, @Greedy map: String?) {
        if (map == null) {
            return ctx.send("Usage: `${ctx.trigger}map <we/kc>`\nExample: `${ctx.trigger}map we`")
        }

        val lowered = map.toLowerCase()
        val mapName = aliases[lowered] ?: lowered
        val image = mapImages[lowered] ?: mapName
        val actualName = realMapName[lowered] ?: lowered

        ctx.send {
            setTitle(actualName)
            setImage(image)
        }
    }

    @Command(description = "Ranked stats on a player")
    suspend fun ranked(ctx: Context, platform: String, username: String) = apiCommand(ctx, platform, username) {
        val stat = StringBuilder("**Current rank**: ${global.rank.rankName}\n")
            .append("**Ranked score**: ${global.rank.rankScore}\n")
            .append("**Ranked division**: ${global.rank.rankDiv}")

        ctx.send {
            setTitle("Stats for ${global.name} [${platform.toUpperCase()}]")
            setDescription(stat.toString())
            setThumbnail(global.rank.rankImg)
            setFooter("Info provided https://mozambiquehe.re/")
        }
    }

    @Command(description = "Global stats on a player")
    suspend fun global(ctx: Context, platform: String, username: String) = apiCommand(ctx, platform, username) {
        val stat = StringBuilder("**Level**: ${global.level}\n")
            .append("**Rank**: ${global.rank.rankName}\n")
            .append("**Percent to next level**: ${global.toNextLevelPercent}\n")
            .append("**Battlepass level**: ${global.battlepass.level}\n")

        ctx.send {
            setTitle("Stats for ${global.name} [${platform.toUpperCase()}]")
            setThumbnail(legends.selected.ImgAssets.icon)
            setDescription(stat.toString())
            setFooter("Info provided https://mozambiquehe.re/")
        }
    }

    @Command(description = "Get recent Apex news")
    suspend fun news(ctx: Context) {
        val newNews = http.fetchNews()
        val news = gson.fromJson(newNews.body(), ApexNews::class.java).take(3)
        val articles = news.joinToString("\n") { "[${it.title}](${it.link})\n${it.short_desc}" }

        ctx.sendAsync {
            setTitle("Apex Legends News")
            setDescription(articles.replace("&#x27;","'"))
        }
    }
}