package me.stylite.predator

import me.stylite.predator.utils.DblTimer
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object JdaEvents : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        DblTimer.postStats()
    }
}