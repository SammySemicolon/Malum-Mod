package com.sammy.malum.mixin;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

import static net.minecraft.world.item.Item.*;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract Item getItem();

    @ModifyVariable(method = "getTooltipLines", at = @At("STORE"))
    private Multimap<Attribute, AttributeModifier> malum$getTooltip(Multimap<Attribute, AttributeModifier> map, @Nullable Player player, TooltipFlag flag) {
        if (player != null && getItem() instanceof MalumScytheItem) {
            Multimap<Attribute, AttributeModifier> copied = LinkedHashMultimap.create();
            for (Map.Entry<Attribute, AttributeModifier> entry : map.entries()) {
                Attribute key = entry.getKey();
                AttributeModifier modifier = entry.getValue();
                double amount = modifier.getAmount();

                if (modifier.getId() != null) {
                    if (modifier.getId().equals(BASE_ATTACK_DAMAGE_UUID)) {
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

    @Inject(method = "is(Lnet/minecraft/tags/TagKey;)Z", at = @At("HEAD"), cancellable = true)
    private void malum$ultimateRebound(TagKey<Item> tag, CallbackInfoReturnable<Boolean> cir) {
        if (tag.equals(ItemTagRegistry.REBOUND_SCYTHE)) {
            //noinspection ConstantValue
            if (CommonConfig.ULTIMATE_REBOUND.getConfigValue() && (Object)this instanceof TieredItem) {
                cir.setReturnValue(true);
            }
        }

    }
}
