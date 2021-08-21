package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.core.init.block.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.mod_systems.particle.ParticleManager;
import com.sammy.malum.core.mod_systems.tile.SimpleTileEntity;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.Direction;

import java.awt.*;

public class EtherTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public int firstColor;
    public int secondColor;

    public EtherTileEntity()
    {
        super(MalumTileEntities.ETHER_BLOCK_TILE_ENTITY.get());
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        firstColor = compound.getInt("firstColor");
        secondColor = compound.getInt("secondColor");
        super.readData(compound);
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putInt("firstColor", firstColor);
        compound.putInt("secondColor", secondColor);
        return super.writeData(compound);
    }

    @Override
    public void remove()
    {
        super.remove();
    }

    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            int red = ColorHelper.PackedColor.getRed(firstColor);
            int green = ColorHelper.PackedColor.getGreen(firstColor);
            int blue = ColorHelper.PackedColor.getBlue(firstColor);
            Color firstColor = new Color(red, green, blue);

            red = ColorHelper.PackedColor.getRed(this.secondColor);
            green = ColorHelper.PackedColor.getGreen(this.secondColor);
            blue = ColorHelper.PackedColor.getBlue(this.secondColor);
            Color secondColor = new Color(red, green, blue);

            firstColor = MalumHelper.darker(firstColor, 2);
            secondColor = MalumHelper.brighter(secondColor, 1);


            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.6;
            double z = pos.getZ() + 0.5;
            int lifeTime = 14 + world.rand.nextInt(4);
            float scale = 0.17f + world.rand.nextFloat() * 0.03f;
            float velocity = 0.04f + world.rand.nextFloat() * 0.02f;
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
                lifeTime = 8 + world.rand.nextInt(2);
            }
            if (getBlockState().getBlock() instanceof EtherBrazierBlock)
            {
                y -= 0.2f;
                lifeTime = 12 + world.rand.nextInt(3);
                scale *= 1.25f;
            }
            ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                    .setScale(scale * 2, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.2f)
                    .setColor(firstColor, secondColor)
                    .spawn(world, x, y, z);

            if (world.rand.nextFloat() < 0.98f)
            {
                ParticleManager.create(MalumParticles.WISP_PARTICLE)
                        .setScale(scale, 0)
                        .setLifetime(lifeTime)
                        .setAlpha(0.9f, 0.75f)
                        .setColor(firstColor, secondColor)
                        .addVelocity(0, velocity, 0)
                        .setSpin(world.rand.nextFloat() * 0.5f)
                        .spawn(world, x, y, z);
            }
            if (world.getGameTime() % 3L == 0 && world.rand.nextFloat() < 0.5f)
            {
                ParticleManager.create(MalumParticles.SPIRIT_FLAME)
                        .setScale(0.75f, 0)
                        .setColor(firstColor, secondColor)
                        .randomOffset(0.2f, 0.3f)
                        .addVelocity(0, 0.02f, 0)
                        .spawn(world, x, y, z);

                ParticleManager.create(MalumParticles.SPIRIT_FLAME)
                        .setScale(0.5f, 0)
                        .setColor(firstColor, secondColor)
                        .randomOffset(0.1f, 0.3f)
                        .addVelocity(0, velocity, 0)
                        .spawn(world, x, y, z);
            }
        }
    }
}