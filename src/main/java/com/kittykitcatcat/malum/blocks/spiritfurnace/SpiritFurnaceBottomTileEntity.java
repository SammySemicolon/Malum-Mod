package com.kittykitcatcat.malum.blocks.spiritfurnace;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.network.packets.FurnaceSoundStartPacket;
import com.kittykitcatcat.malum.network.packets.FurnaceSoundStopPacket;
import com.kittykitcatcat.malum.recipes.SpiritFurnaceRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
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
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ListIterator;
import java.util.Objects;

import static com.kittykitcatcat.malum.network.NetworkManager.*;

public class SpiritFurnaceBottomTileEntity extends TileEntity implements ITickableTileEntity
{
    public SpiritFurnaceBottomTileEntity()
    {
        super(ModTileEntities.spirit_furnace_bottom_tile_entity);
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

    public boolean isSmelting;
    public int burnTime;
    public int burnProgress;
    public ItemStackHandler inventory = new ItemStackHandler(3)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            if(stack.getItem().equals(ModItems.spirit_charcoal))
            {
                if (slot == 0)
                {
                    return true;
                }
            }
            return false;
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
        compound.putBoolean("isSmelting", isSmelting);
        compound.putInt("burnProgress", burnProgress);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        burnTime = compound.getInt("burnTime");
        isSmelting = compound.getBoolean("isSmelting");
        burnProgress = compound.getInt("burnProgress");
    }

    public enum spiritFuranceSlotEnum
    {
        fuel (0),
        input (1);

        private final int slot;
        private spiritFuranceSlotEnum(int slot) { this.slot = slot;}
    }

    public ItemStack getItemStack(spiritFuranceSlotEnum spiritFuranceSlot)
    {
        return inventory.getStackInSlot(spiritFuranceSlot.slot);
    }
    public void updateIsSmelting(boolean newIsSmelting)
    {
        if (world.getBlockState(pos).getBlock() instanceof SpiritFurnaceBottomBlock && world.getBlockState(pos.up()).getBlock() instanceof SpiritFurnaceTopBlock)
        {
            this.isSmelting = newIsSmelting;
            BlockState bottomState = world.getBlockState(pos).with(BlockStateProperties.LIT, newIsSmelting);
            BlockState topState = world.getBlockState(pos.up()).with(BlockStateProperties.LIT, newIsSmelting);

            world.notifyBlockUpdate(pos, world.getBlockState(pos), bottomState, 3);
            world.setBlockState(pos, bottomState, 3);
            world.notifyBlockUpdate(pos.up(), world.getBlockState(pos.up()), topState, 3);
            world.setBlockState(pos.up(), topState, 3);
            if (!world.isRemote)
            {
                if (newIsSmelting)
                {
                    INSTANCE.send(
                            PacketDistributor.TRACKING_CHUNK.with(() -> this.world.getChunkAt(pos)),
                            new FurnaceSoundStartPacket(pos.getX(), pos.getY(), pos.getZ()));
                }
                else
                {
                    INSTANCE.send(
                            PacketDistributor.TRACKING_CHUNK.with(() -> this.world.getChunkAt(pos)),
                            new FurnaceSoundStopPacket(pos.getX(), pos.getY(), pos.getZ()));
                }
            }
        }
    }
    public void output(World world, BlockPos pos, Item item, int count)
    {
        Vec3i direction = world.getBlockState(pos).get(BlockStateProperties.HORIZONTAL_FACING).getDirectionVec();
        Vec3d entityPos = new Vec3d(pos).add(0.5,1.5,0.5).subtract(direction.getX() * 0.8f, 0, direction.getZ() * 0.8f);
        ItemStack stack = new ItemStack(item);
        stack.setCount(count);
        ItemEntity entity = new ItemEntity(world,entityPos.x,entityPos.y,entityPos.z, stack);
        entity.setMotion(-direction.getX() * 0.1f, 0.05f, -direction.getZ() * 0.1f);
        world.addEntity(entity);
    }
    @Override
    public void tick()
    {
        //INPUT FROM TOP BLOCK
        if (world.getTileEntity(pos.up()) instanceof SpiritFurnaceTopTileEntity)
        {
            SpiritFurnaceTopTileEntity topTileEntity = (SpiritFurnaceTopTileEntity) world.getTileEntity(pos.up());
            ItemStack stackToAdd = topTileEntity.inventory.getStackInSlot(0);
            ItemStack inputStack = getItemStack(spiritFuranceSlotEnum.input);
            if (stackToAdd.getItem().equals(inputStack.getItem()) || inputStack.isEmpty())
            {
                if (stackToAdd.getCount() > 0 && inputStack.getCount() < 64)
                {
                    if (inputStack.equals(ItemStack.EMPTY))
                    {
                        MalumHelper.setStackInTEInventory(inventory, new ItemStack(stackToAdd.getItem()), 1);
                    }
                    else
                    {
                        MalumHelper.addStackToTEInventory(inventory, stackToAdd, 1);
                    }
                    MalumHelper.setStackInTEInventory(topTileEntity.inventory, ItemStack.EMPTY, 0);
                }
            }
        }
        //SMELTING
        if (getItemStack(spiritFuranceSlotEnum.fuel).getItem().equals(ModItems.spirit_charcoal))
        {
            ItemStack inputStack = getItemStack(spiritFuranceSlotEnum.input);
            if (ModRecipes.getSpiritFurnaceRecipe(inputStack.getItem()) != null)
            {
                if (!isSmelting)
                {
                    //send start packet here
                    isSmelting = true;
                    updateIsSmelting(isSmelting);
                }
                SpiritFurnaceRecipe recipe = ModRecipes.getSpiritFurnaceRecipe(inputStack.getItem());
                burnProgress++;
                if (burnProgress >= recipe.getBurnTime())
                {
                    MalumHelper.decreaseStackSizeInTEInventory(inventory, 1, spiritFuranceSlotEnum.input.slot);
                    output(world, pos, recipe.getOutputItem(), recipe.getOutputCount());
                    burnProgress = 0;
                }
                if (burnTime <= 0)
                {
                    MalumHelper.decreaseStackSizeInTEInventory(inventory, 1, spiritFuranceSlotEnum.fuel.slot);
                    burnTime = 800;
                }
            }
            else if (isSmelting)
            {
                isSmelting = false;
                burnProgress = 0;
                updateIsSmelting(isSmelting);
            }
        }
        else if (isSmelting)
        {
            burnProgress = 0;
            isSmelting = false;
            updateIsSmelting(isSmelting);
        }
        if (burnTime > 0)
        {
            burnTime--;
        }
    }
}