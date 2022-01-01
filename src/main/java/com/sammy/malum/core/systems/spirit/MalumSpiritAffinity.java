package com.sammy.malum.core.systems.spirit;

public class MalumSpiritAffinity {
    public final String identifier;

    public MalumSpiritAffinity(MalumSpiritType type) {
        this.identifier = type.identifier + "_affinity";
    }
    public MalumSpiritAffinity(String identifier) {
        this.identifier = identifier;
    }
}
