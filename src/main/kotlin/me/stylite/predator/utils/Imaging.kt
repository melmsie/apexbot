package me.stylite.predator.utils

import kotlinx.coroutines.future.await
import me.stylite.predator.models.stats.ApexProfile
import java.awt.Color
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture
import javax.imageio.ImageIO

object Imaging {
    private val http = HttpClient.newHttpClient()

    suspend fun generateProfileCard(profile: ApexProfile): ByteArray {
        return CompletableFuture.supplyAsync { generateProfileCard0(profile) }
            .await()
    }

    fun downloadImage(url: String): InputStream {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build()

        return http.send(request, HttpResponse.BodyHandlers.ofInputStream()).body()
    }

    private fun generateProfileCard0(profile: ApexProfile): ByteArray {
        val baseFont = Resources.font
        // Fonts
        val headerFont = baseFont.deriveFont(32f)
        val barFont = baseFont.deriveFont(24f)
        // Colors
        val white = Color(255, 255, 255)
        val barRed = Color(218, 41, 42)
        val black = Color(0, 0, 0)

        val base = ImageIO.read(Resources.card)
        val gfx = base.createGraphics()
        gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP)

        gfx.font = headerFont
        gfx.color = white

        val metrics = gfx.fontMetrics
        val width = metrics.stringWidth(profile.global.name)
        println("width: $width")
        val nameX = 251 + (221 - width) / 2
        // 292
        gfx.drawString(profile.global.name, nameX, 143 + metrics.ascent)

        val barWidth = (219 * (profile.global.toNextLevelPercent.toDouble() / 100)).toInt()
        gfx.color = barRed
        gfx.fillRect(253, 183, barWidth, 31)

        gfx.font = barFont
        gfx.color = black
        gfx.drawString("Level ${profile.global.level}", 314, 188 + (metrics.height / 2))

        val legend = Resources.legend(profile.legends.selected.LegendName.decapitalize())
        val legendImg = ImageIO.read(legend)

        val aspectRatio = legendImg.width.toDouble() / legendImg.height
        val newWidth = 244 // Fixed
        val newHeight = (newWidth / aspectRatio).toInt()
        val image = legendImg.getScaledInstance(newWidth, newHeight, legendImg.type)

        val heightAdjust = when {
            newHeight > 336 -> 78 - (newHeight - 336)
            newHeight < 336 -> 78 + (336 - newHeight)
            else -> 78
        }

        println(heightAdjust)
        gfx.drawImage(image, 0, heightAdjust, null)

        gfx.dispose()

        return ByteArrayOutputStream().use {
            ImageIO.write(base, "png", it)
            it.toByteArray()
        }
    }

}
