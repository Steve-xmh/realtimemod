package net.stevexmh.realtimemod;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.stevexmh.realtimemod.utils.RealTime;
import java.util.function.Function;

public class PersistentWorldData extends PersistentState implements Function<NbtCompound, PersistentWorldData> {

    public boolean enabled = true;
    public long startTime = RealTime.getRealMinecraftDateTime();

    public static PersistentWorldData getFromPersistentStateManager(PersistentStateManager manager) {
        return manager.getOrCreate(
                PersistentWorldData::getFromNbtCompound,
                PersistentWorldData::new,
                "realtimemod"
        );
    }

    public static PersistentWorldData getFromNbtCompound(NbtCompound nbtCompound) {
        var result = new PersistentWorldData();
        result.enabled = nbtCompound.getBoolean("enabled");
        result.startTime = nbtCompound.getLong("startTime");
        if (result.startTime == 0) {
            result.startTime = RealTime.getRealMinecraftDateTime();
        }
        return result;
    }

    public void setToPersistentStateManager(PersistentStateManager manager) {
        manager.set("realtimemod", this);
    }

    /**
     * Applies this function to the given argument.
     *
     * @param nbtCompound the function argument
     * @return the function result
     */
    @Override
    public PersistentWorldData apply(NbtCompound nbtCompound) {
        return getFromNbtCompound(nbtCompound);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("enabled", enabled);
        nbt.putLong("startTime", startTime);
        return nbt;
    }
}
