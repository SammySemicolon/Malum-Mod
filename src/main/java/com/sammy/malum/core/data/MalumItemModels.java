package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.ether.EtherSconceBlock;
import com.sammy.malum.common.block.ether.EtherTorchBlock;
import com.sammy.malum.common.item.impetus.CrackedImpetusItem;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.common.item.NodeItem;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.common.item.spirit.SoulStaveItem;
import com.sammy.malum.common.item.tools.MalumScytheItem;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.ortus.helpers.DataHelper;
import com.sammy.ortus.systems.item.ModCombatItem;
import com.sammy.ortus.systems.multiblock.MultiBlockItem;
import net.mehvahdjukaar.supplementaries.common.block.blocks.SconceBlock;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.core.setup.content.item.ItemRegistry.ITEMS;
import static com.sammy.ortus.helpers.DataHelper.take;
import static com.sammy.ortus.helpers.DataHelper.takeAll;

public class MalumItemModels extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public MalumItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());

        blightedSpireItem(take(items, ItemRegistry.BLIGHTED_SPIRE));
        generatedItem(take(items, ItemRegistry.NATURAL_QUARTZ));

        takeAll(items, i -> i.get() instanceof MalumScytheItem);
        takeAll(items, i -> i.get() instanceof MalumSpiritItem).forEach(this::spiritSplinterItem);
        takeAll(items, i -> i.get() instanceof NodeItem).forEach(this::nodeItem);
        takeAll(items, i -> i.get() instanceof ImpetusItem || i.get() instanceof CrackedImpetusItem).forEach(this::impetusItem);
        takeAll(items, i -> i.get() instanceof MultiBlockItem).forEach(this::multiBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof WallBlock).forEach(this::wallBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof FenceBlock).forEach(this::fenceBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof DoorBlock).forEach(this::generatedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof TrapDoorBlock).forEach(this::trapdoorBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof PressurePlateBlock).forEach(this::pressurePlateBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof ButtonBlock).forEach(this::buttonBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof BushBlock && !(((BlockItem) i.get()).getBlock() instanceof DoublePlantBlock)).forEach(this::blockGeneratedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof DoublePlantBlock).forEach(this::generatedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof EtherBrazierBlock).forEach(this::etherBrazierItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof LanternBlock).forEach(this::generatedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof EtherSconceBlock).forEach(this::etherSconceItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof EtherTorchBlock).forEach(this::etherTorchItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof TorchBlock).forEach(this::generatedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof SconceBlock).forEach(this::generatedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof EtherBlock).forEach(this::etherItem);
        takeAll(items, i -> i.get() instanceof SignItem).forEach(this::generatedItem);

        takeAll(items, i -> i.get() instanceof BlockItem).forEach(this::blockItem);
        takeAll(items, i -> i.get() instanceof DiggerItem).forEach(this::handheldItem);
        takeAll(items, i -> i.get() instanceof SoulStaveItem).forEach(this::handheldItem);
        takeAll(items, i -> i.get() instanceof ModCombatItem).forEach(this::handheldItem);
        takeAll(items, i -> i.get() instanceof SwordItem).forEach(this::handheldItem);
        takeAll(items, i -> i.get() instanceof BowItem).forEach(this::handheldItem);
        items.forEach(this::generatedItem);
    }

    private static final ResourceLocation GENERATED = new ResourceLocation("item/generated");
    private static final ResourceLocation HANDHELD = new ResourceLocation("item/handheld");

    private void blightedSpireItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", malumPath("block/" + name+"_0"));
    }
    private void multiBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(malumPath("item/" + name + "_item")));
    }

    private void nodeItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/impetus/" + name));
    }

    private void impetusItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        ArrayList<String> split = DataHelper.reverseOrder(new ArrayList<>(), Arrays.asList(name.split("_")));
        split.remove(0);
        String alteredName = String.join("_", split);
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/impetus/" + alteredName));
    }

    private void spiritSplinterItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/spirit_shard"));
    }

    private void etherBrazierItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        String textureName = name.substring(0, 8) + "ether_brazier";
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent) {
            withExistingParent(name, GENERATED).texture("layer0", malumPath("item/iridescent_ether_brazier")).texture("layer1", malumPath("item/" + textureName)).texture("layer2", malumPath("item/iridescent_ether_brazier_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/ether_brazier_overlay")).texture("layer1", malumPath("item/" + textureName));
    }

    private void etherTorchItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent) {
            withExistingParent(name, GENERATED).texture("layer0", malumPath("item/iridescent_ether_torch")).texture("layer1", malumPath("item/ether_torch")).texture("layer2", malumPath("item/iridescent_ether_torch_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/ether_torch_overlay")).texture("layer1", malumPath("item/ether_torch"));
    }

    private void etherSconceItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent) {
            withExistingParent(name, GENERATED).texture("layer0", malumPath("item/iridescent_ether_sconce")).texture("layer1", malumPath("item/ether_sconce")).texture("layer2", malumPath("item/iridescent_ether_sconce_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/ether_sconce_overlay")).texture("layer1", malumPath("item/ether_sconce"));
    }

    private void etherItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent) {
            withExistingParent(name, GENERATED).texture("layer0", malumPath("item/iridescent_ether")).texture("layer1", malumPath("item/iridescent_ether_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/ether"));
    }

    private void handheldItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, HANDHELD).texture("layer0", malumPath("item/" + name));
    }

    private void generatedItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/" + name));
    }

    private void blockGeneratedItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", malumPath("block/" + name));
    }

    private void blockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(malumPath("block/" + name)));
    }

    private void trapdoorBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(malumPath("block/" + name + "_bottom")));
    }

    private void fenceBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceInventory(name, malumPath("block/" + baseName));
    }

    private void wallBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallInventory(name, malumPath("block/" + baseName));
    }

    private void pressurePlateBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(malumPath("block/" + name + "_up")));
    }

    private void buttonBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(malumPath("block/" + name + "_inventory")));
    }

    @Override
    public String getName() {
        return "Malum Item Models";
    }
}