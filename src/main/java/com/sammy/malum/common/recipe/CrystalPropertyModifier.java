package com.sammy.malum.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.soulstone.SoulstoneBudBlock;
import com.sammy.malum.registry.common.MalumContent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.IntBinaryOperator;

public record CrystalPropertyModifier(ResourceLocation blockId, String property, PropertyOperation operation) {
    public static final CrystalPropertyModifier DEFAULT = new CrystalPropertyModifier(MalumContent.Materials.SOULSTONE_BUD.block().getId(), SoulstoneBudBlock.STAGE.getName(), PropertyOperation.ADD);

    public static final Codec<CrystalPropertyModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("block").forGetter(CrystalPropertyModifier::blockId),
            Codec.STRING.fieldOf("int_property_name").forGetter(CrystalPropertyModifier::property),
            PropertyOperation.CODEC.fieldOf("int_operation").forGetter(CrystalPropertyModifier::operation)
    ).apply(instance, CrystalPropertyModifier::new));

    public BlockState modify(BlockState toModify) {
        Block block = BuiltInRegistries.BLOCK.get(blockId);
        if (!toModify.getBlock().equals(block)) return toModify;
        var stateDef = block.getStateDefinition();
        var optionalProperty = getOptionalIntProperty(stateDef);
        if (optionalProperty.isEmpty()) {
            return toModify;
        }
        var integerProperty = optionalProperty.get();
        if (!toModify.hasProperty(integerProperty)) return toModify; //should be validated enough by now
        int currentStage = toModify.getValue(integerProperty);
        int modified = operation.apply(currentStage, 1);
        var possibleValues = integerProperty.getPossibleValues();
        if (!possibleValues.contains(modified)) {
            MalumMod.LOGGER.warn("Value {} for property {} exceeds the valid range of {}", modified, integerProperty.getName(), Arrays.toString(possibleValues.toArray()));
            return toModify;
        }

        return toModify.setValue(integerProperty, modified);
    }

    public Optional<IntegerProperty> getOptionalIntProperty(StateDefinition<?, ?> state) {
        Property<?> property = state.getProperty(this.property);
        if (property == null) {
            MalumMod.LOGGER.warn("Could not find property {} on {}. Proceeding without modification", this.property, state.getOwner());
            return Optional.empty();
        }

        if (!(property instanceof IntegerProperty integerProperty)) {
            MalumMod.LOGGER.warn("Property {} is not an IntegerProperty", this.property);
            return Optional.empty();
        }

        return Optional.of(integerProperty);
    }

    public Block getBlock() {
        return BuiltInRegistries.BLOCK.get(this.blockId);
    }

    public enum PropertyOperation implements StringRepresentable {
        ADD(Integer::sum),
        SUBTRACT((a, b) -> a - b);

        public static final StringRepresentable.EnumCodec<PropertyOperation> CODEC = StringRepresentable.fromEnum(PropertyOperation::values);

        public static final StreamCodec<FriendlyByteBuf, PropertyOperation> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(PropertyOperation.class);

        private final IntBinaryOperator operation;

        PropertyOperation(IntBinaryOperator operation) {
            this.operation = operation;
        }

        public int apply(int a, int b) {
            return operation.applyAsInt(a, b);
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
