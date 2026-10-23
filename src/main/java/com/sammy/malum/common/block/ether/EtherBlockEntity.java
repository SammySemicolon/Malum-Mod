package com.sammy.malum.common.block.ether;

import com.sammy.malum.common.item.ether.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.apache.commons.lang3.mutable.MutableDouble;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.modules.rendering.LodestoneRenderingSystem;
import team.lodestar.lodestone.modules.toolkit.blockentity.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.rendering.particle.standard.builder.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.color.*;

import java.awt.*;

public class EtherBlockEntity extends LodestoneBlockEntity {

    public DyedItemColor firstColor = EtherItem.DEFAULT_FIRST_COLOR;
    public DyedItemColor secondColor = EtherItem.DEFAULT_SECOND_COLOR;

    public EtherBlockEntity(LodestoneBlockEntityType<? extends EtherBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public EtherBlockEntity(BlockPos pos, BlockState state) {
        this(MalumBlockEntities.ETHER.get(), pos, state);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.DYED_COLOR, firstColor);
        components.set(MalumDataComponents.SECONDARY_DYED_COLOR, secondColor);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        firstColor = componentInput.getOrDefault(DataComponents.DYED_COLOR, firstColor);
        secondColor = componentInput.getOrDefault(MalumDataComponents.SECONDARY_DYED_COLOR, secondColor);
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("firstColor");
        tag.remove("secondColor");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        firstColor = DyedItemColor.CODEC.parse(NbtOps.INSTANCE, tag.get("firstColor")).result().orElse(EtherItem.DEFAULT_FIRST_COLOR);
        if (!EtherItem.isIridescent(this)) {
            secondColor = firstColor;
            return;
        }
        secondColor = DyedItemColor.CODEC.parse(NbtOps.INSTANCE, tag.get("secondColor")).result().orElse(EtherItem.DEFAULT_SECOND_COLOR);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (firstColor != null) {
            tag.put("firstColor", DyedItemColor.CODEC.encodeStart(NbtOps.INSTANCE, firstColor).getOrThrow());
        }
        if (EtherItem.isIridescent(this)) {
            if (secondColor != null) {
                tag.put("secondColor", DyedItemColor.CODEC.encodeStart(NbtOps.INSTANCE, secondColor).getOrThrow());
            }
        }
        super.saveAdditional(tag, registries);
    }

    public void modifyParticleCenter(MutableDouble x, MutableDouble y, MutableDouble z) {

    }

    @Override
    public void clientTick(Level level) {
        if (firstColor == null) {
            return;
        }
        var random = level.random;
        var start = new Color(firstColor.rgb());
        var end = new Color(secondColor == null ? firstColor.rgb() : secondColor.rgb());
        var xMutable = new MutableDouble(worldPosition.getX() + 0.5f);
        var yMutable = new MutableDouble(worldPosition.getY() + 0.5f);
        var zMutable = new MutableDouble(worldPosition.getZ() + 0.5f);
        modifyParticleCenter(xMutable, yMutable, zMutable);

        var x = xMutable.getValue();
        var y = yMutable.getValue();
        var z = zMutable.getValue();
        Vec3 sparkPos = new Vec3(x, y - 0.05f, z);
        //Upwards Moving Particles
        if (level.getGameTime() % 2L == 0) {
            var color = ColorParticleData.create(start, end).setCoefficient(1.5f).setEasing(Easing.SINE_IN_OUT).build();
            int lifeTime = Easing.SINE_IN_OUT.asWeighedRandom(random, 50, 60);
            float scale = Easing.SINE_IN_OUT.asWeighedRandom(random, 0.7f, 0.9f);
            float velocity = Easing.SINE_IN_OUT.asWeighedRandom(random, 0.02f, 0.025f);
            var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, sparkPos, color);
            lightSpecs.getBuilder()
                    .setRenderTarget(LodestoneRenderingSystem.LATE_DEFERRED_RENDER)
                    .setLifetime(lifeTime)
                    .setScaleData(GenericParticleData.create(scale, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.05f, 0.2f, 0).setEasing(Easing.EXPO_OUT, Easing.SINE_IN_OUT).build())
                    .addMotion(0, velocity * 1.2f, 0);
            lightSpecs.spawnParticlesRaw();
        }
        //Upwards Moving Sparks
        if (level.getGameTime() % 4L == 0) {
            var color = ColorParticleData.create(start, end).setCoefficient(2.5f).setEasing(Easing.SINE_IN_OUT).build();
            int lifeTime = Easing.SINE_IN_OUT.asWeighedRandom(random, 50, 60);
            float scale = Easing.SINE_IN_OUT.asWeighedRandom(random, 0.3f, 0.5f);
            float velocity = Easing.SINE_IN_OUT.asWeighedRandom(random, 0.02f, 0.025f);
            var lightSpecs = SparkParticleEffects.spiritMotionSparks(level, sparkPos, color);
            lightSpecs.getBuilder()
                    .setRenderTarget(LodestoneRenderingSystem.LATE_DEFERRED_RENDER)
                    .setLifetime(lifeTime)
                    .setScaleData(GenericParticleData.create(scale, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.1f, 0.6f, 0).setEasing(Easing.EXPO_OUT, Easing.SINE_IN_OUT).build())
                    .addMotion(0, velocity * 1.4f, 0)
                    .setRandomOffset(0.1f);
            lightSpecs.spawnParticlesRaw();
        }

        //Big Shine
        if (level.getGameTime() % 16L == 0) {
            var color = ColorParticleData.create(start, end).setCoefficient(0.6f).setEasing(Easing.SINE_IN_OUT).build();
            int lifeTime = Easing.SINE_IN_OUT.asWeighedRandom(random, 50, 60);
            float scale = Easing.SINE_IN_OUT.asWeighedRandom(random, 0.9f, 1.2f);
            WorldParticleBuilder.create(MalumParticles.GIANT_GLOWING_STAR)
                    .setTransparencyData(GenericParticleData.create(0f, 0.2f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN_OUT).build())
                    .setScaleData(GenericParticleData.create(scale, 0).setEasing(Easing.SINE_IN).build())
                    .setRenderTarget(LodestoneRenderingSystem.LATE_DEFERRED_RENDER)
                    .setLifetime(lifeTime)
                    .setColorData(color)
                    .enableNoClip()
                    .spawn(level, x, y, z);
        }
        //Small Shine
        if (level.getGameTime() % 4L == 0) {
            var color = ColorParticleData.create(start, end).setCoefficient(0.6f).setEasing(Easing.SINE_IN_OUT).build();
            int lifeTime = Easing.SINE_IN_OUT.asWeighedRandom(random, 20, 30);
            float scale = Easing.SINE_IN_OUT.asWeighedRandom(random, 0.25f, 0.35f);
            WorldParticleBuilder.create(MalumParticles.STAR)
                    .setTransparencyData(GenericParticleData.create(0f, 0.6f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN_OUT).build())
                    .setScaleData(GenericParticleData.create(scale, 0).setEasing(Easing.SINE_IN).build())
                    .setRenderTarget(LodestoneRenderingSystem.LATE_DEFERRED_RENDER)
                    .setLifetime(lifeTime)
                    .setColorData(color)
                    .enableNoClip()
                    .spawn(level, x, y, z);
        }
    }
}