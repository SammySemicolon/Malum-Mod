package com.sammy.malum.registry.client;

import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import static com.sammy.malum.MalumMod.malumPath;
import static team.lodestar.lodestone.LodestoneLib.lodestonePath;

public class MalumRenderTypeTokens {

    public static final RenderTypeToken CONCENTRATED_TRAIL = RenderTypeToken.createToken(malumPath("textures/vfx/concentrated_trail.png"));
    public static final RenderTypeToken TWINKLE = RenderTypeToken.createToken(lodestonePath("textures/particle/twinkle.png"));
    public static final RenderTypeToken STAR = RenderTypeToken.createToken(malumPath("textures/particle/star.png"));

}
