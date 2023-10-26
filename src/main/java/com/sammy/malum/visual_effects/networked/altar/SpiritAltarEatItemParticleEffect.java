package com.sammy.malum.visual_effects.networked.altar;

import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class SpiritAltarEatItemParticleEffect extends ParticleEffectType {

    public SpiritAltarEatItemParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(BlockPos holderPos, ItemStack stack) {
        NBTEffectData effectData = new NBTEffectData(stack);
        BlockHelper.saveBlockPos(effectData.compoundTag, holderPos);
        return effectData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof SpiritAltarBlockEntity spiritAltar)) {
                return;
            }
            if (!(level.getBlockEntity(BlockHelper.loadBlockPos(nbtData.compoundTag)) instanceof MalumItemHolderBlockEntity itemHolderBlockEntity)) {
                return;
            }
            SpiritAltarParticleEffects.eatItemParticles(spiritAltar, itemHolderBlockEntity, colorData, nbtData.getStack());
        };
    }
}