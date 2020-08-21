package com.kittykitcatcat.malum;

import com.kittykitcatcat.malum.blocks.machines.funkengine.FunkEngineTileEntity;
import com.kittykitcatcat.malum.blocks.machines.redstoneclock.RedstoneClockTileEntity;
import com.kittykitcatcat.malum.blocks.utility.FancyRenderer;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.recipes.SpiritFurnaceFuelData;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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

import static com.kittykitcatcat.malum.MalumMod.random;
import static net.minecraft.util.math.RayTraceResult.Type.BLOCK;

public class MalumHelper
{
    
    //region BLOCKSTATES
    public static <T extends Comparable<T>> BlockState newStateWithOldProperty(BlockState oldState, BlockState newState, IProperty<T> property)
    {
        return newState.with(property, oldState.get(property));
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
    
    //region STACK HANDLING
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
    public static Vec3d randomVector(Random random, double min, double max)
    {
        double x = MathHelper.nextDouble(random, min, max);
        double y = MathHelper.nextDouble(random, min, max);
        double z = MathHelper.nextDouble(random, min, max);
        return new Vec3d(x, y, z);
    }
    public static Vec3d vectorFromBlockPos(BlockPos pos)
    {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        return new Vec3d(x, y, z);
    }

    public static Vec3d entityCenter(Entity entity)
    {
        return new Vec3d((entity.getBoundingBox().minX + entity.getBoundingBox().maxX) / 2, (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2, (entity.getBoundingBox().minZ + entity.getBoundingBox().maxZ) / 2);
    }

    public static Vec3d randPosofEntity(Entity entity, Random rand)
    {
        double x = MathHelper.nextDouble(random, entity.getBoundingBox().minX, entity.getBoundingBox().maxX);
        double y = MathHelper.nextDouble(random, entity.getBoundingBox().minY, entity.getBoundingBox().maxY);
        double z = MathHelper.nextDouble(random, entity.getBoundingBox().minZ, entity.getBoundingBox().maxZ);
        return new Vec3d(x, y, z);
    }

    public static Vec3d randExtendedPosofEntity(Entity entity, Random rand, float multiplier)
    {
        double x = MathHelper.nextDouble(random, entity.getBoundingBox().minX - (entity.getBoundingBox().getXSize() * multiplier), entity.getBoundingBox().maxX + (entity.getBoundingBox().getXSize() * multiplier));
        double y = MathHelper.nextDouble(random, entity.getBoundingBox().minY - (entity.getBoundingBox().getYSize() * multiplier), entity.getBoundingBox().maxY + (entity.getBoundingBox().getYSize() * multiplier));
        double z = MathHelper.nextDouble(random, entity.getBoundingBox().minZ - (entity.getBoundingBox().getZSize() * multiplier), entity.getBoundingBox().maxZ + (entity.getBoundingBox().getZSize() * multiplier));
        return new Vec3d(x, y, z);
    }
    public static Vec3d randSimulatedExtendedPosofEntity(Entity entity, Vec3d pos, Random rand, float multiplier)
    {
        double x = MathHelper.nextDouble(random, pos.x - (entity.getBoundingBox().getXSize() * multiplier), pos.x + (entity.getBoundingBox().getXSize() * multiplier));
        double y = MathHelper.nextDouble(random, pos.y - (entity.getBoundingBox().getYSize() * multiplier), pos.y + (entity.getBoundingBox().getYSize() * multiplier));
        double z = MathHelper.nextDouble(random, pos.z - (entity.getBoundingBox().getZSize() * multiplier), pos.z + (entity.getBoundingBox().getZSize() * multiplier));
        return new Vec3d(x, y, z);
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
    //endregion
    
    //region TOOLTIP THINGIES
    @OnlyIn(Dist.CLIENT)
    public static void makeTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn, ArrayList<ITextComponent> components)
    {
        if (!Screen.hasShiftDown())
        {
            tooltip.add(new TranslationTextComponent("malum.tooltip.hold").applyTextStyle(TextFormatting.GRAY)
                    .appendSibling(makeImportantComponent("malum.tooltip.sneak", false)));
        }
        else
        {
            tooltip.add(new TranslationTextComponent("malum.tooltip.hold").applyTextStyle(TextFormatting.WHITE)
                    .appendSibling(makeImportantComponent("malum.tooltip.sneak", true)));
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
    //endregion
    
    //region ENTITY STUFF

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
    //endregion
    
    //region RENDERING NONSENSE
    public static void renderTEdataInTheCoolFancyWayWithoutCaringAboutSides(TileEntity blockEntity, FancyRenderer renderer, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, TileEntityRendererDispatcher renderDispatcher, ArrayList<ITextComponent> components)
    {
        if (renderDispatcher.renderInfo != null && blockEntity.getDistanceSq(renderDispatcher.renderInfo.getProjectedView().x, renderDispatcher.renderInfo.getProjectedView().y, renderDispatcher.renderInfo.getProjectedView().z) < 128d)
        {
            Minecraft minecraft = Minecraft.getInstance();
            World world = minecraft.world;
            if (minecraft.objectMouseOver == null || world == null)
            {
                return;
            }
            if (minecraft.objectMouseOver.getType().equals(BLOCK))
            {
                BlockRayTraceResult mouseOver = (BlockRayTraceResult) minecraft.objectMouseOver;
                if (renderer.lookingAtPos() == null || !renderer.lookingAtPos().equals(mouseOver.getPos()))
                {
                    renderer.setLookingAtPos( mouseOver.getPos());
                    renderer.setTime(0);
                }
                if (world.getTileEntity(mouseOver.getPos()) != null)
                {
                    if (world.getTileEntity(mouseOver.getPos()).equals(blockEntity))
                    {
                        TileEntity tileEntity = world.getTileEntity(mouseOver.getPos());
                        if (tileEntity.getPos().equals(blockEntity.getPos()))
                        {
                            if (renderer.lookingAtPos() != null && renderer.lookingAtPos().equals(mouseOver.getPos()) && renderer.time() < 1)
                            {
                                renderer.setTime(renderer.time() + 0.1f);
                            }
                            FontRenderer fontrenderer = renderDispatcher.getFontRenderer();
                            float spacing = 0.2f;
                            int current = components.size() + 1;
                            for (ITextComponent component : components)
                            {
                                String text = component.getFormattedText();
                                float xOffset = (float) (-fontrenderer.getStringWidth(text) / 2);
                                matrixStack.push();
                                
                                Matrix4f matrix4f = matrixStack.getLast().getMatrix();
                                
                                matrixStack.translate(0.5, 0.5, 0.5);
                                
                                matrixStack.rotate(renderDispatcher.renderInfo.getRotation());
                                float positionOffset = current * spacing - (float)components.size() * spacing / 2;
                                matrixStack.translate(0, positionOffset, 0);
                                
                                matrixStack.scale(-0.025F * renderer.time(), -0.025F * renderer.time(), 0.025F * renderer.time());
                                
                                fontrenderer.renderString(text, xOffset, 0, -1, true, matrix4f, iRenderTypeBuffer, false, (int) 0f << 24, 192);
                                
                                matrixStack.pop();
                                current--;
                            }
                        }
                    }
                }
            }
            else
            {
                renderer.setLookingAtPos(null);
                renderer.setTime(0);
            }
        }
    }
    public static void renderTEdataInTheCoolFancyWay(TileEntity blockEntity, FancyRenderer renderer, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, TileEntityRendererDispatcher renderDispatcher, ArrayList<ITextComponent> components)
    {
        if (renderDispatcher.renderInfo != null && blockEntity.getDistanceSq(renderDispatcher.renderInfo.getProjectedView().x, renderDispatcher.renderInfo.getProjectedView().y, renderDispatcher.renderInfo.getProjectedView().z) < 128d)
        {
            Minecraft minecraft = Minecraft.getInstance();
            World world = minecraft.world;
            if (minecraft.objectMouseOver == null || world == null)
            {
                return;
            }
            if (minecraft.objectMouseOver.getType().equals(BLOCK))
            {
                BlockRayTraceResult mouseOver = (BlockRayTraceResult) minecraft.objectMouseOver;
                if ((renderer.lookingAtFace() == null || !renderer.lookingAtFace().equals(mouseOver.getFace())) || (renderer.lookingAtPos() == null || !renderer.lookingAtPos().equals(mouseOver.getPos())))
                {
                    renderer.setLookingAtFace( mouseOver.getFace());
                    renderer.setLookingAtPos( mouseOver.getPos());
                    renderer.setTime(0);
                }
                if (world.getTileEntity(mouseOver.getPos()) != null)
                {
                    if (world.getTileEntity(mouseOver.getPos()).equals(blockEntity))
                    {
                        TileEntity tileEntity = world.getTileEntity(mouseOver.getPos());
                        Vector3f direction = mouseOver.getFace().toVector3f();
                        if (tileEntity.getPos().equals(blockEntity.getPos()))
                        {
                            if (renderer.lookingAtPos() != null && renderer.lookingAtPos().equals(mouseOver.getPos()) && renderer.time() < 1)
                            {
                                renderer.setTime(renderer.time() + 0.1f);
                            }
                            FontRenderer fontrenderer = renderDispatcher.getFontRenderer();
                            float spacing = 0.2f;
                            int current = components.size() + 1;
                            for (ITextComponent component : components)
                            {
                                String text = component.getFormattedText();
                                float xOffset = (float) (-fontrenderer.getStringWidth(text) / 2);
                                matrixStack.push();
                
                                Matrix4f matrix4f = matrixStack.getLast().getMatrix();
                
                                matrixStack.translate(0.5, 0.5, 0.5);
                
                                matrixStack.translate(direction.getX(), direction.getY(), direction.getZ());
                                
                                matrixStack.rotate(renderDispatcher.renderInfo.getRotation());
                                float positionOffset = current * spacing - (float)components.size() * spacing / 2;
                                matrixStack.translate(0, positionOffset, 0);
                
                                matrixStack.scale(-0.025F * renderer.time(), -0.025F * renderer.time(), 0.025F * renderer.time());
                
                                fontrenderer.renderString(text, xOffset, 0, -1, true, matrix4f, iRenderTypeBuffer, false, (int) 0f << 24, 192);
                
                                matrixStack.pop();
                                current--;
                            }
                        }
                    }
                }
            }
            else
            {
                renderer.setLookingAtPos(null);
                renderer.setTime(0);
            }
        }
    }
    //endregion
    
    //region SOUND MAGIC
    
    public static boolean playLoopingSound(SimpleSound sound)
    {
        if (sound != null)
        {
            if (!Minecraft.getInstance().getSoundHandler().isPlaying(sound))
            {
                Minecraft.getInstance().getSoundHandler().play(sound);
            }
        }
        return sound == null;
    }
    public static void stopPlayingSound(SimpleSound sound)
    {
        if (sound != null)
        {
            if (Minecraft.getInstance().getSoundHandler().isPlaying(sound))
            {
                Minecraft.getInstance().getSoundHandler().stop(sound);
            }
        }
    }
    public static void makeMachineToggleSound(World world, BlockPos pos, float pitch)
    {
        world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS,1f,pitch);
        world.playSound(null, pos, ModSounds.machine_toggle_sound, SoundCategory.BLOCKS,1f,pitch);
    }
    //endregion
}