package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.sammy.malum.ClientHandler;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.blocks.utility.IConfigurableTileEntity;
import com.sammy.malum.blocks.utility.multiblock.MultiblockTileEntity;
import com.sammy.malum.init.ModRecipes;
import com.sammy.malum.init.ModTileEntities;
import com.sammy.malum.particles.spiritflame.SpiritFlameParticleData;
import com.sammy.malum.recipes.SpiritFurnaceRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

import static com.sammy.malum.MalumHelper.getMachineSoundVolume;
import static com.sammy.malum.MalumHelper.inputStackIntoTE;
import static com.sammy.malum.MalumMod.random;

public class SpiritSmelteryTileEntity extends MultiblockTileEntity implements ITickableTileEntity, IConfigurableTileEntity
{
    
    public SpiritSmelteryTileEntity()
    {
        super(ModTileEntities.spirit_smeltery_tile_entity);
    }
    
    public int ticksSinceItemConsumption;
    public int[] burnTimes;
    public boolean vanilla_furnace;
    public int speedyTime;
    public float fluidHeight;
    public float delayedFluidHeight;
    public int itemCount;
    public float totalItemCount = 27 * 64;
    public int option;
    
    public ItemStackHandler inventory = new ItemStackHandler(27)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }
        
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return ModRecipes.getSpiritFurnaceFuelData(stack) != null || ModRecipes.getSpiritFurnaceRecipe(stack) != null;
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
            SpiritSmelteryTileEntity.this.markDirty();
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
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put("inventory", inventory.serializeNBT());
        compound.putIntArray("burnTimes", burnTimes);
        compound.putBoolean("vanillaFurnace",vanilla_furnace);
        compound.putInt("speedyTime",speedyTime);
        compound.putFloat("delayedFluidHeight",delayedFluidHeight);
        compound.putInt("itemCount", itemCount);
        writeOption(compound);
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        burnTimes = compound.getIntArray("burnTimes");
        vanilla_furnace = compound.getBoolean("vanillaFurnace");
        speedyTime = compound.getInt("speedyTime");
        delayedFluidHeight = compound.getFloat("delayedFluidHeight");
        itemCount = compound.getInt("itemCount");
        readOption(compound);
    }
    
    @Override
    public void tick()
    {
        if (world.getGameTime() % 10L == 0)
        {
            List<Entity> entities = findEntities();
            if (!entities.isEmpty())
            {
                for (Entity entity : entities)
                {
                    if (entity instanceof PlayerEntity)
                    {
                        ((PlayerEntity) entity).addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 11, 2, false, false));
                    }
                    else
                    {
                        ItemEntity itemEntity = (ItemEntity) entity;
                        ItemStack stack = itemEntity.getItem();
                        if (ModRecipes.getSpiritFurnaceRecipe(stack) != null)
                        {
                            if (entity.isAlive())
                            {
                                int cachedCount = itemEntity.getItem().getCount();
                                boolean success = MalumHelper.inputStackIntoTE(this, itemEntity.getItem());
                                if (success)
                                {
                                    playSounds();
                                    itemCount+= cachedCount;
                                    ticksSinceItemConsumption = 20;
                                    entity.remove();
                                    markDirty();
                                }
                            }
                        }
                        if (ModRecipes.getSpiritFurnaceFuelData(stack) != null)
                        {
                            if (entity.isAlive())
                            {
                                playSounds();
                                speedyTime += ModRecipes.getSpiritFurnaceFuelData(stack).getFuelTime() * stack.getCount();
                                ticksSinceItemConsumption = 40;
                                entity.remove();
                                markDirty();
                            }
                        }
                    }
                }
            }
        }
        delayedFluidHeight = MathHelper.lerp(0.075f, delayedFluidHeight, itemCount / totalItemCount);
        fluidHeight = 1.25f + (delayedFluidHeight * 1.5f);
        if (world.getGameTime() % 20L == 0)
        {
            if (random.nextFloat() >= 0.8f)
            {
                playSounds();
            }
        }
        if (world.getGameTime() % 10L == 0)
        {
            Vector3d particlePos = MalumHelper.vectorFromBlockPos(pos).add(-0.5,fluidHeight,-0.5).add(random.nextFloat() *2f,0,random.nextFloat() *2f);
            world.addParticle(new SpiritFlameParticleData(MathHelper.nextFloat(random, 0.25f, 0.4f)), particlePos.x,particlePos.y,particlePos.z,0.0,0.02f,0);
            world.addParticle(ParticleTypes.SMOKE, particlePos.x,particlePos.y,particlePos.z,0.0,0.1f,0);
        }
        if (ticksSinceItemConsumption > 0)
        {
            ticksSinceItemConsumption--;
        }
        if (burnTimes == null)
        {
            burnTimes = new int[27];
        }
        if (itemCount > 0)
        {
            for (int i = 0; i < inventory.getSlots(); i++)
            {
                ItemStack stack = inventory.getStackInSlot(i);
                if (!stack.isEmpty())
                {
                    burnTimes[i]++;
                    if (speedyTime > 0)
                    {
                        speedyTime--;
                        burnTimes[i]++;
                    }
                    if (vanilla_furnace)
                    {
                
                    }
                    else
                    {
                        SpiritFurnaceRecipe recipe = ModRecipes.getSpiritFurnaceRecipe(stack);
                        if (recipe != null)
                        {
                            if (burnTimes[i] > recipe.getBurnTime())
                            {
                                output(recipe.getOutputItem().getDefaultInstance());
                                if (random.nextInt(recipe.getSideItemChance()) == 0)
                                {
                                    output(recipe.getSideItem().getDefaultInstance());
                                }
                                stack.shrink(1);
                                itemCount--;
                                burnTimes[i] = 0;
                            }
                        }
                    }
                }
                else
                {
                    break;
                }
            }
        }
    }
    
    public List<Entity> findEntities()
    {
        return world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() - 0.5f, pos.getY() + 1, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 2, pos.getZ() + 1.5f), e -> (e instanceof ItemEntity || e instanceof PlayerEntity));
    }
    
    public void output(ItemStack stack)
    {
        BlockPos[] offsets = new BlockPos[]{new BlockPos(2, 0, 0), new BlockPos(-2, 0, 0), new BlockPos(0, 0, 2), new BlockPos(0, 0, -2)};
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
        dropItem(stack,pos);
    }
    public void playSounds()
    {
        Vector3d soundPos = new Vector3d(pos.getX()+0.5f, pos.getY()+fluidHeight, pos.getZ()+0.5f);
        if (random.nextFloat() >= 0.25f)
        {
            world.playSound(soundPos.x,soundPos.y,soundPos.z, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, getMachineSoundVolume(), 0.9F + random.nextFloat() * 0.15F, true);
        }
        else
        {
            world.playSound(soundPos.x,soundPos.y,soundPos.z, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, getMachineSoundVolume(), 0.9F + random.nextFloat() * 0.15F, true);
        }
        world.playSound(soundPos.x,soundPos.y,soundPos.z, SoundEvents.ENTITY_BOAT_PADDLE_WATER, SoundCategory.BLOCKS,getMachineSoundVolume(),MathHelper.nextFloat(random, 0.25f, 0.75f), true);
    }
    
    @Override
    public int getOption()
    {
        return option;
    }
    
    @Override
    public void setOption(int option)
    {
        this.option = option;
    }
}