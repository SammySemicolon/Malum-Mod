package com.sammy.malum.client.event;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.misc.MalumLeavesBlock;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.item.ether.EtherBrazierItem;
import com.sammy.malum.common.item.ether.EtherItem;
import com.sammy.malum.common.item.ether.EtherTorchItem;
import com.sammy.malum.common.tile.EtherTileEntity;
import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.MalumHelper.*;
import static com.sammy.malum.core.registry.items.ItemRegistry.ITEMS;
import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.EARTHEN_SPIRIT_COLOR;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class SetBlockItemColors
{
    @SubscribeEvent
    public static void setBlockColors(ColorHandlerEvent.Block event)
    {
        BlockColors blockColors = event.getBlockColors();

        blockColors.register((state, reader, pos, color) ->
        {
            TileEntity tileEntity = reader.getBlockEntity(pos);
            if (tileEntity instanceof EtherTileEntity)
            {
                EtherTileEntity etherTileEntity = (EtherTileEntity) tileEntity;
                if (etherTileEntity.firstColor != null)
                {
                    return color == 0 ? etherTileEntity.firstColor.getRGB() : -1;
                }
            }
            return -1;
        }, Arrays.stream(getModBlocks(EtherBlock.class)).filter(b -> !(b instanceof EtherBrazierBlock)).toArray(Block[]::new));

        blockColors.register((state, reader, pos, color) ->
        {
            float i = state.getValue(MalumLeavesBlock.COLOR);
            MalumLeavesBlock malumLeavesBlock = (MalumLeavesBlock) state.getBlock();
            int r = (int) MathHelper.lerp(i / 5f, malumLeavesBlock.minColor.getRed(), malumLeavesBlock.maxColor.getRed());
            int g = (int) MathHelper.lerp(i / 5f, malumLeavesBlock.minColor.getGreen(), malumLeavesBlock.maxColor.getGreen());
            int b = (int) MathHelper.lerp(i / 5f, malumLeavesBlock.minColor.getBlue(), malumLeavesBlock.maxColor.getBlue());
            return r << 16 | g << 8 | b;
        }, getModBlocks(MalumLeavesBlock.class));
    }

    @SubscribeEvent
    public static void setItemColors(ColorHandlerEvent.Item event)
    {
        ItemColors itemColors = event.getItemColors();
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());

        MalumHelper.takeAll(items, item -> item.get() instanceof BlockItem && ((BlockItem) item.get()).getBlock() instanceof MalumLeavesBlock).forEach(item ->
        {
            MalumLeavesBlock malumLeavesBlock = (MalumLeavesBlock) ((BlockItem) item.get()).getBlock();
            ClientHelper.registerItemColor(itemColors, item, malumLeavesBlock.minColor);
        });
        itemColors.register((s, c)->{
            AbstractEtherItem etherItem = (AbstractEtherItem) s.getItem();
            if (c == 2)
            {
                return etherItem.getSecondColor(s);
            }
            return c == 0 ? etherItem.getFirstColor(s) : -1;
        }, MalumHelper.getModItems(EtherTorchItem.class, EtherBrazierItem.class));
        itemColors.register((s, c)->{
            AbstractEtherItem etherItem = (AbstractEtherItem) s.getItem();
            if (c == 1)
            {
                return etherItem.getSecondColor(s);
            }
            return c == 0 ? etherItem.getFirstColor(s) : -1;
        }, MalumHelper.getModItems(EtherItem.class));

        ClientHelper.registerItemColor(itemColors, ItemRegistry.SACRED_SPIRIT, brighter(SACRED_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, ItemRegistry.WICKED_SPIRIT, WICKED_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, ItemRegistry.ARCANE_SPIRIT, brighter(ARCANE_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, ItemRegistry.ELDRITCH_SPIRIT, darker(ELDRITCH_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, ItemRegistry.AERIAL_SPIRIT, brighter(AERIAL_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, ItemRegistry.AQUEOUS_SPIRIT, brighter(AQUEOUS_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, ItemRegistry.INFERNAL_SPIRIT, brighter(INFERNAL_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, ItemRegistry.EARTHEN_SPIRIT, brighter(EARTHEN_SPIRIT_COLOR, 1));
    }
}