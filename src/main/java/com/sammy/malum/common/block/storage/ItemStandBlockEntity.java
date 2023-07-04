package com.sammy.malum.common.block.storage;

import com.sammy.malum.client.*;
import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;


public class ItemStandBlockEntity extends ItemHolderBlockEntity implements IAltarProvider {

    public ItemStandBlockEntity(BlockEntityType<? extends ItemStandBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = new LodestoneBlockEntityInventory(1, 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    public ItemStandBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.ITEM_STAND.get(), pos, state);
    }

    @Override
    public LodestoneBlockEntityInventory getInventoryForAltar() {
        return inventory;
    }

    @Override
    public Vec3 getItemPosForAltar() {
        return getItemPos();
    }

    @Override
    public BlockPos getBlockPosForAltar() {
        return worldPosition;
    }

    public Vec3 getItemPos() {
        return BlockHelper.fromBlockPos(getBlockPos()).add(itemOffset());
    }

    public Vec3 itemOffset() {
        Direction direction = getBlockState().getValue(BlockStateProperties.FACING);
        Vec3 directionVector = new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ());
        return new Vec3(0.5f - directionVector.x() * 0.25f, 0.5f - directionVector.y() * 0.1f, 0.5f - directionVector.z() * 0.25f);
    }

    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        Direction direction = getBlockState().getValue(BlockStateProperties.FACING);
        BlockPos totemPolePos = getBlockPos().relative(direction.getOpposite());
        if (level.getBlockEntity(totemPolePos) instanceof TotemPoleBlockEntity totemPole) {
            TotemBaseBlockEntity totemBase = totemPole.totemBase;
            if (totemBase != null) {
                totemBase.addFilter(this);
                BlockHelper.updateState(level, totemBase.getBlockPos());
                BlockHelper.updateState(level, totemPole.getBlockPos());
            }
        }
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem item) {
                Vec3 pos = getItemPos();
                double x = pos.x;
                double y = pos.y + Math.sin((level.getGameTime()) / 20f) * 0.05f;
                double z = pos.z;
                ParticleEffects.spawnSpiritGlimmerParticles(level, x, y, z, item.type.getPrimaryColor(), item.type.getSecondaryColor());
            }
        }
    }
}
