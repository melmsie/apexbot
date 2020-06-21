package me.stylite.predator

import java.io.FileInputStream
import java.util.Properties

object Config {
    private val conf = Properties().apply { load(FileInputStream("config.properties")) }

    operator fun get(key: String) = conf.getProperty(key)?.takeIf { it.isNotEmpty() }
        ?: throw IllegalStateException("$key is missing or was empty in config.properties!")

    val prefix = this["prefix"].split(", ")
    val token = this["token"]
    val apiKey = this["apiKey"]
    //val dblKey = this["dblkey"]
}
