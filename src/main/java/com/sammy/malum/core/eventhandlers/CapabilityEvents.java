package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.common.capability.PlayerDataCapability;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.systems.capability.SimpleCapabilityProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEvents {

    @SubscribeEvent
    public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            final PlayerDataCapability capability = new PlayerDataCapability();
            event.addCapability(DataHelper.prefix("player_data"), new SimpleCapabilityProvider<>(PlayerDataCapability.CAPABILITY, () -> capability));
        }
    }


    @SubscribeEvent
    public static void playerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player) {
            PlayerDataCapability.getCapability(player).ifPresent(capability -> capability.firstTimeJoin = true);
        }
    }
}
