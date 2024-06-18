package com.sammy.malum.visual_effects.networked.pylon;

import com.sammy.malum.common.block.curiosities.repair_pylon.RepairPylonCoreBlockEntity;
import com.sammy.malum.common.block.storage.IMalumSpecialItemAccessPoint;
import com.sammy.malum.visual_effects.RepairPylonParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import team.lodestar.lodestone.helpers.BlockHelper;

import java.util.function.Supplier;

public class PylonRepairParticleEffect extends ParticleEffectType {

    public PylonRepairParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(BlockPos holderPos) {
        NBTEffectData effectData = new NBTEffectData(new CompoundTag());
        BlockHelper.saveBlockPos(effectData.compoundTag, holderPos);
        return effectData;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof RepairPylonCoreBlockEntity pylon)) {
                return;
            }
            if (!(level.getBlockEntity(BlockHelper.loadBlockPos(nbtData.compoundTag)) instanceof IMalumSpecialItemAccessPoint holder)) {
                return;
            }
            RepairPylonParticleEffects.repairItemParticles(pylon, holder, colorData);
        };
    }
}