package com.sammy.malum;

import com.sammy.malum.core.data.*;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_systems.particle.ParticleRendering;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.UUID;

import static com.sammy.malum.common.item.equipment.curios.CurioTokenOfGratitude.sammy_uuid;
import static com.sammy.malum.core.init.MalumEffects.EFFECTS;
import static com.sammy.malum.core.init.MalumEntities.ENTITY_TYPES;
import static com.sammy.malum.core.init.MalumSounds.SOUNDS;
import static com.sammy.malum.core.init.block.MalumBlocks.BLOCKS;
import static com.sammy.malum.core.init.block.MalumTileEntities.TILE_ENTITIES;
import static com.sammy.malum.core.init.enchantment.MalumEnchantments.ENCHANTMENTS;
import static com.sammy.malum.core.init.items.MalumItems.ITEMS;
import static com.sammy.malum.core.init.particles.MalumParticles.PARTICLES;

@SuppressWarnings("unused")
@Mod(MalumMod.MODID)
public class MalumMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "malum";
    public static final Random RANDOM = new Random();

    public MalumMod()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ENCHANTMENTS.register(modBus);
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TILE_ENTITIES.register(modBus);
        ENTITY_TYPES.register(modBus);
        EFFECTS.register(modBus);
        PARTICLES.register(modBus);
        SOUNDS.register(modBus);
        modBus.addListener(this::gatherData);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () ->
        {
            MinecraftForge.EVENT_BUS.register(new ParticleRendering());
            return new Object();
        });
    }

    public void gatherData(GatherDataEvent event)
    {
        BlockTagsProvider provider = new MalumBlockTagProvider(event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(new MalumBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new MalumItemModelProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new MalumLangProvider(event.getGenerator()));
        event.getGenerator().addProvider(provider);
        event.getGenerator().addProvider(new MalumLootTableProvider(event.getGenerator()));
        event.getGenerator().addProvider(new MalumItemTagProvider(event.getGenerator(), provider, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new MalumRecipeProvider(event.getGenerator()));
    }

    public static void showGratitude(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
            if (MalumHelper.areWeOnServer(playerEntity.world))
            {
                if (playerEntity.getUniqueID().equals(UUID.fromString(sammy_uuid)))
                {
                    if (!MalumHelper.findCosmeticCurio(s -> s.getItem().equals(MalumItems.TOKEN_OF_GRATITUDE.get()), playerEntity).isPresent())
                    {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, MalumItems.TOKEN_OF_GRATITUDE.get().getDefaultInstance());
                    }
                }
            }
        }
    }
}