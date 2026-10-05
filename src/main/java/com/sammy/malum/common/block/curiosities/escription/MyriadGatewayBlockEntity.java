package com.sammy.malum.common.block.curiosities.escription;

import com.sammy.malum.common.block.curiosities.weavers_workbench.WeaversWorkbenchItemHandler;
import com.sammy.malum.common.block.storage.MalumItemHolderBlockEntity;
import com.sammy.malum.common.block.storage.pedestal.ItemPedestalItemDisplayData;
import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import com.sammy.malum.common.data.component.ItemSkinComponent;
import com.sammy.malum.registry.common.block.MalumBlockEntities;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import com.sammy.malum.registry.common.sound.MalumSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.block.BlockPosHelper;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;

public class MyriadGatewayBlockEntity extends MalumItemHolderBlockEntity {

    public MyriadGatewayBlockEntity(BlockPos pos, BlockState state) {
        super(MalumBlockEntities.MYRIAD_GATEWAY.get(), pos, state);
        inventory.attachDisplayData(MyriadGatewayDisplayData::new);
    }
}