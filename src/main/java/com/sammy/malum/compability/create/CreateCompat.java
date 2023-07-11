package com.sammy.malum.compability.create;

import com.sammy.malum.registry.common.*;
import com.simibubi.create.content.equipment.potatoCannon.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class CreateCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("create");
    }

    public static class LoadedOnly {

        public static void convertCaramelToMagicDamage(LivingHurtEvent event) {
            if (event.getSource().getDirectEntity() instanceof PotatoProjectileEntity potatoProjectile) {
                LivingEntity target = event.getEntity();
                ItemStack item = potatoProjectile.getItem();
                DamageSource damageSource = event.getSource().getEntity() instanceof Player player ? DamageSourceRegistry.causeVoodooDamage(player) : DamageSourceRegistry.VOODOO;
                if (item.getItem().equals(UNHOLY_CARAMEL.get()) || item.getItem().equals(HOLY_CARAMEL.get())) {
                    event.setCanceled(true);
                    target.invulnerableTime = 0;
                    target.hurt(damageSource, event.getAmount());
                }
            }
        }
    }
}