package com.sammy.malum.client.texture;

import com.sammy.malum.client.texture.palette.*;
import net.minecraft.server.packs.resources.*;
import net.neoforged.neoforge.client.event.*;

import java.util.*;

public final class TextureLoaderReloadListener implements ResourceManagerReloadListener {

	public static final TextureLoaderReloadListener DATA = new TextureLoaderReloadListener();

	private final ArrayList<LoadedTexture> textures = new ArrayList<>();

	public final LoadedDyePalettes woolColors = register(new LoadedDyePalettes("textures/palettes/wools", 8));

	public static void register(RegisterClientReloadListenersEvent event) {
		event.registerReloadListener(DATA);
	}

	@Override
	public void onResourceManagerReload(ResourceManager manager) {
		for (LoadedTexture texture : textures) {
			texture.reload(manager);
		}
	}

	public <T extends LoadedTexture> T register(T texture) {
		textures.add(texture);
		return texture;
	}
}