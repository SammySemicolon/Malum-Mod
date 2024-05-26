package com.sammy.malum.registry.client;

import com.sammy.malum.common.recipe.*;
import net.minecraft.client.player.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.handlers.screenparticle.*;

import java.util.*;

import static com.sammy.malum.visual_effects.ScreenParticleEffects.VoidTransmutableParticleEffect.*;

public class ParticleEmitterRegistry {

    public static boolean registeredVoidParticleEmitters = false;

    public static boolean addParticleEmitters(Entity entity, Level level, boolean b) {
        if (!registeredVoidParticleEmitters) {
            if (entity instanceof AbstractClientPlayer player && player.equals(Minecraft.getInstance().player)) {
                final List<FavorOfTheVoidRecipe> recipes = FavorOfTheVoidRecipe.getRecipes(player.level());
                if (!recipes.isEmpty()) {
                    for (FavorOfTheVoidRecipe recipe : recipes) {
                        for (ItemStack item : recipe.input.getItems()) {
                            ParticleEmitterHandler.registerItemParticleEmitter(item.getItem(), INSTANCE);
                        }
                    }
                    registeredVoidParticleEmitters = true;
                }
            }
        }
        return true;
    }
}
