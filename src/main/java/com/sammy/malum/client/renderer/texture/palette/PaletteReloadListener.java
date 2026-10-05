package com.sammy.malum.client.renderer.texture.palette;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public final class PaletteReloadListener implements ResourceManagerReloadListener {

	public static final PaletteReloadListener INSTANCE = new PaletteReloadListener();

	private PaletteReloadListener() {
	}

	@Override
	public void onResourceManagerReload(ResourceManager manager) {
		FlwPrograms.reload(manager);
		NoiseTextures.reload(manager);
	}
}