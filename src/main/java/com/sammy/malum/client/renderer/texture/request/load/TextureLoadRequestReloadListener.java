package com.sammy.malum.client.renderer.texture.request.load;

import dev.latvian.mods.kubejs.client.*;
import net.minecraft.server.packs.resources.*;
import net.neoforged.neoforge.event.*;

import java.util.*;

public final class TextureLoadRequestReloadListener implements ResourceManagerReloadListener {

	public static final TextureLoadRequestReloadListener DATA = new TextureLoadRequestReloadListener();

	private final ArrayList<NativeTextureLoadRequest> textures = new ArrayList<>();

	public final NativeTextureLoadRequest woolColors = register(new DyePalettesLoadRequest("textures/palettes/wools", 8));

	public static void register(AddReloadListenerEvent event) {
		event.addListener(DATA);
	}

	@Override
	public void onResourceManagerReload(ResourceManager manager) {
		for (NativeTextureLoadRequest texture : textures) {
			texture.reload(manager);
		}
	}

	public NativeTextureLoadRequest register(NativeTextureLoadRequest texture) {
		textures.add(texture);
		return texture;
	}
}