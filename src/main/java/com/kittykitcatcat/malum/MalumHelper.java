package com.kittykitcatcat.malum;

import com.kittykitcatcat.malum.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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

    public static void updateState(World world, BlockState state, BlockPos pos)
    {
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    public static void updateState(World world, BlockState state, BlockState newState, BlockPos pos)
    {
        world.notifyBlockUpdate(pos, state, newState, 3);
    }
    //endregion
    //region TE STACK HANDLING

    public static boolean stackRestrictedItemTEHandling(PlayerEntity player, Item restrictedItem, ItemStack heldItem, ItemStackHandler inventory, int slot)
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
                        MalumHelper.addStackToTEInventory(inventory, heldItem, 0);
                        heldItem.setCount(heldItem.getCount() - 1);
                    }
                }
                return true;
            }
        }
        if (targetItem.isEmpty())
        {
            if (heldItem.getItem().equals(restrictedItem))
            {
                MalumHelper.setStackInTEInventory(inventory, heldItem, 0);
            }
        }
        else
        {
            MalumHelper.giveItemStackToPlayer(player, targetItem);
            MalumHelper.setStackInTEInventory(inventory, ItemStack.EMPTY, 0);
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
                    MalumHelper.addStackToTEInventory(inventory, heldItem, 0);
                    heldItem.setCount(heldItem.getCount() - 1);

                }
            }
            return true;
        }
        if (targetItem.isEmpty())
        {
            MalumHelper.setStackInTEInventory(inventory, heldItem, 0);
            player.setHeldItem(hand, ItemStack.EMPTY);
        }
        else
        {
            MalumHelper.giveItemStackToPlayer(player, targetItem);
            MalumHelper.setStackInTEInventory(inventory, ItemStack.EMPTY, 0);
        }
        return true;
    }
    public static void setStackInTEInventory(ItemStackHandler inventory, ItemStack stack, int slot)
    {
        inventory.setStackInSlot(slot, stack);
    }
    public static void addStackToTEInventory(ItemStackHandler inventory, ItemStack stack, int slot)
    {
        inventory.getStackInSlot(slot).setCount(inventory.getStackInSlot(slot).getCount() + stack.getCount());
    }
    public static void increaseStackSizeInTEInventory(ItemStackHandler inventory, int amount, int slot)
    {
        inventory.getStackInSlot(slot).setCount(inventory.getStackInSlot(slot).getCount() + amount);
    }

    public static void decreaseStackSizeInTEInventory(ItemStackHandler inventory, int amount, int slot)
    {
        inventory.getStackInSlot(slot).setCount(inventory.getStackInSlot(slot).getCount() - amount);
    }

    public static void putStackInTE(TileEntity inputTileEntity, Direction direction, ItemStack stack)
    {
        IItemHandler inventory = inputTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);

        if (!stack.isEmpty())
        {
            ItemStack simulate = ItemHandlerHelper.insertItem(inventory, stack, true);
            int count = stack.getCount() - simulate.getCount();
            ItemHandlerHelper.insertItem(inventory, stack.split(count), false);
        }
        inputTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction).ifPresent(itemHandler ->
        {
            for (int j = 0; j < itemHandler.getSlots(); j++)
            {
                ItemStack remaining = ItemHandlerHelper.insertItem(itemHandler, stack, false);
                if (remaining.isEmpty())
                {
                    return;
                }
                stack.setCount(remaining.getCount());
            }
        });
    }
    //endregion
    public static void giveItemStackToPlayer(PlayerEntity playerEntity, ItemStack stack)
    {
        ItemHandlerHelper.giveItemToPlayer(playerEntity, stack);
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

    public static Vec3d randPosofEntity(Entity entity, Random rand)
    {
        double x = MathHelper.nextDouble(rand, entity.getBoundingBox().minX, entity.getBoundingBox().maxX);
        double y = MathHelper.nextDouble(rand, entity.getBoundingBox().minY, entity.getBoundingBox().maxY);
        double z = MathHelper.nextDouble(rand, entity.getBoundingBox().minZ, entity.getBoundingBox().maxZ);
        return new Vec3d(x, y, z);
    }

    public static Vec3d randExtendedPosofEntity(Entity entity, Random rand)
    {
        double x = MathHelper.nextDouble(rand, entity.getBoundingBox().minX - (entity.getBoundingBox().getXSize() / 4), entity.getBoundingBox().maxX) + (entity.getBoundingBox().getXSize() / 4);
        double y = MathHelper.nextDouble(rand, entity.getBoundingBox().minY - (entity.getBoundingBox().getYSize() / 4), entity.getBoundingBox().maxY) + (entity.getBoundingBox().getYSize() / 4);
        double z = MathHelper.nextDouble(rand, entity.getBoundingBox().minZ - (entity.getBoundingBox().getZSize() / 4), entity.getBoundingBox().maxZ) + (entity.getBoundingBox().getZSize() / 4);
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
        return new Vec3d(((entity.getBoundingBox().minX + entity.getBoundingBox().maxX) / 2) + entity.getMotion().x / 2, ((entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2) + entity.getMotion().y / 2, ((entity.getBoundingBox().minZ + entity.getBoundingBox().maxZ) / 2) + entity.getMotion().z / 2);
    }

    public static Vec3d entityFacingPlayer(Entity entity, PlayerEntity playerEntity)
    {
        return new Vec3d(((entity.getBoundingBox().minX + entity.getBoundingBox().maxX) / 2) - playerEntity.getLookVec().x / 2, ((entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2) - playerEntity.getLookVec().y / 2, ((entity.getBoundingBox().minZ + entity.getBoundingBox().maxZ) / 2) - playerEntity.getLookVec().z / 2);
    }

    public static Vec3d entityCenter(Entity entity)
    {
        return new Vec3d((entity.getBoundingBox().minX + entity.getBoundingBox().maxX) / 2, (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2, (entity.getBoundingBox().minZ + entity.getBoundingBox().maxZ) / 2);
    }

    @OnlyIn(Dist.CLIENT)
    public static void makeTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn, ArrayList<ITextComponent> components)
    {
        if (!Screen.hasShiftDown())
        {
            tooltip.add(new TranslationTextComponent("malum.tooltip.sneak.desc.a").applyTextStyle(TextFormatting.GRAY)
                    .appendSibling(makeImportantComponent("malum.tooltip.sneak.desc.b", false)));
        }
        else
        {
            tooltip.add(new TranslationTextComponent("malum.tooltip.sneak.desc.a").applyTextStyle(TextFormatting.WHITE)
                    .appendSibling(makeImportantComponent("malum.tooltip.sneak.desc.b", true)));
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

    public static Vec3d tryTeleportPlayer(PlayerEntity playerEntity, Vec3d direction, Vec3d newPosition, int i)
    {
        Vec3d cachedPosition = playerEntity.getPositionVec();
        Vec3d testPosition = (playerEntity.getPositionVec()).add(direction.mul(i, i, i));
        playerEntity.teleportKeepLoaded(testPosition.x, testPosition.y, testPosition.z);
        if (!playerEntity.world.checkBlockCollision(playerEntity.getBoundingBox()))
        {
            newPosition = testPosition;
        }
        playerEntity.teleportKeepLoaded(cachedPosition.x, cachedPosition.y, cachedPosition.z);
        return newPosition;
    }
}