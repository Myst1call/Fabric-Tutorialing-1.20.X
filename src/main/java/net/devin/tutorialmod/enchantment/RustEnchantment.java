package net.devin.tutorialmod.enchantment;

import net.devin.tutorialmod.item.ModItems;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class RustEnchantment extends Enchantment {

    public RustEnchantment (Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return this != other && !(other instanceof DamageEnchantment);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.isOf(ModItems.Sickle);
    }



    @Override
    public boolean isTreasure() {
        return true;
    }
}