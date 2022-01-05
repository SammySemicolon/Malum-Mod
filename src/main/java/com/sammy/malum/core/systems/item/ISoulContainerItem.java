package com.sammy.malum.core.systems.item;

import com.sammy.malum.common.entity.spirit.SoulEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public interface ISoulContainerItem {
    public InteractionResult interactWithSoul(Player pPlayer, InteractionHand pHand, SoulEntity soul);
}
