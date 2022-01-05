package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.entity.spirit.SoulEntity;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class SoulVialItem extends Item implements ISoulContainerItem {
    public SoulVialItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactWithSoul(Player pPlayer, InteractionHand pHand, SoulEntity soul) {
        return null;
    }
}
