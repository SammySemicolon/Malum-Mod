package com.kittykitcatcat.malum.events;

import com.kittykitcatcat.malum.ClientHandler;
import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.items.armor.ItemSpiritHunterArmor;
import com.kittykitcatcat.malum.items.armor.ItemSpiritedSteelBattleArmor;
import com.kittykitcatcat.malum.items.armor.ItemUmbraSteelBattleArmor;
import com.kittykitcatcat.malum.items.armor.ModArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.util.text.TextFormatting.WHITE;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientRuntimeEvents
{
    @SubscribeEvent
    public static void addSpiritTooltips(ItemTooltipEvent event)
    {
        if (event.getPlayer() != null)
        {
            ItemStack stack = event.getItemStack();
            ClientHandler.makeSpiritTooltip(event.getPlayer(), stack, event.getPlayer().world, event.getToolTip(), event.getFlags());
        }
    }
}