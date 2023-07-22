package com.sammy.malum.common.block.curiosities.weavers_workbench;

import com.sammy.malum.common.container.*;
import com.sammy.malum.common.packets.particle.curiosities.blight.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;

import java.util.*;

import static com.sammy.malum.common.item.cosmetic.skins.ArmorSkin.*;
import static com.sammy.malum.registry.common.PacketRegistry.*;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class WeaversWorkbenchBlockEntity extends LodestoneBlockEntity {

    public final WeaversWorkbenchItemHandler itemHandler = new WeaversWorkbenchItemHandler(2, 1,this);
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public WeaversWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WEAVERS_WORKBENCH.get(), pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            MenuProvider container = new SimpleMenuProvider((w, p, pl) -> new WeaversWorkbenchContainer(w, p, this), WeaversWorkbenchContainer.component);
            NetworkHooks.openGui(serverPlayer, container, buf -> buf.writeBlockPos(this.getBlockPos()));
        }
        return InteractionResult.SUCCESS;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
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