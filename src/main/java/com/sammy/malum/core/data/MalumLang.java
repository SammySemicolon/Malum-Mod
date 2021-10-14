package com.sammy.malum.core.data;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.cooler_book.BookEntry;
import com.sammy.malum.client.screen.cooler_book.ProgressionBookScreen;
import com.sammy.malum.common.block.ether.WallEtherTorchBlock;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.enchantment.MalumEnchantments;
import com.sammy.malum.core.init.MalumRites;
import com.sammy.malum.core.mod_systems.rites.MalumRiteType;
import net.minecraft.block.Block;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.items.MalumItems.ITEMS;
import static com.sammy.malum.core.init.MalumSounds.SOUNDS;
import static com.sammy.malum.core.init.block.MalumBlocks.BLOCKS;

public class MalumLang extends LanguageProvider
{
    public MalumLang(DataGenerator gen)
    {
        super(gen, MalumMod.MODID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
        MalumRites.init();
        ProgressionBookScreen.setupEntries();
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SOUNDS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(MalumEnchantments.ENCHANTMENTS.getEntries());
        Set<RegistryObject<Effect>> effects = new HashSet<>(MalumEffects.EFFECTS.getEntries());
        ArrayList<BookEntry> coolerBookEntries = ProgressionBookScreen.entries;
        ArrayList<MalumRiteType> rites = MalumRites.RITES;
        MalumHelper.takeAll(items, i -> i.get() instanceof BlockItem);
        MalumHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        MalumHelper.takeAll(blocks, i -> i.get() instanceof WallEtherTorchBlock);
        MalumHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        blocks.forEach(b ->
        {
            String name = b.get().getTranslationKey().replaceFirst("block.malum.", "");
            name = MalumHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(b.get().getTranslationKey(), name);
        });

        items.forEach(i ->
        {
            String name = i.get().getTranslationKey().replaceFirst("item.malum.", "");
            name = MalumHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(i.get().getTranslationKey(), name);
        });

        sounds.forEach(s -> {
            String name = MalumHelper.toTitleCase(s.getId().getPath(), "_");
            add("malum.subtitle." + s.getId().getPath(), name);
        });
        enchantments.forEach(e -> {
            String name = MalumHelper.toTitleCase(e.getId().getPath(), "_");
            add(e.get().getName(), name);
        });

        effects.forEach(e -> {
            String name = MalumHelper.toTitleCase(e.getId().getPath(), "_");
            add("effect.malum." + e.get().getRegistryName().getPath(), name);
        });

        rites.forEach(r -> add("malum.gui.rite." + r.identifier, MalumHelper.toTitleCase(r.identifier, "_")));

        coolerBookEntries.forEach(b -> add(b.translationKey(), MalumHelper.toTitleCase(b.identifier, "_")));



        addHeadline("introduction", "Introduction");
        addDescription("introduction", "Soul magic awaits");
        addPage("introduction_a", "Within this world, every living body has a soul. The body is a physical vessel that the soul occupies. The soul is one's consciousness and what animates the body. It is an arcane representation of who you are.");
        addPage("introduction_b", "The encyclopedia arcana is a book meant to explain everything about the soul and how to potentially utilize various soul magics. Even though a soul is an arcane thing, performing soul magic is not easy.");
        addPage("introduction_c", "To properly utilize soul magics you're gonna need to separate the desired magic elements from all the other junk found within a soul. These magic elements are known as spirits, magical fragments of potential.");
        addPage("introduction_d", "The magical presence of a soul derives entirely from the spirits found within it. Each soul is made unique by what spirits it holds, it's safe to assume that the soul is only alive due to them too!");

        addHeadline("spirit_magics", "Spirit Magics");
        addDescription("spirit_magics", "Soul fragments");
        addPage("spirit_magics_a", "\"What exactly is a spirit?\" You might ask yourself. Spirits are raw, untainted magic. There are also many spirit types, all with unique uses and abilities. Perfect for your evil needs.");
        addPage("spirit_magics_b", "Each soul contains a specific set of spirits, different souls contain different combinations of spirits! When hunting for spirits you'll have to carefully think about which souls will have what you need.");
        addPage("spirit_magics_c", "When hunting spirits it is important to not leave any of them behind. Uncollected spirits will slowly dissolve into the world around and alter it into a new twisted one. Many natural wonders you may find were created this way!");

        addHeadline("soulstone", "Soulstone");
        addDescription("soulstone", "Altered minerals");
        addPage("soulstone_a", "The soulstone is a strange ore found both on the surface and in the deepest ends of every cave. The ore itself was created from carelessness, when a dissolved spirit merges with ore it'll shape the mineral to be something new. Soulstone is extremely valuable to any soul hunter.");
        addPage("soulstone_b", "Soulstone is very precious due to it's ability to resonate and mangle with souls and spirits in various ways. This unique ability of the soulstone makes it the perfect mineral to create soul hunting equipment out of. Any weapon made out of soulstone is most certainly sufficient for damaging an opponent's soul.");

        addHeadline("runewood", "Runewood");
        addDescription("runewood", "Arcane oak");
        addPage("runewood_a", "The runewood tree is yet another result of spirits dissolving into nature. These trees can be most frequently found within plains, while also rarely appearing in forests.");
        addPage("runewood_b", "When infernal and arcane spirits are infused into oak the properties of the wood will be altered and runewood will be created. This volatile process is how runewood trees are created, with the right tools you'll be able to replicate it!");
        addHeadline("holy_extract", "Holy Sap");
        addPage("holy_extract_a", "Within the runewood trees a special type of sap is born. To get your hands on this holy extract you'll need to strip away the bark off of exposed logs and then bottle up all the sap you can get!");
        addPage("holy_extract_b", "Holy extract can be used for quite a few things. Firstly, by combining it with a slimeball you'll obtain sapballs, effectively providing you with three times as much slime!");
        addPage("holy_extract_c", "Secondly, you can create holy syrup by heating up your holy extract in a furnace. Drinking this rejuvenating drink will replenish plenty of hunger and clear you of certain negative effects. Additionally if you drink it during day time health will also be restored and you'll gain a regenerative effect.");
        addPage("holy_extract_d", "Try your best to not think about reverting sapballs into slimeballs using a certain expansion mod!");

        addHeadline("scythes", "Scythes!");
        addDescription("scythes", "Basic soul shattering");
        addPage("scythes_a", "A scythe is a weapon of magic origin designed for cutting through hordes of enemies. The axe exchanges attack speed for extra damage, the scythe on the other hand gains crowd control. Any attack done with the scythe will count as a stronger version of a sword's sweep attack.");
        addPage("scythes_b", "The scythe is also the most important tool of a spirit hunter, it's excellence at crowd control combined with it's ability to shatter souls makes it the perfect weapon for hunting spirits.");
        addPage("scythes_c", "The simplest scythe you can make uses iron for it's blade and a basic soulstone as a reagent. The scythe also accepts many exclusive enchantments!");

        addHeadline("haunted", "Haunted");
        addPage("haunted", "The haunted enchantment utilizes magic created when a soulstone is brought close to a hurt soul to deal extra magic damage to anyone you strike! Higher tiers hit with even stronger magic");

        addHeadline("spirit_plunder", "Spirit Plunder");
        addPage("spirit_plunder", "The spirit plunder enchantment forces the scythe's soulstone to utilize even more magic, any soul you shatter with this enchanted scythe will yield extra spirits. This may however damage the soulstone, taking extra durability off of your scythe.");

        addHeadline("rebound", "Rebound");
        addPage("rebound", "The rebound enchantment is by far the strangest one of them all. It grants you the ability to throw the scythe like a boomerang, allowing for a wacky ranged attack. Higher tiers allow you to throw the scythe more frequently.");

        addHeadline("sacred_spirit", "Sacred Spirit");
        addPage("sacred_spirit_a", "Sacred spirit represents two things, the pure and the holy. Magic derived from sacred spirits is generally focused on various forms of healing and other positive effects.");
        addPage("sacred_spirit_b", "When you're in need of a specific spirit you'll need to do some thinking. It'd be boring if the solution was handed out to you right away! Each soul has very specific spirits and their types depend on what kind of soul it is. The sacred spirit for example is found mainly within passive, relaxed souls.");

        addHeadline("wicked_spirit", "Wicked Spirit");
        addPage("wicked_spirit_a", "Opposite of the sacred, wicked spirit represents various impurities and dark magics. Even just touching this spirit creates a mild pain. Wicked magics can be really dangerous in the right hands.");
        addPage("wicked_spirit_b", "Generally when a body dies, the soul peacefully and slowly fades out alongside it. However, if the body is to be brought back to life through necromancy or some other reanimating magic just seconds after death the soul may be brought back! Through this process the soul is implanted with wicked spirit and overtime becomes one with it.");

        addHeadline("arcane_spirit", "Arcane Spirit");
        addPage("arcane_spirit_a", "The arcane spirit is magic in it's purest form. Arcane spirit is often needed to fully utilize the potential stored within other spirits, it has little power alone but can greatly amplify other magic.");
        addPage("arcane_spirit_b", "Spirits found in a soul aren't always set in stone when a vessel is created. For example, it is rumored that the very first illagers were villagers who broke their oath and turned evil. If that is the case, their soul would've adapted and became completely wicked.");
        addPage("arcane_spirit_c", "A similar process most likely happened when a apprentice witch became proficient enough in magics. Souls can develop spirits over a long period of time based on how the body reacts to magic. This is very often the case with arcane spirit due to how pure and frequent it is in the world. Arcane Spirit is found within the supernatural.");

        addHeadline("spirit_infusion", "Spirit Infusion");
        addPage("spirit_infusion_a", "Spirit infusion is a crafting process that revolves around infusing spirit magic into various items, twisting them to fit your evil desires. Every infusion recipe requires a prime item as well as spirits to infuse the item with. These must all be placed right on the altar.");
        addPage("spirit_infusion_b", "Some recipes may also ask for additional reagents to be infused into the prime item. These extra items must be placed on nearby item holders, such as ones made from runewood. These can be placed anywhere as long as they're within 4 blocks of the altar.");
        addPage("spirit_infusion_c", "Once everything is setup correctly, spirit infusion will begin and one by one each additional ingredient will be absorbed into the prime item alongside needed spirits. Once this lengthy process is done your desired item will be created!");
        addHeadline("hex_ash", "Hex Ash");
        addPage("hex_ash", "Hex ash is a simple magical powder used in creating various magical items. The ash can animate things, bring them to life in some extreme cases even. It's crated by infusing gunpowder with arcane spirit.");

        addHeadline("tainted_rock", "Tainted Rock");
        addPage("tainted_rock", "By infusing cobblestone with spirit magics we can create a much more building-friendly arcane rock. Tainted rock is a simple magical building block with quite a few neat usages, it can also be shaped into item holders.");
        addHeadline("twisted_rock", "Twisted Rock");
        addPage("twisted_rock", "If we are to replace sacred spirit with it's opposite the result will change to a more gloomy stone. Twisted rock is indifferent from it's holy variant apart from looks.");

        addHeadline("ether", "Ether");
        addPage("ether_a", "Nowadays most magics offer you a unique magical source of light for all purpose use, spirit magics are no different. Ether, alternatively known as Nitor is a magical flame that burns and shines bright forever. It can be placed on a torch or an arcane rock brazier which can be placed or hanged.");
        addPage("ether_b", "Additionally, the ether flame can be dyed into practically any color. Simply combine the ether item with any combination of dyes and it's color will be altered! For various reasons monochromatic dyes may yield possibly undesirable results without other dyes mixed in.");

        addHeadline("iridescent_ether", "Iridescent Ether");
        addPage("iridescent_ether_a", "In contrast to most other magical lights aside from pleasing the eye ether has a double flame colored variant, iridescent ether. This pristine form of ether allows you to dye the item once more to alter the ending color of the burn. Much like normal ether it can be placed on a brazier and a torch");
        addPage("iridescent_ether_b", "Getting just the right coloring for your light may be a bit tricky however. You cannot alter the original color of ether once it's transformed into it's iridescent variant, applying any dye at this stage will only change the second color.");

        addHeadline("earthen_spirit", "Earthen Spirit");
        addPage("earthen_spirit_a", "Earthen magic can be seen as two separate things merged into one, nature and strength. The magic is incredibly potent even when used in small amounts!");
        addPage("earthen_spirit_b", "With the power of the earth you can do quite a lot. For one, earthen spirit is perfect for creating strong and durable gear. For two, it's natural properties open up many opportunities in spell crafting. Earthen spirit is found within the strong and the giving.");
        addHeadline("infernal_spirit", "Infernal Spirit");
        addPage("infernal_spirit_a", "Hellish magic is incredibly dangerous. PLaying with fire is never a good idea especially when spirit magic is involved.");
        addPage("infernal_spirit_b", "The forever on fire hellish nether, it's a surprise that life exists there. Infernal spirit derives almost entirely from the anomaly that is the nether and everything that lives in it. Certain souls found within the overworld also hold infernal spirit due to their explosive nature or a strong fire resistance.");
        addHeadline("aerial_spirit", "Aerial Spirit");
        addPage("aerial_spirit_a", "Apart from being convenient, mobility is very important. Aerial magic is all about agility.");
        addPage("aerial_spirit_b", "Aerial magics, light and versatile, weak in effect yet excel in utility. Any nimble body is bound to have or develop aerial spirit within their soul.");
        addHeadline("aquatic_spirit", "Aquatic Spirit");
        addPage("aquatic_spirit_a", "Similar to the earthen, aquatic magics are immensely powerful. On top of that, the oceans in this world seem to have lots of spirit unrelated magics already within them!");
        addPage("aquatic_spirit_b", "Aquatic magic derives a lot from various ocean wonders already present in the world. Ideas such as channeling or loyalty are one with the seas and everything within. Aquatic spirit provides similar powers.");

        addHeadline("spirit_fabric", "Spirit Fabric");
        addPage("spirit_fabric", "Spirit fabric is a wicked type of weave used for various spirit related crafting processes.");
        addHeadline("soul_hunter_armor", "Soul Hunter Armor");
        addPage("soul_hunter_armor", "The soul hunter armor is an offensive armor set made from spirit fabric. At the cost of below average protection the armor set boosts magic and scythe damage and grants you an extra spirit per spirit harvest.");
        addHeadline("spirit_pouch", "Spirit Pouch");
        addPage("spirit_pouch", "If you've ever wished for a spirit backpack of sorts, the spirit pouch is just that! The item allows you to store a huge amount of spirits within it's internal inventory. All collected spirits are transferred into the pouch if it's in your inventory!");

        addHeadline("hallowed_gold", "Hallowed Gold");
        addPage("hallowed_gold_a", "Gold is very often used as a basis for various magics, this is also the case with spirit magic. Infusing arcane and sacred spirits into a gold ingot will imbue it with magic and yield a much more desirable magic metal. A few additional reagents are also needed for this process.");
        addPage("hallowed_gold_b", "While not too useful in causing harm or crafting powerful gear, hallowed gold is a metal perfect for spirit manipulation.");
        addHeadline("spirit_jar", "Spirit Jar");
        addPage("spirit_jar", "The spirit jar is a simple hallowed gold craft. It's a placeable jar block that can store a really large amount of a single spirit, very convenient to have next to a spirit altar. You can take spirits out by right clicking, sneaking will take out an entire stack.");
        addHeadline("soul_stained_steel", "Soul Stained Steel");
        addPage("soul_stained_steel_a", "The holy origins of hallowed gold make it nearly impossible to use for harm. Soul stained steel is nothing like that, it's a metal twisted evil beyond recognition. It excels at harmful things, perfect for various gear and trinkets.");
        addPage("soul_stained_steel_b", "Both metals can also be used to create a type of spirit resonator, a more complex crafting component meant for utilizing spirit magics in various ways.");

        addHeadline("soul_stained_scythe", "Soul Stained Scythe");
        addPage("soul_stained_scythe", "After some time using the crude scythe you may start wishing for an upgrade or a stronger version, just like the one made from soul stained steel. The soul stained steel scythe is a direct upgrade with slightly increased physical damage and a bonus to magic damage triggered by the scythe.");
        addHeadline("soul_stained_armor", "Soul Stained Armor");
        addPage("soul_stained_armor", "Just like how you can upgrade an iron scythe you're able to create soul stained steel armor. In addition to providing near diamond levels of protection the armor also shields you from magic damage. Additionally while wearing the full set collecting spirits will grant you even more defense!");

        addHeadline("spirit_trinkets", "Spirit Trinkets");
        addPage("spirit_trinkets_a", "A trinket is a simple accessory that will provide you with helpful benefits, some know them as baubles or curios. Hallowed gold and soul stained steel both offer two basic trinkets that can be shaped into many more using the spirit altar.");
        addPage("spirit_trinkets_b", "Gilded trinkets provide boosts to armor while ornate ones grant you armor toughness.");

        add("malum.jei.spirit_infusion", "Spirit Infusion");
        add("malum.jei.spirit_rites", "Spirit Rites");
        add("malum.jei.rune_table", "Rune Table");

        add("itemGroup.malum", "Malum");
        add("itemGroup.malum_shaped_stones", "Malum Shaped Stones");
        add("itemGroup.malum_spirits", "Malum Spirits");
        add("itemGroup.malum_natural_wonders", "Malum Natural Wonders");

        add("enchantment.malum.haunting.desc", "Attacking enemies deals extra magic damage.");
        add("enchantment.malum.rebound.desc", "Allows the scythe to be thrown much like a boomerang, cooldown decreases with tier.");
        add("enchantment.malum.spirit_plunder.desc", "Increases spirit yields from each successful spirit harvest.");
    }

    @Override
    public String getName()
    {
        return "Malum Lang Entries";
    }

    public void addPage(String identifier, String tooltip)
    {
        add("malum.gui.book.entry.page.text." + identifier, tooltip);
    }
    public void addDescription(String identifier, String tooltip)
    {
        add("malum.gui.book.entry." + identifier + ".description", tooltip);
    }
    public void addHeadline(String identifier, String tooltip)
    {
        add("malum.gui.book.entry.page.headline." + identifier, tooltip);
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