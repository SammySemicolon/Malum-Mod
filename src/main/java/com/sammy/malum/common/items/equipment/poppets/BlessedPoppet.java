package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.ClientHelper;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.totems.rites.IPoppetBlessing;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

public class BlessedPoppet extends PoppetItem
{
    public BlessedPoppet(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, ArrayList<LivingEntity> targets, SimpleInventory inventory)
    {
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (isSelected)
        {
            if (entityIn instanceof PlayerEntity)
            {
                if (worldIn.getGameTime() % 100 == 0)
                {
                    CompoundNBT nbt = stack.getOrCreateTag();
                    if (nbt.contains("blessing"))
                    {
                        String blessingType = nbt.getString("blessing");
                        MalumRites.MalumRite rite = MalumRites.getRite(blessingType);
                        if (rite instanceof IPoppetBlessing)
                        {
                            ((IPoppetBlessing) rite).blessingEffect((PlayerEntity) entityIn);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (stack.getTag() != null)
        {
            CompoundNBT nbt = stack.getTag();
            if (nbt.contains("blessing"))
            {
                String blessingType = nbt.getString("blessing");
                MalumRites.MalumRite rite = MalumRites.getRite(blessingType);
                if (rite instanceof IPoppetBlessing)
                {
                    tooltip.add(ClientHelper.importantTranslatableComponent(rite.translationKey));
                }
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}