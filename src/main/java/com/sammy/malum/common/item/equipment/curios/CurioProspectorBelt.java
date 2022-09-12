package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.common.entity.nitrate.AbstractNitrateEntity;
import com.sammy.malum.common.entity.nitrate.EthericExplosion;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.item.ItemTagRegistry;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.world.ExplosionEvent;
import team.lodestar.lodestone.helpers.CurioHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.common.CuriosHelper;

public class CurioProspectorBelt extends MalumCurioItem {

    public CurioProspectorBelt(Properties builder) {
        super(builder);
    }

    public static void processExplosion(ExplosionEvent.Detonate event) {
        LivingEntity exploder = event.getExplosion().getSourceMob();
        if (exploder != null && CurioHelper.hasCurioEquipped(exploder, ItemRegistry.BELT_OF_THE_PROSPECTOR.get())) {
            event.getAffectedEntities().removeIf(e -> e instanceof ItemEntity itemEntity && itemEntity.getItem().is(ItemTagRegistry.PROSPECTORS_TREASURE));
        }
    }


    public static LootContext.Builder applyFortune(Entity source, LootContext.Builder builder) {
        if (source instanceof LivingEntity livingEntity) {
            if (CurioHelper.hasCurioEquipped(livingEntity, ItemRegistry.BELT_OF_THE_PROSPECTOR.get())) {
                int fortuneBonus = 3 + CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(h -> h.getFortuneLevel(null)).orElse(0);
                ItemStack diamondPickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
                diamondPickaxe.enchant(Enchantments.BLOCK_FORTUNE, fortuneBonus);
                return builder.withParameter(LootContextParams.TOOL, diamondPickaxe);
            }
        }
        return builder;
    }


    @Override
    public boolean isGilded() {
        return true;
    }
}