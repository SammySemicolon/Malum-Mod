package com.sammy.malum.client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sammy.malum.core.handlers.HiddenItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class VoidRevelationHandler {

	private static final Gson GSON = new Gson();

	private static boolean checkedFile = false;
	private static boolean revelationState = false;

	@OnlyIn(Dist.CLIENT)
	private static Path getPath() {
		return Minecraft.getInstance().gameDirectory.toPath().resolve("malum_extra.json");
	}

	public static boolean hasSeenTheRevelation() {
		if (!checkedFile) {
			try {
				String s = Files.readString(getPath(), StandardCharsets.UTF_8);
				RevelationData data = GSON.fromJson(s, RevelationData.class);
				if (data != null)
					revelationState = data.hasSeenIt;
			} catch (IOException | JsonSyntaxException exception) {
				revelationState = false;
			}

			checkedFile = true;
		}

		return revelationState;
	}

	public static void seeTheRevelation() {
		if (!revelationState) {
			revelationState = true;
			try {
				RevelationData data = new RevelationData();
				data.hasSeenIt = true;

				Files.writeString(getPath(), GSON.toJson(data), StandardCharsets.UTF_8);
			} catch (IOException exception) {
				// NO-OP
			}

			HiddenItemHandler.conditionsChanged();
		}
	}

	private static class RevelationData {
		public boolean hasSeenIt;
	}
}
