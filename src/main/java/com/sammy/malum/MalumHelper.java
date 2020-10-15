package com.sammy.malum;

import com.sammy.malum.blocks.machines.funkengine.FunkEngineTileEntity;
import com.sammy.malum.init.ModRecipes;
import com.sammy.malum.init.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.sammy.malum.MalumMod.random;

public class MalumHelper
{
    //region BLOCKSTATES
    public static <T extends Comparable<T>> BlockState newStateWithOldProperty(BlockState oldState, BlockState newState, Property<T> property)
    {
        return newState.with(property, oldState.get(property));
    }
    
    public static void setBlockStateWithExistingProperties(World world, BlockPos pos, BlockState newState)
    {
        BlockState oldState = world.getBlockState(pos);
        
        BlockState finalState = newState;
        for (Property<?> property : oldState.getProperties())
        {
            if (newState.hasProperty(property))
            {
                finalState = newStateWithOldProperty(oldState, finalState, property);
            }
        }
        
        world.notifyBlockUpdate(pos, oldState, finalState, 3);
        world.setBlockState(pos, finalState);
    }
    
    //endregion
    
    //region TE STACK HANDLING
    public static boolean funkyMultiStackTEHandling(PlayerEntity player, Hand hand, ItemStack heldItem, ItemStackHandler inventory)
    {
        int slotToTransfer = -1;
    
        if (heldItem.isEmpty())
        {
            for (int i = inventory.getSlots(); i >0; i--)
            {
                ItemStack targetItem = inventory.getStackInSlot(i);
                if (!targetItem.isEmpty())
                {
                    slotToTransfer = i;
                    break;
                }
            }
        }
        else if (heldItem.getItem() instanceof MusicDiscItem)
        {
            for (int i = 0; i < inventory.getSlots(); i++)
            {
                ItemStack targetItem = inventory.getStackInSlot(i);
                if (targetItem.isEmpty())
                {
                    slotToTransfer = i;
                    break;
                }
            }
        }
        if (slotToTransfer != -1)
        {
            return singleItemTEHandling(player,hand,heldItem,inventory, slotToTransfer);
        }
        return false;
    }
    public static boolean multiStackTEHandling(PlayerEntity player, Hand hand, ItemStack heldItem, ItemStackHandler inventory)
    {
        int slotToTransfer = -1;
    
        if (heldItem.isEmpty())
        {
            for (int i = inventory.getSlots()-1; i >= 0; i--)
            {
                MalumMod.LOGGER.info(i);
                ItemStack targetItem = inventory.getStackInSlot(i);
                if (!targetItem.isEmpty())
                {
                    slotToTransfer = i;
                    break;
                }
            }
        }
        else
        {
            for (int i = 0; i < inventory.getSlots(); i++)
            {
                ItemStack targetItem = inventory.getStackInSlot(i);
                if (targetItem.isEmpty())
                {
                    slotToTransfer = i;
                    break;
                }
            }
        }
        if (slotToTransfer != -1)
        {
            return singleItemTEHandling(player,hand,heldItem,inventory, slotToTransfer);
        }
        return false;
    }
    public static boolean funkyItemTEHandling(PlayerEntity player, Hand hand, ItemStack heldItem, FunkEngineTileEntity funkEngineTileEntity, int slot)
    {
        ItemStackHandler inventory = funkEngineTileEntity.inventory;
        ItemStack targetItem = inventory.getStackInSlot(slot);
        if (targetItem.isEmpty())
        {
            if (heldItem.getItem() instanceof MusicDiscItem)
            {
                inventory.setStackInSlot(0,heldItem);
                player.setHeldItem(hand, ItemStack.EMPTY);
                return true;
            }
        }
        else
        {
            MalumHelper.giveItemStackToPlayer(player, targetItem);
            inventory.setStackInSlot(0,ItemStack.EMPTY);
        }
        return false;
    }
    public static boolean spiritFurnaceItemTEHandling(PlayerEntity player, Hand hand, ItemStack heldItem, ItemStackHandler inventory, int slot)
    {
        ItemStack targetItem = inventory.getStackInSlot(slot);
        if (heldItem.getItem().equals(targetItem.getItem()))
        {
            int cachedCount = heldItem.getCount();
            for (int i = 0; i < cachedCount; i++)
            {
                if (targetItem.getCount() < targetItem.getMaxStackSize())
                {
                    targetItem.grow(1);
                    heldItem.shrink(1);
                }
            }
            return true;
        }
        if (targetItem.isEmpty())
        {
            if (ModRecipes.getSpiritFurnaceFuelData(heldItem) != null)
            {
                inventory.setStackInSlot(0, heldItem);
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
        }
        else
        {
            MalumHelper.giveItemStackToPlayer(player, targetItem);
            inventory.setStackInSlot(0,ItemStack.EMPTY);
        }
        return true;
    }
    public static boolean singleItemTEHandling(PlayerEntity player, Hand hand, ItemStack heldItem, ItemStackHandler inventory, int slot)
    {
        ItemStack targetItem = inventory.getStackInSlot(slot);
        if (targetItem.isEmpty())
        {
            inventory.setStackInSlot(slot,heldItem);
            player.setHeldItem(hand, ItemStack.EMPTY);
            return true;
        }
        else
        {
            MalumHelper.giveItemStackToPlayer(player, targetItem);
            inventory.setStackInSlot(slot,ItemStack.EMPTY);
            return false;
        }
    }
    public static boolean basicItemTEHandling(PlayerEntity player, Hand hand, ItemStack heldItem, ItemStackHandler inventory, int slot)
    {
        ItemStack targetItem = inventory.getStackInSlot(slot);
        if (heldItem.getItem().equals(targetItem.getItem()))
        {
            int cachedCount = heldItem.getCount();
            for (int i = 0; i < cachedCount; i++)
            {
                if (targetItem.getCount() < targetItem.getMaxStackSize())
                {
                    targetItem.grow(1);
                    heldItem.setCount(heldItem.getCount() - 1);
                }
            }
            return true;
        }
        return singleItemTEHandling(player,hand,heldItem,inventory,slot);
    }
    public static boolean inputStackIntoTE(TileEntity inputTileEntity, Direction direction, ItemStack stack)
    {
        IItemHandler inventory = inputTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction).orElse(null);
        for (int j = 0; j < inventory.getSlots(); j++)
        {
            ItemStack simulate = ItemHandlerHelper.insertItem(inventory, stack, true);
            int count = stack.getCount() - simulate.getCount();
            ItemHandlerHelper.insertItem(inventory, stack.split(count), false);
            inputTileEntity.getWorld().notifyBlockUpdate(inputTileEntity.getPos(), inputTileEntity.getBlockState(), inputTileEntity.getBlockState(), 3);
            if (simulate.isEmpty())
            {
                return true;
            }
        }
        return false;
    }
    public static boolean inputStackIntoTE(TileEntity inputTileEntity, ItemStack stack)
    {
        IItemHandler inventory = inputTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        for (int j = 0; j < inventory.getSlots(); j++)
        {
            ItemStack simulate = ItemHandlerHelper.insertItem(inventory, stack, true);
            int count = stack.getCount() - simulate.getCount();
            ItemHandlerHelper.insertItem(inventory, stack.split(count), false);
            inputTileEntity.getWorld().notifyBlockUpdate(inputTileEntity.getPos(), inputTileEntity.getBlockState(), inputTileEntity.getBlockState(), 3);
            if (simulate.isEmpty())
            {
                return true;
            }
        }
        return false;
    }
    //endregion
    
    //region STACK HANDLING
    
    public static void addDrop(LivingDropsEvent event, ItemStack stack)
    {
        LivingEntity entity = event.getEntityLiving();
        ItemEntity entityitem = new ItemEntity(entity.world, entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ, stack);
        entityitem.setPickupDelay(10);
        event.getDrops().add(entityitem);
    }
    public static boolean hasArmorSet(PlayerEntity player, Item helmet, Item chest, Item leggings, Item feet)
    {
        if (player != null || player.inventory != null)
        {
            return player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(helmet) && player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem().equals(chest) && player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem().equals(leggings) && player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(feet);
        }
        return false;
    }
    public static void giveItemStackToPlayer(PlayerEntity playerEntity, ItemStack stack)
    {
        ItemHandlerHelper.giveItemToPlayer(playerEntity, stack);
    }
    
    public static ItemStack stackWithNBT(ItemStack originalStack, String tag, INBT inbt)
    {
        CompoundNBT nbt = originalStack.getOrCreateTag();
        nbt.put(tag, inbt);
        return originalStack;
    }
    
    public static ItemStack stackWithItemChanged(ItemStack originalStack, Item item)
    {
        ItemStack stack = new ItemStack(item, originalStack.getCount());
        if (originalStack.getTag() != null)
        {
            stack.setTag(originalStack.getTag().copy());
        }
        return stack;
    }
    
    public static ItemStack stackWithCount(Item item, int count)
    {
        ItemStack stack = new ItemStack(item);
        stack.setCount(count);
        return stack;
    }
    //endregion
    
    //region VECTOR STUFF AND THINGS
    public static Vector3d randomVector(Random random, double min, double max)
    {
        double x = MathHelper.nextDouble(random, min, max);
        double y = MathHelper.nextDouble(random, min, max);
        double z = MathHelper.nextDouble(random, min, max);
        return new Vector3d(x, y, z);
    }
    public static Vector3d vectorFromBlockPos(BlockPos pos)
    {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        return new Vector3d(x, y, z);
    }
    
    public static Vector3d entityCenter(Entity entity)
    {
        return new Vector3d((entity.getBoundingBox().minX + entity.getBoundingBox().maxX) / 2, (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2, (entity.getBoundingBox().minZ + entity.getBoundingBox().maxZ) / 2);
    }
    
    public static Vector3d randPosofEntity(Entity entity, Random rand)
    {
        double x = MathHelper.nextDouble(random, entity.getBoundingBox().minX, entity.getBoundingBox().maxX);
        double y = MathHelper.nextDouble(random, entity.getBoundingBox().minY, entity.getBoundingBox().maxY);
        double z = MathHelper.nextDouble(random, entity.getBoundingBox().minZ, entity.getBoundingBox().maxZ);
        return new Vector3d(x, y, z);
    }
    
    public static Vector3d randExtendedPosofEntity(Entity entity, Random rand, float multiplier)
    {
        double x = MathHelper.nextDouble(random, entity.getBoundingBox().minX - (entity.getBoundingBox().getXSize() * multiplier), entity.getBoundingBox().maxX + (entity.getBoundingBox().getXSize() * multiplier));
        double y = MathHelper.nextDouble(random, entity.getBoundingBox().minY - (entity.getBoundingBox().getYSize() * multiplier), entity.getBoundingBox().maxY + (entity.getBoundingBox().getYSize() * multiplier));
        double z = MathHelper.nextDouble(random, entity.getBoundingBox().minZ - (entity.getBoundingBox().getZSize() * multiplier), entity.getBoundingBox().maxZ + (entity.getBoundingBox().getZSize() * multiplier));
        return new Vector3d(x, y, z);
    }
    public static Vector3d randSimulatedExtendedPosofEntity(Entity entity, Vector3d pos, Random rand, float multiplier)
    {
        double x = MathHelper.nextDouble(random, pos.x - (entity.getBoundingBox().getXSize() * multiplier), pos.x + (entity.getBoundingBox().getXSize() * multiplier));
        double y = MathHelper.nextDouble(random, pos.y - (entity.getBoundingBox().getYSize() * multiplier), pos.y + (entity.getBoundingBox().getYSize() * multiplier));
        double z = MathHelper.nextDouble(random, pos.z - (entity.getBoundingBox().getZSize() * multiplier), pos.z + (entity.getBoundingBox().getZSize() * multiplier));
        return new Vector3d(x, y, z);
    }
    public static Vector3d tryTeleportPlayer(PlayerEntity playerEntity, Vector3d testPosition)
    {
        Vector3d cachedPosition = playerEntity.getPositionVec();
        Vector3d newPosition = cachedPosition;
        playerEntity.teleportKeepLoaded(testPosition.x, testPosition.y, testPosition.z);
        if (!playerEntity.world.checkNoEntityCollision(playerEntity))
        {
            newPosition = testPosition;
        }
        playerEntity.teleportKeepLoaded(cachedPosition.x, cachedPosition.y, cachedPosition.z);
        return newPosition;
    }
    //endregion
    
    //region ENTITY STUFF
    
    @Nullable
    public static Entity getClosestEntity(List<Entity> entities, Vector3d pos)
    {
        double cachedDistance = -1.0D;
        Entity resultEntity = null;
        
        for (Entity entity : entities)
        {
            double newDistance = entity.getDistanceSq(pos.x,pos.y,pos.z);
            if (cachedDistance == -1.0D || newDistance < cachedDistance)
            {
                cachedDistance = newDistance;
                resultEntity = entity;
            }
        }
        return resultEntity;
    }
    //endregion
    
    //region SOUND MAGIC
    
    public static void makeMachineToggleSound(World world, BlockPos pos, float pitch)
    {
        world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS,1f,pitch);
        world.playSound(null, pos, ModSounds.machine_toggle_sound, SoundCategory.BLOCKS,1f,pitch);
    }
    //endregion
}