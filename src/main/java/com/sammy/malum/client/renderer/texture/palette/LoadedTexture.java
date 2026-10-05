package com.sammy.malum.client.renderer.texture.palette;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class LoadedTexture {

	private final ResourceLocation readLocation;
	private NativeImage image;

	public LoadedTexture(ResourceLocation readLocation) {
		this.readLocation = readLocation.withPath(p -> p.endsWith(".png") ? p : p + ".png");
	}

	public void reload(ResourceManager manager) {
		var optional = manager.getResource(readLocation);

		if (optional.isEmpty()) {
			return;
		}

		try (var is = optional.get().open()) {
			image = NativeImage.read(NativeImage.Format.LUMINANCE, is);

		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}