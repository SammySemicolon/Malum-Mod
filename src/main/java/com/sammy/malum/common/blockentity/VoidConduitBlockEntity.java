package com.sammy.malum.common.blockentity;

import com.sammy.malum.common.blockentity.storage.ItemPedestalBlockEntity;
import com.sammy.malum.common.packets.particle.block.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.common.recipe.AugmentingRecipe;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.ParticleRenderTypes;

import java.awt.*;
import java.util.List;
import java.util.Random;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class VoidConduitBlockEntity extends LodestoneBlockEntity {
    public VoidConduitBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.VOID_CONDUIT.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
    }
}