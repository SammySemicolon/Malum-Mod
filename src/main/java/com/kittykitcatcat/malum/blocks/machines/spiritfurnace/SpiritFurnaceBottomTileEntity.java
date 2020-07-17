package com.kittykitcatcat.malum.blocks.machines.spiritfurnace;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.network.packets.FurnaceSoundStartPacket;
import com.kittykitcatcat.malum.network.packets.FurnaceSoundStopPacket;
import com.kittykitcatcat.malum.particles.soulflame.SoulFlameParticleData;
import com.kittykitcatcat.malum.recipes.SpiritFurnaceRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
import java.util.Objects;

import static com.kittykitcatcat.malum.MalumMod.random;
import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritFurnaceBottomTileEntity extends TileEntity implements ITickableTileEntity
{
    public SpiritFurnaceBottomTileEntity()
    {
        super(ModTileEntities.spirit_furnace_bottom_tile_entity);
    }
    public boolean isSmelting;
    public int burnTime;
    public int burnProgress;
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
            return stack.getItem().equals(ModItems.spirit_charcoal);
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
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        burnTime = compound.getInt("burnTime");
        burnProgress = compound.getInt("burnProgress");
    }
    @Override
    public void tick()
    {
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
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(pos, 0, nbt);
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        handleUpdateTag(pkt.getNbtCompound());
    }
}