package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.particle_effects.*;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import java.util.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchAqueousRiteType extends MalumRiteType {
    public EldritchAqueousRiteType() {
        super("greater_aqueous_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {

            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                getNearbyBlocks(totemBase, PointedDripstoneBlock.class, getRiteEffectRadius()*4).forEach(p -> {
                    if (level.random.nextFloat() < 0.1f) {
                        for (int i = 0; i < 4 + level.random.nextInt(2); i++) {
                            level.getBlockState(p).randomTick((ServerLevel) level, p, level.random);
                        }
                        ParticleEffectTypeRegistry.DRIPPING_SMOKE.createBlockEffect(level, p, new ColorEffectData(AQUEOUS_SPIRIT.getPrimaryColor()));
                    }
                });
            }

            @Override
            public boolean canAffectBlock(TotemBaseBlockEntity totemBase, Set<Block> filters, BlockState state, BlockPos pos) {
                return super.canAffectBlock(totemBase, filters, state, pos) && PointedDripstoneBlock.isStalactiteStartPos(state, totemBase.getLevel(), pos);
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new EntityAffectingRiteEffect() {
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, Zombie.class).filter(z -> !(z instanceof Drowned)).forEach(e -> {
                    if (!e.isUnderWaterConverting()) {
                        e.startUnderWaterConversion(100);

                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(AQUEOUS_SPIRIT.getPrimaryColor()));
                    }
                });
            }
        };
    }
}
