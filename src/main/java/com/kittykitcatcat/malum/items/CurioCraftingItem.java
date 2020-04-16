package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.SpiritData;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.MalumHelper.makeTooltip;
import static com.kittykitcatcat.malum.SpiritData.makeDefaultTooltip;
import static com.kittykitcatcat.malum.SpiritData.spiritProperty;

@Mod.EventBusSubscriber
public class CurioCraftingItem extends Item
{
    public static final IItemPropertyGetter curioProperty = (stack, world, entity) -> stack.getTag() != null && stack.getTag().contains("power") ? stack.getTag().getInt("power") : 0;

    public CurioCraftingItem(Properties properties)
    {
        super(properties);
        this.addPropertyOverride(new ResourceLocation("power"), curioProperty);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (stack.getTag() != null)
        {
            if (stack.getTag().contains("power"))
            {
                CompoundNBT nbt = stack.getTag();
                ArrayList<ITextComponent> arrayList = new ArrayList<>();
                arrayList.add(new TranslationTextComponent("malum.tooltip.power.desc").applyTextStyle(TextFormatting.GRAY)
                        .appendSibling(new StringTextComponent(" " + nbt.getInt("curioPower")).applyTextStyle(TextFormatting.DARK_PURPLE)));
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}