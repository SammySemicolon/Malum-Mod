package com.sammy.malum.core.systems.config;

import com.mojang.datafixers.util.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class SimpleConfig {

    @SuppressWarnings("rawtypes")
    public static final HashMap<Pair<String, String[]>, ArrayList<ConfigValueHolder>> VALUE_HOLDERS = new HashMap<>();

    public SimpleConfig(String type, ForgeConfigSpec.Builder builder) {
        VALUE_HOLDERS.forEach(((s, h) -> {
            if (s.getFirst().equals(type)) {
                builder.push(List.of(s.getSecond()));
                h.forEach(v -> v.value = v.valueSupplier.createBuilder(builder));
                builder.pop(s.getSecond().length);
            }
        }));

    }

    public static class ConfigValueHolder<T> {
        private final BuilderSupplier<T> valueSupplier;
        private ForgeConfigSpec.ConfigValue<T> value;

        public ConfigValueHolder(String path, BuilderSupplier<T> valueSupplier) {
            this.valueSupplier = valueSupplier;
            ArrayList<String> entirePath = new ArrayList<>(List.of(path.split("/")));
            String config = entirePath.remove(0);
            String[] newPath = entirePath.toArray(new String[]{});
            VALUE_HOLDERS.computeIfAbsent(Pair.of(config, newPath), (p) -> new ArrayList<>()).add(this);
        }

        public T get() {
            return value.get();
        }
    }

    public interface BuilderSupplier<T> {
        ForgeConfigSpec.ConfigValue<T> createBuilder(ForgeConfigSpec.Builder builder);
    }
}