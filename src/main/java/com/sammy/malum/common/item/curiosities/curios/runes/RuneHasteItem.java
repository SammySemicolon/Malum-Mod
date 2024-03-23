package com.sammy.malum.common.item.curiosities.curios.runes;

import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import team.lodestar.lodestone.helpers.CurioHelper;

import java.util.function.Consumer;

public class RuneHasteItem extends MalumRuneCurioItem {

    public RuneHasteItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("haste"));
    }

    public static void increaseDigSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (CurioHelper.hasCurioEquipped(player, ItemRegistry.RUNE_OF_HASTE.get())) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.25f);
        }
    }
}
