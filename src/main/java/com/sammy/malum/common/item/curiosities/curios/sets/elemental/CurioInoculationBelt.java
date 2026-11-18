 package com.sammy.malum.common.item.curiosities.curios.sets.elemental;

 import com.google.common.collect.ImmutableMultimap;
 import com.google.common.collect.Multimap;
 import com.sammy.malum.MalumMod;
 import com.sammy.malum.common.item.*;
 import com.sammy.malum.common.item.curiosities.curios.*;
 import com.sammy.malum.core.helpers.*;
 import net.minecraft.core.Holder;
 import net.minecraft.network.chat.*;
 import net.minecraft.resources.ResourceLocation;
 import net.minecraft.world.effect.MobEffectInstance;
 import net.minecraft.world.entity.ai.attributes.*;
 import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
 import net.minecraft.world.item.ItemStack;
 import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;
 import top.theillusivec4.curios.api.SlotContext;

 import java.util.function.*;

 public class CurioInoculationBelt extends MalumCurioItem implements IMalumEventResponderBody {

     public static final ResourceLocation INOCULATION_BUFF = MalumMod.malumPath("inoculation_belt");
     public static final Multimap<Holder<Attribute>, AttributeModifier> INOCULATION_MODIFIERS =
             ImmutableMultimap.<Holder<Attribute>, AttributeModifier>builder()
                     .put(WaywardAttributeTypes.MAGIC_PROFICIENCY.getDelegate(), new AttributeModifier(INOCULATION_BUFF, 0.05f, Operation.ADD_VALUE))
                     .put(WaywardAttributeTypes.MAGIC_RESISTANCE.getDelegate(), new AttributeModifier(INOCULATION_BUFF, 0.05f, Operation.ADD_VALUE))
                     .put(Attributes.ARMOR, new AttributeModifier(INOCULATION_BUFF, 0.5f, Operation.ADD_VALUE))
                     .put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(INOCULATION_BUFF, 0.5f, Operation.ADD_VALUE))

                     .build();

     public CurioInoculationBelt(Properties builder) {
         super(builder, MalumTrinketFamily.ELEMENTAL);
     }

     @Override
     public void addExtraTooltipLines(Consumer<Component> consumer) {
         consumer.accept(TooltipComponentHelper.positiveCurioEffect("inoculation_effect_duration"));
         consumer.accept(TooltipComponentHelper.positiveCurioEffect("inoculation_effect_buff"));
     }

     @Override
     public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
         var entity = slotContext.entity();
         entity.getAttributes().removeAttributeModifiers(INOCULATION_MODIFIERS);
     }

     @Override
     public void curioTick(SlotContext slotContext, ItemStack stack) {
         var entity = slotContext.entity();
         var level = entity.level();
         if (level.getGameTime() % 10 > 0) {
             return;
         }
         float effectStacks = 0;
         for (MobEffectInstance instance : entity.getActiveEffects()) {
             var holder = instance.getEffect().value();
             if (holder.isBeneficial()) {
                 effectStacks += 1 + instance.amplifier * 0.1f;
             }
         }
         var attributes = entity.getAttributes();
         attributes.removeAttributeModifiers(INOCULATION_MODIFIERS);
         for (Holder<Attribute> holder : INOCULATION_MODIFIERS.keys()) {
             var instance = attributes.getInstance(holder);
             if (instance == null) {
                 return;
             }
             var modifiers = INOCULATION_MODIFIERS.get(holder);
             for (AttributeModifier modifier : modifiers) {
                 instance.removeModifier(modifier);
                 var copy = new AttributeModifier(modifier.id(), modifier.amount() * effectStacks, modifier.operation());
                 instance.addTransientModifier(copy);
                 instance.addTransientModifier(modifier);
             }
         }
     }
 }