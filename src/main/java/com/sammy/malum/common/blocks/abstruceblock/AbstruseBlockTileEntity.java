package com.sammy.malum.common.blocks.abstruceblock;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import java.awt.*;

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
        if (progress == 1)
        {
            particles(world, pos, 2);
        }
        if (progress > 100)
        {
            world.destroyBlock(pos, false);
        }
    }
    
    @Override
    public void remove()
    {
        particles(world, pos, 8);
    
        if (owner != null)
        {
            owner.playSound(MalumSounds.ABSTRUSE_BLOCK_RETURNS, 1, world.rand.nextFloat() * 1.5f);
            ItemHandlerHelper.giveItemToPlayer(owner, new ItemStack(MalumItems.ABSTRUSE_BLOCK.get()));
        }
        else
        {
            world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MalumItems.ABSTRUSE_BLOCK.get())));
        }
        super.remove();
    }
    public void particles(World worldIn, BlockPos pos, int countMultiplier)
    {
        if (MalumHelper.areWeOnClient(worldIn))
        {
            Color color = MalumSpiritTypes.AIR_SPIRIT_COLOR;
            Color colorAgain = MalumSpiritTypes.WATER_SPIRIT_COLOR;
            
            ParticleManager.create(MalumParticles.SMOKE_PARTICLE).setAlpha(0.25f, 0f).setLifetime(20).setScale(0.05f, 0).setColor(color, color.darker()).randomVelocity(0f, 0.01f).enableNoClip().evenlyRepeatEdges(worldIn, pos,  countMultiplier).setColor(colorAgain, colorAgain).evenlyRepeatEdges(worldIn, pos,  countMultiplier);
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.05f, 0f).setLifetime(40).setScale(0.2f, 0).setColor(color, color.darker()).randomVelocity(0.0025f, 0.0025f).enableNoClip().evenlyRepeatEdges(worldIn, pos, 2 * countMultiplier).setColor(colorAgain, colorAgain).evenlyRepeatEdges(worldIn, pos, 2 * countMultiplier);
        }
    }

}