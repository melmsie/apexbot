package me.stylite.predator.utils

import java.awt.Font
import java.io.InputStream

object Resources {
    private val defaultFont = this::class.java.classLoader.getResourceAsStream("font.ttf")

    val card: InputStream
        get() = this::class.java.classLoader.getResourceAsStream("card.png")!!

    val font = Font.createFont(Font.TRUETYPE_FONT, defaultFont)

    fun legend(name: String) = this::class.java.classLoader.getResourceAsStream("legends/$name.png")!!
}
