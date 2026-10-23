package com.sammy.malum.datagen.lang;

public class IntroductionChapterLangDatagen extends CodexLangDatagen {

    public static void generateEntries() {

        addEntryLang("introduction", "Introduction", "On the nature of souls", "Study of the soul, and the magecraft born from its boundless complexity");
        addPages("introduction",
                "\"Within our world, every living being has a soul. That soul is consciousness, what animates the body, and the meeting point between matter and magic. These represent our existence; as the body is presented to the physical world, so the soul is to the arcane.\"",
                "I seem to have stumbled upon something peculiar: a form of magic so far undocumented. I could hardly call myself a magus if I refused the opportunity to study it. In this codex, the Encyclopedia Arcana, I write my research into this power, hoping to uncover and document everything about it.",
                "The energies this thaumaturgical discipline manipulates seem to be rooted in the soul. More accurately, they are the true energies of the soul at it's lowest level of composition, the inclinations and impulses that make up each one of us, the foundation and continuous self-assertation of one's existence. Proof, that one is real.",
                "So far, what I have described is basic. But I have found a way to separate, and then condense, the impulse of a soul into a physical form I call a spirit crystal. Upon the death of it's vessel, the soul fizzles out and merges with the surrounding area, it 'dies' so to speak. However, with precise timing, the soul can be 'shattered' just before it's natural death, shedding it's stored essence. This forms the basis of my research.",
                "The natures of the soul I condense influence the crystal's properties. Each soul is slightly different, and that can result in changes to the crystals formed. It is unorthodox by many standards, and yet, I feel a strange sensation, as if a great discovery was just made clear to me. I believe these spirit crystals to be just the breakthrough I need.");

        addEntryLang("spirit_crystals", "Spirit Crystals", "Matter and magic", "The foundation of sorcery");
        addPages("spirit_crystals",
                "The soul is a notoriously fickle thing. Even confirming its existence is difficult, requiring the highest thaumaturgies to get a reading. That is what sets spirit arcana apart from other magic. We don't need grand assemblies and esoteric artifice to see a soul. Simply destroying it is proof enough.",
                "A material I have named Soulstone is the means by which we do so. It appears mundane until refined, but once it is rid of impurities, it seems... out of phase with the world. By creating a blade using it as a core, I should be able to strike not only the physical form, but also the soul, shattering it to energy before it can disperse.",
                "These energies, as previously noted, have different 'frequencies' of sorts. A being burning with light would have a soul that reflects that radiance, and a being prone to adaptation would have a soul as malleable as itself. Occasionally, the energy has no flavor to it at all, leaving only the raw impulse of creation behind. That type of crystal bears further study.");

        addEntryLang("runewood", "Runewood", "Arcane oak", "It basks in the glorious hues of the sun");
        addPages("runewood",
                "Runewood is a strange mix of magic and nature that has always stood out amongst the many trees of the overworld. While pretty, I am more interested in practicality. Runewood is soaked in magic, and as such, can serve as the basis for the arcane.");

        addHeadline("runewood.placement", "Runewood's Habitat", "Where to locate");
        addPages("runewood.placement",
                "The tree is predominantly found within large open plains and sometimes forests. It is rare, but not outrageously so. It stands taller than most trees found within the same climate and thus can be easily identified by its leaves, proudly displaying an orange-yellow palette soaked in hues of the sun.");
        addHeadline("runewood.genesis", "Runewood's Genesis", "How it came to be");
        addPages("runewood.genesis",
                "The exact origins of the tree are yet unknown to me. It has clear ancestral connection rooted in that of Oak, yes, however the reason behind that connection remains rather enigmatic. Given the arcana involved, it may be impossible to tell the true origin of Runewood.");
        addHeadline("runewood.azure", "Runewood's Brilliant Blue", "A twin sister");
        addPages("runewood.azure",
                "During recent travel I came across another discovery. Azure Runewood, as I have named it, is a variant of the tree that adapted to grow in a colder climate. Its arcane composition and looks are all mostly the same, though its crown took on a bluish appearance. It can be found in most colder biomes.");

        addHeadline("runewood.arcane_charcoal", "Arcane Charcoal", "Burns with a bright blue hue");
        addPages("runewood.arcane_charcoal",
                "Runewood's charcoal, as magic-infused as it is, burns with an arcane fervor for longer than regular charcoal. When fed to a furnace it is capable of supporting its flame for exactly twice as long. This makes it rather useful for fueling any smelting I need to do.");
        addStandaloneGizmo("runewood.arcane_charcoal.0", "Runewood's Pyrolysis", "When broken down under extreme heat, the leftover arcana of Runewood remains imbued in the resulting charcoal.");
        addStandaloneGizmo("runewood.arcane_charcoal.1", "Compacting Arcane Charcoal", "Arcane Charcoal can be compacted into a block form.");

        addHeadline("runewood.runic_sap", "Runic Sap", "Tasty");
        addPages("runewood.runic_sap",
                "Runewood trees tend to have a buildup of sticky sap on the sides of their lower logs. When this happens, if you strip off the bark, you'll be able to bottle the sap. In terms of taste, it is a bit like honey, sweet but with a more earthly flavor, but where it shines most is it's rejuvenating aftertaste.");
        addStandaloneGizmo("runewood.runic_sap.0", "Stripping Sappy Runewood", "When stripped of it's bark, Sappy Runewood exposes it's stored sap for collection.");
        addStandaloneGizmo("runewood.runic_sap.1", "Harvesting Runic Sap", "The sap inside can be collected and stored within a bottle.");
        addStandaloneGizmo("runewood.runic_sap.2", "Mixing Runic Sapballs", "Mixing Runic Sap together with an added dough thickens it's composition.");

        addEntryLang("arcane_wonders", "Arcane Wonders", "The world around us", "Where we all gather");
        addPages("arcane_wonders",
                "After having carved open a few samples of raw Soulstone, I've been able to notice signs of a pattern. Both Runewood and Soulstone appear to be two instances of magic influence spanning the world, brought into existence through the same exact principle.",
                "The existence of Runewood is closely intertwined with Oak. That is to say, the biology, formation and reality of Runewood could not have taken precedence before Oak specifically. Soulstone on the other hand, in opposition to such a tightly carved ancestry appears to be able to manifest through the use of " + italic("any") +  " raw metal as a base material.",
                "When cut or crushed, differing amounts of copper, iron and gold bits can be spotted inside the inner layers of Soulstone strata. Some samples show signs of exclusively iron, whereas others indicate a mixed balance- though apart from internal makeup, I've not been able to identify any other differing influences the various metals might enact upon the resulting out of phase mineral.",
                "For all of recorded history, humanity's usage of thaumaturgy has been nothing short of commonplace. And yet, no known archive documents either of these clearly arcana-driven features of the overworld. Are they a maxim of nature, or a forgotten piece of human wisdom?");

        addEntryLang("soulstone", "Soulstone", "Out of phase", "A new dawn");
        addPages("soulstone",
                "Sometimes, it appears that matter can be charged with the energies of a soul, despite not having a soul of its own. This serves as the basis for spirit arcana and sorcery as a whole - the ensouling of the soulless, a means of taking what is static and showing it a new purpose.");
        addHeadline("soulstone.synopsis", "Soulstone's Existence", "It's truth");
        addPages("soulstone.synopsis",
                "Soulstone is an ore that exists more in the arcane than the physical, and, when refined, presents many uses for my magic. Refined Soulstone strongly radiates it's internal pool of arcana upon physical contact. The sensation builds up rather slowly and while uncomfortable, does not appear to have any true repercussions that can be observed through just touch.");
        addHeadline("soulstone.ore_deposits", "Soulstone' Deposits", "Where to locate");
        addPages("soulstone.ore_deposits",
                "Soulstone ore deposits can be found exclusively underground, forming most commonly in the deepslate layer. To be as precise as can be with my current knowledge at hand, the ore can generate starting from approximately fourteen blocks beneath sea level, continuing to appear all the way down until bedrock.");
        addHeadline("soulstone.buds", "Soulstone' Buds", "Odd formations");
        addPages("soulstone.buds",
                "Ore deposits of Soulstone take shape through a natural process of assimilation with other, existing metal deposits. Seemingly so, as a result said process, the ore forms Buds which stick out outwards and support the growth of a small Soulstone shard.");

        addHeadline("soulstone.refinement", "Refinement of Soulstone", "To purify the ethereal");
        addPages("soulstone.refinement",
                "Soulstone is a naturally excellent source of magic, but in that excellence there is still impurity. Like any other mineral, it must first be refined before being given a proper use. Both Raw Soulstone and Soulstone Buds can be processed into Refined Soulstone.");
        addStandaloneGizmo("soulstone.refinement.0", "Refinement of Soulstone", "When processed in a furnace or blast furnace, soulstone sheds it's impurities and takes on a bright purple shine.");
        addStandaloneGizmo("soulstone.refinement.1", "Compacting Raw Soulstone", "Raw Soulstone can be compacted into a block form.");
        addStandaloneGizmo("soulstone.refinement.2", "Compacting Refined Soulstone", "Refined Soulstone can be compacted into a block form.");


        addEntryLang("scythes", "Scythes", "Harvest", "Reap");
        addPages("scythes",
                "After several inert attempts, I have socketed Soulstone into a weapon that can reliably harvest these spirit crystals. The long blade allows time for the body to die before I strike the soul, while also providing a wide sweep attack. It isn't as sharp as a sword, but for my purposes, it will do nicely.",
                "With each slash of the scythe, a figurative 'wound' is etched at the point of impact with the soul. This wound brings the soul into disorder, and for as long as the soul is in such a state during the moment of its vessel's death, the soul will shatter. The actual cause of death does not matter, simply wounding a target's soul through the use of arcana enables spirit collection. If the soul however is left to recover, the wound will close and the soul will be permitted a natural end.",
                "What I had managed to do before with careful, painstaking experiments, the scythe did in a matter of seconds. The souls of the monsters I slew shattered, streaming bits of deeply hued matter towards me: the spirit crystals. Finally, my research can begin in earnest.");


        addEntryLang("scythes.enchanting", "Enchanting a Scythe", "Two ways to skin a soul");
        addPages("scythes.enchanting",
                "At its core, the scythe enchants like other weapons or tools I've used. It has its own set of enchantments, of course, due to its differing nature, but can take Unbreaking and the like as well as a sword can.");
        addHeadline("scythes.enchanting.spirit_plunder", "Spirit Plunder");
        addPages("scythes.enchanting.spirit_plunder",
                "This is not a perfect method. Some of the soul is unavoidably lost in the moment between blade and stone. But by enchanting the blade, that loss can be mitigated, and more of the soul condensed. This unfortunately strains the stone, and can result in my scythe's durability decreasing.");
        addHeadline("scythes.enchanting.haunted", "Haunted");
        addPages("scythes.enchanting.haunted",
                "Soulstone interacts with souls, obviously. The crudest and simplest use is blunt force, shattering a soul like a pane of glass. If I condense the arcane power of the stone, it would therefore hit and shatter harder, perhaps even damaging the body with the backlash.");
        addHeadline("scythes.enchanting.animated", "Animated");
        addPages("scythes.enchanting.animated",
                "If power can be concentrated, it can be diffused. Interestingly, by diffusing the power that would be drawn together for Haunted into the rest of the scythe, the entire weapon gains a conceptual \"lightness\", making its swings lighter and faster.");
        addHeadline("scythes.enchanting.rebound", "Rebound");
        addPages("scythes.enchanting.rebound",
                "A scythe reaps. That is its purpose, and that can be harnessed. Should a reaper need my hand? By harnessing the purpose of the blade as a weapon, I can let it act on its own to some extent, as if it were a boomerang. The stronger the enchantment, the sooner it’s ready to throw again.");
        addHeadline("scythes.enchanting.ascension", "Ascension");
        addPages("scythes.enchanting.ascension",
                "A fascinating bit of symbolism is the role of the tool in raising man up. Civilization was built by the food a scythe harvests. By harnessing the purpose of the blade as a tool, I can… well, raise myself, the winds I rise on sharp as the blade itself to my foes. Rebound is incompatible; literally, at cross purposes with this enchantment.");


        addEntryLang("natural_quartz", "Natural Quartz", "Deep in the earth");
        addPages("natural_quartz",
                "Natural Quartz is, as the name implies, a natural equivalent of the nether resource. It's used for most of the same things. It's rare, and found deep underground, sometimes in geodes.");

        addEntryLang("cthonic_gold", "Cthonic Gold", "Fused with the arcane");
        addPages("cthonic_gold",
                "Cthonic Gold is a strange yet useful metal. Its physical makeup is that of gold, yet its properties are entirely distinct. The ore is found deep underground in the deepslate layer of the world, rooted deeply into existing veins of gold.",
                "Physically, Cthonic Gold resembles pyrite, albeit with the density of true gold. The altered nature of the metal appears to derive from a mix of earthen and infernal arcana somehow bonded to its physical structure, creating a strange alloy. Arcana does not normally interact with metal in this way, at least in my experiments.",
                "The alloy of physical and metaphysical causes this material to serve as a bridging point, a gate between realms, so to speak. Or perhaps a guardian of those gates? It exists as purely physical, yet the arcane acknowledges its passage.",
                "I'm not sure what use this metal will have quite yet, but I doubt I will be short for applications for a material with such atypical properties.");

        addEntryLang("blazing_quartz", "Blazing Quartz", "Ignition");
        addPages("blazing_quartz",
                "It stands to reason that a place like the nether would have a substance that was flammable, and Blazing Quartz certainly fits the bill. It acts much like coal, even being able to form torches. A useful substance, even if fairly mundane.");

        addEntryLang("brilliance", "Brilliance", "The stuff of experience");
        addPages("brilliance",
                "Brilliance is a term I have heard bandied about for what others call experience. It is a part of the soul, though improperly attached, and can be collected and used for enchanting and repairs.",
                "What many don't know is that it can condense into a physical form. I have heard rumors of solid Brilliance coming from crushing ore, but the most reliable source is small clusters of ore where a soul faded away, leaving its experiences engraved on the stone.");

        addEntryLang("spirit_infusion", "Spirit Infusion", "Creation of wonders");
        addPages("spirit_infusion",
                "By using Runewood's natural magic as a base, I have designed the altar that will serve as the basis for my magecraft - the Spirit Altar. It is the other piece of the equation, the use for the arcana. By infusing them into items, and using the energies to effect other fusions, I can begin to explore this.",
                "To use the altar, I must lay the item I wish to infuse on top of it, along with an appropriate set of arcana. If I wish to fuse other items in the process, I must place them on some form of Runewood item holder. They must be within four blocks of the altar to work.",
                "Once all the arcana are present, the power within the crystals will begin to flow into the central item. If other items are fused in, they are pulled in during this process. When all of that is done, the product of the infusion will appear. It " + italic("is") + " rather slow, though...");

        addEntryLang("common_reagents", "Common Reagents", "Applied sorcery");
        addPages("common_reagents", "");

        addEntryLang("common_reagents.hex_ash", "Hex Ash", "Obligatory magic powder");
        addPages("common_reagents.hex_ash",
                "My first product with this process is a powder I call Hex Ash, after its color. It is a simple and useful grit, with the niter and sulfur mostly transmuted by the raw arcana, leaving a mixture of reagent and carbon.");
        addEntryLang("common_reagents.living_flesh", "Living Flesh", "Please don't try to eat it");
        addPages("common_reagents.living_flesh",
                "Next, for the sake of understanding how spirit arcana interacts with living substance, I have created... " + italic("something") + " which is now known as Living Flesh. It is a disgusting meaty chunk completely unfit for human consumption. Who knows if I'll end up finding a proper use for it.");
        addEntryLang("common_reagents.alchemical_calx", "Alchemical Calx", "Clay 2");
        addPages("common_reagents.alchemical_calx",
                "Lastly, I have created an experimental substrate I named Alchemical Calx. It's initially strong and tallow-like, but when met with a lesser amount of force it turns extremely malleable. It's bound to prove an useful ingredient.");


        addEntryLang("esoteric_reaping", "Esoteric Reaping", "Leaked magic");
        addPages("esoteric_reaping",
                "When a being dies, its soul disperses. This is basic theory, and well proven by this point. However, when a soul is shattered, some of it's energy tends to self-immolate. This phenomena causes bits of the soul to collide and fuse with the remains of the creature as it dies, bringing forth new artefacts.",
                "But now, with my scythe, I have proved it beyond doubt. When a soul is shattered, even if only for a brief moment, the energy collides with what's left of it's vessel, it's physical remains. That collision creates brand new reagents that cannot be sourced by destroying just the body. This phenomena appears to create a strong reaction, a change of sorts.",
                "I have thus far discovered four reagents born through this process, all of which are detailed in further pages. In summary, the flesh of zombies can curdle to Rotting Essence; the bones of skeletons can crystalize to Grim Talc; the wings of phantoms can spin to Eerie Weave; and the magic of endermen can coalesce into Warp Flux.");
        addEntryLang("esoteric_reaping.rotting_essence", "Rotting Essence", "Yucky!");
        addPages("esoteric_reaping.rotting_essence",
                "When exposed to the energy of the soul, the flesh of the undead can curdle into Rotting Essence, a toxic and foul substance that smells like death itself.",
                "It has... " + italic("some") + " nutritional value- though much like Rotten Flesh, it would be in your best interests avoid it's consumption.");
        addEntryLang("esoteric_reaping.grim_talc", "Grim Talc", "Sharp!");
        addPages("esoteric_reaping.grim_talc",
                "Bones exposed to the energy of the soul can crystallize into Grim Talc, a useful pseudo-mineral that can also be broken down into bonemeal.");
        addEntryLang("esoteric_reaping.eerie_weave", "Eerie Weave", "Fancy!");
        addPages("esoteric_reaping.eerie_weave",
                "The membrane of a phantom exposed to the energy of it's soul will spin into Eerie Weave, a mystic cloth with strange arcane properties capable of \"binding\" the flow of arcana.",
                "The exact properties of this binding effect and it's potential use cases demand further study. Nonetheless, it still makes for a rather fine silk.");
        addEntryLang("esoteric_reaping.warp_flux", "Warp Flux", "Odd.");
        addPages("esoteric_reaping.warp_flux",
                "The magic that envelops the endermen coalesces into Warp Flux, a strange essence that seems to be inimical to natural law.");
        addEntryLang("esoteric_reaping.core_keeping", "Core-Keeping", "Ancient Constructs");
        addPages("esoteric_reaping.core_keeping",
                "Amongst the various creatures studied by earth's historians, the Breeze and Blaze by far remain shrouded in the most mystery. They are constructs of ancient design, animated by arcane energies and imbued with rudimentary intelligence. Their only known purpose appears to be the protection of their own respective domains.",
                "Having studied their souls upon defeat, I have come to a fascinating conclusion. When the Soul of a Construct is shattered, it leaves behind a core; the Nucleus. It is an intricately woven arcane design that serves as the heart of the being. This Nucleus appears to hold etchings that dictate the properties of the soul, demanding further study.",
                "The existence of the Nucleus is fascinating in and of itself as it proves beyond a doubt that these constructs are created with deliberate intent. The etchings found within are... complicated- overly so, but they're not indecipherable. The etched runic scriptures are not written in any known language, but the fundamentals of arcana apply much the same.",
                "Lastly, although similar in acquisition to other cases of esoteric reaping- the Nuclei are stable arcane matter that the constructs already bear, rather than reagents born from the collision of soul energy and physical remains.");
        addHeadline("esoteric_reaping.core_keeping.wind_nucleus", "Wind Nucleus");
        addPages("esoteric_reaping.core_keeping.wind_nucleus",
                "The core of the Breeze, it is well preserved and bears runic etchings that appear to dictate the rules upon which wind bends around it. In a pinch, these etchings can be released to push surrounding creatures away.");
        addHeadline("esoteric_reaping.core_keeping.pyre_nucleus", "Pyre Nucleus");
        addPages("esoteric_reaping.core_keeping.pyre_nucleus",
                "The core of the Blaze, it is warm to the touch and bears runic etchings that attract heat and ash from nearby. The etchings are greatly interwoven, and upon any sudden change the entire core detonates. Useful, perhaps.");

        addEntryHeader("primary_arcana", "Primary Arcana", "The components of magic");
        addHeadline("primary_arcana.sacred", "Sacred Spirit");
        addPages("primary_arcana.sacred",
                "Sacred arcana is essential to any magic that enhances life. It can be defined as holy, the energy of particularly vibrant life, or even the simplicity of youth. It is pure and untainted, making it a useful component.",
                "It is the impulse of purity, the desire for optimism. It is found in those who are passive, innocent, or holy in origin.");
        addHeadline("primary_arcana.wicked", "Wicked Spirit");
        addPages("primary_arcana.wicked",
                "Wicked arcana is inimical to life. It seeks death and despair, and warps the living into something else. Even touching the crystal makes my soul shudder in pain.",
                "It is the impulse of corruption, the desire to cause suffering. It is found in those whose souls lack life, or those twisted by malice.");
        addHeadline("primary_arcana.arcane", "Arcane Spirit");
        addPages("primary_arcana.arcane",
                "While other arcana are impulses of the soul, it would be more accurate to say that the arcane is the impulse of the arcana themselves. This " + bold("raw arcana") + " lacks any particular quality, simply being undirected spiritual power.",
                "It is the impulse of creation, the first principle of all things. It is found within those who have opened their soul to power, or whose origins lie in that power.",
                "I suspect that this arcana, unlike others, can join a soul over time. Most things about the soul are defined early on. The impulses that define you are woven into your very self, after all. But lacking an impulse, perhaps this arcana is different. A witch was not born a mage, after all.");

        addEntryLang("elemental_arcana", "Elemental Arcana", "Focused magic");
        addHeadline("elemental_arcana.aerial", "Aerial Spirit");
        addPages("elemental_arcana.aerial",
                "Aerial arcana is the simplest of the elemental arcana. That very simplicity that gives it its utility. I have heard tales of magi soaring on the winds, ruling the skies. If any arcana is to make those tales achievable, it is this.",
                "It is the impulse of speed given form, the desire to run and to soar. It is found in anything particularly swift or mobile.");
        addHeadline("elemental_arcana.earthen", "Earthen Spirit");
        addPages("elemental_arcana.earthen",
                "Earthen arcana is relatively simple as well. It lends itself easily to strength, communion with nature, and the force of vitality. If I wish to enhance myself, or reshape the world, this arcana will be the key.",
                "It is the impulse of stability, the desire to stand and endure. It is found in anything that is unconcerned with the world around it changing.");
        addHeadline("elemental_arcana.infernal", "Infernal Spirit");
        addPages("elemental_arcana.infernal",
                "Infernal arcana is more complex, but not nearly as malicious as it might seem. Fire is dangerous, yes, but it is also the source of light and heat. It can burn something down as easily as it can fuse two things together.",
                "It is the impulse of light, the desire to burn. It is found in anything that shines brightly, as well as most denizens of the nether.");
        addHeadline("elemental_arcana.aqueous", "Aqueous Spirit");
        addPages("elemental_arcana.aqueous",
                "And finally, Aqueous arcana. It is strange, to say the least. It is malleable, yet doesn't do much by itself. It grants an affinity for the sea, but beyond that, its effects are rather esoteric.",
                "It is the impulse of change, the desire to adapt. It is found in anything that embodies that adaptation, as well as anything which lives in the flowing waters.");

        addEntryHeader("eldritch_arcana", "Eldritch Arcana", "For every push there is a pull");
        addHeadline("eldritch_arcana", "Eldritch Spirit");
        addPages("eldritch_arcana",
                "Eldritch arcana is a mystery to me. It has no impulse, none that I can understand, at least. And yet, it doesn't act like raw arcana. It changes, emboldens, enlightens... Raw arcana merely amplifies. This... this alters.",
                "I am not sure I understand what impulse creates this arcana. I find it in very few beings, and those I find it in are those who already defy explanation. But if it must be the pair to raw arcana, then that would imply that it's the impulse of endings, the " + italic("last") + " principle of all things.\n\nI do not like that thought.");

    }
}
