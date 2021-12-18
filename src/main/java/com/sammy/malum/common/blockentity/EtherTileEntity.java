package com.sammy.malum.common.blockentity;

import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.ether.EtherTorchBlock;
import com.sammy.malum.common.block.ether.WallEtherTorchBlock;
import com.sammy.malum.core.helper.ClientHelper;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public class EtherTileEntity extends SimpleBlockEntity {
    public Color firstColor;
    public Color secondColor;

    public EtherTileEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ETHER_BLOCK_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound) {
        firstColor = ClientHelper.getColor(compound.getInt("firstColor"));
        secondColor = ClientHelper.getColor(compound.getInt("secondColor"));
        super.load(compound);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (firstColor != null) {
            compound.putInt("firstColor", firstColor.getRGB());
        }
        if (secondColor != null) {
            compound.putInt("secondColor", secondColor.getRGB());
        }
        super.saveAdditional(compound);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (firstColor == null || secondColor == null) {
                return;
            }
            Color firstColor = ClientHelper.darker(this.firstColor, 1);
            Color secondColor = ClientHelper.brighter(this.secondColor, 1);


            double x = worldPosition.getX() + 0.5;
            double y = worldPosition.getY() + 0.6;
            double z = worldPosition.getZ() + 0.5;
            int lifeTime = 14 + level.random.nextInt(4);
            float scale = 0.17f + level.random.nextFloat() * 0.03f;
            float velocity = 0.04f + level.random.nextFloat() * 0.02f;
            if (getBlockState().getBlock() instanceof WallEtherTorchBlock) {
                Direction direction = getBlockState().getValue(WallTorchBlock.FACING);
                x += direction.getNormal().getX() * -0.28f;
                y += 0.2f;
                z += direction.getNormal().getZ() * -0.28f;
                lifeTime -= 6;
            }

            if (getBlockState().getBlock() instanceof EtherTorchBlock) {
                lifeTime -= 4;
            }
            if (getBlockState().getBlock() instanceof EtherBrazierBlock) {
                y -= 0.2f;
                lifeTime -= 2;
                scale *= 1.25f;
            }
            RenderUtilities.create(ParticleRegistry.SPARKLE_PARTICLE)
                    .setScale(scale * 2, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.2f)
                    .setColor(firstColor, secondColor)
                    .setColorCurveMultiplier(1.5f)
                    .spawn(level, x, y, z);

            RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                    .setScale(scale, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.9f, 0.75f)
                    .setColor(firstColor, secondColor)
                    .setColorCurveMultiplier(2f)
                    .addVelocity(0, velocity, 0)
                    .setSpin(level.random.nextFloat() * 0.5f)
                    .spawn(level, x, y, z);

            if (level.getGameTime() % 2L == 0 && level.random.nextFloat() < 0.25f) {
                y += 0.05f;
                RenderUtilities.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                        .setScale(0.75f, 0)
                        .setColor(firstColor, secondColor)
                        .setColorCurveMultiplier(3f)
                        .randomOffset(0.15f, 0.25f)
                        .addVelocity(0, 0.02f, 0)
                        .spawn(level, x, y, z);

                RenderUtilities.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                        .setScale(0.5f, 0)
                        .setColor(firstColor, secondColor)
                        .setColorCurveMultiplier(3f)
                        .randomOffset(0.1f, 0.25f)
                        .addVelocity(0, velocity, 0)
                        .spawn(level, x, y, z);
            }
        }
    }
}