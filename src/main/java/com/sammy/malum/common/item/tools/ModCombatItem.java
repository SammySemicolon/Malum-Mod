package com.sammy.malum.common.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.inventory.EquipmentSlot;
import net.minecraft.item.IItemTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.Level.Level;
import net.minecraftforge.fmllegacy.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.item.ItemStack.DECIMALFORMAT;

import net.minecraft.item.Item.Properties;

public class ModCombatItem extends TieredItem {
    private Multimap<Attribute, AttributeModifier> attributes;

    public ModCombatItem(IItemTier tier, Properties builderIn) {
        super(tier, builderIn);
    }

    public void createAttributes(IItemTier tier, float attackDamageIn, float attackSpeedIn) {
        float attackDamage = attackDamageIn + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        putExtraAttributes(attributeBuilder);
        this.attributes = attributeBuilder.build();
    }

    public void putExtraAttributes(ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder) {

    }

    @Override
    public boolean canAttackBlock(BlockState state, Level LevelIn, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (entity) ->
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level LevelIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getDestroySpeed(LevelIn, pos) != 0.0F) {
            stack.hurtAndBreak(2, entityLiving, (entity) ->
                    entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockIn) {
        return blockIn.is(Blocks.COBWEB);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.category.equals(EnchantmentType.WEAPON)) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}