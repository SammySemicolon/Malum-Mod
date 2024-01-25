package com.sammy.malum.common.item;

import com.sammy.malum.client.screen.codex.ArcanaProgressionScreen;
import com.sammy.malum.client.screen.codex.VoidProgressionScreen;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EncyclopediaArcanaItem extends Item {

    public static boolean shouldOpenVoidCodex;

    public EncyclopediaArcanaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            if (shouldOpenVoidCodex) {
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