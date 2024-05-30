package com.sammy.malum.common.item.curiosities.curios.sets.prospector;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class CurioProspectorBelt extends MalumCurioItem {

    public CurioProspectorBelt(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("enchanted_explosions", Enchantments.BLOCK_FORTUNE.getFullname(3).copy().withStyle(ChatFormatting.BLUE)));
        consumer.accept(positiveEffect("explosions_spare_valuables"));
    }

    public static void processExplosion(Level level, Explosion explosion, List<Entity> entities, double v) {
        LivingEntity exploder = explosion.getIndirectSourceEntity();
        if (exploder != null && TrinketsHelper.hasTrinketEquipped(exploder, ItemRegistry.BELT_OF_THE_PROSPECTOR.get())) {
            entities.removeIf(e -> e instanceof ItemEntity itemEntity && itemEntity.getItem().is(ItemTagRegistry.PROSPECTORS_TREASURE));
        }
    }

    public static LootParams.Builder applyFortune(Entity source, LootParams.Builder builder) {
        if (source instanceof LivingEntity livingEntity) {
            if (TrinketsHelper.hasTrinketEquipped(livingEntity, ItemRegistry.BELT_OF_THE_PROSPECTOR.get())) {
                Optional<Integer> v = TrinketsHelper.getEquippedTrinkets(livingEntity).stream().map(h -> EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, h.getB())).findFirst();
                int fortuneBonus = 3 + v.orElse(0);
                ItemStack diamondPickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
                diamondPickaxe.enchant(Enchantments.BLOCK_FORTUNE, fortuneBonus);
                return builder.withParameter(LootContextParams.TOOL, diamondPickaxe);
            }
        }
        return builder;
    }


}
