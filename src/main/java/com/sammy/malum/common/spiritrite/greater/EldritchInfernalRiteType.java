package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.block.BlockSparkleParticlePacket;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.rites.*;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.Optional;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;

public class EldritchInfernalRiteType extends MalumRiteType {
    public EldritchInfernalRiteType() {
        super("eldritch_infernal_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                getBlocksUnderBase(totemBase, Block.class).forEach(p -> {
                    BlockState state = level.getBlockState(p);
                    Optional<SmeltingRecipe> optional = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(new ItemStack(state.getBlock().asItem(), 1)), level);
                    if (optional.isPresent()) {
                        SmeltingRecipe recipe = optional.get();
                        ItemStack output = recipe.getResultItem();
                        if (output.getItem() instanceof BlockItem) {
                            Block block = ((BlockItem) output.getItem()).getBlock();
                            BlockState newState = block.defaultBlockState();
                            level.setBlockAndUpdate(p, newState);
                            level.levelEvent(2001, p, Block.getId(newState));
                            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new BlockSparkleParticlePacket(INFERNAL_SPIRIT.getColor(), p));
                        }
                    }
                });
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(LivingEntity.class, MalumMobEffectRegistry.MINERS_RAGE, INFERNAL_SPIRIT);
    }
}