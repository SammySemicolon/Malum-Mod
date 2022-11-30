package com.sammy.malum.common.blockentity.alteration_plinth;

import com.sammy.malum.common.blockentity.storage.ItemPedestalBlockEntity;
import com.sammy.malum.common.packets.particle.block.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.common.recipe.AugmentingRecipe;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;

import java.util.List;
import java.util.Random;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;

public class AlterationPlinthBlockEntity extends ItemPedestalBlockEntity {
    public AlterationPlinthBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ALTERATION_PLINTH.get(), pos, state);
        inventory = new LodestoneBlockEntityInventory(1, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.5f, 0.5f);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack target = inventory.getStackInSlot(0);
        if (!target.isEmpty()) {
            ItemStack applied = player.getItemInHand(hand);
            AugmentingRecipe recipe = AugmentingRecipe.getRecipe(level, target, applied);
            if (recipe != null) {
                if (!level.isClientSide) {
                    RandomSource random = level.random;
                    CompoundTag tag = target.getOrCreateTag();
                    CompoundTag modifiedTag = tag.copy().merge(recipe.tagAugment);
                    if (tag.equals(modifiedTag)) {
                        return InteractionResult.FAIL;
                    }
                    target.setTag(modifiedTag);
                    Vec3 pos = getItemPos();
                    ItemEntity pEntity = new ItemEntity(level, pos.x(), pos.y(), pos.z(), target);
                    pEntity.setDeltaMovement(Mth.nextFloat(random, -0.1F, 0.1F), Mth.nextFloat(random, 0.25f, 0.5f), Mth.nextFloat(random, -0.1F, 0.1F));
                    MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(getBlockPos())), new BlightTransformItemParticlePacket(List.of(ARCANE_SPIRIT.identifier), pos));
                    level.playSound(null, worldPosition, SoundRegistry.ALTERATION_PLINTH_ALTERS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.25f);

                    level.addFreshEntity(pEntity);
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                    if (!player.isCreative()) {
                        applied.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.onUse(player, hand);
    }
}