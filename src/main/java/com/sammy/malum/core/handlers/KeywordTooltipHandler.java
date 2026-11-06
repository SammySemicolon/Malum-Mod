package com.sammy.malum.core.handlers;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.helpers.TooltipComponentHelper;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class KeywordTooltipHandler {

    //Addon makers please add your mod ids here pretty please :3333
    public static List<String> ALLOWED_MODS = new ArrayList<>(List.of(MalumMod.MALUM));
    public static List<TooltipKeyword> KEYWORDS = new ArrayList<>();

    public static final TooltipKeyword SOUL_WARD = addKeyword("soul_ward");

    public static final TooltipKeyword GLUTTONY = addKeyword("gluttony");
    public static final TooltipKeyword AVARICE = addKeyword("avarice");

    public static final TooltipKeyword ARCANE_RESONANCE = addKeyword("arcane_resonance");

    public static final TooltipKeyword TRIAL_OF_FAITH = addKeyword("trial_of_faith");
    public static final TooltipKeyword DESPERATE_NEED = addKeyword("desperate_need");

    public static void addKeywords(ItemTooltipEvent event) {
        var stack = event.getItemStack();
        var modName = stack.getItemHolder().getKey().location().getNamespace();
        if (!ALLOWED_MODS.contains(modName)) {
            return;
        }
        var tooltip = event.getToolTip();
        final var presentKeywords = getPresentKeywords(tooltip);
        for (TooltipKeyword presentKeyword : presentKeywords) {
            int index = tooltip.size();
            if (event.getFlags().isAdvanced()) {
                index--;
            }
            tooltip.add(index, TooltipComponentHelper.effectKeyword(presentKeyword));
        }
    }

    private static @NotNull ArrayList<TooltipKeyword> getPresentKeywords(List<Component> tooltip) {
        var presentKeywords = new ArrayList<TooltipKeyword>();
        for (TooltipKeyword keyword : KEYWORDS) {
            for (Component component : tooltip) {
                var raw = component.getString().toLowerCase();

                if (raw.contains(keyword.name)) {
                    presentKeywords.add(keyword);
                    break;
                }
            }
        }
        if (presentKeywords.contains(TRIAL_OF_FAITH) || presentKeywords.contains(DESPERATE_NEED)) {
            presentKeywords.remove(GLUTTONY);
        }
        return presentKeywords;
    }

    public static TooltipKeyword addKeyword(String id) {
        var keyword = new TooltipKeyword(id);
        KEYWORDS.add(keyword);
        return keyword;
    }

    public record TooltipKeyword(String id, String name) {

        public TooltipKeyword(String id) {
            this(id, id.replaceAll("_", " "));
        }

        public String getLangKey() {
            return "malum.effect.keyword." + id;
        }
    }
}
