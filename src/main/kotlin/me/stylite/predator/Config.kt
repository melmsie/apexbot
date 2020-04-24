package me.stylite.predator

import java.io.FileInputStream
import java.util.Properties

object Config {
    val conf: Properties = Properties()

    init {
        conf.load(FileInputStream("config.properties"))
    }

    val prefix: String = conf.getProperty("prefix")
    val token: String = conf.getProperty("token")
    val api_key: String = conf.getProperty("apiKey")

}