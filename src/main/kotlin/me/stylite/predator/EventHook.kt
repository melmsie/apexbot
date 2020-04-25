package me.stylite.predator

import me.devoxin.flight.api.CommandFunction
import me.devoxin.flight.api.Context
import me.devoxin.flight.api.exceptions.BadArgument
import me.devoxin.flight.api.hooks.DefaultCommandEventAdapter

class EventHook : DefaultCommandEventAdapter() {

    override fun onCommandError(ctx: Context, command: CommandFunction, error: Throwable) {
        ctx.send("```\n${error.localizedMessage}```")
    }

    override fun onBadArgument(ctx: Context, command: CommandFunction, error: BadArgument) {
        ctx.send("You need to specify `${error.argument.name}`")
    }

}
