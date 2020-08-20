
package com.kittykitcatcat.malum.items.curios;

import com.kittykitcatcat.malum.SpiritConsumer;
import com.kittykitcatcat.malum.SpiritDataHelper;
import com.kittykitcatcat.malum.SpiritDescription;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;

import java.util.ArrayList;

import static com.kittykitcatcat.malum.SpiritDataHelper.consumeSpirit;
import static com.kittykitcatcat.malum.SpiritDataHelper.makeGenericSpiritDependantTooltip;

public class CurioNecroticCatalyst extends Item implements ICurio, SpiritConsumer, SpiritDescription
{
    public CurioNecroticCatalyst(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            @Override
            public void playEquipSound(LivingEntity entityLivingBase)
            {
                entityLivingBase.world.playSound(null, entityLivingBase.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }
    
            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }
        });
    }
    
    @Override
    public void onCurioTick(String identifier, int index, LivingEntity livingEntity)
    {
    }
    
    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count)
    {
        if (player instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) player;
            if (playerEntity.world.getGameTime() % 10L == 0L)
            {
                if (playerEntity.world.rand.nextDouble() < 0.2f && playerEntity.getFoodStats().needFood())
                {
                    boolean success = consumeSpirit(playerEntity, stack);
                    if (success)
                    {
                        playerEntity.getFoodStats().addStats(1,1);
                    }
                }
            }
        }
    }
    
    @Override
    public int durability()
    {
        return 10;
    }

    @Override
    public String spirit()
    {
        return "minecraft:zombie";
    }

    @Override
    public ArrayList<ITextComponent> components()
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(makeGenericSpiritDependantTooltip("malum.tooltip.necroticcatalyst.effect", SpiritDataHelper.getName(spirit())));
        return components;
    }
}