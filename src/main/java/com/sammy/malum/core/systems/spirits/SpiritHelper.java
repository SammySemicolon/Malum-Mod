package com.sammy.malum.core.systems.spirits;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entities.SpiritSplinterItemEntity;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.enchantments.MalumEnchantments;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.init.spirits.MalumSpiritTypes;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.item.SpiritSplinterItem;
import com.sammy.malum.core.systems.spirits.types.MalumSpiritType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemHandlerHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SpiritHelper
{
    public static void harvestSpirit(String spirit, PlayerEntity player)
    {
        ArrayList<Pair<String, Integer>> spirits = new ArrayList<>();
        spirits.add(new Pair<>(spirit, 1));
        harvestSpirit(spirits, player);
    }
    
    public static void summonSpirits(LivingEntity target, PlayerEntity player, ItemStack stack)
    {
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
        float speed = 0.2f + 0.5f / (totalSpirits(target) + 1);
        if (!spirits.isEmpty())
        {
            player.world.playSound(null, target.getPosX(), target.getPosY(), target.getPosZ(), MalumSounds.SPIRIT_HARVEST, player.getSoundCategory(), 1.0F, 0.9f + player.world.rand.nextFloat() * 0.2f);
        }
        for (int i = 0; i < spirits.size(); i++)
        {
            String spirit = spirits.get(i).getFirst();
            int count = spirits.get(i).getSecond();
            if (count == 0)
            {
                continue;
            }
            for (int j = 0; j < count; j++)
            {
                SpiritSplinterItemEntity essenceEntity = new SpiritSplinterItemEntity(MalumEntities.SPIRIT_ESSENCE.get(), player.world);
                essenceEntity.ownerUUID = player.getUniqueID();
                essenceEntity.setMotion(MathHelper.nextFloat(MalumMod.RANDOM, -speed, speed), MathHelper.nextFloat(MalumMod.RANDOM, 0.05f, 0.05f), MathHelper.nextFloat(MalumMod.RANDOM, -speed, speed));
                essenceEntity.setPosition(target.getPositionVec().x, target.getPositionVec().y + target.getHeight() / 2f, target.getPositionVec().z);
                essenceEntity.splinter = figureOutType(spirit).splinterItem;
                essenceEntity.getDataManager().set(SpiritSplinterItemEntity.SPLINTER_NAME, spirit);
                player.world.addEntity(essenceEntity);
                spirits.set(i, Pair.of(spirit, count - 1));
            }
        }
    }
    public static boolean consumeSpirit(String identifier, int count, int integrity, PlayerEntity playerEntity, ItemStack stack)
    {
        ArrayList<ItemStack> stacks = (ArrayList<ItemStack>) playerEntity.inventory.mainInventory.stream().filter(i -> i.getItem() instanceof SpiritSplinterItem).collect(Collectors.toList());
        CompoundNBT tag = stack.getOrCreateTag();
    
        if (integrity != 0)
        {
    
            if (tag.getInt("spiritIntegrity") > 0)
            {
                tag.putInt("spiritIntegrity", tag.getInt("spiritIntegrity") - 1);
                return true;
            }
        }
        for (ItemStack splinter : stacks)
        {
            SpiritSplinterItem item = (SpiritSplinterItem) splinter.getItem();
            if (item.type.identifier.equals(identifier))
            {
                if (splinter.getCount() >= count)
                {
                    if (integrity != 0)
                    {
                        tag.putInt("spiritIntegrity", integrity);
                    }
                    splinter.shrink(count);
                    return true;
                }
            }
        }
        return false;
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
            if (type.predicate.test(entity))
            {
                spirits.add(Pair.of(type.identifier, type.spiritCount(entity)));
            }
        }
        return spirits;
    }
}