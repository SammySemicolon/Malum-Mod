package com.sammy.malum.core.data;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.common.book.pages.HeadlineTextPage;
import com.sammy.malum.core.modcontent.MalumBookCategories;
import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.entries.BookEntryGrouping;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.StartupEvents;
import com.sammy.malum.core.init.enchantments.MalumEnchantments;
import com.sammy.malum.core.modcontent.*;
import net.minecraft.block.Block;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.MalumItems.ITEMS;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;

public class MalumLangProvider extends LanguageProvider
{
    public MalumLangProvider(DataGenerator gen)
    {
        super(gen, MalumMod.MODID, "en_us");
    }
    
    @Override
    protected void addTranslations()
    {
        StartupEvents.registerModContents(null);
        MalumBookCategories.init();
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(MalumEnchantments.ENCHANTMENTS.getEntries());
        Set<RegistryObject<Effect>> effects = new HashSet<>(MalumEffects.EFFECTS.getEntries());
        ArrayList<MalumRites.MalumRite> rites = MalumRites.RITES;
        ArrayList<BookCategory> bookChapters = MalumBookCategories.CATEGORIES;
        MalumHelper.takeAll(items, i -> i.get() instanceof BlockItem);
        MalumHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        blocks.forEach(b -> {
            String name = b.get().getTranslationKey().replaceFirst("block.malum.", "");
            name = MalumHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(b.get().getTranslationKey(), name);
        });
        
        items.forEach(i -> {
            if (!(i.get() instanceof BlockItem))
            {
                String name = i.get().getTranslationKey().replaceFirst("item.malum.", "");
                name = MalumHelper.toTitleCase(specialBlockNameChanges(name), "_");
                add(i.get().getTranslationKey(), name);
            }
        });
        
        enchantments.forEach(e -> {
            String name = MalumHelper.toTitleCase(e.getId().getPath(), "_");
            add(e.get().getName(), name);
        });
        
        effects.forEach(e -> {
            String name = MalumHelper.toTitleCase(e.getId().getPath(), "_");
            add("effect.malum." + e.get().getRegistryName().getPath(), name);
        });
        rites.forEach(r -> add(r.translationKey, MalumHelper.toTitleCase(r.identifier, "_")));
        bookChapters.forEach(r -> {
            add(r.translationKey, MalumHelper.toTitleCase(r.translationKey.substring("malum.gui.book.chapter.".length()), "_"));
            for (BookEntryGrouping grouping : r.groupings)
            {
                for (BookEntry entry : grouping.entries)
                {
                    add(entry.translationKey, MalumHelper.toTitleCase(entry.translationKey.substring("malum.gui.book.entry.".length()), "_"));
                    for (BookPage page : entry.pages)
                    {
                        if (page instanceof HeadlineTextPage)
                        {
                            HeadlineTextPage textPage = (HeadlineTextPage) page;
                            add(textPage.headline, MalumHelper.toTitleCase(textPage.headline.substring("malum.gui.book.page.headline.".length()), "_"));
                        }
                    }
                }
            }
        });
        add("malum.gui.book.page.arcane_basics", "This is the codex item for Malum, since the mod is in beta I won't bother with fancy text and I'll just explain everything as is. If you think u can help me write proper text, message me on discord/twitter");
        add("malum.gui.book.page.soul_sand", "Here's some magical nonsense about soul sand. Soul sand is one of the few sources of spirit energy present outside a living body. Due to this cool fact you need soul sand to draw out spirit energy");
        add("malum.gui.book.page.redstone_dust", "Redstone, yay! Aside from being a great engineering component, it contains a little bit of magic.");
    
        add("malum.gui.book.page.runewood_trees", "Runewood trees are the cool fancy yellow trees in the mod. They have some magic in them, that's why they're important. Basically take an oak tree, shove some spirit energy into it and there u go.");
        add("malum.gui.book.page.sun_kissed_leaves", "The leaves of the runewood tree appear in a gradient. For building purposes you can change their color property by right clicking with glowstone");
        add("malum.gui.book.page.sun_kissed_grass", "Fancy yellow grass spawns around the runewood tree. Lavender can often spawn in said grass, use it to make purple dye and also the book.");
    
        add("malum.gui.book.page.arcane_charcoal", "Charcoal made from runewood logs actually becomes arcane charcoal. This cooler charcoal smelts twice as many items. This is due to the tree being all magic and stuff");
        add("malum.gui.book.page.blaze_quartz", "Malum adds a new ore to the nether, blaze quartz. It's a fuel, smelts 1.5x as many items as coal.");
    
        add("malum.gui.book.page.solar_sap", "Runewood trees sometimes have sap filled logs in them. Right click with a bottle to take sap out");
        add("malum.gui.book.page.solar_sap_again", "The second page of the solar sap thingymajig.");
        add("malum.gui.book.page.solar_syrup", "Sap + Furnace = Solar Syrup. This thing has cool recovering properties.");
        add("malum.gui.book.page.solar_sapball", "Usually in modded mc u have very little slimeballs and every mod seems to like slimeballs. With solar sap u can take your 1-2 slimeballs and turn them into 3-6 sapballs");
    
        add("malum.gui.book.page.unholy_blend", "Unholy blend is a cool crafting material made from redstone dust, soulsand and rotten flesh. I'll make some building blocks made from it at some point.");
        add("malum.gui.book.page.unholy_blend_again", "Right now this material is only used to make arcane grit. Shove the thing into a furnace and get your cool powder.");
    
        add("malum.gui.book.page.soul_gem", "Soul gem = Diamond + Arcane Grit. Das about it");
    
        add("malum.gui.book.page.spirit_basics", "Spirit energy is the special resource of malum. It's found mainly within living things. There's many types of spirits, each have a different entity criteria.");
        add("malum.gui.book.page.spirit_basics_part_two", "A buncha lore stuff will probably go here");
        add("malum.gui.book.page.spirit_basics_part_three", "Yeah. Lore stuff.");
    
        add("malum.gui.book.page.spirit_harvesting", "To get spirit energy you need to have a scythe. A scythe will create spirit splinters when you kill an entity. ");
        add("malum.gui.book.page.spirit_harvesting_part_two", "Scythes are powerful weapons too. Think of an axe, take away it's extra damage in exchange for AOE damage.");
        add("malum.gui.book.page.spirit_harvesting_part_three", "Scythes have cool enchantments also");
    
        add("malum.gui.book.page.spirit_altar", "Spirit energy won't ever be utilized in regular crafting. Instead use this thing, the spirit altar. Put in an item and up to 8 spirits and watch the thing do it's magic.");
    
        add("malum.gui.book.page.tainted_rock", "Tainted rock is made with life and magic. It's a cool building block and crafting material");
        add("malum.gui.book.page.darkened_rock", "Darkened rock is made with death and magic. It's also a cool building block and crafting material");
        add("malum.gui.book.page.runewood_replication", "If you want more runewood, you can convert your regular old wood using earth and magic spirits. Same applies for sun kissed grass.");
        add("malum.gui.book.page.bringing_forth_life", "If you want regular old grass, don't use magic.");
    
        add("malum.gui.book.page.life_spirit", "Life is found within pretty much everything that is considered 'alive and pure'. Animals, Villagers, Piglins. That kinda stuff");
        add("malum.gui.book.page.death_spirit", "Death is found within opposites of life, 'dead and ruined'. Zombies, Zomblified things, Illagers.");
        add("malum.gui.book.page.magic_spirit", "Magic is found in any wizard like mob and anything that is brought to life using magic, like a skeleton.");
        add("malum.gui.book.page.earth_spirit", "Earth; Anything associated with metal, the underground, that kinda stuff. ");
        add("malum.gui.book.page.fire_spirit", "Fire; Nether, immune to fire.");
        add("malum.gui.book.page.air_spirit", "Air, fast things, flying things, light things");
        add("malum.gui.book.page.water_spirit", "Water, anything aquatic really.");
        add("malum.gui.book.page.eldritch_spirit", "Rare powerful things, endermen, ravagers, bosses");
    
        add("malum.gui.book.page.hallowed_gold", "Gold but magic, made from earth fire air and water");
        add("malum.gui.book.page.spirit_jar", "Spirit jar; stores spirit splinters.");
    
        add("malum.gui.book.page.soul_stained_steel", "Iron but powerful-er.");
        add("malum.gui.book.page.soul_stained_gear", "Powerful metal = Powerful gear");
        add("malum.gui.book.page.basic_baubles", "Hallowed gold makes great curios, they provide a little bit of armor and are used as a basis for crafting other gear");
        add("malum.gui.book.page.poppet_belt", "The poppet belt lets you store poppets in it. To put a poppet in the belt simply hold said belt and desired poppet and right click. Up to two poppets can be stored");
    
        add("malum.gui.book.page.ether", "Ether is a pretty lightsource. U can put it on a torch and a brazier. ");
    
        add("malum.gui.book.page.elixir_of_life", "Yknow solar syrup, it heals u a bunch. This does that, but more.");
        add("malum.gui.book.page.void_berries", "These things were made cause I was pissed off with blood magic. Eating this thing will quickly get rid of a large chunk of your hunger.");
        add("malum.gui.book.page.abstruse_block", "This fancy cool block will return to it's owner after 5 seconds. This internal timer is reset if it's stepped on. Breaking it will also return it directly to you.");
        add("malum.gui.book.page.impervious_rock", "The impervious rock is completely immune to any and ALL explosions.");
        add("malum.gui.book.page.wither_sand", "Wither sand will damage anything on top of it.");
        add("malum.gui.book.page.unrelenting_belt", "Greatly reduces incoming explosion damage");
        add("malum.gui.book.page.archangel_ring", "Reduces gravity and increases movement speed");
        add("malum.gui.book.page.living_capacitor", "When receiving the hunger potion effect you will instead recover hunger and health. Also triggers when eating void berries");
        add("malum.gui.book.page.stronghold_armor", "Soul stained stronghold armor. It's big and it's tanky, you love to see it.");
        
        add("malum.gui.book.page.voodoo_magic", "Voodoo magic is one of many sinister magics in malum. It revolves around poppets, AKA cool voodoo dolls.");
        add("malum.gui.book.page.voodoo_magic_again", "If you want a poppet to take effect, hold it in either hand or slot it in an equipped poppet belt.");
        add("malum.gui.book.page.offensive_poppets", "Poppets are split into two groups: offensive and defensive. Offensive poppets will take effect on any harm inflicted on you.");
        add("malum.gui.book.page.poppet_of_vengeance", "Enemies that attack you will take damage");
        add("malum.gui.book.page.poppet_of_defiance", "Enemies that attack you with magic damage take 50% of the damage dealt. You take 50% less.");
        add("malum.gui.book.page.poppet_of_misfortune", "This doll will cause knockback to anything that attacks you. It's meant to be annoying <3");
        add("malum.gui.book.page.poppet_of_sapping", "This thing will inflict a buncha random debuffs onto whatever attacks you.");
        add("malum.gui.book.page.defensive_poppets", "The second category of poppets are defensive poppets. They take effect on you when harm is inflicted");
        add("malum.gui.book.page.poppet_of_shielding", "Take less damage.");
        add("malum.gui.book.page.poppet_of_infernal_protection", "No fire damage");
        add("malum.gui.book.page.poppet_of_terran_protection", "Less fall damage");
        add("malum.gui.book.page.poppet_of_undying", "Take totem of undying, take poppet, mash em together and now u can put one in your poppet belt.");
    
        add("malum.gui.book.page.totem_magic", "Totem magic is pretty much the ritual side of things in malum. Using a totem core with runewood logs placed atop you can perform rites.");
        add("malum.gui.book.page.totem_magic_part_two", "Each totem rite has a combination of spirits that identify it. These spirits need to be carved onto the mentioned runewood logs. To do so simply right-click the wood with a spirit splinter");
        add("malum.gui.book.page.totem_magic_part_three", "A totem rite can either be an instant or prolonged effect. You can deactivate a rite by sneaking and right clicking the totem core with an empty hand.");
        add("malum.gui.book.page.poppet_blessings", "You can bless a poppet with the effect of a rite. To do so simply drop a poppet near a totem before the desired rite activates. The rite effect on the poppet is weaker in exchange for being portable.");
    
        add("malum.gui.book.page.rite_of_life", "Increases max health in a radius");
        add("malum.gui.book.page.rite_of_death", "Increases damage output in a radius");
        add("malum.gui.book.page.rite_of_magic", "Grants knockback immunity in a radius");
        add("malum.gui.book.page.rite_of_earth", "Grants some armor points in a radius");
        add("malum.gui.book.page.rite_of_fire", "Grants increased attack and dig speed in a radius");
        add("malum.gui.book.page.rite_of_air", "Increases movement speed in a radius");
        add("malum.gui.book.page.rite_of_water", "Increases swim speed in a radius");
        add("malum.gui.book.page.rite_of_growth", "Grows nearby plants at the same Y level as the totem core");
        add("malum.gui.book.page.rite_of_drought", "An instant rite that clears weather");
        add("malum.gui.book.page.rite_of_rain", "An instant rite that triggers a thunderstorm");
    
        add("malum.subtitle.tainted_rock_break", "Tainted Rock Broken");
        add("malum.subtitle.tainted_rock_step", "Tainted Rock Footsteps");
        add("malum.subtitle.tainted_rock_place", "Tainted Rock Placed");
        add("malum.subtitle.tainted_rock_hit", "Tainted Rock Breaking");
    
        add("malum.subtitle.transmissive_alloy_break", "Transmissive Alloy Broken");
        add("malum.subtitle.transmissive_alloy_step", "Transmissive Alloy Footsteps");
        add("malum.subtitle.transmissive_alloy_place", "Transmissive Alloy Placed");
        add("malum.subtitle.transmissive_alloy_hit", "Transmissive Alloy Breaking");
    
        add("malum.subtitle.spirited_steel_break", "Spirited Steel Broken");
        add("malum.subtitle.spirited_steel_step", "Spirited Steel Footsteps");
        add("malum.subtitle.spirited_steel_place", "Spirited Steel Placed");
        add("malum.subtitle.spirited_steel_hit", "Spirited Steel Breaking");
    
        add("malum.subtitle.abstruse_block_return", "Abstruse Block Returns");
        add("malum.subtitle.scythe_strike", "Scythe Cuts");
        add("malum.subtitle.spirit_harvest", "Spirit Harvested");
    
        add("malum.subtitle.totem_charge", "Totem Charges");
        add("malum.subtitle.totem_complete", "Totem Fully Charges");
        
        add("death.attack.bleeding", "%1$s bled out");
        add("death.attack.bleeding.player", "%1$s bled out whilst trying to escape %2$s");
        
        addTooltip("contains", "Contains: ");
        addTooltip("sneak", "Sneak");
        addTooltip("hold", "Hold: ");
        addTooltip("slot", "Slot");
        addTooltip("spirit", "Spirit");
        addTooltip("fuel", "Fuel: ");
        addTooltip("progress", "Progress: ");
        
        add("malum.jei.spirit_infusion", "Spirit Infusion");
        add("malum.jei.runic_chiseling", "Runic Chiseling");
        add("malum.jei.totem_rites", "Totem Rites");
        add("itemGroup.malum", "Malum");
        add("itemGroup.malum_building_blocks", "Malum Building Blocks");
    }
    
    @Override
    public String getName()
    {
        return "Malum Lang Entries";
    }
    
    public void addTooltip(String identifier, String tooltip)
    {
        add("malum.tooltip." + identifier, tooltip);
    }
    
    public String specialBlockNameChanges(String name)
    {
        if ((!name.endsWith("_bricks")))
        {
            if (name.contains("bricks"))
            {
                name = name.replaceFirst("bricks", "brick");
            }
        }
        if (name.contains("_fence") || name.contains("_button"))
        {
            if (name.contains("planks"))
            {
                name = name.replaceFirst("_planks", "");
            }
        }
        return name;
    }
}