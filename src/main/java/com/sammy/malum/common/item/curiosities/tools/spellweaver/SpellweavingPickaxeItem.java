package com.sammy.malum.common.item.curiosities.tools.spellweaver;

import com.google.common.collect.*;
import com.sammy.malum.*;
import com.sammy.malum.common.data.component.*;
import com.sammy.malum.common.data.listener.spellweaving.SpellweavingEqualityData;
import com.sammy.malum.common.data.listener.spellweaving.SpellweavingEqualityReloadListener;
import com.sammy.malum.common.entity.activator.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.SpiritLike;
import com.sammy.malum.registry.common.enchantment.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.*;
import net.minecraft.core.component.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.common.extensions.*;
import net.neoforged.neoforge.event.level.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.toolkit.enchanting.LodestoneEnchantmentEffectActivator;
import team.lodestar.lodestone.modules.toolkit.item.*;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;
import team.lodestar.wayward_attributes.util.MagicPickaxeItem;

import java.util.*;
import java.util.stream.*;

public class SpellweavingPickaxeItem extends MagicPickaxeItem implements ISpiritAffiliatedItem, ISpellweavingTool {

    public static final ResourceLocation BASE_INTERACTION_RANGE = MalumMod.malumPath("spellweaving_tool.base_block_interaction_range");

    public SpellweavingPickaxeItem(Tier tier, float attackDamage, float attackSpeed, float magicDamage, LodestoneItemProperties properties) {
        super(tier, attackDamage, attackSpeed, magicDamage, properties.mergeAttributes(
                ItemAttributeModifiers.builder()
                        .add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BASE_INTERACTION_RANGE, 1.5f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build()));
    }

    public static ResourceLocation getBaseId(IAttributeExtension attribute) {
        if (attribute.equals(Attributes.BLOCK_INTERACTION_RANGE.value())) {
            return BASE_INTERACTION_RANGE;
        }
        return null;
    }

    @Override
    public SpiritLike getDefiningSpiritType(ItemStack stack) {
        return MalumSpiritTypes.EARTHEN_SPIRIT;
    }

    @Override
    public Mode getMode() {
        return Mode.NEAREST;
    }

    public static int getStateDisplay(ItemStack stack) {
        var data = stack.get(MalumDataComponents.SPELLWEAVING_TOOL_STATE);
        if (data == null) {
            return -1;
        }
        return data.isPrimed() ? 1 : -1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (SpellweavingPickaxeItem.toggleState(player, usedHand)) {
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        inventoryTick(pStack, pEntity, pIsSelected);
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public static void triggerSpellweavingEffect(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        var player = event.getPlayer();
        var tool = player.getMainHandItem();
        if (!(tool.getItem() instanceof ISpellweavingTool spellweavingTool)) {
            return;
        }
        var component = tool.get(MalumDataComponents.SPELLWEAVING_TOOL_STATE);
        if (component != null && component.isPrimed()) {
            var state = event.getState();
            if (!matches(state, tool)) {
                return;
            }
            var random = player.getRandom();
            var pos = event.getPos();
            var primaryDirection = Direction.orderedByNearest(player)[0];
            var directionOrder = getDirectionOrder(player);
            Multimap<Integer, BlockPos> nearbyBlocks = LinkedHashMultimap.create();
            var mutable = event.getPos().mutable();
            int startingIndex = 16;
            int travelTokens = startingIndex;
            Set<BlockPos> exploredBlocks = Sets.newHashSet();
            Set<BlockPos> currentLayer = Sets.newHashSet();
            Set<BlockPos> nextLayer = Sets.newHashSet();
            currentLayer.add(mutable.immutable());
            while (travelTokens > 0) {
                for (BlockPos markedPos : currentLayer) {
                    for (Direction direction : directionOrder) {
                        mutable.setWithOffset(markedPos, direction);
                        propagate(level, markedPos, exploredBlocks, mutable, nextLayer, state);
                    }
                    if (nextLayer.isEmpty()) {
                        for (int x = -1; x <= 1; x++) {
                            for (int y = -1; y <= 1; y++) {
                                for (int z = -1; z <= 1; z++) {
                                    if (x == 0 && y == 0 && z == 0) {
                                        continue;
                                    }
                                    mutable.set(markedPos).move(x, y, z);
                                    propagate(level, markedPos, exploredBlocks, mutable, nextLayer, state);
                                }
                            }
                        }
                    }
                    currentLayer = new HashSet<>(nextLayer);
                }
                if (nextLayer.isEmpty()) {
                    break;
                }
                var toAdd = new ArrayList<>(nextLayer);
                toAdd.sort(Comparator.comparingDouble(blockPos -> blockPos.getCenter().distanceTo(player.getEyePosition())));
                for (BlockPos markedPos : toAdd) {
                    nearbyBlocks.put(travelTokens, markedPos);
                }
                travelTokens--;
                nextLayer.clear();
            }
            if (nearbyBlocks.isEmpty()) {
                return;
            }
            boolean isNearest = spellweavingTool.getMode().equals(Mode.NEAREST);
            var spirit = tool.getItem() instanceof ISpiritAffiliatedItem spiritItem ? spiritItem.getDefiningSpiritType(tool) : MalumSpiritTypes.ARCANE_SPIRIT;
            int spawnedLoci = getSpawnedLoci(level, tool, player);
            float lociSpeed = getLociSpeed(level, tool, player);

            int finalTravelTokens = travelTokens;
            var backup = IntStream.range(0, startingIndex/2).map(i -> finalTravelTokens + i).mapToObj(nearbyBlocks::get).flatMap(Collection::stream).toList();

            boolean disallowBeneath = pos.getY() >= player.getY() && primaryDirection.getAxis().isHorizontal() && isNearest;

            boolean playSound = false;
            int distance = spawnedLoci+1;
            for (int i = 0; i < distance*2; i++) {
                int offset = i % distance;
                int index = isNearest ? startingIndex-offset : travelTokens+1+offset;
                var blocks = nearbyBlocks.get(index);
                if (!blocks.isEmpty()) {
                    var toRemove = new ArrayList<BlockPos>();
                    for (BlockPos markedPos : blocks) {
                        if (disallowBeneath && markedPos.getY() < player.getY()) {
                            continue;
                        }
                        if (spawnedLoci <= 0) {
                            break;
                        }
                        spawnBreaker(level, markedPos, random, tool, player, lociSpeed, pos, spirit, backup);
                        toRemove.add(markedPos);
                        playSound = true;
                        spawnedLoci--;
                    }
                    blocks.removeAll(toRemove);
                    toRemove.clear();
                }
                if (i == distance) {
                    if (disallowBeneath) {
                        disallowBeneath = false;
                    }
                    else {
                        break;
                    }
                }
            }
            if (playSound) {
                SoundPlayer.create(MalumGearSoundEvents.SPELLWOVEN_SPRITE_SPAWN).play(player);
            }
        }
    }

    private static void propagate(ServerLevel level, BlockPos markedPos, Set<BlockPos> exploredBlocks, BlockPos.MutableBlockPos mutable, Set<BlockPos> nextLayer, BlockState state) {
        if (!exploredBlocks.contains(mutable) && !nextLayer.contains(mutable)) {
            var markedState = level.getBlockState(mutable);
            if (matches(state, markedState)) {
                var immutable = mutable.immutable();
                exploredBlocks.add(markedPos);
                nextLayer.add(immutable);
            }
        }
    }

    private static void spawnBreaker(ServerLevel level, BlockPos markedPos, RandomSource random, ItemStack tool, Player player, float lociSpeed, BlockPos pos, SpiritLike spirit, List<BlockPos> backup) {
        float velocity = 0.35f;
        var velocityVector = new Vec3(
                Easing.SINE_IN_OUT.asWeighedRandom(random, -velocity, velocity),
                Easing.SINE_IN_OUT.asWeighedRandom(random, -velocity, velocity),
                Easing.SINE_IN_OUT.asWeighedRandom(random, -velocity, velocity)
        );
        var breaker = new SpellweaverToolEffectActivator(level, tool, player.getUUID(), lociSpeed, markedPos, pos.getCenter(), velocityVector);
        breaker.setSpirit(spirit.getSpirit());
        breaker.addBackupPositions(backup);
        level.addFreshEntity(breaker);
    }

    public static boolean toggleState(Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);
        var component = stack.getOrDefault(MalumDataComponents.SPELLWEAVING_TOOL_STATE, new SpellweavingToolStateComponent());
        boolean isPrimed = component.isPrimed();
        var sound = isPrimed ? MalumGearSoundEvents.SPELLWEAVING_TOOL_DAMPEN.get() : MalumGearSoundEvents.SPELLWEAVING_TOOL_PRIME.get();
        stack.set(MalumDataComponents.SPELLWEAVING_TOOL_STATE, new SpellweavingToolStateComponent(!isPrimed, 0));
        SoundPlayer.create(sound).play(player);
        player.swing(usedHand, true);
        final ItemCooldowns cooldowns = player.getCooldowns();
        boolean wasOnCooldown = cooldowns.isOnCooldown(stack.getItem());
        cooldowns.addCooldown(stack.getItem(), 8);
        return !wasOnCooldown;
    }

    public static void inventoryTick(ItemStack stack, Entity entity, boolean isSelected) {
        var data = stack.get(MalumDataComponents.SPELLWEAVING_TOOL_STATE);
        if (data != null) {
            boolean isPrimed = data.isPrimed();
            if (!isPrimed) {
                return;
            }
            int timer = data.timer();
            timer = isSelected ? 0 : timer + 1;
            if (timer >= 40) {
                timer = 0;
                isPrimed = false;
                SoundPlayer.create(MalumGearSoundEvents.SPELLWEAVING_TOOL_DAMPEN).play(entity);
            }
            stack.set(MalumDataComponents.SPELLWEAVING_TOOL_STATE, new SpellweavingToolStateComponent(isPrimed, timer));
        }
    }

    public static int getSpawnedLoci(ServerLevel level, ItemStack stack, Player player) {
        float spawnedLoci = 4 + LodestoneEnchantmentEffectActivator.createEffectActivator(ModEnchantmentComponents.LOCUS_COUNT.get(), level)
                .setItemContext()
                .countValue(stack, player);
        return Mth.floor(spawnedLoci);
    }

    public static float getLociSpeed(ServerLevel level, ItemStack stack, Player player) {
        return 0.5f + LodestoneEnchantmentEffectActivator.createEffectActivator(ModEnchantmentComponents.LOCUS_SPEED.get(), level)
                .setItemContext()
                .countValue(stack, player);
    }

    public static Direction[] getDirectionOrder(Player player) {
        var lookDirection = Direction.orderedByNearest(player);

        // Rearrange to prioritize directions perpendicular to the player's primary look direction
        return new Direction[]{
                lookDirection[1],
                lookDirection[2],
                lookDirection[3],
                lookDirection[4],
                lookDirection[0],
                lookDirection[5]
        };
    }

    public static boolean matches(BlockState state, ItemStack tool) {
        var toolProperties = tool.get(DataComponents.TOOL);
        if (toolProperties == null) {
            return false;
        }
        for (Tool.Rule tool$rule : toolProperties.rules()) {
            if (state.is(tool$rule.blocks())) {
                return true;
            }
        }
        return false;
    }

    public static boolean matches(BlockState first, BlockState second) {
        if (second.isEmpty()) {
            return false;
        }
        var data = SpellweavingEqualityReloadListener.EQUALITY_DATA;
        for (SpellweavingEqualityData datum : data) {
            var equalBlocks = datum.equalBlocks();
            var firstHolder = first.getBlockHolder();
            var secondHolder = second.getBlockHolder();
            if (equalBlocks.contains(firstHolder) && equalBlocks.contains(secondHolder)) {
                return true;
            }
        }
        return first.getBlock().equals(second.getBlock());
    }
}