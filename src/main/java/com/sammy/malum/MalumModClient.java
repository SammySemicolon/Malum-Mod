package com.sammy.malum;

import com.sammy.malum.config.ClientConfig;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import net.fabricmc.api.ClientModInitializer;

public class MalumModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigRegistry.registerConfig(MalumMod.MALUM, ConfigType.CLIENT, ClientConfig.SPEC);
    }
}
