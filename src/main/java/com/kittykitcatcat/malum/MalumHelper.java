package com.kittykitcatcat.malum;

import com.kittykitcatcat.malum.blocks.machines.funkengine.FunkEngineTileEntity;
import com.kittykitcatcat.malum.init.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MalumHelper
{

    //region BLOCKSTATES
    public static <T extends Comparable<T>> BlockState newStateWithOldProperty(BlockState oldState, BlockState newState, IProperty<T> property)
    {
        return newState.with(property, oldState.get(property));
    }

    public static void makeMachineToggleSound(World world, BlockPos pos)
    {
        world.playSound(null, pos, ModSounds.machine_toggle_sound, SoundCategory.BLOCKS,1f,0.4f + MalumMod.random.nextFloat());
    }

    public static void setBlockStateWithExistingProperties(World world, BlockPos pos, BlockState newState)
    {
        BlockState oldState = world.getBlockState(pos);

        BlockState finalState = newState;
        for (IProperty<?> property : oldState.getProperties())
        {
            if (newState.has(property))
            {
                finalState = newStateWithOldProperty(oldState, finalState, property);
            }
        }

        world.notifyBlockUpdate(pos, oldState, finalState, 3);
        world.setBlockState(pos, finalState);
    }

    //endregion
    //region TE STACK HANDLING

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
            funkEngineTileEntity.stopSound();
            MalumHelper.giveItemStackToPlayer(player, targetItem);
            inventory.setStackInSlot(0,ItemStack.EMPTY);
            return false;
        }
        return false;
    }
    public static boolean stackRestrictedItemTEHandling(PlayerEntity player,Hand hand, Item restrictedItem, ItemStack heldItem, ItemStackHandler inventory, int slot)
    {
        ItemStack targetItem = inventory.getStackInSlot(slot);
        if (heldItem.getItem().equals(targetItem.getItem()))
        {
            if (heldItem.getItem().equals(restrictedItem))
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
        }
        if (targetItem.isEmpty())
        {
            if (heldItem.getItem().equals(restrictedItem))
            {
                inventory.setStackInSlot(0,heldItem);
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
        if (targetItem.isEmpty())
        {
            inventory.setStackInSlot(0,heldItem);
            player.setHeldItem(hand, ItemStack.EMPTY);
        }
        else
        {
            MalumHelper.giveItemStackToPlayer(player, targetItem);
            inventory.setStackInSlot(0,ItemStack.EMPTY);
        }
        return true;
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

    public static Vec3d randVelocity(Random rand, double min, double max)
    {
        double x = MathHelper.nextDouble(rand, min, max);
        double y = MathHelper.nextDouble(rand, min, max);
        double z = MathHelper.nextDouble(rand, min, max);
        return new Vec3d(x, y, z);
    }


    public static Vec3d randPos(BlockPos pos, Random rand, double min, double max)
    {
        double x = MathHelper.nextDouble(rand, min, max) + pos.getX();
        double y = MathHelper.nextDouble(rand, min, max) + pos.getY();
        double z = MathHelper.nextDouble(rand, min, max) + pos.getZ();
        return new Vec3d(x, y, z);
    }

    public static Vec3d randPos(Vec3d pos, Random rand, double min, double max)
    {
        double x = MathHelper.nextDouble(rand, min, max) + pos.getX();
        double y = MathHelper.nextDouble(rand, min, max) + pos.getY();
        double z = MathHelper.nextDouble(rand, min, max) + pos.getZ();
        return new Vec3d(x, y, z);
    }

    public static Vec3d frontOfEntity(Entity entity)
    {
        return entityCenter(entity).add(entity.getLookVec());
    }

    public static Vec3d entityFacingPlayer(Entity entity, PlayerEntity playerEntity)
    {
        return entityCenter(entity).add(playerEntity.getLookVec().mul(0.5,0.5,0.5));
    }

    public static Vec3d entityCenter(Entity entity)
    {
        return new Vec3d((entity.getBoundingBox().minX + entity.getBoundingBox().maxX) / 2, (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2, (entity.getBoundingBox().minZ + entity.getBoundingBox().maxZ) / 2);
    }

    public static Vec3d randPosofEntity(Entity entity, Random rand)
    {
        double x = MathHelper.nextDouble(rand, entity.getBoundingBox().minX, entity.getBoundingBox().maxX);
        double y = MathHelper.nextDouble(rand, entity.getBoundingBox().minY, entity.getBoundingBox().maxY);
        double z = MathHelper.nextDouble(rand, entity.getBoundingBox().minZ, entity.getBoundingBox().maxZ);
        return new Vec3d(x, y, z);
    }

    public static Vec3d randExtendedPosofEntity(Entity entity, Random rand, float multiplier)
    {
        double x = MathHelper.nextDouble(rand, entity.getBoundingBox().minX - (entity.getBoundingBox().getXSize() * multiplier), entity.getBoundingBox().maxX + (entity.getBoundingBox().getXSize() * multiplier));
        double y = MathHelper.nextDouble(rand, entity.getBoundingBox().minY - (entity.getBoundingBox().getYSize() * multiplier), entity.getBoundingBox().maxY + (entity.getBoundingBox().getYSize() * multiplier));
        double z = MathHelper.nextDouble(rand, entity.getBoundingBox().minZ - (entity.getBoundingBox().getZSize() * multiplier), entity.getBoundingBox().maxZ + (entity.getBoundingBox().getZSize() * multiplier));
        return new Vec3d(x, y, z);
    }
    public static Vec3d randSimulatedExtendedPosofEntity(Entity entity, Vec3d pos, Random rand, float multiplier)
    {
        double x = MathHelper.nextDouble(rand, pos.x - (entity.getBoundingBox().getXSize() * multiplier), pos.x + (entity.getBoundingBox().getXSize() * multiplier));
        double y = MathHelper.nextDouble(rand, pos.y - (entity.getBoundingBox().getYSize() * multiplier), pos.y + (entity.getBoundingBox().getYSize() * multiplier));
        double z = MathHelper.nextDouble(rand, pos.z - (entity.getBoundingBox().getZSize() * multiplier), pos.z + (entity.getBoundingBox().getZSize() * multiplier));
        return new Vec3d(x, y, z);
    }
    @OnlyIn(Dist.CLIENT)
    public static void makeTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn, ArrayList<ITextComponent> components)
    {
        if (!Screen.hasShiftDown())
        {
            tooltip.add(new TranslationTextComponent("malum.tooltip.hold.desc").applyTextStyle(TextFormatting.GRAY)
                    .appendSibling(makeImportantComponent("malum.tooltip.sneak.desc", false)));
        }
        else
        {
            tooltip.add(new TranslationTextComponent("malum.tooltip.hold.desc").applyTextStyle(TextFormatting.WHITE)
                    .appendSibling(makeImportantComponent("malum.tooltip.sneak.desc", true)));
            tooltip.addAll(components);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static ITextComponent makeImportantComponent(String message, boolean litUp)
    {
        if (litUp)
        {
            return new StringTextComponent("[").applyTextStyle(TextFormatting.WHITE)
                    .appendSibling(new TranslationTextComponent(message).applyTextStyle(TextFormatting.LIGHT_PURPLE))
                    .appendSibling(new StringTextComponent("] ").applyTextStyle(TextFormatting.WHITE)).applyTextStyle(TextFormatting.BOLD);
        }
        else
        {
            return new StringTextComponent("[").applyTextStyle(TextFormatting.GRAY)
                    .appendSibling(new TranslationTextComponent(message).applyTextStyle(TextFormatting.DARK_PURPLE))
                    .appendSibling(new StringTextComponent("] ").applyTextStyle(TextFormatting.GRAY)).applyTextStyle(TextFormatting.BOLD);
        }
    }

    @Nullable
    public static Entity getClosestEntity(List<Entity> entities, double x, double y, double z)
    {
        double cachedDistance = -1.0D;
        Entity resultEntity = null;

        for (Entity entity : entities)
        {
            double newDistance = entity.getDistanceSq(x, y, z);
            if (cachedDistance == -1.0D || newDistance < cachedDistance)
            {
                cachedDistance = newDistance;
                resultEntity = entity;
            }
        }
        return resultEntity;
    }

    @Nullable
    public static Entity getClosestEntity(List<Entity> entities, Vec3d pos)
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
    public static Vec3d tryTeleportPlayer(PlayerEntity playerEntity, Vec3d testPosition)
    {
        Vec3d cachedPosition = playerEntity.getPositionVec();
        Vec3d newPosition = cachedPosition;
        playerEntity.teleportKeepLoaded(testPosition.x, testPosition.y, testPosition.z);
        if (!playerEntity.world.checkBlockCollision(playerEntity.getBoundingBox()))
        {
            newPosition = testPosition;
        }
        playerEntity.teleportKeepLoaded(cachedPosition.x, cachedPosition.y, cachedPosition.z);
        return newPosition;
    }
}