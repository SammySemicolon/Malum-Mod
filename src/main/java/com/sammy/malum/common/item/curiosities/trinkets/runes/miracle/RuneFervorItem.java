package com.sammy.malum.common.item.curiosities.trinkets.runes.miracle;

import com.sammy.malum.common.item.curiosities.trinkets.runes.AbstractRuneTrinketsItem;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.Consumer;

public class RuneFervorItem extends AbstractRuneTrinketsItem {

    public RuneFervorItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("fervor"));
    }

    public static void increaseDigSpeed(PlayerEvents.BreakSpeed event) {
        Player player = event.getEntity();
        if (TrinketsHelper.hasTrinketEquipped(player, ItemRegistry.RUNE_OF_FERVOR.get())) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.25f);
        }
    }
}
