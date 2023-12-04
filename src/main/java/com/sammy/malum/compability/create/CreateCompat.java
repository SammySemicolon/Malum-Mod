package com.sammy.malum.compability.create;

import com.sammy.malum.registry.common.DamageTypeRegistry;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.ModList;

import static com.sammy.malum.registry.common.item.ItemRegistry.HOLY_CARAMEL;
import static com.sammy.malum.registry.common.item.ItemRegistry.UNHOLY_CARAMEL;

public class CreateCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("create");
    }

    public static class LoadedOnly {

        public static void convertCaramelToMagicDamage(LivingHurtEvent event) {
            if (event.getSource().getDirectEntity() instanceof PotatoProjectileEntity potatoProjectile) {
                LivingEntity target = event.getEntityLiving();
                ItemStack item = potatoProjectile.getItem();
                DamageSource damageSource = event.getSource().getEntity() instanceof Player player ? DamageTypeRegistry.causeVoodooDamage(player) : DamageTypeRegistry.VOODOO;
                if (item.getItem().equals(UNHOLY_CARAMEL.get()) || item.getItem().equals(HOLY_CARAMEL.get())) {
                    event.setCanceled(true);
                    target.invulnerableTime = 0;
                    target.hurt(damageSource, event.getAmount());
                }
            }
        }
    }
}