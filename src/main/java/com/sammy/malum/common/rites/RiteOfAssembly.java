package com.sammy.malum.common.rites;

import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;

import static com.sammy.malum.core.modcontent.MalumSpiritTypes.*;

public abstract class RiteOfAssembly extends MalumRiteType
{
    public MalumSpiritType assemblyType;
    public RiteOfAssembly(MalumSpiritType assemblyType, String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
        this.assemblyType = assemblyType;
    }

    public interface IAssembled
    {
        public void assemble(RiteOfAssembly assemblyType);
    }
}
