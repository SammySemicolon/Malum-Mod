package com.sammy.malum.common.block.curiosities.sorcery.wand_tinkerer;

import com.sammy.malum.common.block.MalumBlockItemStackHandler;
import com.sammy.malum.common.item.curiosities.weapons.wand.AbstractWandItem;
import com.sammy.malum.registry.common.block.MalumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Clearable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import team.lodestar.lodestone.modules.toolkit.blockentity.IInventoryCapabilityProvider;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntityType;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackHandler;

import static com.sammy.malum.common.block.curiosities.sorcery.wand_tinkerer.WandTinkererContainer.WAND_TINKERER;

public class WandTinkererBlockEntity extends LodestoneBlockEntity implements IInventoryCapabilityProvider, Clearable {

    public MalumBlockItemStackHandler wandSlot;
    public MalumBlockItemStackHandler wandOutput;

    public WandTinkererBlockEntity(LodestoneBlockEntityType<? extends WandTinkererBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        wandSlot = MalumBlockItemStackHandler.create(this, 1).setInputPredicate(this::isStaff).limitItemSize(1).build();
        wandOutput = MalumBlockItemStackHandler.create(this, 1).limitItemSize(1).build();
    }

    public WandTinkererBlockEntity(BlockPos pos, BlockState state) {
        this(MalumBlockEntities.WAND_TINKERER.get(), pos, state);
    }

    public boolean isStaff(LodestoneItemStackHandler handler, ItemStack stack) {
        var presentStacks = handler.getNonEmptyStacks();
        if (presentStacks.isEmpty()) {
            return true;
        }
        return stack.getItem() instanceof AbstractWandItem;
    }

    @Override
    public ItemInteractionResult onUse(Player player, InteractionHand pHand) {
        if (player instanceof ServerPlayer serverPlayer) {
            var container = new SimpleMenuProvider((w, p, pl) -> new WandTinkererContainer(w, p, ContainerLevelAccess.create(pl.level(), getBlockPos())), WAND_TINKERER);
            serverPlayer.openMenu(container, buf -> buf.writeBlockPos(this.getBlockPos()));
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        wandSlot.save(registries, tag);
        wandOutput.save(registries, tag, "wand_output");
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        wandSlot.load(pRegistries, compound);
        wandOutput.load(pRegistries, compound, "wand_output");
        super.loadAdditional(compound, pRegistries);
    }

    @Override
    public IItemHandler getInventory(Direction direction) {
        return wandSlot;
    }

    @Override
    public void clearContent() {
        wandSlot.clear();
        wandOutput.clear();
    }
}