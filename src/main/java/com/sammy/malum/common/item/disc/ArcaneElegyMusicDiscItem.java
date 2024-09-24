package com.sammy.malum.common.item.disc;

import net.minecraft.world.item.*;

public class ArcaneElegyMusicDiscItem extends Item {

    public ArcaneElegyMusicDiscItem(Properties builder) {
        super(builder.jukeboxPlayable(JukeboxSongs.BLOCKS));
    }
}