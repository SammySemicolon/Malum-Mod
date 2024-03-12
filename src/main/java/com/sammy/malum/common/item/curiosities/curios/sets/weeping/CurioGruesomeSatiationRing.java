package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.item.ItemTagRegistry.GROSS_FOODS;

public class CurioGruesomeSatiationRing extends MalumCurioItem implements IVoidItem {
    public CurioGruesomeSatiationRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.curio.effect.ring_of_gruesome_satiation"));
    }

    public static void finishEating(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = event.getResultStack();
            if (CurioHelper.hasCurioEquipped(player, ItemRegistry.RING_OF_GRUESOME_SATIATION.get())) {
                if (stack.is(GROSS_FOODS)) {
                    double arcaneResonance = player.getAttribute(AttributeRegistry.ARCANE_RESONANCE.get()).getValue();
                    MobEffect gluttony = MobEffectRegistry.GLUTTONY.get();
                    MobEffectInstance effect = player.getEffect(MobEffectRegistry.GLUTTONY.get());
                    if (effect != null) {
                        EntityHelper.amplifyEffect(effect, player, 2, 9);
                    }
                    else {
                        player.addEffect(new MobEffectInstance(gluttony, 600 + (int) (arcaneResonance * 600), 1, true, true, true));
                    }
                }
            }
        }
    }
}