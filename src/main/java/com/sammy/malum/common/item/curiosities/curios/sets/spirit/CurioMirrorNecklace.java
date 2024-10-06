import com.google.common.collect.Multimap;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

@Override
public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
    addAttributeModifier(map, AttributeRegistry.ARCANE_RESONANCE.get(), uuid -> new AttributeModifier(uuid,
            "Curio Arcane Resonance", 1f, AttributeModifier.Operation.MULTIPLY_BASE));
}