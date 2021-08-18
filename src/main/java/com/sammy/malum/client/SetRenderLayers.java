package com.sammy.malum.client;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.spirit_altar.SpiritAltarBlock;
import com.sammy.malum.core.init.block.MalumBlocks;
import com.sammy.malum.core.mod_systems.multiblock.BoundingBlock;
import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.block.MalumBlocks.BLOCKS;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class SetRenderLayers
{
    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event)
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TorchBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BoundingBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LeavesBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof EtherBrazierBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ItemStandBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ItemPedestalBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TotemBaseBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TotemPoleBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SpiritJarBlock).forEach(SetRenderLayers::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SpiritAltarBlock).forEach(SetRenderLayers::setCutout);
        setCutout(MalumBlocks.BLAZING_QUARTZ_ORE);
    }
    
    public static void setCutout(RegistryObject<Block> b)
    {
        RenderTypeLookup.setRenderLayer(b.get(), RenderType.getCutout());
    }
}