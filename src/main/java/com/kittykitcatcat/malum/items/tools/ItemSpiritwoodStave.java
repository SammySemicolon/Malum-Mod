package com.kittykitcatcat.malum.items.tools;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemSpiritwoodStave extends Item
{
    public ItemSpiritwoodStave(Properties properties)
    {
        super(properties);
    }

    //VISUAL AND CLIENT STUFF
    @SubscribeEvent
    public static void zoomIn(FOVUpdateEvent event)
    {
        if (event.getEntity() != null)
        {
            PlayerEntity player = event.getEntity();
            if (player.getHeldItemMainhand().getItem().equals(ModItems.spiritwood_stave))
            {
                float drainProgress = CapabilityValueGetter.getDrainProgress(player);
                event.setNewfov(event.getFov()-drainProgress/100);
            }
        }
    }
    @SubscribeEvent
    public static void previewTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem().equals(ModItems.spiritwood_stave))
            {
                if (findEntity(player) != null)
                {
                    LivingEntity target = findEntity(player);
                    if (isEntityValid(target))
                    {
                        target.addPotionEffect(new EffectInstance(Effects.GLOWING, 5, 1, true, false));
                    }
                }
            }
        }
    }
    //LOGIC HANDLING
    @SubscribeEvent
    public static void cancelDrain(LivingEntityUseItemEvent.Start event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem().equals(ModItems.spiritwood_stave))
            {
                if (player.getHeldItemMainhand().getTag() != null)
                {
                    if (player.getHeldItemMainhand().getTag().contains("entityRegistryName"))
                    {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void stopDrain(LivingEntityUseItemEvent.Stop event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem().equals(ModItems.spiritwood_stave))
            {
                if (CapabilityValueGetter.getDrainProgress(player) > 0)
                {
                    CapabilityValueGetter.setDrainProgress(player, 0);
                    stopDrain(player);
                }
            }
        }
    }
    @SubscribeEvent
    public static void drainSoul(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem().equals(ModItems.spiritwood_stave))
            {
                if (player.getHeldItemMainhand().getTag() != null)
                {
                    if (player.getHeldItemMainhand().getTag().contains("entityRegistryName"))
                    {
                        event.setCanceled(true);
                    }
                }

                if (findEntity(player) != null)
                {
                    LivingEntity target = findEntity(player);
                    if (isEntityValid(target))
                    {
                        attemptDrain(player,target);
                    }
                    else
                    {
                        stopDrain(player);
                        event.setCanceled(true);
                    }
                }
                else
                {
                    if (CapabilityValueGetter.getDrainProgress(player) > 0) //FAIL
                    {
                        CapabilityValueGetter.setDrainProgress(player, 0);
                        stopDrain(player);
                    }
                    event.setCanceled(true);
                }
            }
        }
    }
    public static boolean isEntityValid(LivingEntity target)
    {
        return target.getHealth() <= target.getMaxHealth() / 10 || target.getHealth() <= 2;
    }
    public static LivingEntity findEntity(PlayerEntity player)
    {
        World world = player.world;
        float yaw = player.rotationYaw;
        float pitch = player.rotationPitch;
        float f = -MathHelper.sin(yaw * ((float) Math.PI / 180F)) * MathHelper.cos(pitch * ((float) Math.PI / 180F));
        float f1 = -MathHelper.sin(pitch * ((float) Math.PI / 180F));
        float f2 = MathHelper.cos(yaw * ((float) Math.PI / 180F)) * MathHelper.cos(pitch * ((float) Math.PI / 180F));

        Vec3d looKVec = new Vec3d(f, f1, f2);
        for (int i = 1; i < 10; i++)
        {
            Vec3d pos = player.getPositionVector().add(looKVec.mul(i*0.5, i*0.5, i*0.5));
            float strength = 0.8f;
            if (!world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos.x - strength, pos.y - strength, pos.z - strength, pos.x + strength, pos.y + strength, pos.z + strength)).isEmpty())
            {
                Entity entity = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos.x - strength, pos.y - strength, pos.z - strength, pos.x + strength, pos.y + strength, pos.z + strength)).get(0);
                if (entity instanceof LivingEntity)
                {
                    return (LivingEntity) entity;
                }
            }
        }
        return null;
    }
    public static void attemptDrain(PlayerEntity player, LivingEntity target)
    {
        //moveCamera(player, target);
        float drainProgress = CapabilityValueGetter.getDrainProgress(player);
        CapabilityValueGetter.setDrainProgress(player, drainProgress+1);
        if (drainProgress == 1)
        {
            startDrain(player);
        }
        if (drainProgress >= 86)
        {
            CapabilityValueGetter.setDrainProgress(player, 0);
            drainSoul(player,target);
        }
    }
    public static void drainSoul(PlayerEntity player, LivingEntity target)
    {
        float purity = 1f;
        SpiritData data = new SpiritData(target, purity);
        data.writeSpiritDataIntoNBT(player.getHeldItemMainhand().getOrCreateTag());
        player.world.playSound(Minecraft.getInstance().player, player.getPosition(), ModSounds.soul_harvest_success, SoundCategory.PLAYERS, 1F, 1F);
        Minecraft.getInstance().getSoundHandler().stop(new ResourceLocation(MalumMod.MODID, "soul_harvest_loop"), SoundCategory.PLAYERS);
    }
    public static void startDrain(PlayerEntity player)
    {
        player.world.playSound(Minecraft.getInstance().player, player.getPosition(), ModSounds.soul_harvest_loop, SoundCategory.PLAYERS, 1F, 1F);
    }
    public static void stopDrain(PlayerEntity player)
    {
        player.world.playSound(Minecraft.getInstance().player, player.getPosition(), ModSounds.soul_harvest_fail, SoundCategory.PLAYERS, 1F, 1F);
        Minecraft.getInstance().getSoundHandler().stop(new ResourceLocation(MalumMod.MODID, "soul_harvest_loop"), SoundCategory.PLAYERS);
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.getTag() != null)
        {
            if (!itemstack.getTag().contains("entityRegistryName"))
            {
                playerIn.setActiveHand(handIn);
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    //ACTUAL ITEM
    public UseAction getUseAction(ItemStack stack)
    {
        if (stack.getTag() != null)
        {
            if (!stack.getTag().contains("entityRegistryName"))
            {
                return UseAction.BOW;
            }
        }
        return UseAction.NONE;
    }
    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (stack.getTag() == null)
        {
            stack.setTag(new CompoundNBT());
        }
    }
}

