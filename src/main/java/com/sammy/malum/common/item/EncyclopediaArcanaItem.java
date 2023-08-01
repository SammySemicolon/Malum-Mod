package com.sammy.malum.common.item;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.core.handlers.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EncyclopediaArcanaItem extends Item {
    public EncyclopediaArcanaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        boolean isNearWeepingWell = TouchOfDarknessHandler.checkForWeepingWellInfluence(player);
        if (level.isClientSide) {
            if (isNearWeepingWell) {
                VoidProgressionScreen.openCodexViaItem();
            }
            else {
                ArcanaProgressionScreen.openCodexViaItem();
            }
            player.swing(hand);
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
        return super.use(level, player, hand);
    }
}