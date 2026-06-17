/*package io.github.zinnialily.minecraft_mod.block;

import io.github.zinnialily.minecraft_mod.Main;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.block.Block;

public class ModBlocks {
    public static final Block CABBAGE_BLOCK=registerBlock("cabbage_block",
            new Block(AbstractBlock.Settings.create.strength(4f).requiresTool().sounds(//idk)))
    private static Block registerBlock(String name, Block block){
        registerBlockItem(name,block);
        return Registry.register(Registries.BLOCK,Identifier.of(Main.MOD_ID,name),block);
    }
    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM,Identifier.of(Main.MOD_ID, name), new BlockItem(block,new Item.Settings*()));
    }
    public static void registerModBlocks(){
        Main.LOGGER.info("registering mod blocks for "+Main.MOD_ID);

    }
}*/
package io.github.zinnialily.minecraft_mod.block;

import io.github.zinnialily.minecraft_mod.Main;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // 1. Registered the block using Fabric/Yarn mappings
    public static final Block CABBAGE_BLOCK = registerBlock("cabbage_block",
            new Block(AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sound(BlockSoundGroup.CROP)) // Note: Fabric uses .sound(), not .sounds()
    );

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        // 2. Fabric uses Identifier.of() in newer versions, or new Identifier() in older ones.
        // Identifier.of() is standard for 1.21+
        return Registry.register(Registries.BLOCK, Identifier.of(Main.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM,
                Identifier.of(Main.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){
        Main.LOGGER.info("Registering mod blocks for " + Main.MOD_ID);
    }
}