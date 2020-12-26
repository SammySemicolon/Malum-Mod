package com.sammy.malum.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.item.ISpiritHolderBlockItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MalumMod.MODID)
public class ClientHelper
{
    static IRenderTypeBuffer.Impl DELAYED_RENDER = null;
    
    public static IRenderTypeBuffer.Impl getDelayedRender()
    {
        if (DELAYED_RENDER == null)
        {
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for (RenderType type : new RenderType[]{RenderUtil.DELAYED_PARTICLE, RenderUtil.GLOWING_PARTICLE, RenderUtil.GLOWING_BLOCK_PARTICLE, RenderUtil.GLOWING, RenderUtil.GLOWING_SPRITE})
            {
                buffers.put(type, new BufferBuilder(type.getBufferSize()));
            }
            DELAYED_RENDER = IRenderTypeBuffer.getImpl(buffers, new BufferBuilder(256));
        }
        return DELAYED_RENDER;
    }
    
    @OnlyIn(Dist.CLIENT)
    static float clientTicks = 0;
    
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event)
    {
        RenderSystem.pushMatrix(); // this feels...cheaty
        RenderSystem.multMatrix(event.getMatrixStack().getLast().getMatrix());
        getDelayedRender().finish(RenderUtil.DELAYED_PARTICLE);
        getDelayedRender().finish(RenderUtil.GLOWING_PARTICLE);
        getDelayedRender().finish(RenderUtil.GLOWING_BLOCK_PARTICLE);
        RenderSystem.popMatrix();
    
        getDelayedRender().finish(RenderUtil.GLOWING_SPRITE);
        getDelayedRender().finish(RenderUtil.GLOWING);
    
        clientTicks += event.getPartialTicks();
    }
    
    @OnlyIn(Dist.CLIENT)
    public static float getClientTicks()
    {
        return clientTicks;
    }
    
    public static void makeTooltip(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn, ArrayList<ITextComponent> components)
    {
        tooltip.add(combinedComponent(simpleTranslatableComponent("malum.tooltip.hold"), importantTranslatableComponent("malum.tooltip.sneak", Screen.hasShiftDown())));
        if (Screen.hasShiftDown())
        {
            tooltip.addAll(components);
        }
    }
    
    public static ArrayList<ITextComponent> stackSpiritsTooltip(ItemStack stack, ArrayList<Pair<String, Integer>> spirits)
    {
        ArrayList<ITextComponent> tooltip = new ArrayList<>();
        if (SpiritHelper.validate(stack))
        {
            ISpiritHolderBlockItem essenceHolder = (ISpiritHolderBlockItem) stack.getItem();
            for (int i = 0; i < spirits.size(); i++)
            {
                String spirit = spirits.get(i).getFirst();
                int count = spirits.get(i).getSecond();
                boolean lit = !spirit.equals("empty");
                tooltip.add(combinedComponent(simpleTranslatableComponent("malum.tooltip.slot"), simpleComponent(" " + (i + 1) + ": "), importantComponent(count + "/" + essenceHolder.getMaxSpirits(), lit), importantComponent(spirit, lit)));
            }
        }
        return tooltip;
    }
    
    public static IFormattableTextComponent simpleComponent(String message)
    {
        return new StringTextComponent(message).mergeStyle(TextFormatting.WHITE);
    }
    
    public static IFormattableTextComponent simpleTranslatableComponent(String message)
    {
        return new TranslationTextComponent(message).mergeStyle(TextFormatting.WHITE);
    }
    
    public static IFormattableTextComponent importantComponent(String message, boolean litUp)
    {
        TextFormatting textFormatting = litUp ? TextFormatting.LIGHT_PURPLE : TextFormatting.DARK_PURPLE;
        return combinedComponent(new StringTextComponent("[").mergeStyle(TextFormatting.WHITE), new StringTextComponent(message).mergeStyle(textFormatting), new StringTextComponent("] ").mergeStyle(TextFormatting.WHITE)).mergeStyle(TextFormatting.BOLD);
    }
    
    public static IFormattableTextComponent importantTranslatableComponent(String message, boolean litUp)
    {
        TextFormatting textFormatting = litUp ? TextFormatting.LIGHT_PURPLE : TextFormatting.DARK_PURPLE;
        return combinedComponent(new StringTextComponent("[").mergeStyle(TextFormatting.WHITE), new TranslationTextComponent(message).mergeStyle(textFormatting), new StringTextComponent("] ").mergeStyle(TextFormatting.WHITE)).mergeStyle(TextFormatting.BOLD);
    }
    
    public static IFormattableTextComponent combinedComponent(IFormattableTextComponent... components)
    {
        IFormattableTextComponent finalComponent = components[0];
        for (IFormattableTextComponent component : components)
        {
            if (!component.equals(finalComponent))
            {
                finalComponent.append(component);
            }
        }
        return finalComponent;
    }
}