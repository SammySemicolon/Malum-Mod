package com.kittykitcatcat.malum.blocks.spiritbellows;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.blocks.spiritfurnace.SpiritFurnaceBottomTileEntity;
import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Objects;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@Mod.EventBusSubscriber
public class SpiritBellowsTileEntity extends TileEntity implements ITickableTileEntity
{
    public SpiritBellowsTileEntity()
    {
        super(ModTileEntities.spirit_bellows_tile_entity);
    }
    public boolean active;
    public boolean stretching;
    public float stretch;
    public int burnTime;
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put("inventory", inventory.serializeNBT());
        compound.putBoolean("active", active);
        compound.putBoolean("stretching",stretching);
        compound.putFloat("stretch",stretch);
        compound.putInt("burnTime", burnTime);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        active = compound.getBoolean("active");
        stretching = compound.getBoolean("stretching");
        stretch = compound.getFloat("stretch");
        burnTime = compound.getInt("burnTime");
    }
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
            if(stack.getItem().equals(ModItems.spirit_charcoal))
            {
                return true;
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
            SpiritBellowsTileEntity.this.markDirty();
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
    public void tick()
    {
//        if (stretch > 1f)
//        {
//            stretching = true;
//            stretch = 1f;
//        }
//        if (stretch < 0.56f)
//        {
//            stretching = false;
//        }
//        if (stretching)
//        {
//            if (active)
//            {
//                stretch -= 0.04f;
//            }
//        }
//        else
//        {
//            stretch +=0.02f;
//        }
//        Direction direction = getBlockState().get(HORIZONTAL_FACING);
//        BlockPos furnacePos = pos.add(direction.getDirectionVec());
//        if (world.getBlockState(furnacePos).getBlock().equals(ModBlocks.spirit_furnace))
//        {
//            BlockState state = world.getBlockState(furnacePos);
//            if (!state.get(HORIZONTAL_FACING).equals(direction.getOpposite()) && !state.get(HORIZONTAL_FACING).equals(direction))
//            {
//                if (world.getTileEntity(furnacePos) instanceof SpiritFurnaceBottomTileEntity)
//                {
//                    SpiritFurnaceBottomTileEntity furnaceEntity = (SpiritFurnaceBottomTileEntity) world.getTileEntity(furnacePos);
//                    if (furnaceEntity.isSmelting)
//                    {
//                        active = true;
//                        if (world.getGameTime() % 4 == 0)
//                        {
//                            furnaceEntity.burnProgress++;
//                            if (burnTime > 0)
//                            {
//                                furnaceEntity.burnProgress++;
//                            }
//                            furnaceEntity.markDirty();
//                        }
//                        if (burnTime <= 0)
//                        {
//                            if (inventory.getStackInSlot(0).getCount() > 0)
//                            {
//                                MalumHelper.decreaseStackSizeInTEInventory(inventory, 1, 0);
//                                burnTime = 400;
//                            }
//                        }
//                    }
//                    else
//                    {
//                        active = false;
//                    }
//                }
//            }
//        }
//        else
//        {
//            active = false;
//        }
//        if (burnTime > 0)
//        {
//            burnTime--;
//        }
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