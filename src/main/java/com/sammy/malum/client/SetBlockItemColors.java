package com.sammy.malum.client;

import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.entity_renderer.FloatingItemEntityRenderer;
import com.sammy.malum.client.entity_renderer.ScytheBoomerangEntityRenderer;
import com.sammy.malum.client.tile_renderer.*;
import com.sammy.malum.common.block.generic.MalumLeavesBlock;
import com.sammy.malum.common.tile.EtherTileEntity;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.init.block.MalumBlocks;
import com.sammy.malum.core.init.block.MalumTileEntities;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.MalumHelper.brighter;
import static com.sammy.malum.MalumHelper.darker;
import static com.sammy.malum.core.init.block.MalumBlocks.BLOCKS;
import static com.sammy.malum.core.init.items.MalumItems.ITEMS;
import static com.sammy.malum.core.mod_content.MalumSpiritTypes.*;
import static com.sammy.malum.core.mod_content.MalumSpiritTypes.EARTHEN_SPIRIT_COLOR;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class SetBlockItemColors
{
    @SubscribeEvent
    public static void setBlockColors(ColorHandlerEvent.Block event)
    {
        BlockColors blockColors = event.getBlockColors();
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        blockColors.register((state, reader, pos, color) ->
        {
            TileEntity tileEntity = reader.getTileEntity(pos);
            if (tileEntity instanceof EtherTileEntity)
            {
                EtherTileEntity etherTileEntity = (EtherTileEntity) tileEntity;
                return color == 0 ? etherTileEntity.color : -1;
            }
            return -1;
        }, MalumBlocks.ETHER_TORCH.get(), MalumBlocks.WALL_ETHER_TORCH.get());

        MalumHelper.takeAll(blocks, block -> block.get() instanceof MalumLeavesBlock).forEach(block -> blockColors.register((state, reader, pos, color) ->
        {
            float i = state.get(MalumLeavesBlock.COLOR);
            MalumLeavesBlock malumLeavesBlock = (MalumLeavesBlock) block.get();
            int r = (int) MathHelper.lerp(i / 5f, malumLeavesBlock.minColor.getRed(), malumLeavesBlock.maxColor.getRed());
            int g = (int) MathHelper.lerp(i / 5f, malumLeavesBlock.minColor.getGreen(), malumLeavesBlock.maxColor.getGreen());
            int b = (int) MathHelper.lerp(i / 5f, malumLeavesBlock.minColor.getBlue(), malumLeavesBlock.maxColor.getBlue());
            return r << 16 | g << 8 | b;
        }, block.get()));
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
        itemColors.register((stack, color) ->
                        color == 0 ? ((IDyeableArmorItem) stack.getItem()).getColor(stack) : -1,
                MalumItems.ETHER_TORCH.get(), MalumItems.ETHER_BRAZIER.get());
        itemColors.register((stack, color) ->
                        ((IDyeableArmorItem) stack.getItem()).getColor(stack),
                MalumItems.ETHER.get());

        ClientHelper.registerItemColor(itemColors, MalumItems.SACRED_SPIRIT, brighter(SACRED_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.WICKED_SPIRIT, WICKED_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.ARCANE_SPIRIT, brighter(ARCANE_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.ELDRITCH_SPIRIT, darker(ELDRITCH_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.AERIAL_SPIRIT, brighter(AERIAL_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.AQUATIC_SPIRIT, brighter(AQUATIC_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.INFERNAL_SPIRIT, brighter(INFERNAL_SPIRIT_COLOR, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.EARTHEN_SPIRIT, brighter(EARTHEN_SPIRIT_COLOR, 1));
    }
}