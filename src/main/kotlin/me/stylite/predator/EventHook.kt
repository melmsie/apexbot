package me.stylite.predator

import me.devoxin.flight.api.CommandFunction
import me.devoxin.flight.api.Context
import me.devoxin.flight.api.exceptions.BadArgument
import me.devoxin.flight.api.hooks.DefaultCommandEventAdapter
import me.stylite.predator.utils.Utils

class EventHook : DefaultCommandEventAdapter() {

    override fun onCommandError(ctx: Context, command: CommandFunction, error: Throwable) {
        ctx.send("There was an error, gibby will be angry...")
        error.printStackTrace()
    }

    override fun onBadArgument(ctx: Context, command: CommandFunction, error: BadArgument) {
        ctx.send("You need to specify `${error.argument.name}`")
    }

    override fun onCommandCooldown(ctx: Context, command: CommandFunction, cooldown: Long) {
        ctx.send("Too fast! (Wait ${Utils.toTimeString(cooldown)})")
    }

}
