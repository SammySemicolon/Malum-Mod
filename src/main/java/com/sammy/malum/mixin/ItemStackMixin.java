package com.sammy.malum.mixin;

import com.sammy.malum.common.item.tools.ModScytheItem;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.UUID;

import static com.sammy.malum.core.setup.content.AttributeRegistry.UUIDS;
import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_UUID;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Unique
    private AttributeModifier attributeModifier;

    @ModifyVariable(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getId()Ljava/util/UUID;", ordinal = 0), index = 13)
    private AttributeModifier getTooltip(AttributeModifier value) {
        this.attributeModifier = value;
        return value;
    }

    @ModifyVariable(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getOperation()Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;", ordinal = 0), index = 16)
    private boolean getTooltip(boolean value, @Nullable Player player, TooltipFlag flag) {
        if (player != null) {
            if (attributeModifier.getId().equals(UUIDS.get(AttributeRegistry.MAGIC_DAMAGE))) {
                return true;
            }
        }
        return value;
    }

    @ModifyVariable(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getOperation()Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;", ordinal = 0), index = 14)
    private double getTooltip(double value, @Nullable Player player, TooltipFlag flag) {
        if (player != null) {
            if (attributeModifier.getId().equals(UUIDS.get(AttributeRegistry.MAGIC_DAMAGE))) {
                value += player.getAttributeBaseValue(AttributeRegistry.MAGIC_DAMAGE.get());
                AttributeInstance instance = player.getAttribute(AttributeRegistry.MAGIC_PROFICIENCY.get());
                if (instance != null && instance.getValue() > 0) {
                    value += instance.getValue() * 0.5f;
                }
                return value;
            }
            if (attributeModifier.getId().equals(BASE_ATTACK_DAMAGE_UUID) && ((ItemStack) (Object) this).getItem() instanceof ModScytheItem) {
                AttributeInstance instance = player.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY.get());
                if (instance != null && instance.getValue() > 0) {
                    value += instance.getValue() * 0.5f;
                }
                return value;
            }
        }
        return value;
    }
}