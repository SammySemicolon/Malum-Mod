package com.sammy.malum.common.item.curiosities.curios.sets.prospector;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.level.ExplosionEvent;
import team.lodestar.lodestone.helpers.CurioHelper;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.*;

public class CurioProspectorBelt extends MalumCurioItem {

    public CurioProspectorBelt(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.curio.effect.belt_of_the_prospector"));
    }

    public static void processExplosion(ExplosionEvent.Detonate event) {
        LivingEntity exploder = event.getExplosion().getIndirectSourceEntity();
        if (exploder != null && CurioHelper.hasCurioEquipped(exploder, ItemRegistry.BELT_OF_THE_PROSPECTOR.get())) {
            event.getAffectedEntities().removeIf(e -> e instanceof ItemEntity itemEntity && itemEntity.getItem().is(ItemTagRegistry.PROSPECTORS_TREASURE));
        }
    }

    public static LootParams.Builder applyFortune(Entity source, LootParams.Builder builder) {
        if (source instanceof LivingEntity livingEntity) {
            if (CurioHelper.hasCurioEquipped(livingEntity, ItemRegistry.BELT_OF_THE_PROSPECTOR.get())) {
                int fortuneBonus = 3 + CuriosApi.getCuriosInventory(livingEntity).map(h -> h.getFortuneLevel(null)).orElse(0);
                ItemStack diamondPickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
                diamondPickaxe.enchant(Enchantments.BLOCK_FORTUNE, fortuneBonus);
                return builder.withParameter(LootContextParams.TOOL, diamondPickaxe);
            }
        }
        return builder;
    }
}