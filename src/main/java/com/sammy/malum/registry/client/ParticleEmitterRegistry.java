package com.sammy.malum.registry.client;

import com.sammy.malum.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.data.recipe.*;
import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import team.lodestar.lodestone.handlers.screenparticle.*;

import java.util.*;

import static com.sammy.malum.visual_effects.ScreenParticleEffects.VoidTransmutableParticleEffect.INSTANCE;

@Mod.EventBusSubscriber(modid= MalumMod.MALUM, bus= Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ParticleEmitterRegistry {

    @SubscribeEvent
    public static void addParticleEmitters(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            final List<FavorOfTheVoidRecipe> recipes = FavorOfTheVoidRecipe.getRecipes(player.level());
            for (FavorOfTheVoidRecipe recipe : recipes) {
                for (ItemStack item : recipe.input.getItems()) {
                    ParticleEmitterHandler.registerItemParticleEmitter(item.getItem(), INSTANCE);
                }
            }
        }
    }
}
