package com.kittykitcatcat.malum.events;

import com.kittykitcatcat.malum.SpiritDataHelper;
import com.kittykitcatcat.malum.items.BowofLostSouls;
import com.kittykitcatcat.malum.items.curios.CurioEnchantedLectern;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientRuntimeEvents
{
    @SubscribeEvent
    public static void addSpiritTooltips(ItemTooltipEvent event)
    {
        if (event.getPlayer() != null)
        {
            SpiritDataHelper.makeSpiritTooltip(event.getItemStack(), event.getPlayer().world, event.getToolTip(), event.getFlags());
        }
    }
}