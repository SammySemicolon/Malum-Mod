package com.sammy.malum.common.spiritrite.eldritch;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.curiosities.rite.AerialBlockFallRiteEffectPacket;
import com.sammy.malum.common.spiritrite.BlockAffectingRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteType;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchAerialRiteType extends TotemicRiteType {
    public EldritchAerialRiteType() {
        super("greater_aerial_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                if (level instanceof ServerLevel serverLevel) {
                    BlockPos pos = totemBase.getBlockPos();
                    getBlocksAhead(totemBase).forEach(p -> {
                        BlockState stateBelow = level.getBlockState(p.below());
                        if (FallingBlock.isFree(stateBelow) || !stateBelow.canOcclude() || stateBelow.is(BlockTags.SLABS)) {
                            BlockState state = level.getBlockState(p);
                            if (!state.isAir() && level.getBlockEntity(p) == null && canSilkTouch(serverLevel, pos, state)) {
                                FallingBlockEntity.fall(level, p, state);
                                level.playSound(null, p, SoundRegistry.AERIAL_FALL.get(), SoundSource.BLOCKS, 0.5f, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
                                MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new AerialBlockFallRiteEffectPacket(AERIAL_SPIRIT.getPrimaryColor(), p));
                            }
                        }
                    });
                }
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, ServerPlayer.class).forEach(p -> {
                    ServerStatsCounter stats = p.getStats();
                    Stat<ResourceLocation> sleepStat = Stats.CUSTOM.get(Stats.TIME_SINCE_REST);
                    int value = stats.getValue(sleepStat);
                    stats.setValue(p, sleepStat, Math.max(0, value - 1000));
                    ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(p, new ColorEffectData(AERIAL_SPIRIT));
                });
            }
        };
    }

    private static final List<Item> TOOLS = List.of(Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_HOE);
    // From Botania, modified slightly

    private static ItemStack getToolForState(BlockState state) {
        if (!state.requiresCorrectToolForDrops()) {
            return new ItemStack(Items.NETHERITE_PICKAXE);
        } else {
            for (Item item : TOOLS) {
                ItemStack stack = new ItemStack(item);
                if (stack.isCorrectToolForDrops(state)) {
                    return stack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    private static boolean canSilkTouch(ServerLevel level, BlockPos pos, BlockState state) {
        if (state.is(BlockTagRegistry.GREATER_AERIAL_WHITELIST)) {
            return true;
        }

        ItemStack harvestToolStack = getToolForState(state);
        if (harvestToolStack.isEmpty()) {
            return false;
        }
        harvestToolStack.enchant(Enchantments.SILK_TOUCH, 1);
        List<ItemStack> drops = Block.getDrops(state, level, pos, null, null, harvestToolStack);
        Item blockItem = state.getBlock().asItem();
        return drops.stream().anyMatch(s -> s.getItem() == blockItem);
    }
}
