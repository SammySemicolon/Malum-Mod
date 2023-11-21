package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.blight.BlightedSoilBlock;
import com.sammy.malum.common.block.storage.IMalumSpecialItemAccessPoint;
import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.blight.BlightMistParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.common.recipe.SpiritTransmutationRecipe;
import com.sammy.malum.common.worldevent.TotemCreatedBlightEvent;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.handlers.WorldEventHandler;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;

import java.util.List;
import java.util.Set;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class ArcaneRiteType extends MalumRiteType {
    public ArcaneRiteType() {
        super("arcane_rite", ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.ONE_TIME_EFFECT) {

            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                WorldEventHandler.addWorldEvent(totemBase.getLevel(), new TotemCreatedBlightEvent().setPosition(totemBase.getBlockPos()).setBlightData(1, 4, 4));
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT) {

            @Override
            public boolean canAffectBlock(TotemBaseBlockEntity totemBase, Set<Block> filters, BlockState state, BlockPos pos) {
                pos = pos.above();
                return super.canAffectBlock(totemBase, filters, totemBase.getLevel().getBlockState(pos), pos);
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                BlockPos pos = totemBase.getBlockPos();
                List<BlockPos> nearbyBlocks = getNearbyBlocks(totemBase, BlightedSoilBlock.class).toList();
                for (BlockPos p : nearbyBlocks) {
                    BlockPos posToTransmute = p.above();
                    BlockState stateToTransmute = level.getBlockState(posToTransmute);
                    if (level.getBlockEntity(posToTransmute) instanceof IMalumSpecialItemAccessPoint iMalumSpecialItemAccessPoint) {
                        LodestoneBlockEntityInventory inventoryForAltar = iMalumSpecialItemAccessPoint.getSuppliedInventory();
                        ItemStack stack = inventoryForAltar.getStackInSlot(0);
                        var recipe = SpiritTransmutationRecipe.getRecipe(level, stack);
                        if (recipe != null) {
                            Vec3 itemPos = iMalumSpecialItemAccessPoint.getItemPos();
                            level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, recipe.output.copy()));
                            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new BlightTransformItemParticlePacket(List.of(ARCANE_SPIRIT.identifier), itemPos));
                            inventoryForAltar.getStackInSlot(0).shrink(1);
                            BlockHelper.updateAndNotifyState(level, p);
                        }
                    }
                    ItemStack stack = stateToTransmute.getBlock().asItem().getDefaultInstance();
                    var recipe = SpiritTransmutationRecipe.getRecipe(level, stack);
                    if (recipe != null) {
                        ItemStack output = recipe.output.copy();
                        if (output.getItem() instanceof BlockItem blockItem) {
                            Block block = blockItem.getBlock();
                            BlockEntity entity = level.getBlockEntity(posToTransmute);
                            BlockState newState = BlockHelper.setBlockStateWithExistingProperties(level, posToTransmute, block.defaultBlockState(), 3);
                            level.levelEvent(2001, posToTransmute, Block.getId(newState));
                            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(posToTransmute)), new BlockSparkleParticlePacket(ARCANE_SPIRIT.getPrimaryColor(), posToTransmute));
                            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(posToTransmute)), new BlightMistParticlePacket(posToTransmute)); //TODO: convert these 2 into a single packet, rlly don't feel like doing it rn
                            if (block instanceof EntityBlock entityBlock) {
                                if (entity != null) {
                                    BlockEntity newEntity = entityBlock.newBlockEntity(pos, newState);
                                    if (newEntity != null) {
                                        if (newEntity.getClass().equals(entity.getClass())) {
                                            level.setBlockEntity(entity);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }
}
