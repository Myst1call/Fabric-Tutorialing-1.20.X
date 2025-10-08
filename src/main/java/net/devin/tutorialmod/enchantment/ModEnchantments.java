package net.devin.tutorialmod.enchantment;

import net.devin.tutorialmod.TutorialMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class ModEnchantments {

    public static Enchantment RUST = register("rust", new RustEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, TutorialMod.id(name), enchantment);
    }

    public static void init() {
    }
}