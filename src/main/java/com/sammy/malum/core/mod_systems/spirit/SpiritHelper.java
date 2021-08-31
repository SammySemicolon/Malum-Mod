package com.sammy.malum.core.mod_systems.spirit;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entity.spirit.PlayerHomingItemEntity;
import com.sammy.malum.common.item.equipment.curios.MalumCurioItem;
import com.sammy.malum.common.item.tools.spirittools.ISpiritTool;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.enchantment.MalumEnchantments;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.init.MalumSpiritTypes;
import com.sammy.malum.core.mod_systems.particle.ParticleManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static net.minecraft.util.math.MathHelper.nextFloat;

public class SpiritHelper
{
    public static DamageSource voodooDamageSource()
    {
        return new DamageSource("voodoo").setMagicDamage();
    }

    public static DamageSource voodooDamageSource(Entity attacker)
    {
        return new EntityDamageSource("voodoo", attacker).setMagicDamage();
    }

    public static void causeVoodooDamage(LivingEntity attacker, LivingEntity target, float amount)
    {
        target.hurtResistantTime = 0;
        target.attackEntityFrom(voodooDamageSource(attacker), amount);
    }

    public static void summonSpirits(LivingEntity target)
    {
        ArrayList<ItemStack> spirits = spiritItemStacks(target);
        if (!spirits.isEmpty())
        {
            summonSpirits(spirits, target, null);
        }
    }

    public static void playerSummonSpirits(LivingEntity target, LivingEntity attacker, ItemStack harvestStack)
    {
        if (!(harvestStack.getItem() instanceof ISpiritTool))
        {
            return;
        }
        if (target instanceof PlayerEntity)
        {
            return;
        }
        ArrayList<ItemStack> spirits = spiritItemStacks(target);
        if (spirits.isEmpty())
        {
            return;
        }
        ISpiritTool tool = (ISpiritTool) harvestStack.getItem();
        ArrayList<MalumCurioItem> equippedMalumCurios = MalumHelper.equippedMalumCurios(attacker);
        int itemBonus = 0;
        for (MalumCurioItem item : equippedMalumCurios)
        {
            if (item.spiritYieldBonus() > itemBonus)
            {
                itemBonus = item.spiritYieldBonus();
            }
        }
        if (MalumHelper.hasArmorSet(attacker, MalumItems.SOUL_HUNTER_CLOAK.get().getItem()))
        {
            if (itemBonus == 0)
            {
                itemBonus++;
            }
        }
        int plunder = EnchantmentHelper.getEnchantmentLevel(MalumEnchantments.SPIRIT_PLUNDER.get(), harvestStack) + tool.spiritBonus(target) + itemBonus;
        if (plunder > 0)
        {
            for (int i = 0; i < plunder; i++)
            {
                int random = attacker.world.rand.nextInt(spirits.size());
                spirits.get(random).grow(1);
                if (attacker.world.rand.nextInt(4) == 0)
                {
                    harvestStack.damageItem(1, attacker, (e) -> e.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                }
            }
        }
        Optional<MalumCurioItem> firstModifier = equippedMalumCurios.stream().filter(s -> !s.spiritReplacementStack(ItemStack.EMPTY).equals(ItemStack.EMPTY)).findFirst();
        if (firstModifier.isPresent())
        {
            ArrayList<ItemStack> newSpirits = new ArrayList<>();
            for (ItemStack stack : spirits)
            {
                newSpirits.add(firstModifier.get().spiritReplacementStack(stack));
            }
            spirits = newSpirits;
        }
        summonSpirits(spirits, target, attacker);
    }

    public static void summonSpirits(ArrayList<ItemStack> spirits, LivingEntity target, LivingEntity attacker)
    {
        if (attacker == null)
        {
            attacker = target.world.getClosestPlayer(target.getPosX(), target.getPosY(), target.getPosZ(), 8, e -> true);
        }
        float speed = 0.2f + 0.5f / (totalSpiritCount(target) + 1);
        for (ItemStack stack : spirits)
        {
            int count = stack.getCount();
            if (count == 0)
            {
                continue;
            }
            if (attacker == null)
            {
                for (int j = 0; j < count; j++)
                {
                    ItemEntity itemEntity = new ItemEntity(target.world, target.getPosXRandom(1), target.getPosYRandom(), target.getPosZRandom(1), MalumHelper.copyWithNewCount(stack, 1));
                    target.world.addEntity(itemEntity);
                }
                continue;
            }
            for (int j = 0; j < count; j++)
            {
                PlayerHomingItemEntity entity = PlayerHomingItemEntity.makeEntity(target.world, attacker.getUniqueID(), MalumHelper.copyWithNewCount(stack, 1),
                        target.getPositionVec().x,
                        target.getPositionVec().y + target.getHeight() / 2f,
                        target.getPositionVec().z,
                        nextFloat(MalumMod.RANDOM, -speed, speed),
                        nextFloat(MalumMod.RANDOM, 0.05f, 0.05f),
                        nextFloat(MalumMod.RANDOM, -speed, speed));
                target.world.addEntity(entity);
            }
        }
        target.world.playSound(null, target.getPosX(), target.getPosY(), target.getPosZ(), MalumSounds.SPIRIT_HARVEST, target.getSoundCategory(), 1.0F, 0.9f + target.world.rand.nextFloat() * 0.2f);
    }

    public static void collectSpirit(LivingEntity collector)
    {
        if (MalumHelper.hasArmorSet(collector, MalumItems.SOUL_STAINED_STEEL_HELMET.get().getItem()))
        {
            MalumHelper.giveStackingEffect(Effects.RESISTANCE, collector, 100, 0);
        }
        ArrayList<MalumCurioItem> equippedMalumCurios = MalumHelper.equippedMalumCurios(collector);
        for (MalumCurioItem item : equippedMalumCurios)
        {
            item.consumeSpiritEffect(collector);
        }
    }

    public static MalumSpiritType spiritType(String spirit)
    {
        ArrayList<MalumSpiritType> type = (ArrayList<MalumSpiritType>) MalumSpiritTypes.SPIRITS.stream().filter(s -> s.identifier.equals(spirit)).collect(Collectors.toList());
        if (spirit.equals("holy"))
        {
            return MalumSpiritTypes.SACRED_SPIRIT;
        }
        if (type.isEmpty())
        {
            throw new RuntimeException("An incorrect spirit was found: " + spirit);
        }
        return type.get(0);
    }

    public static MalumEntitySpiritDataBundle spiritBundle(LivingEntity entity)
    {
        return MalumSpiritType.SPIRIT_DATA.get(entity.getType().getRegistryName());
    }
    public static int totalSpiritCount(LivingEntity entity)
    {
        MalumEntitySpiritDataBundle bundle = spiritBundle(entity);
        if (bundle == null)
        {
            return 0;
        }
        return bundle.totalCount;
    }
    public static ArrayList<ItemStack> spiritItemStacks(LivingEntity entity)
    {
        ArrayList<ItemStack> spirits = new ArrayList<>();
        MalumEntitySpiritDataBundle bundle = spiritBundle(entity);
        if (bundle == null)
        {
            return spirits;
        }
        for (MalumEntitySpiritDataBundle.MalumEntitySpiritData data : bundle.data)
        {
            spirits.add(new ItemStack(data.type.splinterItem(), data.count));
        }
        return spirits;
    }

    public static void spiritParticles(World world, double x, double y, double z, Color color)
    {
        Random rand = world.rand;
        ParticleManager.create(MalumParticles.TWINKLE_PARTICLE)
                .setAlpha(0.18f, 0f)
                .setLifetime(10 + rand.nextInt(4))
                .setScale(0.3f + rand.nextFloat() * 0.1f, 0)
                .setColor(color, color.darker())
                .randomOffset(0.05f)
                .enableNoClip()
                .randomVelocity(0.02f, 0.02f)
                .repeat(world, x, y, z, 1);

        ParticleManager.create(MalumParticles.WISP_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(20 + rand.nextInt(4))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.2f + rand.nextFloat() * 0.05f, 0)
                .setColor(color, color.darker())
                .randomOffset(0.1f)
                .enableNoClip()
                .randomVelocity(0.02f, 0.02f)
                .repeat(world, x, y, z, 1);

        ParticleManager.create(MalumParticles.WISP_PARTICLE)
                .setAlpha(0.2f, 0f)
                .setLifetime(10 + rand.nextInt(2))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.15f + rand.nextFloat() * 0.05f, 0f)
                .setColor(color, color.darker())
                .enableNoClip()
                .randomVelocity(0.01f, 0.01f)
                .repeat(world, x, y, z, 1);
    }
}