package com.sammy.malum.common.blocks.spiritkiln.functional;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes.MalumSpiritKilnRecipe;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;

import static com.sammy.malum.common.blocks.spiritkiln.functional.SpiritKilnCoreBlock.POWERED;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@SuppressWarnings("ConstantConditions")
public class SpiritKilnCoreTileEntity extends MultiblockTileEntity implements ITickableTileEntity
{
    public SpiritKilnCoreTileEntity()
    {
        super(MalumTileEntities.SPIRIT_KILN_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritKilnCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
                SpiritKilnCoreTileEntity.this.currentRecipe = MalumSpiritKilnRecipes.getRecipe(this.getStackInSlot(0));
            }
        };
        outputInventory = new SimpleInventory(3, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritKilnCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
    }
    
    public SimpleInventory inventory;
    public SimpleInventory outputInventory;
    
    public MalumSpiritKilnRecipe currentRecipe;
    public float progress;

    @Override
    public void readData(CompoundNBT compound)
    {
        inventory.readData(compound);
        outputInventory.readData(compound, "outputInventory");
        progress = compound.getFloat("progress");
        
        super.readData(compound);
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        inventory.writeData(compound);
        outputInventory.writeData(compound, "outputInventory");
        compound.putFloat("progress", progress);
        
        return super.writeData(compound);
    }
    
    @Override
    public void tick()
    {
        ItemStack stack = inventory.getStackInSlot(0);
        
    }
    public void passiveSound()
    {
        if (world.rand.nextFloat() < 0.05f)
        {
            world.playSound(null,pos, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
    //endregion
    public void setAndUpdateState(boolean value)
    {
        world.setBlockState(pos,getBlockState().with(POWERED, value));
        world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(POWERED, value));
    
        MalumHelper.updateState(world.getBlockState(pos), world, pos);
        MalumHelper.updateState(world.getBlockState(pos.up()), world, pos.up());
    }
    public void damage()
    {
        world.setBlockState(pos, MalumBlocks.DAMAGED_SPIRIT_KILN.get().getDefaultState().with(HORIZONTAL_FACING, getBlockState().get(HORIZONTAL_FACING)));
        world.setBlockState(pos.up(), MalumBlocks.DAMAGED_SPIRIT_KILN_TOP.get().getDefaultState().with(HORIZONTAL_FACING, getBlockState().get(HORIZONTAL_FACING)));
    
        MalumHelper.updateState(world.getBlockState(pos), world, pos);
        MalumHelper.updateState(world.getBlockState(pos.up()), world, pos.up());
    }
    //endregion
    
    //region vector helpers
    public Vector3d smokeParticleOutputPos()
    {
        Direction direction = getBlockState().get(HORIZONTAL_FACING);
        Vector3d directionVector = new Vector3d(direction.getXOffset() * 0.4f, 0, direction.getZOffset() * 0.4f);
        Vector3d particlePos = MalumHelper.randPos(pos, world.rand, -0.3f, 0.3f);
        Vector3d finalPos = new Vector3d(0, 0, 0);
        if (directionVector.getZ() != 0)
        {
            finalPos = new Vector3d(particlePos.getX() + 0.5f, particlePos.getY() + 2, particlePos.getZ() + 0.5f - directionVector.getZ());
        }
        if (directionVector.getX() != 0)
        {
            finalPos = new Vector3d(particlePos.getX() + 0.5f - directionVector.getX(), particlePos.getY() + 2, particlePos.getZ() + 0.5f);
        }
        return finalPos;
    }
    
    public static Vector3d itemOffset(SimpleTileEntity tileEntity)
    {
        Direction direction = tileEntity.getBlockState().get(HORIZONTAL_FACING);
        Vector3d directionVector = new Vector3d(direction.getXOffset(), 1.25, direction.getZOffset());
        return new Vector3d(0.5 + directionVector.getX() * 0.25, directionVector.getY(), 0.5 + directionVector.getZ() * 0.25);
    }
}