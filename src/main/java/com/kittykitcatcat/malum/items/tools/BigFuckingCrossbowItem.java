package com.kittykitcatcat.malum.items.tools;

import com.google.common.collect.Lists;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.particles.bfcshockwave.BigFuckingShockwaveData;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static com.kittykitcatcat.malum.MalumHelper.entityCenter;
import static com.kittykitcatcat.malum.MalumHelper.frontOfEntity;

@Mod.EventBusSubscriber
public class BigFuckingCrossbowItem extends CrossbowItem
{
    /**
     * Set to {@code true} when the crossbow is 20% charged.
     */
    private boolean isLoadingStart = false;
    /**
     * Set to {@code true} when the crossbow is 50% charged.
     */
    private boolean isLoadingMiddle = false;

    public BigFuckingCrossbowItem(Item.Properties propertiesIn)
    {
        super(propertiesIn);
        this.addPropertyOverride(new ResourceLocation("pull"), (stack, world, livingEntity) ->
        {
            if (livingEntity != null && stack.getItem() == this)
            {
                return isCharged(stack) ? 0.0F : (float) (stack.getUseDuration() - livingEntity.getItemInUseCount()) / (float) getChargeTime(stack);
            }
            else
            {
                return 0.0F;
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), (stack, world, livingEntity) ->
                livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == stack && !isCharged(stack) ? 1.0F : 0.0F);
        this.addPropertyOverride(new ResourceLocation("charged"), (stack, world, livingEntity) ->
                livingEntity != null && isCharged(stack) ? 1.0F : 0.0F);
    }

    @Override
    public Predicate<ItemStack> getAmmoPredicate()
    {
        return ARROWS;
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate()
    {
        return ARROWS;
    }

    @Override
    public boolean isCrossbow(ItemStack stack)
    {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (isCharged(itemstack))
        {
            fireProjectiles(worldIn, playerIn, handIn, itemstack, shootSpeed(), 1F);
            setCharged(itemstack, false);
            return ActionResult.resultConsume(itemstack);
        }
        else if (!playerIn.findAmmo(itemstack).isEmpty())
        {
            if (!isCharged(itemstack))
            {
                this.isLoadingStart = false;
                this.isLoadingMiddle = false;
                playerIn.setActiveHand(handIn);
            }

            return ActionResult.resultConsume(itemstack);
        }
        else
        {
            return ActionResult.resultFail(itemstack);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
    {
        int i = this.getUseDuration(stack) - timeLeft;
        float f = getCharge(i, stack);
        if (f >= 1.0F && !isCharged(stack) && hasAmmo(entityLiving, stack))
        {
            setCharged(stack, true);
            SoundCategory soundcategory = entityLiving instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            worldIn.playSound(null, entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, soundcategory, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

    }

    private static boolean hasAmmo(LivingEntity entityIn, ItemStack stack)
    {
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.MULTISHOT, stack);
        int j = i == 0 ? 1 : 3;
        boolean flag = entityIn instanceof PlayerEntity && ((PlayerEntity) entityIn).abilities.isCreativeMode;
        ItemStack itemstack = entityIn.findAmmo(stack);
        ItemStack itemstack1 = itemstack.copy();

        for (int k = 0; k < j; ++k)
        {
            if (k > 0)
            {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag)
            {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!func_220023_a(entityIn, stack, itemstack, k > 0, flag))
            {
                return false;
            }
        }

        return true;
    }

    private static boolean func_220023_a(LivingEntity livingEntity, ItemStack crossbow, ItemStack arrow, boolean p_220023_3_, boolean p_220023_4_)
    {
        if (arrow.isEmpty())
        {
            return false;
        }
        else
        {
            boolean flag = p_220023_4_ && arrow.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            if (!flag && !p_220023_4_ && !p_220023_3_)
            {
                itemstack = arrow.split(1);
                if (arrow.isEmpty() && livingEntity instanceof PlayerEntity)
                {
                    ((PlayerEntity) livingEntity).inventory.deleteStack(arrow);
                }
            }
            else
            {
                itemstack = arrow.copy();
            }

            addChargedProjectile(crossbow, itemstack);
            return true;
        }
    }

    public static boolean isCharged(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.getBoolean("Charged");
    }

    public static void setCharged(ItemStack stack, boolean chargedIn)
    {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putBoolean("Charged", chargedIn);
    }

    private static void addChargedProjectile(ItemStack crossbow, ItemStack projectile)
    {
        CompoundNBT compoundnbt = crossbow.getOrCreateTag();
        ListNBT listnbt;
        if (compoundnbt.contains("ChargedProjectiles", 9))
        {
            listnbt = compoundnbt.getList("ChargedProjectiles", 10);
        }
        else
        {
            listnbt = new ListNBT();
        }

        CompoundNBT compoundnbt1 = new CompoundNBT();
        projectile.write(compoundnbt1);
        listnbt.add(compoundnbt1);
        compoundnbt.put("ChargedProjectiles", listnbt);
    }

    private static List<ItemStack> getChargedProjectiles(ItemStack stack)
    {
        List<ItemStack> list = Lists.newArrayList();
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("ChargedProjectiles", 9))
        {
            ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 10);
            if (listnbt != null)
            {
                for (int i = 0; i < listnbt.size(); ++i)
                {
                    CompoundNBT compoundnbt1 = listnbt.getCompound(i);
                    list.add(ItemStack.read(compoundnbt1));
                }
            }
        }

        return list;
    }

    private static void clearProjectiles(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null)
        {
            ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 9);
            listnbt.clear();
            compoundnbt.put("ChargedProjectiles", listnbt);
        }

    }

    private static void fireProjectile(World worldIn, LivingEntity shooter, Hand handIn, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle)
    {
        if (!worldIn.isRemote)
        {
            AbstractArrowEntity arrow = createArrow(worldIn, shooter, crossbow, projectile);
            if (isCreativeMode || projectileAngle != 0.0F)
            {
                arrow.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            }


            if (shooter instanceof ICrossbowUser)
            {
                ICrossbowUser icrossbowuser = (ICrossbowUser) shooter;
                icrossbowuser.shoot(icrossbowuser.getAttackTarget(), crossbow, arrow, projectileAngle);
            }
            else
            {
                Vec3d vec3d1 = shooter.getUpVector(1.0F);
                Quaternion quaternion = new Quaternion(new Vector3f(vec3d1), projectileAngle, true);
                Vec3d vec3d = shooter.getLook(1.0F);
                Vector3f vector3f = new Vector3f(vec3d);
                vector3f.transform(quaternion);
                arrow.shoot((double) vector3f.getX(), (double) vector3f.getY(), (double) vector3f.getZ(), velocity, inaccuracy);
            }

            crossbow.damageItem(1, shooter, (livingEntity) -> livingEntity.sendBreakAnimation(handIn));
            worldIn.addEntity(arrow);
            worldIn.playSound(null, shooter.getPosX(), shooter.getPosY(), shooter.getPosZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
            worldIn.playSound(null, shooter.getPosX(), shooter.getPosY(), shooter.getPosZ(), ModSounds.shattering_sound, SoundCategory.PLAYERS, 0.3F, 2f);
            worldIn.playSound(null, shooter.getPosX(), shooter.getPosY(), shooter.getPosZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 0.3f, 2f);

            shooter.setMotion(-shooter.getLookVec().x, -shooter.getLookVec().y / 2, -shooter.getLookVec().z);
            shooter.velocityChanged = true;
        }
    }

    private static AbstractArrowEntity createArrow(World worldIn, LivingEntity shooter, ItemStack crossbow, ItemStack ammo)
    {
        ArrowItem arrowitem = (ArrowItem) (ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, ammo, shooter);
        if (shooter instanceof PlayerEntity)
        {
            abstractarrowentity.setIsCritical(true);
        }

        abstractarrowentity.setHitSound(SoundEvents.ITEM_CROSSBOW_HIT);
        abstractarrowentity.setShotFromCrossbow(true);
        abstractarrowentity.addTag("big_fucking_crossbow_shot");
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.PIERCING, crossbow);
        if (i > 0)
        {
            abstractarrowentity.setPierceLevel((byte) i);
        }

        return abstractarrowentity;
    }
    @SubscribeEvent
    public static void makeBoltGoBOOM(ProjectileImpactEvent event)
    {
        if (event.getEntity() instanceof ArrowEntity)
        {
            ArrowEntity entity = (ArrowEntity) event.getEntity();
            if (entity.getTags().contains("big_fucking_crossbow_shot"))
            {
                if (entity.getEntityWorld() instanceof ServerWorld)
                {
                    ((ServerWorld) entity.getEntityWorld()).spawnParticle(new BigFuckingShockwaveData(), frontOfEntity(event.getEntity()).x, frontOfEntity(event.getEntity()).y, frontOfEntity(event.getEntity()).z, 1,0,0,0,0);
                }
                for (Entity target : entity.world.getEntitiesWithinAABBExcludingEntity(entity, new AxisAlignedBB(entity.getPosX() - 3, entity.getPosY() - 3, entity.getPosZ() - 3, entity.getPosX() + 3, entity.getPosY() + 3, entity.getPosZ() + 3)))
                {
                    if (target instanceof LivingEntity)
                    {
                        if (!target.equals(entity.getShooter()))
                        {
                            target.attackEntityFrom(DamageSource.causeArrowDamage(entity, entity.getShooter()), (float) entity.getDamage());
                        }
                        Vec3d direction = target.getPositionVec().subtract(entity.getPositionVec());
                        ((LivingEntity) target).knockBack(entity, 1f, -direction.x, -direction.z);
                        target.velocityChanged = true;
                    }
                }
            }
        }
    }

    public static void fireProjectiles(World worldIn, LivingEntity shooter, Hand handIn, ItemStack stack, float velocityIn, float inaccuracyIn)
    {
        List<ItemStack> list = getChargedProjectiles(stack);
        float[] afloat = getRandomSoundPitches(shooter.getRNG());

        for (int i = 0; i < list.size(); ++i)
        {
            ItemStack itemstack = list.get(i);
            boolean flag = shooter instanceof PlayerEntity && ((PlayerEntity) shooter).abilities.isCreativeMode;
            if (!itemstack.isEmpty())
            {
                if (i == 0)
                {
                    fireProjectile(worldIn, shooter, handIn, stack, itemstack, afloat[i], flag, velocityIn, inaccuracyIn, 0.0F);
                }
                else if (i == 1)
                {
                    fireProjectile(worldIn, shooter, handIn, stack, itemstack, afloat[i], flag, velocityIn, inaccuracyIn, -10.0F);
                }
                else if (i == 2)
                {
                    fireProjectile(worldIn, shooter, handIn, stack, itemstack, afloat[i], flag, velocityIn, inaccuracyIn, 10.0F);
                }
            }
        }

        fireProjectilesAfter(worldIn, shooter, stack);
    }

    private static float[] getRandomSoundPitches(Random rand)
    {
        boolean flag = rand.nextBoolean();
        return new float[]{1.0F, getRandomSoundPitch(flag), getRandomSoundPitch(!flag)};
    }

    private static float getRandomSoundPitch(boolean flagIn)
    {
        float f = flagIn ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void fireProjectilesAfter(World worldIn, LivingEntity shooter, ItemStack stack)
    {
        if (shooter instanceof ServerPlayerEntity)
        {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) shooter;
            if (!worldIn.isRemote)
            {
                CriteriaTriggers.SHOT_CROSSBOW.func_215111_a(serverplayerentity, stack);
            }

            serverplayerentity.addStat(Stats.ITEM_USED.get(stack.getItem()));
        }

        clearProjectiles(stack);
    }

    @Override
    public void func_219972_a(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count)
    {
        if (!worldIn.isRemote)
        {
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
            SoundEvent soundevent = this.getSoundEvent(i);
            SoundEvent soundevent1 = i == 0 ? SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE : null;
            float f = (float) (stack.getUseDuration() - count) / (float) getChargeTime(stack);
            if (f < 0.2F)
            {
                this.isLoadingStart = false;
                this.isLoadingMiddle = false;
            }

            if (f >= 0.2F && !this.isLoadingStart)
            {
                this.isLoadingStart = true;
                worldIn.playSound(null, livingEntityIn.getPosX(), livingEntityIn.getPosY(), livingEntityIn.getPosZ(), soundevent, SoundCategory.PLAYERS, 0.5F, 1.0F);
            }

            if (f >= 0.5F && soundevent1 != null && !this.isLoadingMiddle)
            {
                this.isLoadingMiddle = true;
                worldIn.playSound(null, livingEntityIn.getPosX(), livingEntityIn.getPosY(), livingEntityIn.getPosZ(), soundevent1, SoundCategory.PLAYERS, 0.5F, 1.0F);
            }
        }

    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return getChargeTime(stack) + 3;
    }

    public static int getChargeTime(ItemStack stack)
    {
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        return i == 0 ? 55 : 55 - 5 * i;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.CROSSBOW;
    }

    private SoundEvent getSoundEvent(int enchantmentLevel)
    {
        switch (enchantmentLevel)
        {
            case 1:
                return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_1;
            case 2:
                return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_2;
            case 3:
                return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_3;
            default:
                return SoundEvents.ITEM_CROSSBOW_LOADING_START;
        }
    }

    private static float getCharge(int useTime, ItemStack stack)
    {
        float f = (float) useTime / (float) getChargeTime(stack);
        if (f > 1.0F)
        {
            f = 1.0F;
        }
        return f;
    }

    private static float shootSpeed()
    {
        return 3F;
    }
}