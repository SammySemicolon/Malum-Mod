package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import static com.sammy.malum.MalumHelper.prefix;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider
{
    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, MalumMod.MODID, existingFileHelper);
    }
    
    @Override
    protected void registerModels()
    {
        blockItem(MalumItems.SPIRIT_STONE);
        blockItem(MalumItems.SPIRIT_STONE_SLAB);
        blockItem(MalumItems.SPIRIT_STONE_STAIRS);
        
    }
    
    private static final ResourceLocation GENERATED = new ResourceLocation("item/generated");
    private static final ResourceLocation HANDHELD = new ResourceLocation("item/handheld");
    
    private void handheldItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, HANDHELD).texture("layer0", prefix("item/" + name));
    }
    
    private void generatedItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", prefix("item/" + name));
    }
    
    private void blockItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, prefix("block/" + name));
    }
    
    @Override
    public String getName()
    {
        return "Malum item models";
    }
}