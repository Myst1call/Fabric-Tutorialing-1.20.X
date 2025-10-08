package net.devin.tutorialmod;

import net.devin.tutorialmod.enchantment.ModEnchantments;
import net.devin.tutorialmod.enchantment.RustEnchantment;
import net.devin.tutorialmod.index.TutorialModEntities;
import net.devin.tutorialmod.item.ModItemGroups;
import net.devin.tutorialmod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String name) {
        return new Identifier("tutorialmod", name);}

    @Override
	public void onInitialize() {
        ModItemGroups.registerItemGroups();
        ModItems.registerModItems();
        ModEnchantments.init();
        TutorialModEntities.initialize();

	}
}