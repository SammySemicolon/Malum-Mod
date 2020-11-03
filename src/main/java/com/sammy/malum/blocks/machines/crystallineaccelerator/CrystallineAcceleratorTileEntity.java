package com.sammy.malum.blocks.machines.crystallineaccelerator;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.blocks.utility.multiblock.MultiblockTileEntity;
import com.sammy.malum.blocks.utility.spiritstorage.SpiritStoringBlock;
import com.sammy.malum.blocks.utility.spiritstorage.SpiritStoringTileEntity;
import com.sammy.malum.init.ModRecipes;
import com.sammy.malum.init.ModTileEntities;
import com.sammy.malum.recipes.CrystallineAcceleratorRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

import static com.sammy.malum.MalumHelper.getMachineSoundVolume;
import static com.sammy.malum.MalumHelper.inputStackIntoTE;
import static com.sammy.malum.MalumMod.random;
import static com.sammy.malum.SpiritDataHelper.findStorage;

public class CrystallineAcceleratorTileEntity extends MultiblockTileEntity implements ITickableTileEntity
{
    
    public CrystallineAcceleratorTileEntity()
    {
        super(ModTileEntities.crystalline_accelerator_tile_entity);
    }
    
    //region inventory stuff
    public float progress;
    public ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return ModRecipes.getCrystallineAcceleratorRecipe(stack) != null;
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
            CrystallineAcceleratorTileEntity.this.markDirty();
            if (!world.isRemote)
            {
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    };
    public final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> inventory);
    
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventoryOptional.cast();
        }
        return super.getCapability(cap);
    }
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, BlockPos pos)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventoryOptional.cast();
        }
        return super.getCapability(cap, pos);
    }
    //endregion
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put("inventory", inventory.serializeNBT());
        compound.putFloat("progress", progress);
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        progress = compound.getFloat("progress");
    }
    @Override
    public void tick()
    {
        ItemStack stack = inventory.getStackInSlot(0);
        if (ModRecipes.getCrystallineAcceleratorRecipe(stack) != null)
        {
            CrystallineAcceleratorRecipe recipe = ModRecipes.getCrystallineAcceleratorRecipe(stack);
            if (stack.getCount() >= recipe.getInputCount())
            {
                if (world.getGameTime() % 10L == 0)
                {
                    for (int i = 0; i <= 10; i++)
                    {
                        spawnParticles();
                        world.playSound(null, pos.up(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, getMachineSoundVolume(), MathHelper.nextFloat(random, 1, 1.15f));
                        world.playSound(null, pos.up(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, getMachineSoundVolume() / 4f, MathHelper.nextFloat(random, 0.8f, 0.95f));
                    }
                }
                progress++;
                if (progress > recipe.getRecipeTime())
                {
                    if (recipe.getOutputItem() != null)
                    {
                        ItemStack outputStack = recipe.getOutputItem().getDefaultInstance();
                        outputStack.setCount(recipe.getOutputCount());
                        output(outputStack);
                        stack.setCount(stack.getCount() - recipe.getInputCount());
                        for (int i = 0; i < 4; i++)
                        {
                            spawnParticles();
                        }
                    }
                    if (recipe.getOutputSpirit())
                    {
                        SpiritStoringTileEntity spiritStoringTileEntity = findStorage(world, pos.down());
                        SpiritStoringBlock block = (SpiritStoringBlock) world.getBlockState(pos.down()).getBlock();
                        SpiritDataHelper.increaseSpiritOfStorage(spiritStoringTileEntity, block.capacity(), stack.getOrCreateTag().getString("infusedSpirit"));
                    }
                    progress = 0;
                }
            }
        }
        else
        {
            progress = 0;
        }
    }
    public void output(ItemStack stack)
    {
        BlockPos[] offsets = new BlockPos[]{new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1)};
        for (BlockPos currentOffset : offsets)
        {
            BlockPos containerPos = pos.add(currentOffset);
            TileEntity containerTileEntity = world.getTileEntity(containerPos);
            if (containerTileEntity != null)
            {
                LazyOptional<IItemHandler> inventory = containerTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
                if (inventory.isPresent())
                {
                    IItemHandler itemHandler = inventory.orElse(null);
                    ItemStack simulate = ItemHandlerHelper.insertItem(itemHandler, stack, true);
                    if (simulate.isEmpty())
                    {
                        inputStackIntoTE(containerTileEntity, stack);
                        return;
                    }
                }
            }
        }
        dropItem(stack,pos.up(1));
    }
    public void spawnParticles()
    {
        Vector3d position = new Vector3d(pos.getX(), pos.getY() + 1, pos.getZ()).add(MathHelper.nextDouble(random, 0.45f, 0.55f), MathHelper.nextDouble(random, 0.45f, 0.55f), MathHelper.nextDouble(random, 0.45f, 0.55f));
        Vector3d velocity = new Vector3d(MathHelper.nextDouble(random, -0.05f, 0.05f), MathHelper.nextDouble(random, -0.05f, 0.05f), MathHelper.nextDouble(random, -0.05f, 0.05f));
        world.addParticle(new ItemParticleData(ParticleTypes.ITEM, inventory.getStackInSlot(0)), position.x,position.y,position.z,velocity.x,velocity.y,velocity.z);
        world.addParticle(ParticleTypes.SMOKE, position.x,position.y,position.z,velocity.x,-velocity.y,velocity.z);
    }
}