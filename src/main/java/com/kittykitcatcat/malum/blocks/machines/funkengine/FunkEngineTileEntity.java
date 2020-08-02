package com.kittykitcatcat.malum.blocks.machines.funkengine;

import com.kittykitcatcat.malum.blocks.utility.BasicTileEntity;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class FunkEngineTileEntity extends BasicTileEntity implements ITickableTileEntity
{
    public FunkEngineTileEntity()
    {
        super(ModTileEntities.funk_engine_tile_entity);
    }
    public int loopCooldown;
    public ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return stack.getItem() instanceof MusicDiscItem;
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
        compound.putInt("loopCooldown",loopCooldown);
        compound.put("inventory", inventory.serializeNBT());
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        loopCooldown = compound.getInt("loopCooldown");
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
    }

    public void playSound()
    {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            SimpleSound sound = getSound();
            if (sound != null)
            {
                if (!Minecraft.getInstance().getSoundHandler().isPlaying(sound))
                {
                    Minecraft.getInstance().getSoundHandler().play(sound);
                }
            }
        });
    }

    public void stopSound()
    {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            SimpleSound sound = getSound();
            if (sound != null)
            {
                Minecraft.getInstance().getSoundHandler().stop(sound);
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    public SimpleSound getSound()
    {
        ItemStack stack = inventory.getStackInSlot(0);
        if (stack.getItem() instanceof MusicDiscItem)
        {
            MusicDiscItem item = (MusicDiscItem) stack.getItem();
            return SimpleSound.record(item.getSound(), pos.getX(), pos.getY(), pos.getZ());
        }
        return null;
    }

    @Override
    public void tick()
    {
        if (world.isRemote)
        {
            if (inventory.getStackInSlot(0).getItem() instanceof MusicDiscItem)
            {
                playSound();
            }
        }
    }
}