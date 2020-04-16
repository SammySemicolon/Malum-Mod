package com.kittykitcatcat.malum.items.curios;

import com.kittykitcatcat.malum.MalumMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;
@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class CrossbowReloadCurioItem extends Item implements ICurio
{
    public CrossbowReloadCurioItem(Properties builder)
    {
        super(builder);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioItem.createProvider(new ICurio()
        {
            @Override
            public void playEquipSound(LivingEntity entityLivingBase)
            {
                entityLivingBase.world.playSound(null, entityLivingBase.getPosition(),
                    SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL,
                    1.0f, 1.0f);
            }

            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }
        });
    }
    @SubscribeEvent
    public static void handleReload(LivingDamageEvent event)
    {
        if (event.getSource().getImmediateSource() instanceof ArrowEntity)
        {
            if (event.getSource().getTrueSource() instanceof PlayerEntity)
            {
                PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
                if (CuriosAPI.getCurioEquipped(stack1 -> stack1.getItem() instanceof CrossbowReloadCurioItem, playerEntity).isPresent())
                {
                    if (MathHelper.nextInt(playerEntity.world.rand, 0, 99) <= 50)
                    {
                        if (playerEntity.getHeldItemMainhand().getItem() instanceof CrossbowItem)
                        {
                            ItemStack crossbow = playerEntity.getHeldItemMainhand();
                            if (crossbow.getTag() != null)
                            {
                                ListNBT listnbt = new ListNBT();
                                crossbow.getTag().putBoolean("Charged", true);
                                CompoundNBT compoundnbt1 = new CompoundNBT();
                                new ItemStack(Items.ARROW).write(compoundnbt1);
                                listnbt.add(compoundnbt1);
                                crossbow.getTag().put("ChargedProjectiles", listnbt);
                            }
                        }
                    }
                }
            }
        }
    }
}