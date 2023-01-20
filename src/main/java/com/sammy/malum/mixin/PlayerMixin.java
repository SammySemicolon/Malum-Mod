package com.sammy.malum.mixin;

import com.sammy.malum.common.effect.AqueousAura;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyArg(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"), index = 1)
    private AABB malum$aiStep(AABB aabb) {
        Player player = (Player) (Object) this;
        return AqueousAura.growBoundingBox(player, aabb);
    }
}
