package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.UUID;

import static top.theillusivec4.curios.api.type.capability.ICurio.DropRule.ALWAYS_KEEP;

public class CurioTokenOfGratitude extends MalumCurioItem {
    public static final ArrayList<UUID> GRADITUDE_CERTIFIED = new ArrayList<>();
    public static final UUID SAMMY = getUuid("0ca54301-6170-4c44-b3e0-b8afa6b81ed2");
    public static final UUID OWL_PERSON = getUuid("0ca54301-6170-4c44-b3e0-b8afa6b81ed2");

    public CurioTokenOfGratitude(Properties builder) {
        super(builder);
    }

    public static UUID getUuid(String uuid)
    {
        UUID id = UUID.fromString(uuid);
        GRADITUDE_CERTIFIED.add(id);
        return id;
    }
    public static void giveItem(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player playerEntity) {
            if (!playerEntity.level.isClientSide) {
                if (GRADITUDE_CERTIFIED.stream().anyMatch(u -> u.equals(playerEntity.getUUID()))) {
                    if (ItemHelper.findCosmeticCurio(s -> s.getItem().equals(ItemRegistry.TOKEN_OF_GRATITUDE.get()), playerEntity).isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, ItemRegistry.TOKEN_OF_GRATITUDE.get().getDefaultInstance());
                    }
                }
            }
        }
    }

    @Override
    public boolean isOrnate() {
        return true;
    }

    @Override
    public boolean isGilded() {
        return true;
    }

    @Nonnull
    @Override
    public ICurio.DropRule getDropRule(LivingEntity livingEntity, ItemStack stack) {
        return ALWAYS_KEEP;
    }
}