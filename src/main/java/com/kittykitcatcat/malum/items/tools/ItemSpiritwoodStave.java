package com.kittykitcatcat.malum.items.tools;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModItems;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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
                        moveCamera(player, target);

                    }
                    else
                    {
                        event.setCanceled(true);
                    }
                }
                else
                {
                    event.setCanceled(true);
                }
            }
        }
    }
    public static void moveCamera(PlayerEntity playerEntity, LivingEntity target)
    {
        double minX = target.getBoundingBox().minX;
        double minY = target.getBoundingBox().minY;
        double minZ = target.getBoundingBox().minZ;
        double maxX = target.getBoundingBox().maxX;
        double maxY = target.getBoundingBox().maxY;
        double maxZ = target.getBoundingBox().maxZ;
        Vec3d boundingVec = (new Vec3d(minX,minY,minZ).add(maxX,maxY,maxZ)).mul(0.5,0.5,0.5);
        faceLocation(boundingVec, playerEntity);
    }
    public static void faceLocation(Vec3d lookat, PlayerEntity player) {
        double d0 = lookat.getX() + 0.5 - player.getPosX();
        double d1 = lookat.getY() + 0.5 - player.getPosY() - player.getEyeHeight();
        double d2 = lookat.getZ() + 0.5 - player.getPosZ();

        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        float yaw = (float) (Math.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float pitch = (float) (-(Math.atan2(d1, d3) * (180D / Math.PI)));
        rotatePlayer(player, player.rotationYaw + (yaw - player.rotationYaw) / 4, player.rotationPitch + (pitch - player.rotationPitch) / 4);
    }

    public static void rotatePlayer(PlayerEntity player, float yaw, float pitch) {
        final float oldYawHead = player.rotationYawHead;
        player.rotationYawHead = yaw;
        player.prevRotationYawHead += player.rotationYawHead - oldYawHead;
        final float oldYaw = player.rotationYaw;
        player.rotationYaw = yaw;
        player.prevRotationYaw += player.rotationYaw - oldYaw;

        final float oldPitch = player.rotationPitch;
        player.rotationPitch = pitch;
        player.prevRotationPitch += player.rotationPitch - oldPitch;
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
    public static boolean isEntityValid(LivingEntity target)
    {
        return target.getHealth() <= target.getMaxHealth() / 10 || target.getHealth() <= 2;
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
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
    {
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
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
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
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

