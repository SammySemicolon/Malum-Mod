package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.spirit_altar.IAltarProvider;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import com.sammy.malum.core.systems.blockentity.SimpleInventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.awt.*;


public class ItemStandTileEntity extends SimpleInventoryBlockEntity implements IAltarProvider
{
    public ItemStandTileEntity(BlockPos pos, BlockState state)
    {
        super(TileEntityRegistry.ITEM_STAND_TILE_ENTITY.get(), pos, state);
        inventory = new SimpleBlockEntityInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ItemStandTileEntity.this.setChanged();
                setChanged();
                MalumHelper.updateAndNotifyState(level, worldPosition);
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

    public static Vec3 itemPos(SimpleInventoryBlockEntity tileEntity)
    {
        return MalumHelper.fromBlockPos(tileEntity.getBlockPos()).add(itemOffset(tileEntity));
    }
    public static Vec3 itemOffset(SimpleInventoryBlockEntity tileEntity)
    {
        Direction direction = tileEntity.getBlockState().getValue(BlockStateProperties.FACING_HOPPER);
        Vec3 directionVector = new Vec3(direction.getStepX(), 0.5f, direction.getStepZ());
        return new Vec3(0.5f - directionVector.x() * 0.25f, directionVector.y(), 0.5f - directionVector.z() * 0.25f);
    }


    public void tick()
    {
        if (level.isClientSide) {
            if (inventory.getStackInSlot(0).getItem() instanceof MalumSpiritItem) {
                MalumSpiritItem item = (MalumSpiritItem) inventory.getStackInSlot(0).getItem();
                Color color = item.type.color;
                Vec3 pos = itemPos(this);
                double x = pos.x;
                double y = pos.y + Math.sin((level.getGameTime() % 360) / 20f) * 0.05f;
                double z = pos.z;
                SpiritHelper.spawnSpiritParticles(level, x, y, z, color);
            }
        }
    }
}
