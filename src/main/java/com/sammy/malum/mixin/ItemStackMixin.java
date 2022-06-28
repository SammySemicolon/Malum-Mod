package com.sammy.malum.mixin;

import com.sammy.malum.common.item.tools.MalumScytheItem;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.item.MalumEnchantments;
import com.sammy.ortus.setup.OrtusAttributeRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static com.sammy.ortus.setup.OrtusAttributeRegistry.MAGIC_DAMAGE;
import static com.sammy.ortus.setup.OrtusAttributeRegistry.UUIDS;
import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_UUID;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Unique
    private AttributeModifier attributeModifier;

    @ModifyVariable(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getId()Ljava/util/UUID;", ordinal = 0), index = 13)
    private AttributeModifier malumTooltipAttributeModifierGetterMixin(AttributeModifier value) {
        this.attributeModifier = value;
        return value;
    }

    @ModifyVariable(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getOperation()Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;", ordinal = 0), index = 14)
    private double malumTooltipDamageCalculationsMixin(double value, @Nullable Player player, TooltipFlag flag) {
        if (player != null) {
            ItemStack stack = ((ItemStack) (Object) this);
            if (stack.getItem() instanceof MalumScytheItem) {
                if (attributeModifier.getId().equals(UUIDS.get(MAGIC_DAMAGE))) {
                    int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(MalumEnchantments.HAUNTED.get(), stack);
                    if (enchantmentLevel > 0) {
                        value += enchantmentLevel;
                    }
                    return value;
                }
                if (attributeModifier.getId().equals(BASE_ATTACK_DAMAGE_UUID)) {
                    AttributeInstance instance = player.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY.get());
                    if (instance != null && instance.getValue() > 0) {
                        value += instance.getValue() * 0.5f;
                    }
                    return value;
                }
            }
        }
        return value;
    }
}