package com.sammy.malum.mixin;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_UUID;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract Item getItem();

    @ModifyVariable(method = "getTooltipLines", at = @At("STORE"))
    private Multimap<Attribute, AttributeModifier> malum$getTooltip(Multimap<Attribute, AttributeModifier> map, @Nullable Player player, TooltipFlag flag) {
        if (player != null) {
            Multimap<Attribute, AttributeModifier> copied = LinkedHashMultimap.create();
            for (Map.Entry<Attribute, AttributeModifier> entry : map.entries()) {
                Attribute key = entry.getKey();
                AttributeModifier modifier = entry.getValue();
                double amount = modifier.getAmount();

                if (modifier.getId() != null) {
                    if (modifier.getId().equals(BASE_ATTACK_DAMAGE_UUID) && getItem() instanceof MalumScytheItem) {
                        AttributeInstance instance = player.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY.get());
                        if (instance != null && instance.getValue() > 0) {
                            amount += instance.getValue() * 0.5f;
                        }

                        copied.put(key, new AttributeModifier(
                                modifier.getId(), modifier.getName(), amount, modifier.getOperation()
                        ));
                    } else {
                        copied.put(key, modifier);
                    }
                }


            }

            return copied;
        }
        return map;
    }
}
