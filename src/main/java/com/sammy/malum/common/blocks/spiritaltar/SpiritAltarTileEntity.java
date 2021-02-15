package com.sammy.malum.common.blocks.spiritaltar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritkiln.functional.SpiritKilnCoreTileEntity;
import com.sammy.malum.common.items.SpiritSplinterItem;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritAltarTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public SpiritAltarTileEntity()
    {
        super(MalumTileEntities.SPIRIT_ALTAR_TILE_ENTITY.get());
    
        spiritInventory = new SimpleInventory(5, 64, t-> t.getItem() instanceof SpiritSplinterItem)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritAltarTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
    }
    
    public SimpleInventory spiritInventory;
    
    int progress;
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (progress != 0)
        {
            compound.putInt("progress", progress);
        }
        spiritInventory.writeData(compound, "spiritInventory");
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        progress = compound.getInt("progress");
        spiritInventory.readData(compound, "spiritInventory");
        super.readData(compound);
    }
    
    @Override
    public void tick()
    {
    
    }
    
    public static Vector3d itemOffset(SpiritAltarTileEntity tileEntity, int slot)
    {
        return MalumHelper.circlePosition(tileEntity.getWorld(), new Vector3d(0.5f,0.5f,0.5f), 1,slot, tileEntity.spiritInventory.nonEmptyItems());
    }
}