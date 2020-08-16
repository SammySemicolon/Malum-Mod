package com.kittykitcatcat.malum.blocks.machines.spiritfurnace;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.blocks.utility.BasicTileEntity;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.recipes.SpiritFurnaceFuelData;
import com.kittykitcatcat.malum.recipes.SpiritFurnaceRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritFurnaceBottomTileEntity extends BasicTileEntity implements ITickableTileEntity
{
    public SpiritFurnaceBottomTileEntity()
    {
        super(ModTileEntities.spirit_furnace_bottom_tile_entity);
    }
    public int burnTime;
    public int burnProgress;
    public boolean isSmelting;
    public ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return ModRecipes.getSpiritFurnaceFuelData(stack) != null;
        }
        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return ItemStack.EMPTY;
        }
        @Override
        protected void onContentsChanged(int slot)
        {
            SpiritFurnaceBottomTileEntity.this.markDirty();
            if (!world.isRemote)
            {
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    };
    public final LazyOptional<IItemHandler> lazyOptional = LazyOptional.of(() -> inventory);
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put("inventory", inventory.serializeNBT());
        compound.putInt("burnTime", burnTime);
        compound.putInt("burnProgress", burnProgress);
        compound.putBoolean("isSmelting", isSmelting);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        burnTime = compound.getInt("burnTime");
        burnProgress = compound.getInt("burnProgress");
        isSmelting = compound.getBoolean("isSmelting");
    }

    public void updateState(boolean isSmelting)
    {
        if (this.isSmelting != isSmelting)
        {
            this.isSmelting = isSmelting;
            BlockState state = world.getBlockState(pos);
            BlockState topState = world.getBlockState(pos.up());
            if (state.getBlock() instanceof SpiritFurnaceBottomBlock && topState.getBlock() instanceof SpiritFurnaceTopBlock)
            {
                BlockState newState = state.with(BlockStateProperties.LIT, isSmelting);
                BlockState newTopState = topState.with(BlockStateProperties.LIT, isSmelting);

                world.notifyBlockUpdate(pos, state, newState, 3);
                world.setBlockState(pos, newState, 3);
                world.notifyBlockUpdate(pos.up(), topState, newTopState, 3);
                world.setBlockState(pos.up(), newTopState, 3);
            }
        }
    }
    public void output(World world, BlockPos pos, ItemStack stack)
    {
        Direction direction = getBlockState().get(HORIZONTAL_FACING);
        TileEntity inputTileEntity = world.getTileEntity(pos.subtract(direction.getDirectionVec()).add(0, 1, 0));
        if (inputTileEntity != null)
        {
            boolean success = MalumHelper.inputStackIntoTE(inputTileEntity, direction.getOpposite(),stack);
            if (success)
            {
                return;
            }
        }
        Vec3i directionVec = direction.getDirectionVec();
        Vec3d entityPos = new Vec3d(pos).add(0.5, 1.5, 0.5).subtract(directionVec.getX() * 0.8f, 0, directionVec.getZ() * 0.8f);
        ItemEntity entity = new ItemEntity(world, entityPos.x, entityPos.y, entityPos.z, stack.copy());
        entity.setMotion(-directionVec.getX() * 0.1f, 0.05f, -directionVec.getZ() * 0.1f);
        world.addEntity(entity);
    }
    @Override
    public void tick()
    {
        BlockPos topPos = pos.up();
        if (world.getTileEntity(topPos) instanceof SpiritFurnaceTopTileEntity)
        {
            SpiritFurnaceTopTileEntity topTileEntity = (SpiritFurnaceTopTileEntity) world.getTileEntity(topPos);
            ItemStack fuelItem = inventory.getStackInSlot(0);
            ItemStack inputItem = topTileEntity.inventory.getStackInSlot(0);
            if (inputItem.isEmpty())
            {
                updateState(false);
            }
            if (ModRecipes.getSpiritFurnaceRecipe(inputItem) != null)
            {
                SpiritFurnaceRecipe recipe = ModRecipes.getSpiritFurnaceRecipe(inputItem);
                burnTime--;
                if (!fuelItem.isEmpty())
                {
                    SpiritFurnaceFuelData data = ModRecipes.getSpiritFurnaceFuelData(fuelItem);
                    if (data != null)
                    {
                        if (burnTime < 0)
                        {
                            fuelItem.shrink(1);
                            burnTime = data.getFuelTime() + 1;
                        }
                    }
                }
                if (burnTime >= 0)
                {
                    updateState(true);
                    burnProgress++;
                    if (burnProgress > recipe.getBurnTime())
                    {
                        output(world, pos, new ItemStack(recipe.getOutputItem()));
                        if (recipe.getSideItem() != null)
                        {
                            if (world.rand.nextInt(recipe.getSideItemChance()) == 0)
                            {
                                output(world, pos, new ItemStack(recipe.getSideItem()));
                            }
                        }
                        inputItem.shrink(1);
                        burnProgress = 0;
                    }
                }
                else
                {
                    burnProgress = 0;
                    updateState(false);
                }
            }
            else
            {
                updateState(false);
            }
        }
    }
}