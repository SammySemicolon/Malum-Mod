package com.sammy.malum.blocks.machines.mirror;

import com.sammy.malum.blocks.utility.ConfigurableTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.DropperBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static com.sammy.malum.MalumHelper.inputStackIntoTE;
import static net.minecraft.item.ItemStack.EMPTY;
import static net.minecraft.state.properties.AttachFace.CEILING;
import static net.minecraft.state.properties.AttachFace.FLOOR;
import static net.minecraft.state.properties.BlockStateProperties.FACE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class BasicMirrorTileEntity extends ConfigurableTileEntity
{
    public int transferAmount;
    public int transferCooldown;
    public int specialFunction;
    public boolean isFiltered;
    public Item filter;
    
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
            return true;
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
            BasicMirrorTileEntity.this.markDirty();
            if (!world.isRemote)
            {
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    };
    
    public final LazyOptional<IItemHandler> lazyOptional = LazyOptional.of(() -> inventory);
    int[] transferAmounts = new int[]{1, 2, 4, 8, 16, 32, 64};
    public BasicMirrorTileEntity(TileEntityType type)
    {
        super(type);
    }

    public static boolean transferItem(TileEntity itemSender, TileEntity itemReceiver, int transferAmount)
    {
        IItemHandler senderInventory = itemSender.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        for (int i = 0; i < senderInventory.getSlots(); i++)
        {
            ItemStack stack = senderInventory.getStackInSlot(i);
            for (int j = 0; j < transferAmount; j++)
            {
                boolean success = inputStackIntoTE(itemReceiver, stack.split(1));
                if (success)
                {
                    
                    itemSender.getWorld().notifyBlockUpdate(itemSender.getPos(), itemSender.getBlockState(), itemSender.getBlockState(), 3);
                    itemReceiver.getWorld().notifyBlockUpdate(itemReceiver.getPos(), itemReceiver.getBlockState(), itemReceiver.getBlockState(), 3);
                }
                else
                {
                    stack.grow(1);
                    return false;
                }
            }
        }
        return false;
    }

    public static TileEntity getAttachedTileEntity(World world, BlockPos pos)
    {
        Direction direction = world.getBlockState(pos).get(HORIZONTAL_FACING);
        Vector3i directionVector = direction.getDirectionVec();
        if (world.getBlockState(pos).get(FACE).equals(CEILING))
        {
            directionVector = new Vector3i(0, -1, 0);
        }
        if (world.getBlockState(pos).get(FACE).equals(FLOOR))
        {
            directionVector = new Vector3i(0, 1, 0);
        }
        if (!(world.getTileEntity(pos.subtract(directionVector)) instanceof BasicMirrorTileEntity))
        {
            return world.getTileEntity(pos.subtract(directionVector));
        }
        return null;
    }
    
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
        compound.putInt("transferAmount", transferAmount);
        compound.put("inventory", inventory.serializeNBT());
        compound.putInt("specialFunction", specialFunction);
        compound.putBoolean("isFiltered", isFiltered);
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        transferAmount = compound.getInt("transferAmount");
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        specialFunction = compound.getInt("specialFunction");
        isFiltered = compound.getBoolean("isFiltered");
    }
    public void globalLogic()
    {
        if (specialFunction == 1)
        {
            List<Entity> entities = findEntities();
            if (!entities.isEmpty())
            {
                for (Entity entity : entities)
                {
                    if (entity instanceof ItemEntity)
                    {
                        ItemStack stack = ((ItemEntity) entity).getItem();
                        boolean success = inputStackIntoTE(this, stack);
                        if (success)
                        {
                            getWorld().notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
                        }
                    }
                }
            }
        }
        if (specialFunction == 2)
        {
            ItemStack stack = inventory.getStackInSlot(0);
            if (!stack.isEmpty())
            {
                ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                entity.setVelocity(0, 0, 0);
                boolean success = world.addEntity(entity);
                if (success)
                {
                    inventory.setStackInSlot(0, EMPTY);
                }
            }
        }
    }
    public List<Entity> findEntities()
    {
        return world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f), e -> (e instanceof ItemEntity));
    }
}