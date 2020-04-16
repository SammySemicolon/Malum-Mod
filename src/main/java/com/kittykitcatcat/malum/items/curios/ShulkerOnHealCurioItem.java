package com.kittykitcatcat.malum.items.curios;

import com.google.common.collect.Lists;
import com.kittykitcatcat.malum.MalumHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;

import java.util.List;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class ShulkerOnHealCurioItem extends Item implements ICurio
{
    public ShulkerOnHealCurioItem(Properties builder)
    {
        super(builder);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioItem.createProvider(new ICurio()
        {
            @Override
            public void playEquipSound(LivingEntity entityLivingBase)
            {
                entityLivingBase.world.playSound(null, entityLivingBase.getPosition(),
                    SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL,
                    1.0f, 1.0f);
            }
            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }
        });
    }
    @SubscribeEvent
    public static void handleHeal(LivingHealEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity)
        {
            if (CuriosAPI.getCurioEquipped(stack1 -> stack1.getItem() instanceof ShulkerOnHealCurioItem, entity).isPresent())
            {
                double x = entity.getPosition().getX();
                double y = entity.getPosition().getY();
                double z = entity.getPosition().getZ();
                AxisAlignedBB bounds = new AxisAlignedBB(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10);
                List<Entity> targets = entity.world.getEntitiesWithinAABBExcludingEntity(entity, bounds);
                List<LivingEntity> livingTargets = Lists.newArrayList();
                if (!targets.isEmpty())
                {
                    for (Entity e : targets)
                    {
                        if (e instanceof LivingEntity)
                        {
                            livingTargets.add((LivingEntity) e);
                        }
                    }
                }
                if (!livingTargets.isEmpty())
                {
                    LivingEntity target = entity.world.getClosestEntity(livingTargets, EntityPredicate.DEFAULT, entity, x, y, z);
                    if (target != null)
                    {
                        ShulkerBulletEntity shulkerBulletEntity = new ShulkerBulletEntity(entity.world, entity, target, Direction.Axis.Y);
                        Vec3d velocity = MalumHelper.randVelocity(target.world.rand, -0.5f, 0.5f);
                        shulkerBulletEntity.addVelocity(velocity.x, 0.4, velocity.z);
                        shulkerBulletEntity.getTags().add("noLevitate");
                        entity.world.addEntity(shulkerBulletEntity);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void addPotion(PotionEvent.PotionAddedEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getLastDamageSource() != null)
        {
            if (entity.getLastDamageSource().getImmediateSource() != null)
            {
                Entity applyingProjectile = entity.getLastDamageSource().getImmediateSource();
                if (applyingProjectile instanceof ShulkerBulletEntity)
                {
                    if (applyingProjectile.getTags().contains("noLevitate"))
                    {
                        entity.getTags().add("removeLevitate");
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void removeLevitate(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving().getTags().contains("removeLevitate"))
        {
            event.getEntityLiving().getTags().remove("removeLevitate");
            event.getEntityLiving().removePotionEffect(Effects.LEVITATION);
        }
    }
}