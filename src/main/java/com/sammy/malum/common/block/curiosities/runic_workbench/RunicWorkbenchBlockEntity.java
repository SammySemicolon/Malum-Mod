package com.sammy.malum.common.block.curiosities.runic_workbench;

import com.sammy.malum.common.block.storage.MalumItemHolderBlockEntity;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.common.packets.particle.curiosities.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.common.recipe.RunicWorkbenchRecipe;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.BlockHelper;

import java.util.List;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class RunicWorkbenchBlockEntity extends MalumItemHolderBlockEntity {

    public static final Vec3 RUNIC_WORKBENCH_ITEM_OFFSET = new Vec3(0.5f, 1.25f, 0.5f);

    public RunicWorkbenchBlockEntity(BlockEntityType<? extends RunicWorkbenchBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public RunicWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.RUNIC_WORKBENCH.get(), pos, state);
    }

    @Override
    public Vec3 getItemOffset(float partialTicks) {
        if (inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem) {
            float gameTime = level.getGameTime() + partialTicks;
            return RUNIC_WORKBENCH_ITEM_OFFSET.add(0, (float) Math.sin((gameTime % 360) / 20f) * 0.05f, 0);
        }
        return RUNIC_WORKBENCH_ITEM_OFFSET;
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        final ItemStack primaryInput = inventory.getStackInSlot(0);
        if (!primaryInput.isEmpty()) {
            ItemStack secondaryInput = player.getItemInHand(hand);
            RunicWorkbenchRecipe recipe = RunicWorkbenchRecipe.getRecipe(level, primaryInput, secondaryInput);
            if (recipe != null) {
                Vec3 itemPos = getItemPos();
                if (level instanceof ServerLevel serverLevel) {
                    primaryInput.shrink(recipe.primaryInput.count);
                    secondaryInput.shrink(recipe.secondaryInput.count);
                    level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, recipe.output.copy()));
                    level.playSound(null, worldPosition, SoundRegistry.ALTERATION_PLINTH_ALTERS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.25f);
                    if (secondaryInput.getItem() instanceof SpiritShardItem spiritShardItem) {
                        MALUM_CHANNEL.sendToClientsTracking(new BlightTransformItemParticlePacket(List.of(spiritShardItem.type.identifier), itemPos), serverLevel, level.getChunkAt(getBlockPos()).getPos());
                    }
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
                return InteractionResult.SUCCESS;
            }
        }


        return super.onUse(player, hand);
    }
}