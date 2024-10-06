import com.sammy.malum.client.renderer.block.SpiritCrucibleRenderer;
import com.sammy.malum.client.renderer.block.TotemBaseRenderer;
import com.sammy.malum.core.handlers.SoulWardHandler;

@SubscribeEvent
public static void clientTickEvent(TickEvent.ClientTickEvent event) {
    SpiritCrucibleRenderer.checkForTuningFork(event);
    CurioHiddenBladeNecklace.ClientOnly.tick(event);
    SoulWardHandler.ClientOnly.tick(event);
    TotemBaseRenderer.checkForTotemicStaff(event);
}