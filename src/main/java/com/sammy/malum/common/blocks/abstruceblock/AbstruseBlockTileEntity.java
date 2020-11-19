package com.sammy.malum.common.blocks.abstruceblock;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.ItemHandlerHelper;

public class AbstruseBlockTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public AbstruseBlockTileEntity()
    {
        super(MalumTileEntities.ABSTRUSE_BLOCK_TILE_ENTITY.get());
    }
    public int progress = 0;
    public PlayerEntity owner;
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        super.readData(compound);
    }
    
    @Override
    public void tick()
    {
        progress++;
        if (progress > 100)
        {
            world.destroyBlock(pos, false);
        }
    }
    
    @Override
    public void remove()
    {
        if (owner != null)
        {
            world.playSound(null, pos, MalumSounds.ABSTRUSE_BLOCK_RETURN, SoundCategory.BLOCKS, 1, MalumMod.RANDOM.nextFloat() * 1.5f);
            ItemHandlerHelper.giveItemToPlayer(owner, new ItemStack(MalumItems.ABSTRUSE_BLOCK.get()));
        }
        else
        {
            world.addEntity(new ItemEntity(world, pos.getX(),pos.getY(),pos.getZ(),new ItemStack(MalumItems.ABSTRUSE_BLOCK.get())));
        }
        super.remove();
    }
}