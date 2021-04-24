package com.sammy.malum.core.systems.spirits;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entities.spirit.SpiritSplinterItemEntity;
import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.enchantments.MalumEnchantments;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SpiritHelper
{
    public static void causeVoodooDamage(LivingEntity attacker, LivingEntity target, float amount)
    {
        target.attackEntityFrom(MalumDamageSources.VOODOO, amount);
    }
    public static void harvestSpirit(String spirit, PlayerEntity player)
    {
        ArrayList<Pair<String, Integer>> spirits = new ArrayList<>();
        spirits.add(new Pair<>(spirit, 1));
        harvestSpirit(spirits, player);
    }
    public static void summonPlayerSoul(PlayerEntity target, PlayerEntity player)
    {
        PlayerSoulEntity soulEntity = new PlayerSoulEntity(MalumEntities.PLAYER_SOUL.get(), player.world);
        float speed = 0.25f;
        soulEntity.setMotion(MathHelper.nextFloat(MalumMod.RANDOM, -speed, speed), MathHelper.nextFloat(MalumMod.RANDOM, 0.05f, 0.05f), MathHelper.nextFloat(MalumMod.RANDOM, -speed, speed));
        soulEntity.setPosition(target.getPositionVec().x, target.getPositionVec().y + target.getHeight() / 2f, target.getPositionVec().z);
        soulEntity.setData(target.getUniqueID(), player.getUniqueID());
        player.world.addEntity(soulEntity);
    }
    public static void summonSpirits(LivingEntity target, PlayerEntity player, ItemStack stack)
    {
        if (target instanceof PlayerEntity)
        {
            summonPlayerSoul((PlayerEntity) target, player);
            return;
        }
        ArrayList<Pair<String, Integer>> spirits = entitySpirits(target);
    
        int plunder = EnchantmentHelper.getEnchantmentLevel(MalumEnchantments.SPIRIT_PLUNDER.get(), stack);
        if (plunder > 0)
        {
            for (int i =0; i < plunder; i++)
            {
                int random = player.world.rand.nextInt(spirits.size());
                Pair<String, Integer> pair = spirits.get(random);
                spirits.set(random, Pair.of(pair.getFirst(), pair.getSecond()+1));
            }
        }
        if (!spirits.isEmpty())
        {
            summonSpirits(spirits,target, player);
        }
    }
    public static void summonSpirits(LivingEntity target)
    {
        ArrayList<Pair<String, Integer>> spirits = entitySpirits(target);
        if (!spirits.isEmpty())
        {
            summonSpirits(spirits,target, null);
        }
    }
    public static void summonSpirits(ArrayList<Pair<String, Integer>> spirits, LivingEntity target, PlayerEntity player)
    {
        if (player == null)
        {
            player = target.world.getClosestPlayer(target.getPosX(),target.getPosY(), target.getPosZ(),-1,e -> true);
        }
        float speed = 0.2f + 0.5f / (totalSpirits(target) + 1);
        target.world.playSound(null, target.getPosX(), target.getPosY(), target.getPosZ(), MalumSounds.SPIRIT_HARVEST, target.getSoundCategory(), 1.0F, 0.9f + target.world.rand.nextFloat() * 0.2f);
        for (int i = 0; i < spirits.size(); i++)
        {
            String spirit = spirits.get(i).getFirst();
            int count = spirits.get(i).getSecond();
            MalumSpiritType type = figureOutType(spirit);
            if (count == 0)
            {
                continue;
            }
            if (player == null)
            {
                for (int j = 0; j < count; j++)
                {
                    ItemEntity itemEntity = new ItemEntity(target.world, target.getPosXRandom(1), target.getPosYRandom(), target.getPosZRandom(1), type.splinterItem.getDefaultInstance());
                    target.world.addEntity(itemEntity);
                    spirits.set(i, Pair.of(spirit, count - 1));
                }
                continue;
            }
            for (int j = 0; j < count; j++)
            {
                SpiritSplinterItemEntity essenceEntity = new SpiritSplinterItemEntity(MalumEntities.SPIRIT_ESSENCE.get(), target.world);
                essenceEntity.setMotion(MathHelper.nextFloat(MalumMod.RANDOM, -speed, speed), MathHelper.nextFloat(MalumMod.RANDOM, 0.05f, 0.05f), MathHelper.nextFloat(MalumMod.RANDOM, -speed, speed));
                essenceEntity.setPosition(target.getPositionVec().x, target.getPositionVec().y + target.getHeight() / 2f, target.getPositionVec().z);
                essenceEntity.setData(type.splinterItem, player.getUniqueID());
                target.world.addEntity(essenceEntity);
                spirits.set(i, Pair.of(spirit, count - 1));
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
        return entitySpirits(entity).stream().mapToInt(Pair::getSecond).sum();
    }
    
    public static ArrayList<Pair<String, Integer>> entitySpirits(LivingEntity entity)
    {
        ArrayList<Pair<String, Integer>> spirits = new ArrayList<>();
        for (MalumSpiritType type : MalumSpiritTypes.SPIRITS)
        {
            int count = type.spiritCount(entity);
            if (count != 0)
            {
                spirits.add(Pair.of(type.identifier, count));
            }
        }
        return spirits;
    }
}