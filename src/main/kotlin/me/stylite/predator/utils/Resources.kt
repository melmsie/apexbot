package me.stylite.predator.utils

import java.awt.Font
import java.io.File
import java.io.IOException
import java.io.InputStream

object Resources {
    private val defaultFont = this::class.java.classLoader.getResourceAsStream("font.ttf")

    val card: InputStream
        get() = this::class.java.classLoader.getResourceAsStream("card.png")!!

    val battlepass: InputStream
        get() = this::class.java.classLoader.getResourceAsStream("battlepass.png")!!

    val font = try {
        Font.createFont(Font.TRUETYPE_FONT, defaultFont)
    } catch (e: IOException) {
        Font.createFont(Font.TRUETYPE_FONT, File(System.getenv("FONT_PATH")))
    }

    fun legend(name: String) = this::class.java.classLoader.getResourceAsStream("legends/$name.png")!!
    fun rank(name: String) = this::class.java.classLoader.getResourceAsStream("ranks/$name.png")!!
}
