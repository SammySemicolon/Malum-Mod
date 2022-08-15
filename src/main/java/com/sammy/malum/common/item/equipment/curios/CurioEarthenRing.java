package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.common.entity.nitrate.EthericExplosion;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.item.ItemTagRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.world.ExplosionEvent;
import team.lodestar.lodestone.helpers.CurioHelper;

public class CurioEarthenRing extends MalumCurioItem {

    public CurioEarthenRing(Properties builder) {
        super(builder);
    }

    public static void processExplosion(ExplosionEvent.Detonate event) {
        if (event.getExplosion() instanceof EthericExplosion) {
            LivingEntity source = event.getExplosion().getSourceMob();
            if (source != null && CurioHelper.hasCurioEquipped(source, ItemRegistry.RING_OF_EARTHEN_LOYALTY)) {
                event.getAffectedEntities().forEach(e -> {
                    if (e instanceof ItemEntity) {
                        e.teleportTo(source.getX(), source.getY(), source.getZ());
                    }
                });
            }
        }
    }


    @Override
    public boolean isGilded() {
        return true;
    }
}