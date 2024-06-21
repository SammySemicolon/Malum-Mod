package com.sammy.malum.visual_effects.networked.pylon;

import com.sammy.malum.common.block.curiosities.repair_pylon.RepairPylonCoreBlockEntity;
import com.sammy.malum.common.block.storage.IMalumSpecialItemAccessPoint;
import com.sammy.malum.visual_effects.RepairPylonParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.BlockHelper;

import java.util.function.Supplier;

public class PylonPrepareRepairParticleEffect extends ParticleEffectType {

    public PylonPrepareRepairParticleEffect(String id) {
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
        return new Supplier<>() {
            @Environment(EnvType.CLIENT)
            @Override
            public ParticleEffectActor get() {
                return new ParticleEffectActor() {
                    @Environment(EnvType.CLIENT)
                    @Override
                    public void act(Level level, RandomSource random, PositionEffectData positionData, ColorEffectData colorData, NBTEffectData nbtData) {
                        if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof RepairPylonCoreBlockEntity pylon)) {
                            return;
                        }
                        if (!(level.getBlockEntity(BlockHelper.loadBlockPos(nbtData.compoundTag)) instanceof IMalumSpecialItemAccessPoint holder)) {
                            return;
                        }
                        RepairPylonParticleEffects.prepareRepairParticles(pylon, holder, colorData);
                    }
                };
            }
        };
    }
}