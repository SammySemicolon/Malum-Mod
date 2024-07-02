package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.common.item.spirit.SpiritShardItem;

import java.awt.*;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class SpiritTypeBuilder {

    public final String identifier;
    public final Supplier<SpiritShardItem> spiritShard;

    public final SpiritVisualMotif spiritVisualMotif;

    public Color itemColor;

    public SpiritTypeBuilder(String identifier, SpiritVisualMotif spiritVisualMotif, Supplier<SpiritShardItem> spiritShard) {
        this.identifier = identifier;
        this.spiritVisualMotif = spiritVisualMotif;
        this.spiritShard = spiritShard;
    }

    public SpiritTypeBuilder setItemColor(Function<SpiritVisualMotif, Color> colorFunction) {
        return setItemColor(colorFunction.apply(spiritVisualMotif));
    }

    public SpiritTypeBuilder setItemColor(Color itemColor) {
        this.itemColor = itemColor;
        return this;
    }

    public MalumSpiritType build() {
        return build((identifier1, spiritShard1, visualMotif, itemColor1) -> new MalumSpiritType(identifier1, visualMotif, spiritShard1, itemColor1));
    }

    public<T extends MalumSpiritType> T  build(SpiritTypeSupplier<T> supplier) {
        return supplier.makeType(identifier, spiritShard, spiritVisualMotif, itemColor);
    }

    public interface SpiritTypeSupplier<T extends MalumSpiritType> {
        T makeType(String identifier, Supplier<SpiritShardItem> spiritShard, SpiritVisualMotif visualMotif, Color itemColor);
    }
}
