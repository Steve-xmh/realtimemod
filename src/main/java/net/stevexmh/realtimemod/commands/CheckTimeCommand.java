package net.stevexmh.realtimemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.stevexmh.realtimemod.utils.RealTime;

import java.util.Date;

import static net.minecraft.server.command.CommandManager.literal;

public class CheckTimeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("checktime").executes(CheckTimeCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        var realTime = RealTime.getRealTime();
        var realMCTime = RealTime.getRealMinecraftTime();
        var player = ctx.getSource().getPlayer();
        player.sendMessage(Text.of("Real Time: " + new Date(realTime)  + " (" + realTime + ")"), false);
        player.sendMessage(Text.of(
                "Real Minecraft Time: " + realMCTime +
                        " (" + (realMCTime % 24000) + ")"
        ), false);
        return 0;
    }
}
