package com.sammy.malum.common.item.curiosities.curios.runes;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.event.entity.player.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class RuneHasteItem extends MalumRuneCurioItem {

    public RuneHasteItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.rune.effect.haste"));
    }

    public static void increaseDigSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (CurioHelper.hasCurioEquipped(player, ItemRegistry.RUNE_OF_HASTE.get())) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.25f);
        }
    }
}