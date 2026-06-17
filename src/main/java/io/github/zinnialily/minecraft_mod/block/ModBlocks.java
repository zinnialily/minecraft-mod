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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static final Block CABBAGE_BLOCK = register(
            "cabbage_block",
            Block::new,
            //need to change
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(4.0f)
                    .requiresCorrectToolForDrops()
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
