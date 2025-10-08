package net.devin.tutorialmod.index;

import net.devin.tutorialmod.TutorialMod;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface TutorialDamageTypes {
    RegistryKey<DamageType> RUST_ENTITY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, TutorialMod.id("rust_entity"));
}
