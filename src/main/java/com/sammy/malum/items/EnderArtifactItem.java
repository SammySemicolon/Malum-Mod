package com.sammy.malum.items;

import com.sammy.malum.SpiritConsumer;
import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.SpiritDescription;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.makeGenericSpiritDependantTooltip;

public class EnderArtifactItem extends Item implements SpiritConsumer, SpiritDescription
{
    public EnderArtifactItem(Properties builder)
    {
        super(builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean success = SpiritDataHelper.consumeSpirit(playerIn, itemstack);
        if (success)
        {
            playerIn.world.playSound(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.PLAYERS, 1f, 0f, true);
            playerIn.openContainer(new SimpleNamedContainerProvider((p_220114_1_, p_220114_2_, p_220114_3_) -> ChestContainer.createGeneric9X3(p_220114_1_, p_220114_2_, playerIn.getInventoryEnderChest()), new TranslationTextComponent("container.enderchest")));
            playerIn.addStat(Stats.ITEM_USED.get(this));
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
        return new ActionResult<>(ActionResultType.FAIL, itemstack);
    }

    @Override
    public int durability()
    {
        return 50;
    }

    @Override
    public String spirit()
    {
        return "minecraft:enderman";
    }

    @Override
    public ArrayList<ITextComponent> components()
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(makeGenericSpiritDependantTooltip("malum.tooltip.artifact.effect", SpiritDataHelper.getName(spirit())));
        return components;
    }
}