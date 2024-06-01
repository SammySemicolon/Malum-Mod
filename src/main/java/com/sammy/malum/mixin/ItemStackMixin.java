package com.sammy.malum.mixin;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.*;

import static net.minecraft.world.item.Item.*;

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
