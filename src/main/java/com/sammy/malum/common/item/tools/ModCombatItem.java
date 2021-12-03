package com.sammy.malum.common.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.item.ItemStack.DECIMALFORMAT;

public class ModCombatItem extends TieredItem {
    private Multimap<Attribute, AttributeModifier> attributes;

    public ModCombatItem(IItemTier tier, Properties builderIn) {
        super(tier, builderIn);
    }

    public void createAttributes(IItemTier tier, float attackDamageIn, float attackSpeedIn) {
        float attackDamage = attackDamageIn + tier.getAttackDamage();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        putExtraAttributes(attributeBuilder);
        this.attributes = attributeBuilder.build();
    }

    public void putExtraAttributes(ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder) {

    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.isIn(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (entity) ->
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(2, entityLiving, (entity) ->
                    entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }

        return true;
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return blockIn.isIn(Blocks.COBWEB);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributes : super.getAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.type.equals(EnchantmentType.WEAPON)) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}