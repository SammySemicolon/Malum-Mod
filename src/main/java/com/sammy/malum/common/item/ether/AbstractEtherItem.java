package com.sammy.malum.common.item.ether;

import team.lodestar.lodestone.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public abstract class AbstractEtherItem extends BlockItem implements DyeableLeatherItem, ItemParticleEmitter {
    public static final String FIRST_COLOR = "firstColor";
    public static final String SECOND_COLOR = "secondColor";
    public static final int DEFAULT_FIRST_COLOR = 15712278;
    public static final int DEFAULT_SECOND_COLOR = 4607909;

    public final boolean iridescent;

    public AbstractEtherItem(Block blockIn, Properties builder, boolean iridescent) {
        super(blockIn, builder);
        this.iridescent = iridescent;
    }

    public String colorLookup() {
        return iridescent ? SECOND_COLOR : FIRST_COLOR;
    }

    public int getSecondColor(ItemStack stack) {
        if (!iridescent) {
            return getFirstColor(stack);
        }
        CompoundTag tag = stack.getTagElement("display");

        return tag != null && tag.contains(SECOND_COLOR, 99) ? tag.getInt(SECOND_COLOR) : DEFAULT_SECOND_COLOR;
    }

    public void setSecondColor(ItemStack stack, int color) {
        stack.getOrCreateTagElement("display").putInt(SECOND_COLOR, color);
    }

    public int getFirstColor(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("display");
        return tag != null && tag.contains(FIRST_COLOR, 99) ? tag.getInt(FIRST_COLOR) : DEFAULT_FIRST_COLOR;
    }

    public void setFirstColor(ItemStack stack, int color) {
        stack.getOrCreateTagElement("display").putInt(FIRST_COLOR, color);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("display");
        return tag != null && tag.contains(colorLookup(), 99) ? tag.getInt(colorLookup()) : DEFAULT_FIRST_COLOR;
    }

    @Override
    public boolean hasCustomColor(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("display");
        return tag != null && tag.contains(colorLookup(), 99);
    }

    @Override
    public void clearColor(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("display");
        if (tag != null && tag.contains(colorLookup())) {
            tag.remove(colorLookup());
        }
    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateTagElement("display").putInt(colorLookup(), color);
    }
}