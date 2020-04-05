package com.kittykitcatcat.malum.blocks.soulbinder;

import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.blocks.ritualanchor.RitualAnchorTileEntity;
import com.kittykitcatcat.malum.blocks.souljar.SoulJarTileEntity;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.recipes.RitualAnchorInput;
import com.kittykitcatcat.malum.recipes.SpiritInfusionRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static com.kittykitcatcat.malum.blocks.soulbinder.SoulBinderBlock.findList;
import static com.kittykitcatcat.malum.blocks.soulbinder.SoulBinderBlock.getAnchorAt;

public class SoulBinderTileEntity extends TileEntity implements ITickableTileEntity
{
    public SoulBinderTileEntity()
    {
        super(ModTileEntities.soul_binder_tile_entity);
    }
    public boolean active;
    public int infusionProgress;
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put("inventory", inventory.serializeNBT());
        compound.putBoolean("active", active);
        compound.putInt("infusionTime", infusionProgress);
        return compound;
    }
    public ItemStack getInputStack(ItemStackHandler inventory)
    {
        return inventory.getStackInSlot(0);
    }
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        active = compound.getBoolean("active");
        infusionProgress = compound.getInt("infusionTime");
    }
    public ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 0;
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
            SoulBinderTileEntity.this.markDirty();
            if (!world.isRemote)
            {
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
        {
            return ItemStack.EMPTY;
        }
    };

    @Override
    public void tick()
    {
        if (!inventory.getStackInSlot(0).isEmpty())
        {
            ItemStack stack = inventory.getStackInSlot(0);
            List<RitualAnchorInput> anchorInputs = findList(pos, world);
            for (SpiritInfusionRecipe recipe : ModRecipes.spiritInfusionRecipes)
            {
                if (RitualAnchorInput.isEqualList(anchorInputs, recipe.getInputs()))
                {
                    if (stack.getItem().equals(recipe.getCatalyst()))
                    {
                        if (SpiritData.findSpiritData(2, recipe, pos, world) != null)
                        {
                            SpiritData data = SpiritData.findSpiritData(2, recipe, pos, world);
                            BlockPos jarPos = SpiritData.findBlockByData(2, data, pos, world);
                            infusionProgress++;
                            if (infusionProgress >= recipe.getInfusionTime())
                            {
                                if (world.getTileEntity(jarPos) instanceof SoulJarTileEntity)
                                {
                                    SoulJarTileEntity tileEntity = (SoulJarTileEntity) world.getTileEntity(jarPos);
                                    tileEntity.purity -= recipe.getData().purity;
                                    infusionProgress = 0;
                                    ItemStack outputStack = recipe.getOutputStack();
                                    if (recipe.getInfusionResult() != null)
                                    {
                                        recipe.getInfusionResult().result(stack, outputStack);
                                    }
                                    for (SoulBinderBlock.anchorOffset offset : SoulBinderBlock.anchorOffset.values())
                                    {
                                        BlockPos anchorPos = pos.add(offset.offsetX, 0, offset.offsetY);
                                        if (getAnchorAt(anchorPos, world) != null)
                                        {
                                            if (world.getTileEntity(anchorPos) instanceof RitualAnchorTileEntity)
                                            {
                                                RitualAnchorTileEntity anchorTileEntity = (RitualAnchorTileEntity) world.getTileEntity(anchorPos);
                                                for (int i = 0; i<4;i++)
                                                {
                                                    anchorTileEntity.inventory.setStackInSlot(i, ItemStack.EMPTY);
                                                }
                                            }
                                        }
                                    }
                                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                                    world.addEntity(new ItemEntity(world,pos.getX()+0.5,pos.getY()+1.1,pos.getZ()+0.5, outputStack));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

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
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }
    @Override
    public void handleUpdateTag(CompoundNBT tag)
    {
        read(tag);
    }
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(pos, 0, getUpdateTag());
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        handleUpdateTag(pkt.getNbtCompound());
    }
}