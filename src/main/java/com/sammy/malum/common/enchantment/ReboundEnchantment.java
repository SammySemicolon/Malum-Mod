package com.sammy.malum.common.enchantment;

import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingTableBehaviorEnchantment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;

public class ReboundEnchantment extends Enchantment implements CustomEnchantingTableBehaviorEnchantment {
    public ReboundEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static boolean onRightClickItem(ServerPlayer player, InteractionHand interactionHand, ItemStack stack) {

        if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.REBOUND.get(), stack) > 0) {
            Level level = player.level();
            if (!level.isClientSide) {
                player.setItemInHand(interactionHand, ItemStack.EMPTY);
                float baseDamage = (float) player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
                float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributeRegistry.MAGIC_DAMAGE.get());

                int slot = interactionHand == InteractionHand.OFF_HAND ? player.getInventory().getContainerSize() - 1 : player.getInventory().selected;
                ScytheBoomerangEntity entity = new ScytheBoomerangEntity(level, player.position().x, player.position().y + player.getBbHeight() / 2f, player.position().z);
                entity.setData(player, baseDamage, magicDamage, slot);
                entity.setItem(stack);

                entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, (float) (1.5F + player.getAttributeValue(AttributeRegistry.SCYTHE_PROFICIENCY.get()) * 0.125f), 0F);
                level.addFreshEntity(entity);
            }
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.is(ItemTagRegistry.SCYTHE) || (CommonConfig.ULTIMATE_REBOUND.getConfigValue() && stack.getItem() instanceof TieredItem);
    }
}