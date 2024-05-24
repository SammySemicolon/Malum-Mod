package com.sammy.malum.common.block.curiosities.weavers_workbench;

import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import com.sammy.malum.common.packets.particle.curiosities.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import java.util.List;

import static com.sammy.malum.common.item.cosmetic.skins.ArmorSkin.MALUM_SKIN_TAG;
import static com.sammy.malum.common.item.cosmetic.skins.ArmorSkin.getApplicableItemSkinTag;
import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class WeaversWorkbenchBlockEntity extends LodestoneBlockEntity {

    public final WeaversWorkbenchItemHandler itemHandler = new WeaversWorkbenchItemHandler(2, 1, this);
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public WeaversWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WEAVERS_WORKBENCH.get(), pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            MenuProvider container = new SimpleMenuProvider((w, p, pl) -> new WeaversWorkbenchContainer(w, p, this), WeaversWorkbenchContainer.component);
            NetworkHooks.openScreen(serverPlayer, container, buf -> buf.writeBlockPos(this.getBlockPos()));
        }
        return InteractionResult.SUCCESS;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }
        return super.getCapability(cap);
    }

    public void onCraft() {
        if (!level.isClientSide) {
            Vec3 itemPos = getItemPos();
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(getBlockPos())), new BlightTransformItemParticlePacket(List.of(ARCANE_SPIRIT.identifier), itemPos));
            level.playSound(null, getBlockPos(), SoundRegistry.ALTERATION_PLINTH_ALTERS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.25f);
        }
        itemHandler.getStackInSlot(0).shrink(1);
        itemHandler.getStackInSlot(1).shrink(1);
    }

    public ItemStack tryCraft() {
        itemHandler.isCrafting = true;
        ItemStack output = getOutput();
        itemHandler.setStackInSlot(2, output);
        itemHandler.isCrafting = false;
        return output;
    }

    public ItemStack getOutput() {
        ItemStack target = itemHandler.getStackInSlot(0);
        ItemStack weave = itemHandler.getStackInSlot(1);
        if (!target.isEmpty() && weave.isEmpty()) {
            if (target.hasTag() && target.getTag().contains(MALUM_SKIN_TAG)) {
                ItemStack result = target.copy();
                result.getTag().remove(MALUM_SKIN_TAG);
                return result;
            }
        }
        if (!target.isEmpty() && !weave.isEmpty()) {
            ItemStack result = target.copy();
            String skinTag = getApplicableItemSkinTag(target, weave);
            if (skinTag != null) {
                CompoundTag tag = target.getOrCreateTag();
                CompoundTag modifiedTag = tag.copy();
                modifiedTag.putString(MALUM_SKIN_TAG, skinTag);
                if (tag.equals(modifiedTag)) {
                    return ItemStack.EMPTY;
                }
                result.setTag(modifiedTag);
                return result;
            }
        }
        return ItemStack.EMPTY;
    }

    public Vec3 getItemPos() {
        return BlockHelper.fromBlockPos(getBlockPos()).add(itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.35f, 0.5f);
    }
}