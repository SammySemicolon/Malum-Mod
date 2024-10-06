import com.sammy.malum.core.handlers.SoulWardHandler;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;

@SubscribeEvent
public static void registerOverlays(RegisterGuiOverlaysEvent event) {
    event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "soul_ward", (gui, poseStack, partialTick, width, height) ->
            SoulWardHandler.ClientOnly.renderSoulWard(gui, poseStack, width, height));
    event.registerAboveAll("hidden_blade_cooldown", (gui, poseStack, partialTick, width, height) ->
            CurioHiddenBladeNecklace.ClientOnly.renderHiddenBladeCooldown(gui, poseStack, width, height));
    event.registerAbove(VanillaGuiOverlay.PLAYER_LIST.id(), "touch_of_darkness", (gui, poseStack, partialTick, width, height) ->
            TouchOfDarknessHandler.ClientOnly.renderDarknessVignette(poseStack));
}