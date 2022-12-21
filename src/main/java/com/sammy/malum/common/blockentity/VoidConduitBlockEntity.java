package com.sammy.malum.common.blockentity;

import com.sammy.malum.common.blockentity.storage.ItemPedestalBlockEntity;
import com.sammy.malum.common.packets.particle.block.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.common.recipe.AugmentingRecipe;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.ParticleRenderTypes;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class VoidConduitBlockEntity extends LodestoneBlockEntity {

    public final List<ItemStack> eatenItems = new ArrayList<>();
    public int progress;

    protected static final VoxelShape WELL_SHAPE = Block.box(-16.0D, 11.0D, -16.0D, 32.0D, 13.0D, 32.0D);
    public VoidConduitBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.VOID_CONDUIT.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (!eatenItems.isEmpty()) {
            compound.putInt("itemCount", eatenItems.size());
            for (int i = 0; i < eatenItems.size(); i++) {
                CompoundTag itemTag = new CompoundTag();
                ItemStack stack = eatenItems.get(i);
                stack.save(itemTag);
                compound.put("item_"+i, itemTag);
            }
        }
        compound.putInt("progress", progress);
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        for (int i = 0; i < compound.getInt("itemCount"); i++) {
            CompoundTag itemTag = compound.getCompound("item_"+i);
            eatenItems.add(ItemStack.of(itemTag));
        }
        progress = compound.getInt("progress");
        super.load(compound);
    }

    @Override
    public void tick() {
        super.tick();
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.getGameTime() % 40L == 0) {
                List<ItemEntity> items = serverLevel.getEntitiesOfClass(
                        ItemEntity.class,
                        new AABB(worldPosition.offset(1, -3, 1), worldPosition.offset(-1, -1, -1)).inflate(1))
                        .stream().sorted(Comparator.comparingInt(itemEntity -> itemEntity.age)).collect(Collectors.toList());

                for (ItemEntity entity : items) {
                    eatenItems.add(entity.getItem());
                    entity.discard();
                }
            }
        }
        else {
            if (level.getGameTime() % 6L == 0) {
                float multiplier = Mth.nextFloat(level.random, 0.4f, 1f);
                Color color = new Color((int)(12*multiplier), (int)(3*multiplier), (int)(12*multiplier));
                Color endColor = color.darker();
                ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                        .setAlpha(0, 0.2f, 0f)
                        .setAlphaEasing(Easing.SINE_IN, Easing.SINE_OUT)
                        .setLifetime(60)
                        .setSpin(0.1f, 0.4f, 0)
                        .setSpinEasing(Easing.SINE_IN, Easing.SINE_OUT)
                        .setScale(0f, 0.9f, 0.5f)
                        .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                        .setColor(color, endColor)
                        .setColorCoefficient(0.5f)
                        .addMotion(0, level.random.nextFloat()*0.01f, 0)
                        .randomOffset(3f, 0.02f)
                        .enableNoClip()
                        .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                        .overwriteRenderType(ParticleRenderTypes.TRANSPARENT)
                        .surroundVoxelShape(level, worldPosition, WELL_SHAPE, 8);
            }
        }
    }
}