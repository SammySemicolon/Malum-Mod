package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.*;
import com.sammy.malum.core.systems.rites.*;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchInfernalRiteType extends MalumRiteType {
    public EldritchInfernalRiteType() {
        super("greater_infernal_rite", ELDRITCH_SPIRIT.get(), ARCANE_SPIRIT.get(), INFERNAL_SPIRIT.get(), INFERNAL_SPIRIT.get());
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                getBlocksAhead(totemBase).forEach(p -> {
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
                            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new BlockSparkleParticlePacket(INFERNAL_SPIRIT.get().getPrimaryColor(), p));
                        }
                    }
                });
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                getNearbyBlocks(totemBase, AbstractFurnaceBlock.class).map(b -> level.getBlockEntity(b)).filter(e -> e instanceof AbstractFurnaceBlockEntity).map(e -> (AbstractFurnaceBlockEntity) e).forEach(f -> {
                    if (f.isLit()) {
                        BlockPos blockPos = f.getBlockPos();
                    //    MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPos)), new InfernalAccelerationRiteEffectPacket(INFERNAL_SPIRIT.getPrimaryColor(), blockPos));
                        f.cookingProgress = Math.min(f.cookingProgress + 5, f.cookingTotalTime-1);
                    }
                });
            }

            @Override
            public int getRiteEffectTickRate() {
                return super.getRiteEffectTickRate()/4;
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