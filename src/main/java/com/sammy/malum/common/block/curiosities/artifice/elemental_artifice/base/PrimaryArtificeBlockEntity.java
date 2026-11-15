package com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.base;

import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.ArtificeBlockConnectionData;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.SequencedBlockConnectionArray;
import com.sammy.malum.common.block.curiosities.artifice.ArtificeTinkeringInfo;
import com.sammy.malum.core.handlers.WindTunnelHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntityType;

import java.util.HashSet;

public abstract class PrimaryArtificeBlockEntity extends ElementalArtificeBlockEntity {

    protected ArtificeBlockConnectionData connectionData;

    public PrimaryArtificeBlockEntity(LodestoneBlockEntityType<? extends PrimaryArtificeBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract ArtificeBlockConnectionData bakeConnectionData(ServerLevel level, SequencedBlockConnectionArray array);

    public abstract void activate(ServerLevel level, boolean powered);

    public abstract void gatherConnectionData(ServerLevel level, HashSet<SecondaryArtificeBlockEntity> connectedBlocks);

    public abstract void clearConnectionData();

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        connectionData = ArtificeBlockConnectionData.CODEC.parse(NbtOps.INSTANCE, tag.get("connection_data")).result().orElse(null);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (connectionData != null) {
            ArtificeBlockConnectionData.CODEC.encodeStart(NbtOps.INSTANCE, connectionData).result().ifPresent(nbt -> tag.put("connection_data", nbt));
        }
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if (level instanceof ServerLevel serverLevel) {
            WindTunnelHandler.modifyComponents(serverLevel, this, false, false);
        }
    }

    @Override
    public void handleTinkeredStateChange(ServerLevel level, boolean openValue, ArtificeTinkeringInfo artificeTinkeringInfo) {
        super.handleTinkeredStateChange(level, openValue, artificeTinkeringInfo);
        WindTunnelHandler.modifyComponents(level, this, openValue, ElementalArtificeBlock.isPowered(getBlockState()));
    }

    public final void setConnectionData(ArtificeBlockConnectionData connectionData) {
        this.connectionData = connectionData;
        setDirty();
    }

    public ArtificeBlockConnectionData getConnectionData() {
        return connectionData;
    }
}