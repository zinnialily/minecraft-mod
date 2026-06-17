package io.github.zinnialily.minecraft_mod;

import io.github.zinnialily.minecraft_mod.block.ModBlocks;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static final String MOD_ID="minecraft_mod";
	public static final Logger LOGGER= LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
	}
}