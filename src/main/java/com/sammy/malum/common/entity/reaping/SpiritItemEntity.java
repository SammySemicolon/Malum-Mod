package com.sammy.malum.common.entity.reaping;

import com.sammy.malum.common.entity.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.sound.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;

import javax.annotation.*;

public class SpiritItemEntity extends FloatingItemEntity {

    public int soundCooldown = 20 + random.nextInt(100);

    public SpiritItemEntity(Level level) {
        super(MalumEntityTypes.NATURAL_SPIRIT.get(), level);
    }

    public SpiritItemEntity(Level level, @Nullable LivingEntity owner, ItemStack stack, Vec3 position, Vec3 velocity) {
        this(level);
        if (owner != null) {
            setDestination(new FloatingItemDestinationData(owner));
        }
        setItem(stack);
        setPos(position);
        setDeltaMovement(velocity);
        if (stack.getItem() instanceof SpiritShardItem spiritShardItem) {
            setSpirit(spiritShardItem.getSpirit());
        }
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    public void collect(ServerLevel level) {
        ItemStack stack = getItem();
        getDestination().getEntityCollector(level).ifPresent(collector -> {
            if (stack.getItem() instanceof SpiritShardItem) {
                SoulHarvestHandler.pickupSpirit(collector, stack);
            } else {
                ItemHelper.giveItemToEntity(collector, stack);
            }
        });
        float pitch = Mth.nextFloat(random, 1.1f, 2f);
        if (random.nextFloat() < 0.6f) {
            playSound(MalumSoundEvents.SPIRIT_PICKUP.get(), 0.5f, pitch);
        }
        playSound(SoundEvents.ITEM_PICKUP, 0.5f, pitch);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            SpiritLightSpecs.spiritParticles(this);
        }
        else {
            if (soundCooldown-- == 0) {
                if (random.nextFloat() < 0.4f) {
                    float pitch = Mth.nextFloat(random, 0.8f, 2f);
                    playSound(MalumSoundEvents.ARCANE_WHISPERS.get(), 0.3f, pitch);
                }
                soundCooldown = 40 + random.nextInt(40);
            }
        }
    }
}