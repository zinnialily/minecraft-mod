package io.github.zinnialily.minecraft_mod.block;

import io.github.zinnialily.minecraft_mod.Main;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.Random;
public class ModBlocks {
    /*public static final Block CABBAGE_BLOCK = register(
            "cabbage",
            Block::new,
            //need to change
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(4.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.WOOD),
            true
    );*/
    public static final Block CABBAGE_BLOCK = register(
            "cabbage",
            (properties) -> new Block(properties) {
                @Override
                public InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
                    if (!level.isClientSide()) {
                        level.destroyBlock(blockPos, false);

                        int offsetX = level.getRandom().nextInt(3) - 1;
                        int offsetY = level.getRandom().nextInt(3) - 1;
                        int offsetZ = level.getRandom().nextInt(3) - 1;

                        BlockPos newPos = blockPos.offset(offsetX, offsetY, offsetZ);

                        if (level.getBlockState(newPos).isAir()) {
                            level.setBlock(newPos, this.defaultBlockState(), 3);
                        }

                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.PASS;
                }
            },
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(0.5f)
                    .sound(SoundType.WOOD),
            true
    );

    private static Block register(
            String name,
            java.util.function.Function<BlockBehaviour.Properties, Block> blockFactory,
            BlockBehaviour.Properties properties,
            boolean shouldRegisterItem
    ) {
        // Create a registry key for the block
        ResourceKey<Block> blockKey = createBlockKey(name);

        // Create the block instance
        Block block = blockFactory.apply(properties.setId(blockKey));

        // Register the block
        Registry.register(BuiltInRegistries.BLOCK, blockKey, block);

        // Some blocks should not have items (technical blocks like moving_piston)
        if (shouldRegisterItem) {
            ResourceKey<Item> itemKey = createItemKey(name);
            BlockItem blockItem = new BlockItem(
                    block,
                    new Item.Properties()
                            .setId(itemKey)
                            .useBlockDescriptionPrefix()
            );
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return block;
    }

    private static ResourceKey<Block> createBlockKey(String name) {
        return ResourceKey.create(
                Registries.BLOCK,
                Identifier.fromNamespaceAndPath(Main.MOD_ID, name)
        );
    }

    private static ResourceKey<Item> createItemKey(String name) {
        return ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(Main.MOD_ID, name)
        );
    }

    public static void initialize() {
        Main.LOGGER.info("Registering mod blocks for {}", Main.MOD_ID);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register((creativeTab) -> {
            creativeTab.accept(ModBlocks.CABBAGE_BLOCK.asItem());
        });
    }
}