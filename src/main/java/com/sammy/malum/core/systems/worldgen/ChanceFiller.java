package com.sammy.malum.core.systems.worldgen;

import com.mojang.serialization.Codec;
import com.sammy.malum.core.setup.content.worldgen.PlacementModifierTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Random;

public class ChanceFiller extends PlacementFilter {
   public static final Codec<ChanceFiller> CODEC = ExtraCodecs.POSITIVE_FLOAT.fieldOf("chance").xmap(ChanceFiller::new, (p_191907_) -> p_191907_.chance).codec();
   private final float chance;

   public ChanceFiller(float chance) {
      this.chance = chance;
   }

   @Override
   protected boolean shouldPlace(PlacementContext p_191903_, Random p_191904_, BlockPos p_191905_) {
      return p_191904_.nextFloat() < chance;
   }

   public PlacementModifierType<?> type() {
      return PlacementModifierTypeRegistry.CHANCE;
   }
}