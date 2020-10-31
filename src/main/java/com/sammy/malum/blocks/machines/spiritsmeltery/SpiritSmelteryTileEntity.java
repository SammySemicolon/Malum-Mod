package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.blocks.utility.multiblock.MultiblockTileEntity;
import com.sammy.malum.init.ModRecipes;
import com.sammy.malum.init.ModTileEntities;
import com.sammy.malum.recipes.SpiritFurnaceRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

import static com.sammy.malum.MalumHelper.inputStackIntoTE;

public class SpiritSmelteryTileEntity extends MultiblockTileEntity implements ITickableTileEntity
{
    
    public SpiritSmelteryTileEntity()
    {
        super(ModTileEntities.spirit_smeltery_tile_entity);
    }
    
    public int ticksSinceItemConsumption;
    public int[] burnTimes;
    public boolean vanilla_furnace;
    public int speedyTime;
    
    public float filled()
    {
        float items = 0;
        float totalItems = inventory.getSlots() * 64;
        for (int i = 0; i < inventory.getSlots(); i++)
        {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty())
            {
                items += stack.getCount();
            }
        }
        return items / totalItems;
    }
    
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
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        burnTimes = compound.getIntArray("burnTimes");
        vanilla_furnace = compound.getBoolean("vanilla");
        speedyTime = compound.getInt("speedyTime");
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
                                boolean success = MalumHelper.inputStackIntoTE(this, itemEntity.getItem());
                                if (success)
                                {
                                    ticksSinceItemConsumption = 20;
                                    entity.remove();
                                }
                            }
                        }
                        if (ModRecipes.getSpiritFurnaceFuelData(stack) != null)
                        {
                            if (entity.isAlive())
                            {
                                speedyTime += ModRecipes.getSpiritFurnaceFuelData(stack).getFuelTime() * stack.getCount();
                                ticksSinceItemConsumption = 40;
                                entity.remove();
                            }
                        }
                    }
                }
            }
        }
        if (ticksSinceItemConsumption > 0)
        {
            ticksSinceItemConsumption--;
        }
        if (burnTimes == null)
        {
            burnTimes = new int[27];
        }
        
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
                            if (MalumMod.random.nextInt(recipe.getSideItemChance()) == 0)
                            {
                                output(recipe.getSideItem().getDefaultInstance());
                            }
                            stack.shrink(1);
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
        dropItem(stack,pos.down());
    }
}