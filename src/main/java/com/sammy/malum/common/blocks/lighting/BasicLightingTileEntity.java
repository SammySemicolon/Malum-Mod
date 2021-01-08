package com.sammy.malum.common.blocks.lighting;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;

import java.awt.*;

public class BasicLightingTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public BasicLightingTileEntity()
    {
        super(MalumTileEntities.ETHER_BLOCK_TILE_ENTITY.get());
    }
    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            if (getBlockState().getBlock() instanceof IColor)
            {
                IColor color = (IColor) getBlockState().getBlock();
                Color finalColor = color.getColor();
    
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.6;
                double z = pos.getZ() + 0.5;
                int lifeTime = 20 + world.rand.nextInt(3);
                float scale = 0.15f + world.rand.nextFloat() * 0.05f;
                if (getBlockState().getBlock() instanceof WallTorchBlock)
                {
                    Direction direction = getBlockState().get(WallTorchBlock.HORIZONTAL_FACING);
                    x += direction.getDirectionVec().getX() * -0.28f;
                    y += 0.2f;
                    z += direction.getDirectionVec().getZ() * -0.28f;
                    lifeTime = 8;
                }
    
                if (getBlockState().getBlock() instanceof TorchBlock)
                {
                    lifeTime = 8;
                }
                if (getBlockState().getBlock() instanceof EtherBrazierBlock)
                {
                    y -= 0.2f;
                    lifeTime = 12;
                    scale *= 1.25f;
                }
                if (world.rand.nextFloat() < 0.9f)
                {
                    ParticleManager.create(MalumParticles.WISP_PARTICLE)
                            .setScale(scale, 0).setLifetime(lifeTime)
                            .setColor(finalColor.getRed()/255f, finalColor.getGreen()/255f, finalColor.getBlue()/255f, (finalColor.getRed() * 0.5f)/255f,(finalColor.getGreen() * 0.5f)/255f,(finalColor.getBlue() * 0.5f)/255f)
                            .addVelocity(0,0.05f,0)
                            .setSpin(world.rand.nextFloat() * 0.5f)
                            .spawn(world,x,y,z);
                }
                if (world.rand.nextFloat() < 0.1f)
                {
                    ParticleManager.create(MalumParticles.SPIRIT_FLAME)
                            .setScale(0.75f, 0)
                            .setColor(finalColor.getRed()/255f, finalColor.getGreen()/255f, finalColor.getBlue()/255f, (finalColor.getRed() * 0.5f)/255f,(finalColor.getGreen() * 0.5f)/255f,(finalColor.getBlue() * 0.5f)/255f)
                            .randomOffset(0.3f)
                            .addVelocity(0,0.01f,0)
                            .spawn(world,x,y,z);
                }
            }
        }
    }
}