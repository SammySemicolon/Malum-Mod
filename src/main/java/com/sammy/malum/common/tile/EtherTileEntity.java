package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.ether.EtherTorchBlock;
import com.sammy.malum.common.block.ether.WallEtherTorchBlock;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.item.ether.EtherItem;
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
    public Color firstColor;
    public Color secondColor;

    public EtherTileEntity()
    {
        super(MalumTileEntities.ETHER_BLOCK_TILE_ENTITY.get());
    }


    @Override
    public void readData(CompoundNBT compound)
    {
        int packedColor = compound.getInt("firstColor");
        int red = ColorHelper.PackedColor.getRed(packedColor);
        int green = ColorHelper.PackedColor.getGreen(packedColor);
        int blue = ColorHelper.PackedColor.getBlue(packedColor);
        firstColor = new Color(red, green, blue);

        packedColor = compound.getInt("secondColor");
        red = ColorHelper.PackedColor.getRed(packedColor);
        green = ColorHelper.PackedColor.getGreen(packedColor);
        blue = ColorHelper.PackedColor.getBlue(packedColor);
        secondColor = new Color(red, green, blue);
        super.readData(compound);
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putInt("firstColor", firstColor.getRGB());
        compound.putInt("secondColor", secondColor.getRGB());
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
            if (firstColor == null || secondColor == null)
            {
                return;
            }
            Color firstColor = MalumHelper.darker(this.firstColor, 2);
            Color secondColor = MalumHelper.brighter(this.secondColor, 1);


            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.6;
            double z = pos.getZ() + 0.5;
            int lifeTime = 14 + world.rand.nextInt(4);
            float scale = 0.17f + world.rand.nextFloat() * 0.03f;
            float velocity = 0.04f + world.rand.nextFloat() * 0.02f;
            if (getBlockState().getBlock() instanceof WallEtherTorchBlock)
            {
                Direction direction = getBlockState().get(WallTorchBlock.HORIZONTAL_FACING);
                x += direction.getDirectionVec().getX() * -0.28f;
                y += 0.2f;
                z += direction.getDirectionVec().getZ() * -0.28f;
                lifeTime-=6;
            }

            if (getBlockState().getBlock() instanceof EtherTorchBlock)
            {
                lifeTime-=4;
            }
            if (getBlockState().getBlock() instanceof EtherBrazierBlock)
            {
                y -= 0.2f;
                lifeTime-=2;
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