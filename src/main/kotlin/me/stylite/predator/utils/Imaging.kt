package me.stylite.predator.utils

import kotlinx.coroutines.future.await
import me.stylite.predator.models.stats.ApexProfile
import java.awt.Color
import java.awt.Font
import java.awt.Image
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.util.concurrent.CompletableFuture
import javax.imageio.ImageIO

object Imaging {

    suspend fun generateProfileCard(profile: ApexProfile): ByteArray {
        return CompletableFuture.supplyAsync { generateProfileCard0(profile) }
            .await()
    }

    /**
     * Scales an image, preserving its aspect ratio.
     */
    private fun scale(bufferedImage: BufferedImage, width: Int): Image {
        val aspectRatio = bufferedImage.width.toDouble() / bufferedImage.height
        val newHeight = (width / aspectRatio).toInt()
        return bufferedImage.getScaledInstance(width, newHeight, bufferedImage.type)
    }

    private fun generateProfileCard0(profile: ApexProfile): ByteArray {
        val baseFont = Resources.font
        // Fonts
        val headerFont = baseFont.deriveFont(32f)
        val barFont = baseFont.deriveFont(24f)
        val subText = baseFont.deriveFont(18f)
        // Colors
        val white = Color(255, 255, 255)
        val notQuiteWhite = Color(235, 235, 235)
        val barRed = Color(218, 41, 42)
        val black = Color(0, 0, 0)
        val gray = Color(168, 168, 168)
        val lightGray = Color(191, 191, 191)

        val base = ImageIO.read(Resources.card)
        val gfx = base.createGraphics()
        gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP)

        gfx.font = headerFont
        gfx.color = white

        val nameMetrics = gfx.fontMetrics
        val width = nameMetrics.stringWidth(profile.global.name)
        val nameX = 251 + (221 - width) / 2  // 292
        gfx.drawString(profile.global.name, nameX, 143 + nameMetrics.ascent)

        val barWidth = (219 * (profile.global.toNextLevelPercent.toDouble() / 100)).toInt()
        gfx.color = barRed
        gfx.fillRect(253, 183, barWidth, 31)

        gfx.font = barFont
        gfx.color = black

        val levelMetrics = gfx.getFontMetrics(barFont)
        val barText = "Level ${profile.global.level}"
        val levelWidth = levelMetrics.stringWidth(barText)
        val levelX = 252 + (221 - levelWidth) / 2
        val glyphVector = barFont.layoutGlyphVector(gfx.fontRenderContext, barText.toCharArray(), 0, barText.length, Font.LAYOUT_LEFT_TO_RIGHT)
        val levelY = 190 + glyphVector.visualBounds.height.toInt()
        gfx.drawString("Level ${profile.global.level}", levelX, levelY)

        val legendName = profile.legends.selected.LegendName
        val legend = Resources.legend(legendName.decapitalize())
        val legendImg = ImageIO.read(legend)

        val aspectRatio = legendImg.width.toDouble() / legendImg.height
        val newHeight = (244 / aspectRatio).toInt()
        val image = scale(legendImg, 244)

        val heightAdjust = when {
            newHeight > 336 -> 78 - (newHeight - 336)
            newHeight < 336 -> 78 + (336 - newHeight)
            else -> 78
        }

        gfx.drawImage(image, 0, heightAdjust, null)

        gfx.font = headerFont
        gfx.color = gray

        val legendWidth = nameMetrics.stringWidth(legendName)
        val legendNameX = 11 + (221 - legendWidth) / 2
        gfx.drawString(legendName, legendNameX, 432 + nameMetrics.ascent)

        gfx.font = subText
        gfx.color = lightGray
        //gfx.drawString("Total Kills: ${profile.total.kills.value}", 13, 488)

        val entries = profile.legends.selected.data.filter { it.name != null }.take(6)
        for ((i, entry) in entries.withIndex()) {
            val offset = 472 + (28 * (i + 1))
            gfx.drawString("${entry.name}: ${entry.value}", 13, offset)
        }

        val rank = profile.global.rank
        val riResource = Resources.rank("${rank.rankName.decapitalize()}${rank.rankDiv}")
        val rankIcon = ImageIO.read(riResource)

        gfx.drawImage(rankIcon, 300, 240, null)

        gfx.color = notQuiteWhite
        gfx.drawString("${rank.rankName} (Division ${rank.rankDiv})", 280, 357 + nameMetrics.ascent)

        val subTextMetrics = gfx.fontMetrics
        gfx.drawString("Ranked Score: ${rank.rankScore}", 280, 392 + subTextMetrics.ascent)

        val bpBadge = Resources.battlepass
        val bpIcon = ImageIO.read(bpBadge)
        val scaledBp = scale(bpIcon, 105)

        gfx.drawImage(scaledBp, 310, 437, null)

        gfx.font = barFont

        val bpLevelWidth = levelMetrics.stringWidth("Level: ${profile.global.battlepass.level}")
        val bpLevelX = 310 + (105 - bpLevelWidth) / 2
        gfx.drawString("Level: ${profile.global.battlepass.level}", bpLevelX, 585)

        gfx.dispose()

        return ByteArrayOutputStream().use {
            ImageIO.write(base, "png", it)
            it.toByteArray()
        }
    }

}
