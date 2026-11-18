package com.sammy.malum.common.item;

import com.sammy.malum.common.data.component.GeasDataComponent;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.entity.player.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.screen.ScreenParticleHolder;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

public class GeasItem extends Item implements ParticleEmitterHandler.ItemParticleSupplier, ICurioItem {

    public static final String GEAS = "malum.gui.geas.type";
    public static final String SWORN = "malum.gui.geas.sworn";
    public static final String CREATIVE = "malum.gui.geas.creative";
    public static final String CREATIVE_HELP = "malum.gui.geas.creative_help";

    public GeasItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        final ItemStack stack = player.getItemInHand(usedHand);
        GeasDataComponent.getStoredGeasEffect(stack).ifPresent(c -> {
            var geas = c.geasEffectType();
            if (GeasEffectHandler.hasGeas(player, geas)) {
                GeasEffectHandler.removeGeasEffect(player, geas);
            } else {
                GeasEffectHandler.addGeasEffect(player, geas);
            }
        });
        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return false;
    }

    @Override
    public @NotNull ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, boolean recentlyHit, ItemStack stack) {
        return ICurio.DropRule.ALWAYS_KEEP;
    }

    @Override
    public List<Component> getSlotsTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        return Collections.emptyList();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (!stack.has(MalumDataComponents.GEAS_EFFECT)) {
            return;
        }
        GeasDataComponent.getStoredGeasEffect(stack).ifPresent(c -> {
            var component = tooltipComponents.getFirst().copy()
                    .append(Component.literal(" <").withStyle(ChatFormatting.DARK_GRAY))
                    .append(Component.translatable(c.geasEffectType().getLangKey()).withStyle(ChatFormatting.GOLD))
                    .append(Component.literal(">").withStyle(ChatFormatting.DARK_GRAY));
            tooltipComponents.set(0, component);
        });
    }

    @Override
    public void spawnEarlyParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleEffects.spawnGeasItemScreenParticles(target, level, partialTick);
    }

    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleEffects.spawnSpiritShardScreenParticles(target, MalumSpiritTypes.ELDRITCH_SPIRIT);
    }

    public static void addGeasTooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        GeasDataComponent.getStoredGeasEffect(itemStack).ifPresent(c -> {
            var entity = event.getEntity();
            var geas = c.geasEffectType();
            List<Component> tooltip = event.getToolTip();
            var index = new AtomicInteger(1);
            Consumer<Component> tooltipConsumer = t -> tooltip.add(index.getAndIncrement(), t);
            tooltipConsumer.accept(
                    Component.translatable(geas.getDescription()).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            tooltipConsumer.accept(
                    Component.translatable("malum.gui.slot").withStyle(ChatFormatting.GOLD).append(Component.translatable(GEAS).withStyle(ChatFormatting.YELLOW)));
            if (c.isCreative() && !itemStack.equals(geas.getDummyCreativeStack())) {
                tooltipConsumer.accept(
                        Component.translatable(CREATIVE).withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
                tooltipConsumer.accept(
                        Component.translatable(CREATIVE_HELP).withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
            }
            tooltipConsumer.accept(
                    Component.empty());
            tooltipConsumer.accept(
                    Component.translatable(SWORN).withStyle(ChatFormatting.GOLD));
            GeasEffect geasEffect = entity != null ? GeasEffectHandler.getGeasEffect(entity, c.geasEffectType().getHolder()) : null;
            if (geasEffect == null) {
                geasEffect = c.geasEffectType().getDefaultInstance();
            }
            geasEffect.addTooltipComponents(entity, tooltipConsumer, event.getFlags());
        });
    }
}