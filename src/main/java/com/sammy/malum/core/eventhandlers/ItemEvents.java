package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.systems.item.ModCombatItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class ItemEvents {

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickItem event)
    {
        if (event.getEntityLiving() instanceof Player player) {
            ItemStack stack = event.getItemStack();
            int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(MalumEnchantments.REBOUND.get(), stack);
            if (enchantmentLevel > 0) {
                Level level = player.level;
                if (!level.isClientSide) {
                    player.setItemInHand(event.getHand(), ItemStack.EMPTY);
                    double baseDamage = player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
                    float multiplier = 1.2f;
                    double damage = 1.0F + baseDamage * multiplier;

                    int slot = event.getHand() == InteractionHand.OFF_HAND ? player.getInventory().getContainerSize() - 1 : player.getInventory().selected;
                    ScytheBoomerangEntity entity = new ScytheBoomerangEntity(level);
                    entity.setPos(player.position().x, player.position().y + player.getBbHeight() / 2f, player.position().z);

                    entity.setData((float) damage, player.getUUID(), slot, stack);
                    entity.getEntityData().set(ScytheBoomerangEntity.SCYTHE, stack);

                    entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, (float) (1.5F + player.getAttributeValue(AttributeRegistry.SCYTHE_PROFICIENCY) * 0.125f), 0F);
                    level.addFreshEntity(entity);
                }
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
        }
    }
    @Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientOnly {
        @SubscribeEvent
        public static void fixItemTooltip(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            Item item = stack.getItem();
            if (item instanceof ModCombatItem) {
                List<Component> tooltip = event.getToolTip();
                ArrayList<Component> clone = new ArrayList<>(tooltip);

                for (int i = 0; i < clone.size(); i++) {
                    Component component = clone.get(i);
                    if (component instanceof TranslatableComponent) {
                        TranslatableComponent textComponent = (TranslatableComponent) component;
                        String rawText = textComponent.getString();
                        if (rawText.contains("+") || rawText.contains("-")) {
                            String amount = textComponent.decomposedParts.get(1).getString();
                            String text = textComponent.decomposedParts.get(3).getString();
                            component = new TextComponent(" " + amount + " " + text).withStyle(ChatFormatting.DARK_GREEN);
                            tooltip.set(i, component);
                        }
                    }
                }
            }
        }
    }
}
