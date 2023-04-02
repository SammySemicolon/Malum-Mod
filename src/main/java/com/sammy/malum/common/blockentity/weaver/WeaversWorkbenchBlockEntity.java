package com.sammy.malum.common.blockentity.weaver;

import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import com.sammy.malum.common.packets.particle.block.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.core.systems.item.ItemSkin;
import com.sammy.malum.registry.client.ItemSkinRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import java.util.List;
import java.util.Random;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class WeaversWorkbenchBlockEntity extends LodestoneBlockEntity {

    private final ItemStackHandler itemHandler = new WeaversWorkbenchItemHandler(2, 1,this);
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public WeaversWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WEAVERS_WORKBENCH.get(), pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (player.level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            MenuProvider container = new SimpleMenuProvider((w, p, pl) -> new WeaversWorkbenchContainer(w, p, this), WeaversWorkbenchContainer.component);
            if (player instanceof ServerPlayer) {
                NetworkHooks.openGui((ServerPlayer) player, container, buf -> buf.writeBlockPos(this.getBlockPos()));
            }
        }
        return super.onUse(player, hand);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return handler.cast();
        }
        return super.getCapability(cap);
    }


    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void tryCraft() {
        Level level = this.level;
        ItemStack target = itemHandler.getStackInSlot(0);
        ItemStack weave = itemHandler.getStackInSlot(1);
        if (!target.isEmpty() && !weave.isEmpty()) {
            ItemStack result = target.copy();
            String skinTag = ItemSkinRegistry.getApplicableItemSkinTag(target, weave);
            if (skinTag != null) {
                if (!level.isClientSide) {
                    Random random = level.random;
                    CompoundTag tag = target.getOrCreateTag();
                    CompoundTag modifiedTag = tag.copy();
                    modifiedTag.putString(ItemSkin.MALUM_SKIN_TAG, skinTag);
                    if (tag.equals(modifiedTag)) {
                        return;
                    }
                    result.setTag(modifiedTag);
                    //Vec3 pos = altarProvider.getItemPosForAltar();
                    //ItemEntity pEntity = new ItemEntity(level, pos.x(), pos.y(), pos.z(), target);
                    //pEntity.setDeltaMovement(Mth.nextFloat(random, -0.1F, 0.1F), Mth.nextFloat(random, 0.25f, 0.5f), Mth.nextFloat(random, -0.1F, 0.1F));
                    //MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(altarProvider.getBlockPosForAltar())), new BlightTransformItemParticlePacket(List.of(ARCANE_SPIRIT.identifier), pos));
                    //level.playSound(null, altarProvider.getBlockPosForAltar(), SoundRegistry.ALTERATION_PLINTH_ALTERS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.25f);

                    //level.addFreshEntity(pEntity);
                    itemHandler.setStackInSlot(2, result);
                }
            }
        }

    }
}