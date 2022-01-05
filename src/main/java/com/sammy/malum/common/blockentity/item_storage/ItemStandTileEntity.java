package com.sammy.malum.common.blockentity.item_storage;

import com.sammy.malum.common.block.spirit_altar.IAltarProvider;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import com.sammy.malum.core.systems.blockentity.SimpleInventoryBlockEntity;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;


public class ItemStandTileEntity extends SimpleInventoryBlockEntity implements IAltarProvider
{
    public ItemStandTileEntity(BlockPos pos, BlockState state)
    {
        super(BlockEntityRegistry.ITEM_STAND.get(), pos, state);
        inventory = new SimpleBlockEntityInventory(1, 64)
        {
            @Override
            public void onContentsChanged(int slot)
            {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    public SimpleBlockEntityInventory providedInventory()
    {
        return inventory;
    }
    @Override
    public Vec3 providedItemPos()
    {
        return itemPos(this);
    }

    public static Vec3 itemPos(SimpleInventoryBlockEntity blockEntity)
    {
        return DataHelper.fromBlockPos(blockEntity.getBlockPos()).add(itemOffset(blockEntity));
    }
    public static Vec3 itemOffset(SimpleInventoryBlockEntity blockEntity)
    {
        Direction direction = blockEntity.getBlockState().getValue(BlockStateProperties.FACING);
        Vec3 directionVector = new Vec3(direction.getStepX(), 0.5f, direction.getStepZ());
        return new Vec3(0.5f - directionVector.x() * 0.25f, directionVector.y(), 0.5f - directionVector.z() * 0.25f);
    }


    @Override
    public void tick()
    {
        if (level.isClientSide) {
            if (inventory.getStackInSlot(0).getItem() instanceof MalumSpiritItem item) {
                Vec3 pos = itemPos(this);
                double x = pos.x;
                double y = pos.y + Math.sin((level.getGameTime() % 360) / 20f) * 0.05f;
                double z = pos.z;
                SpiritHelper.spawnSpiritParticles(level, x, y, z, item.type.color, item.type.endColor);
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
