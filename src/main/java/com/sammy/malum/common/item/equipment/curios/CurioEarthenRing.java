package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.common.entity.nitrate.EthericExplosion;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.item.ItemTagRegistry;
import net.minecraft.core.BlockPos;
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

    public static boolean hasEarthenRing(LivingEntity entity) {
        return entity != null && CurioHelper.hasCurioEquipped(entity, ItemRegistry.RING_OF_EARTHEN_LOYALTY);
    }
    public static BlockPos getExplosionPos(boolean hasTheRing, BlockPos originalPos, LivingEntity entity) {
        return hasTheRing ? entity.blockPosition().above() : originalPos;
    }


    @Override
    public boolean isGilded() {
        return true;
    }
}