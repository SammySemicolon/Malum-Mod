package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.block.curiosities.totem.*;

import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchAqueousRiteType extends MalumRiteType {
    public EldritchAqueousRiteType() {
        super("greater_aqueous_rite", ELDRITCH_SPIRIT.get(), ARCANE_SPIRIT.get(), AQUEOUS_SPIRIT.get(), AQUEOUS_SPIRIT.get());
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {

            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                getNearbyBlocks(totemBase, PointedDripstoneBlock.class).forEach(p -> {
                    if (level.random.nextFloat() < 0.1f) {
                        for (int i = 0; i < 4 + level.random.nextInt(2); i++) {
                            level.getBlockState(p).randomTick((ServerLevel) level, p, level.random);
                        }
                        ParticleEffectTypeRegistry.DRIPPING_SMOKE.createPositionedEffect(level, new PositionEffectData(p), new ColorEffectData(AQUEOUS_SPIRIT.get().getPrimaryColor()));
                    }
                });
            }

            @Override
            public boolean canAffectBlock(TotemBaseBlockEntity totemBase, Set<Block> filters, BlockState state, BlockPos pos) {
                return super.canAffectBlock(totemBase, filters, state, pos) && PointedDripstoneBlock.isStalactiteStartPos(state, totemBase.getLevel(), pos);
            }

            @Override
            public int getRiteEffectVerticalRadius() {
                return 5;
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, Zombie.class).filter(z -> !(z instanceof Drowned)).forEach(e -> {
                    if (!e.isUnderWaterConverting()) {
                        e.startUnderWaterConversion(100);
                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(AQUEOUS_SPIRIT.get().getPrimaryColor()));
                    }
                });
            }
        };
    }

    @Override
    public MalumRiteType setRegistryName(ResourceLocation name) {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return null;
    }

    @Override
    public Class<MalumRiteType> getRegistryType() {
        return null;
    }
}
