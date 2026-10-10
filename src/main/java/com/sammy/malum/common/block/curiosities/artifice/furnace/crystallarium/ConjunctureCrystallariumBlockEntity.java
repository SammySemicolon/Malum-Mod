package com.sammy.malum.common.block.curiosities.artifice.furnace.crystallarium;

import com.sammy.malum.common.block.curiosities.artifice.furnace.MalumAbstractFurnaceBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.furnace.MalumFurnaceBlockItemStackHandler;
import com.sammy.malum.common.block.soulstone.SoulstoneBudBlockEntity;
import com.sammy.malum.common.recipe.furnace.crystallarium.ConjunctureCrystallariumRecipe;
import com.sammy.malum.common.recipe.CrystalPropertyModifier;
import com.sammy.malum.registry.common.MalumDataMaps;
import com.sammy.malum.registry.common.block.MalumBlockEntities;
import com.sammy.malum.registry.common.recipe.MalumRecipeTypes;
import com.sammy.malum.visual_effects.block.ConjunctureCrystallariumParticleEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.modules.toolkit.blockentity.*;

import java.util.Collections;

import static com.sammy.malum.common.block.curiosities.artifice.furnace.crystallarium.ConjunctureCrystallariumContainer.*;

public class ConjunctureCrystallariumBlockEntity extends MalumAbstractFurnaceBlockEntity<SingleRecipeInput, ConjunctureCrystallariumRecipe> {

    private final MalumFurnaceBlockItemStackHandler inv = MalumFurnaceBlockItemStackHandler.create(this, 5).setOutputSlots(2, 3, 4).build();

    public ConjunctureCrystallariumBlockEntity(LodestoneBlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, MalumRecipeTypes.CONJUNCTURE_CRYSTALLARIUM.get());
    }

    public ConjunctureCrystallariumBlockEntity(BlockPos pos, BlockState state) {
        this(MalumBlockEntities.CONJUNCTURE_CRYSTALLARIUM.get(), pos, state);
    }

    @Override
    public ItemInteractionResult onUse(Player player, InteractionHand pHand) {
        if (player instanceof ServerPlayer serverPlayer) {
            var container = new SimpleMenuProvider((w, p, pl) -> new ConjunctureCrystallariumContainer(w, p, ContainerLevelAccess.create(pl.level(), getBlockPos()), this.dataAccess), CONJUNCTURE_CRYSTALLARIUM);
            serverPlayer.openMenu(container, buf -> buf.writeBlockPos(this.getBlockPos()));
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public void clientTick(Level level) {
        ConjunctureCrystallariumParticleEffects.passiveCrystallariumParticles(this); //TODO does this even work?
    }

    @Override
    protected MalumFurnaceBlockItemStackHandler inventory() {
        return inv;
    }

    /**
     * For this BE the method checks if there is a crystal that might grow. <br>
     * If so, the method has to validate that the int operation that is executed later can succeed for this crystal with the given block property.
     *
     */
    @Override
    protected boolean optionalPreValidation(ServerLevel level, NonNullList<ItemStack> results, ConjunctureCrystallariumRecipe recipe) {
        var posAbove = this.getBlockPos().above();
        var stateAbove = level.getBlockState(posAbove);
        var optionalCrystal = recipe.getCrystalToGrow();
        if (optionalCrystal.isEmpty()) return true; //If no crystal is required to grow, then just proceed.

        Block crystalToGrow = optionalCrystal.get().getBlock();
        if (stateAbove.is(crystalToGrow)) { // We need this crystal above
            var optionalProperty = optionalCrystal.get().getOptionalIntProperty(crystalToGrow.getStateDefinition());
            if (optionalProperty.isEmpty()) return false;
            var operation = optionalCrystal.get().operation();

            boolean allowFurnaceProgress = false;

            //If ADD -> don't allow if current property is maxValue or higher
            if (operation == CrystalPropertyModifier.PropertyOperation.ADD) {
                var maxValue = Collections.max(optionalProperty.get().getPossibleValues());
                allowFurnaceProgress = stateAbove.getValue(optionalProperty.get()) < maxValue;
            }

            //If SUB -> don't allow if current property is minValue or lower
            if (operation == CrystalPropertyModifier.PropertyOperation.SUBTRACT) {
                var minValue = Collections.min(optionalProperty.get().getPossibleValues());
                allowFurnaceProgress = stateAbove.getValue(optionalProperty.get()) > minValue;
            }

            return allowFurnaceProgress;
        }

        return false;
    }

    /**
     * If recipe requires a crystal to grow and has this crystal block placed above, it will execute the property modification.
     */
    @Override
    protected void onFinishRecipe(ConjunctureCrystallariumRecipe recipe, ServerLevel level) {
        var optionalCrystal = recipe.getCrystalToGrow();
        if (optionalCrystal.isEmpty()) return;

        var crystalModifier = optionalCrystal.get();

        var posAbove = this.getBlockPos().above();
        var stateAbove = level.getBlockState(posAbove);
        if (!stateAbove.isAir() && !stateAbove.is(crystalModifier.getBlock())) return;

        var modifiedState = crystalModifier.modify(stateAbove);
        level.setBlock(posAbove, modifiedState, Block.UPDATE_CLIENTS);

        //No special handling for other crystals for now
        if (level.getBlockEntity(posAbove) instanceof SoulstoneBudBlockEntity blockEntity && recipe.getMetalData().isPresent()) {
            blockEntity.budData = blockEntity.budData.addMetal(recipe.getMetalData().get(), 5);
        }
    }

    @Override
    protected SingleRecipeInput getRecipeInput() {
        var inputStack = this.inventory().getStackInSlot(1);
        return new SingleRecipeInput(inputStack);
    }

    @Override
    protected int getBurnDuration(ItemStack fuel) {
        var burnData = fuel.getItemHolder().getData(MalumDataMaps.CONJUNCTURE_CRYSTALLARIUM_FUEL);
        return burnData != null ? burnData.burnTime() : 0;
    }
}