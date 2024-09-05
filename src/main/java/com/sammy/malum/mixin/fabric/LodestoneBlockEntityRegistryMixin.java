package com.sammy.malum.mixin.fabric;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import team.lodestar.lodestone.registry.common.LodestoneBlockEntityRegistry;
import team.lodestar.lodestone.systems.block.sign.LodestoneStandingSignBlock;
import team.lodestar.lodestone.systems.block.sign.LodestoneWallSignBlock;
import team.lodestar.lodestone.systems.multiblock.ILodestoneMultiblockComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is so sad, lodestones block entities api is on life support on fabric
 * MALDING
 */
@Mixin(LodestoneBlockEntityRegistry.class)
public class LodestoneBlockEntityRegistryMixin {

    @ModifyReturnValue(method = "getBlocks", at = @At("RETURN"))
    private static Block[] getBlocks(Block[] original, @Local(argsOnly = true) Class<?>... blockClasses) {

        // Use a list to easily add blocks
        List<Block> modifiedList = new ArrayList<>(Arrays.asList(original));

        // Check for instances of LodestoneStandingSignBlock and LodestoneWallSignBlock
        if (containsMyClassInstance(LodestoneStandingSignBlock.class, blockClasses)
                && containsMyClassInstance(LodestoneWallSignBlock.class, blockClasses)) {

            modifiedList.add(BlockRegistry.RUNEWOOD_SIGN.get());
            modifiedList.add(BlockRegistry.RUNEWOOD_WALL_SIGN.get());
            modifiedList.add(BlockRegistry.SOULWOOD_SIGN.get());
            modifiedList.add(BlockRegistry.SOULWOOD_WALL_SIGN.get());
        }

        // Check for instances of ILodestoneMultiblockComponent
        if (containsMyClassInstance(ILodestoneMultiblockComponent.class, blockClasses)) {
            modifiedList.add(BlockRegistry.BRILLIANT_OBELISK_COMPONENT.get());
            modifiedList.add(BlockRegistry.REPAIR_PYLON_COMPONENT.get());
            modifiedList.add(BlockRegistry.SPIRIT_CATALYZER_COMPONENT.get());
            modifiedList.add(BlockRegistry.SPIRIT_CRUCIBLE_COMPONENT.get());
            modifiedList.add(BlockRegistry.RUNEWOOD_OBELISK_COMPONENT.get());
        }

        // Convert the list back to an array
        return modifiedList.toArray(new Block[0]);
    }

    @Unique
    private static boolean containsMyClassInstance(Class<?> interfaced, Class<?>... blockClasses) {
        for (Class<?> blockClass : blockClasses) {
            // Check if interfaced is assignable from blockClass
            if (interfaced.isAssignableFrom(blockClass)) {
                return true; // Found a class that is or extends interfaced
            }
        }
        return false; // None of the classes matched
    }
}