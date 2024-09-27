package com.sammy.malum.mixin;

import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingDamageEvent.Post.class)
public interface AccessorPostDamageEvent {
    @Accessor("newDamage")
    void malum$setNewDamage(float val);
}
