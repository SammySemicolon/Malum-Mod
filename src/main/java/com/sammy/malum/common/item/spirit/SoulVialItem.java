package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.entity.spirit.SoulEntity;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SoulVialItem extends Item implements ISoulContainerItem {
    public SoulVialItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactWithSoul(Player pPlayer, InteractionHand pHand, SoulEntity soul) {
        if (!soul.spiritData.equals(MalumEntitySpiritData.EMPTY))
        {
            ItemStack stack = pPlayer.getItemInHand(pHand);
            soul.spiritData.saveTo(stack.getOrCreateTag());
            soul.discard();
            return InteractionResult.SUCCESS;
        }
        return null;
    }
}
