package com.sammy.malum.common.blocks.taintedfurnace;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.recipes.TaintTransfusion;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TaintedFurnaceCoreTileEntity extends MultiblockTileEntity implements ITickableTileEntity
{
    public TaintedFurnaceCoreTileEntity()
    {
        super(MalumTileEntities.TAINTED_FURNACE_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                TaintedFurnaceCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                updateState(world.getBlockState(pos), world, pos);
            }
        };
    }
    public SimpleInventory inventory;
    public int fuel;
    public int progress;
    
    @Override
    public void readData(CompoundNBT compound)
    {
        inventory.readData(compound);
        compound.putInt("fuel", fuel);
        compound.putInt("progress", progress);
        super.readData(compound);
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        inventory.writeData(compound);
        fuel = compound.getInt("fuel");
        progress = compound.getInt("progress");
        return super.writeData(compound);
    }
    
    @Override
    public void tick()
    {
        ItemStack stack = inventory.getStackInSlot(0);
        TaintTransfusion transfusion = TaintTransfusion.getTransfusion(stack.getItem());
        if (transfusion != null)
        {
            if (fuel > 0)
            {
                progress++;
                if (progress >= 200)
                {
                    Vector3f pos = new Vector3f(this.pos.getX()+0.5f, this.pos.getY()+1, this.pos.getZ()+0.5f);
                    Direction direction = getBlockState().get(HORIZONTAL_FACING);
                    pos.sub(direction.toVector3f());
                    ItemEntity entity = new ItemEntity(world, pos.getX(),pos.getY(),pos.getZ(),transfusion.outputItem.getDefaultInstance());
                    world.addEntity(entity);
                    progress = 0;
                    fuel--;
                    inventory.getStackInSlot(0).shrink(1);
                }
            }
        }
        else
        {
            progress = 0;
        }
    }
}
