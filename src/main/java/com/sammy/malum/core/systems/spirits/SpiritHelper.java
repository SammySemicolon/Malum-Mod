package com.sammy.malum.core.systems.spirits;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entities.spirit.SpiritItemEntity;
import com.sammy.malum.common.items.equipment.curios.MalumCurioItem;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.enchantments.MalumEnchantments;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SpiritHelper
{
    public static DamageSource voodooDamageSource(Entity entity)
    {
        return new EntityDamageSource("voodoo", entity).setMagicDamage();
    }
    public static void causeVoodooDamage(LivingEntity attacker, LivingEntity target, float amount)
    {
        target.attackEntityFrom(voodooDamageSource(attacker), amount);
    }

    public static void harvestSpirit(String spirit, PlayerEntity player)
    {
        ArrayList<Pair<String, Integer>> spirits = new ArrayList<>();
        spirits.add(new Pair<>(spirit, 1));
        harvestSpirit(spirits, player);
    }

    public static void summonSpirits(LivingEntity target, PlayerEntity player, ItemStack harvestStack)
    {
        if (target instanceof PlayerEntity)
        {
            return;
        }
        ArrayList<ItemStack> spirits = entitySpirits(target);

        int plunder = EnchantmentHelper.getEnchantmentLevel(MalumEnchantments.SPIRIT_PLUNDER.get(), harvestStack);
        for (MalumCurioItem item : MalumHelper.equippedMalumCurios(player))
        {
            plunder += item.spiritYieldBonus();
        }
        if (plunder > 0)
        {
            for (int i = 0; i < plunder; i++)
            {
                int random = player.world.rand.nextInt(spirits.size());
                spirits.get(random).grow(1);
            }
        }
        if (player.world.rand.nextFloat() < 0.01f)
        {
            spirits.add(new ItemStack(MalumItems.COMICALLY_LARGE_TOPHAT.get()));
        }
        if (!spirits.isEmpty())
        {
            summonSpirits(spirits, target, player);
        }
    }

    public static void summonSpirits(LivingEntity target)
    {
        ArrayList<ItemStack> spirits = entitySpirits(target);
        if (!spirits.isEmpty())
        {
            summonSpirits(spirits, target, null);
        }
    }

    public static void summonSpirits(ArrayList<ItemStack> spirits, LivingEntity target, PlayerEntity player)
    {
        if (player == null)
        {
            player = target.world.getClosestPlayer(target.getPosX(), target.getPosY(), target.getPosZ(), -1, e -> true);
        }
        float speed = 0.2f + 0.5f / (totalSpirits(target) + 1);
        target.world.playSound(null, target.getPosX(), target.getPosY(), target.getPosZ(), MalumSounds.SPIRIT_HARVEST, target.getSoundCategory(), 1.0F, 0.9f + target.world.rand.nextFloat() * 0.2f);
        for (ItemStack stack : spirits)
        {
            int count = stack.getCount();
            if (count == 0)
            {
                continue;
            }
            if (player == null)
            {
                for (int j = 0; j < count; j++)
                {
                    ItemEntity itemEntity = new ItemEntity(target.world, target.getPosXRandom(1), target.getPosYRandom(), target.getPosZRandom(1), stack.getItem().getDefaultInstance());
                    target.world.addEntity(itemEntity);
                    stack.shrink(1);
                }
                continue;
            }
            for (int j = 0; j < count; j++)
            {
                SpiritItemEntity essenceEntity = new SpiritItemEntity(MalumEntities.SPIRIT_ESSENCE.get(), target.world);
                essenceEntity.setMotion(MathHelper.nextFloat(MalumMod.RANDOM, -speed, speed), MathHelper.nextFloat(MalumMod.RANDOM, 0.05f, 0.05f), MathHelper.nextFloat(MalumMod.RANDOM, -speed, speed));
                essenceEntity.setPosition(target.getPositionVec().x, target.getPositionVec().y + target.getHeight() / 2f, target.getPositionVec().z);
                essenceEntity.setData(stack.getItem().getDefaultInstance(), player.getUniqueID());
                target.world.addEntity(essenceEntity);
                stack.shrink(1);
            }
        }
    }

    public static void harvestSpirit(ArrayList<Pair<String, Integer>> spirits, PlayerEntity player)
    {
        for (int i = 0; i < spirits.size(); i++)
        {
            String spirit = spirits.get(i).getFirst();
            int count = spirits.get(i).getSecond();
            if (count == 0)
            {
                continue;
            }
            MalumSpiritType spiritType = figureOutType(spirit);
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(spiritType.splinterItem, count));
            spirits.set(i, Pair.of(spirit, 0));
        }
    }

    public static MalumSpiritType figureOutType(String spirit)
    {
        ArrayList<MalumSpiritType> type = (ArrayList<MalumSpiritType>) MalumSpiritTypes.SPIRITS.stream().filter(s -> s.identifier.equals(spirit)).collect(Collectors.toList());
        if (type.isEmpty())
        {
            throw new RuntimeException("Somehow, an invalid spirit identifier was harvested. 'Why?' is a good question my friend. Incorrect identifier: " + spirit);
        }
        return type.get(0);
    }

    public static int totalSpirits(LivingEntity entity)
    {
        return entitySpirits(entity).stream().mapToInt(ItemStack::getCount).sum();
    }

    public static ArrayList<ItemStack> entitySpirits(LivingEntity entity)
    {
        ArrayList<ItemStack> spirits = new ArrayList<>();
        for (MalumSpiritType type : MalumSpiritTypes.SPIRITS)
        {
            int count = type.spiritCount(entity);
            if (count != 0)
            {
                spirits.add(new ItemStack(type.splinterItem, count));
            }
        }
        return spirits;
    }
}