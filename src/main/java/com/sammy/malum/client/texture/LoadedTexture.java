package com.sammy.malum.client.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.sammy.malum.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class LoadedTexture {

	protected final ResourceLocation readLocation;
	protected NativeImage image;

	public LoadedTexture(String readLocation) {
		this(MalumMod.malumPath(readLocation));
	}

	public LoadedTexture(ResourceLocation readLocation) {
		this.readLocation = readLocation.withPath(p -> p.endsWith(".png") ? p : p + ".png");
	}

	public void reload(ResourceManager manager) {
		var optional = manager.getResource(readLocation);

		if (optional.isEmpty()) {
			return;
		}

		try (var is = optional.get().open()) {
			image = NativeImage.read(NativeImage.Format.RGBA, is);

		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}