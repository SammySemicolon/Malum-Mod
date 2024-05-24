package com.sammy.malum.common.components;

import com.sammy.malum.core.handlers.ReserveStaffChargeHandler;
import com.sammy.malum.core.handlers.SoulWardHandler;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class MalumPlayerDataComponent implements AutoSyncedComponent {

    private Player player;
    public SoulWardHandler soulWardHandler = new SoulWardHandler();
    public ReserveStaffChargeHandler reserveStaffChargeHandler = new ReserveStaffChargeHandler();

    public boolean obtainedEncyclopedia;
    public boolean hasBeenRejected;

    public MalumPlayerDataComponent(Player player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        if (tag.contains("soulWardData")) {
            soulWardHandler.deserializeNBT(tag.getCompound("soulWardData"));
        }
        if (tag.contains("staffChargeData")) {
            reserveStaffChargeHandler.deserializeNBT(tag.getCompound("staffChargeData"));
        }

        obtainedEncyclopedia = tag.getBoolean("obtainedEncyclopedia");
        hasBeenRejected = tag.getBoolean("hasBeenRejected");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.put("soulWardData", soulWardHandler.serializeNBT());
        tag.put("staffChargeData", reserveStaffChargeHandler.serializeNBT());

        tag.putBoolean("obtainedEncyclopedia", obtainedEncyclopedia);
        tag.putBoolean("hasBeenRejected", hasBeenRejected);
    }
}
