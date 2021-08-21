package com.sammy.malum.core.data;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.cooler_book.CoolerBookEntry;
import com.sammy.malum.client.screen.cooler_book.CoolerBookScreen;
import com.sammy.malum.common.block.ether.WallEtherTorchBlock;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.enchantment.MalumEnchantments;
import com.sammy.malum.core.init.event.StartupEvents;
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
        CoolerBookScreen.setupEntries();
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SOUNDS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(MalumEnchantments.ENCHANTMENTS.getEntries());
        Set<RegistryObject<Effect>> effects = new HashSet<>(MalumEffects.EFFECTS.getEntries());
        ArrayList<CoolerBookEntry> coolerBookEntries = CoolerBookScreen.entries;
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
        coolerBookEntries.forEach(b ->
        {
            String name = MalumHelper.toTitleCase(b.identifier, "_");
            add(b.translationKey(), name);
        });
        rites.forEach(r -> add("malum.gui.rite." + r.identifier, MalumHelper.toTitleCase(r.identifier, "_")));

        addHeadline("introduction", "Introduction");
        addPage("introduction_a", "Within this world exists an arcane phenomenon, spirits. A spirit is a magical property or element depending on how you look at it that makes up souls. The soul is an arcane image or representation of every sentient being. The body is the opposite, it's a physical vessel for the soul.");
        addPage("introduction_b", "Soul magic is a quite complex field with a simpler predecessor: spirit magic. Spirit magic is a much easier way to access most of the arcane phenomenon that derive from the soul. To put it short, soul magic almost entirely derives from the spirits within a soul and by extension spirits alone can be utilized for magic!");
        addPage("introduction_c", "Getting your power hungry hands on some magical spirits may not be so simple though. When a body dies it leaves behind various items and precious loot, likewise when a soul is shattered it bursts out into magical spirit. This brutal way of completely eradicating both the body and the soul from existence is the main way of obtaining spirits.");

        addHeadline("spirit_magics", "Basics of spirit magic");
        addPage("spirit_magics_a", "When a spirit is brought out of a soul it takes on a physical form, a crystal of sorts. Due to how spirits react to nearby souls, the spirit crystal will resonate with only your soul and be pulled towards you making it quite easy to collect. No one can steal your precious spirits, they resonate with your soul only.");
        addPage("spirit_magics_b", "Once in crystal form a spirit's energy can be easily released, it can naturally interact with the world around you, it can be infused into various catalysts and reagents through complex magic processes and more things you'll learn about soon enough. Do note though that if a spirit is left uncollected, it will dissolve into the space around it.");
        addPage("spirit_magics_c", "Spirits left uncollected can have a very strange impact on the world, there are many curiosities you may have already found that only exist due to some careless spirit hunter. Spirits can easily change the world around you.");

        addHeadline("soulstone", "Soulstone");
        addPage("soulstone_a", "The soulstone is a strange ore found both on the surface and in the deepest ends of every cave. The ore itself was created from carelessness, when a dissolved spirit merges with ore it'll shape the mineral to be something new. Soulstone is extremely valuable to any spirit hunter.");
        addPage("soulstone_b", "Soulstone is very precious as it is able to resonate and mangle with souls and spirits in various ways. This unique ability of the soulstone makes it the perfect mineral to create spirit hunting equipment out of. Any weapon made out of soulstone is most certainly sufficient for damaging an opponent's soul.");

        addHeadline("runewood", "Runewood");
        addPage("runewood_a", "The runewood tree is yet another result of spirits dissolving into nature. When an oak tree is touched by infernal or more rarely arcane spirit it quickly takes on a new appearance, it seems to appear most commonly within open plains and forests. Some call this type of tree arcane oak.");
        addPage("runewood_b", "Runewood is perfect for a magic conductor or relay of sorts, it works very well for any arcane craft really.");
        addHeadline("solar_sap", "Solar Sap");
        addPage("solar_sap_a", "The runewood tree often grows with a special sap in it! The mixture known as solar sap naturally flows through the arcane oak and can easily be harvested. To harvest sap you need to locate some sap filled runewood, strip the bark off with an axe and then use a bottle to store the sap.");
        addPage("solar_sap_b", "Solar sap has some select uses, mainly solar syrup and solar sapballs. Solar syrup is a delicious mixture you can obtain my heating up solar sap in a furnace. This rejuvenating drink will restore health atop hunger and provide a regenerative effect. Combining solar sap with slimeballs will yield sapballs, an alternative to slime.");
        addPage("solar_sap_c", "...There's probably some technology out there that would allow you to remove the magic from sapballs and turn them back into slime, best not to worry about that.");

        addHeadline("scythes", "Scythes!");
        addPage("scythes_a", "A scythe is a weapon of magic origin designed for cutting through hordes of enemies. The axe exchanges attack speed for extra damage, the scythe on the other hand trades it off for crowd control. Any attack done with the scythe will count as a stronger version of a sword's sweep attack.");
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
        addPage("wicked_spirit_a", "Opposite of the sacred, the wicked spirit represents impurity and various dark magics. Even just touching this spirit creates a mild pain. Wicked magics can be really dangerous in the right hands.");
        addPage("wicked_spirit_b", "Generally when a body dies, the soul peacefully and slowly fades out alongside it. However, if the body is to be brought back to life through necromancy or some other reanimating magic just seconds after death the soul may be brought back! Through this process the soul is implanted with wicked spirit and overtime becomes one with it.");

        addHeadline("arcane_spirit", "Arcane Spirit");
        addPage("arcane_spirit_a", "The arcane spirit is magic in it's purest form. Arcane spirit is often needed to fully utilize the potential stored within other spirits, it has little power alone but can greatly amplify other magic.");
        addPage("arcane_spirit_b", "Spirits found in a soul aren't always dependant only on the body the soul resided in from when it was created. For example, it is rumored that the very first illagers were villagers who broke their oath and turned evil. If that is the case, their soul would've adapted and became completely wicked.");
        addPage("arcane_spirit_c", "A similar process most likely happened when a apprentice witch became proficient enough in magics. Souls can develop spirits over a long period of time based on how the body reacts to magic. This is very often the case with arcane spirit due to how pure and frequent it is in the world. Arcane Spirit is found within the supernatural.");

        addHeadline("spirit_infusion", "Spirit Infusion");
        addPage("spirit_infusion_a", "Spirit infusion is a crafting process that revolves around infusing spirit magic into various items, twisting them to fit your evil desires. Every infusion recipe requires a prime item as well as spirits to infuse the item with. These must all be placed right on the altar.");
        addPage("spirit_infusion_b", "Some recipes may also ask for additional reagents to be infused into the prime item. These extra items must be placed on nearby item pedestals or item stands, such as ones made from runewood. These can be placed anywhere as long as they're within 4 blocks of the altar.");
        addPage("spirit_infusion_c", "Once everything is setup correctly, spirit infusion will begin and one by one each additional ingredient will be absorbed into the prime item alongside needed spirits. Once this lengthy process is done your desired item will be created!");
        addHeadline("hex_ash", "Hex Ash");
        addPage("hex_ash", "Hex ash is a simple magical powder used in creating various magical items. The ash can animate things, bring them to life in some extreme cases even. It's crated by infusing gunpowder with arcane spirit.");

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