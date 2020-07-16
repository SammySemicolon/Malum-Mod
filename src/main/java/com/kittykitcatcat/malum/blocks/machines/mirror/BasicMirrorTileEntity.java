package com.kittykitcatcat.malum.blocks.machines.mirror;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static com.kittykitcatcat.malum.blocks.machines.mirror.BasicMirrorBlock.*;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class BasicMirrorTileEntity extends TileEntity implements ITickableTileEntity
{
    public BasicMirrorTileEntity()
    {
        super(ModTileEntities.basic_mirror_tile_entity);
    }
    public BasicMirrorTileEntity(TileEntityType type)
    {
        super(type);
    }
    public BlockPos linkedMirrorPos;
    public int transferCooldown;
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
        if (linkedMirrorPos != null)
        {
            compound.putInt("blockPosX", linkedMirrorPos.getX());
            compound.putInt("blockPosY", linkedMirrorPos.getY());
            compound.putInt("blockPosZ", linkedMirrorPos.getZ());
        }
        compound.putInt("transferCooldown", transferCooldown);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if (compound.contains("blockPosX"))
        {
            linkedMirrorPos = new BlockPos(compound.getInt("blockPosX"), compound.getInt("blockPosY"), compound.getInt("blockPosZ"));
        }
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
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

    @Override
    public void tick()
    {
        if (transferCooldown <= 0)
        {
            BasicMirrorBlock block = (BasicMirrorBlock) getBlockState().getBlock();
            ItemStack stack = inventory.getStackInSlot(0);
            if (block.type == mirrorTypeEnum.input)
            {
                if (stack != ItemStack.EMPTY)
                {
                    Direction direction = getBlockState().get(HORIZONTAL_FACING);
                    TileEntity inputTileEntity = world.getTileEntity(pos.subtract(direction.getDirectionVec()));
                    if (inputTileEntity != null)
                    {
                        MalumHelper.putStackInTE(inputTileEntity,direction,stack);
                        if (inputTileEntity instanceof BasicMirrorTileEntity)
                        {
                            ((BasicMirrorTileEntity) inputTileEntity).transferCooldown = 10;
                        }
                    }
                }
            }
            if (inventory.getStackInSlot(0).isEmpty())
            {
                List<Entity> entities = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(pos, pos.add(1, 1, 1)), null);
                if (!entities.isEmpty())
                {
                    for (Entity entity : entities)
                    {
                        if (entity instanceof ItemEntity)
                        {
                            ItemEntity itemEntity = (ItemEntity) entity;
                            MalumHelper.setStackInTEInventory(inventory, itemEntity.getItem(), 0);
                            itemEntity.remove();
                        }
                    }
                }
            }
            transferCooldown = 10;
        }
        else
        {
            transferCooldown--;
        }
    }
}