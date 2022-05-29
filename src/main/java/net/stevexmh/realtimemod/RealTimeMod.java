package net.stevexmh.realtimemod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameRules;
import net.stevexmh.realtimemod.commands.CheckTimeCommand;
import net.stevexmh.realtimemod.utils.RealTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealTimeMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("realtimemod");
    private PersistentWorldData worldData = new PersistentWorldData();

    @Override
    public void onInitialize() {
        ServerWorldEvents.LOAD.register((server, world) -> {
            if (server.getOverworld() == world) {
                LOGGER.info("Initializing real time world!");
                worldData = PersistentWorldData.getFromPersistentStateManager(world.getPersistentStateManager());
                if (worldData.enabled) {
                    LOGGER.info("Real Time World is enabled!");
                }
                world.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(!worldData.enabled, server);
            }
        });
        EntitySleepEvents.ALLOW_SLEEPING.register((entity, sleepingPos) -> {
            if (worldData.enabled) {
                entity.sendMessage(new TranslatableText("realtimemod.cantsleepsinceenabled"), false);
                return PlayerEntity.SleepFailureReason.OTHER_PROBLEM;
            } else {
                return null;
            }
        });
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            if (worldData.enabled) {
                server.getOverworld().setTimeOfDay(RealTime.getRealMinecraftTime() - worldData.startTime);
            }
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) ->
                CheckTimeCommand.register(dispatcher)
        );
    }
}
