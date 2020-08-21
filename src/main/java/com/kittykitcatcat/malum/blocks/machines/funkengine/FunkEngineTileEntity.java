package com.kittykitcatcat.malum.blocks.machines.funkengine;

import com.kittykitcatcat.malum.blocks.utility.BasicTileEntity;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.network.packets.FunkEngineStopPacket;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

import static com.kittykitcatcat.malum.MalumHelper.playLoopingSound;
import static com.kittykitcatcat.malum.MalumHelper.stopPlayingSound;
import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;

public class FunkEngineTileEntity extends BasicTileEntity implements ITickableTileEntity
{
    public FunkEngineTileEntity()
    {
        super(ModTileEntities.funk_engine_tile_entity);
    }
    public SimpleSound sound;
    public ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 1;
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return ItemStack.EMPTY;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return false;
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            FunkEngineTileEntity.this.markDirty();
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
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
    }
    @Override
    public void remove()
    {
        stopPlayingSound(sound);
        super.remove();
    }
    @Override
    public void tick()
    {
        if (world.isRemote())
        {
            if (inventory.getStackInSlot(0).getItem() instanceof MusicDiscItem)
            {
                SimpleSound sound = getSound();
                playLoopingSound(sound);
            }
        }
    }
    
    public void stopSound()
    {
        if (!world.isRemote)
        {
            if (world instanceof ServerWorld)
            {
                INSTANCE.send(
                        PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)),
                        new FunkEngineStopPacket(pos.getX(), pos.getY(), pos.getZ()));
            }
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    public SimpleSound getSound()
    {
        ItemStack stack = inventory.getStackInSlot(0);
        if (stack.getItem() instanceof MusicDiscItem)
        {
            MusicDiscItem item = (MusicDiscItem) stack.getItem();
            boolean success = playLoopingSound(sound);
            if (success)
            {
                sound = new SimpleSound(item.getSound(), SoundCategory.RECORDS, 1, 1, pos);
            }
            return sound;
        }
        else
        {
            sound = null;
        }
        return null;
    }
    
}