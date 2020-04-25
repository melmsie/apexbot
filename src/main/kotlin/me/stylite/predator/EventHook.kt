package me.stylite.predator

import me.devoxin.flight.api.CommandFunction
import me.devoxin.flight.api.Context
import me.devoxin.flight.api.hooks.DefaultCommandEventAdapter

class EventHook : DefaultCommandEventAdapter() {
    override fun onCommandError(ctx: Context, command: CommandFunction, error: Throwable) {
        ctx.send("```\n${error.localizedMessage}\n${error.stackTrace.joinToString("  \n").take(1800)}```")
    }
}
