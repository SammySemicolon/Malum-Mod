package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.ether.EtherTorchBlock;
import com.sammy.malum.common.block.ether.WallEtherTorchBlock;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.registry.particle.ParticleRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import com.sammy.malum.core.systems.tile.SimpleTileEntity;
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
        super(TileEntityRegistry.ETHER_BLOCK_TILE_ENTITY.get());
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
        if (firstColor != null)
        {
            compound.putInt("firstColor", firstColor.getRGB());
        }
        if (secondColor != null)
        {
            compound.putInt("secondColor", secondColor.getRGB());
        }
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
            Color firstColor = MalumHelper.darker(this.firstColor, 1);
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
                lifeTime -= 6;
            }

            if (getBlockState().getBlock() instanceof EtherTorchBlock)
            {
                lifeTime -= 4;
            }
            if (getBlockState().getBlock() instanceof EtherBrazierBlock)
            {
                y -= 0.2f;
                lifeTime -= 2;
                scale *= 1.25f;
            }
            ParticleManager.create(ParticleRegistry.SPARKLE_PARTICLE)
                    .setScale(scale * 2, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.2f)
                    .setColor(firstColor, secondColor)
                    .setColorCurveMultiplier(1.5f)
                    .spawn(world, x, y, z);

            ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                    .setScale(scale, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.9f, 0.75f)
                    .setColor(firstColor, secondColor)
                    .setColorCurveMultiplier(2f)
                    .addVelocity(0, velocity, 0)
                    .setSpin(world.rand.nextFloat() * 0.5f)
                    .spawn(world, x, y, z);

            if (world.getGameTime() % 2L == 0 && world.rand.nextFloat() < 0.25f)
            {
                y += 0.05f;
                ParticleManager.create(ParticleRegistry.SPIRIT_FLAME)
                        .setScale(0.75f, 0)
                        .setColor(firstColor, secondColor)
                        .setColorCurveMultiplier(3f)
                        .randomOffset(0.15f, 0.25f)
                        .addVelocity(0, 0.02f, 0)
                        .spawn(world, x, y, z);

                ParticleManager.create(ParticleRegistry.SPIRIT_FLAME)
                        .setScale(0.5f, 0)
                        .setColor(firstColor, secondColor)
                        .setColorCurveMultiplier(3f)
                        .randomOffset(0.1f, 0.25f)
                        .addVelocity(0, velocity, 0)
                        .spawn(world, x, y, z);
            }
        }
    }
}