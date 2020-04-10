package com.kittykitcatcat.malum;

import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
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
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MalumHelper
{
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

    public static void giveItemStackToPlayer(PlayerEntity target, ItemStack stack)
    {
        target.world.addEntity(new ItemEntity(target.world, target.getPosX(), target.getPosY(), target.getPosZ(), stack));
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

    public static double lerp(double point1, double point2, double alpha)
    {
        return point1 + alpha * (point2 - point1);
    }

    public static Vec3d lerp(Vec3d point1, Vec3d point2, double alpha)
    {
        double x = lerp(point1.x, point2.x, alpha);
        double y = lerp(point1.y, point2.y, alpha);
        double z = lerp(point1.z, point2.z, alpha);
        return new Vec3d(point1.x, y, point1.z);
    }

    public static Vec3d randVelocity(World world, double min, double max)
    {
        double x = MathHelper.nextDouble(world.rand, min, max);
        double y = MathHelper.nextDouble(world.rand, min, max);
        double z = MathHelper.nextDouble(world.rand, min, max);
        return new Vec3d(x, y, z);
    }

    public static ItemStack itemStack(Item item, int count)
    {
        ItemStack stack = new ItemStack(item);
        stack.setCount(count);
        return stack;
    }

    public static Vec3d randPos(BlockPos pos, World world, double min, double max)
    {
        double x = MathHelper.nextDouble(world.rand, min, max) + pos.getX();
        double y = MathHelper.nextDouble(world.rand, min, max) + pos.getY();
        double z = MathHelper.nextDouble(world.rand, min, max) + pos.getZ();
        return new Vec3d(x, y, z);
    }

    public static Vec3d randPos(Vec3d pos, World world, double min, double max)
    {
        double x = MathHelper.nextDouble(world.rand, min, max) + pos.getX();
        double y = MathHelper.nextDouble(world.rand, min, max) + pos.getY();
        double z = MathHelper.nextDouble(world.rand, min, max) + pos.getZ();
        return new Vec3d(x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public static void makeTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn, ArrayList<ITextComponent> components)
    {
        if (!Screen.hasShiftDown())
        {
            tooltip.add(new TranslationTextComponent("malum.tooltip.sneak.desc.a").applyTextStyle(TextFormatting.GRAY)
                    .appendSibling(new StringTextComponent(" [").applyTextStyle(TextFormatting.GRAY))
                    .appendSibling(new TranslationTextComponent("malum.tooltip.sneak.desc.b").applyTextStyle(TextFormatting.DARK_PURPLE))
                    .appendSibling(new StringTextComponent("]").applyTextStyle(TextFormatting.GRAY)));
        }
        else
        {
            tooltip.addAll(components);
        }
    }

    @Nullable
    public static Entity getClosestEntity(List<Entity> entities, double x, double y, double z)
    {
        double d0 = -1.0D;
        Entity t = null;

        for (Entity t1 : entities)
        {
            double d1 = t1.getDistanceSq(x, y, z);
            if (d0 == -1.0D || d1 < d0)
            {
                d0 = d1;
                t = t1;
            }
        }
        return t;
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