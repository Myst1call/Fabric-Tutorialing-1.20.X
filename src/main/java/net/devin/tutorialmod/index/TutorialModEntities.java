package net.devin.tutorialmod.index;

import net.devin.tutorialmod.TutorialMod;
import net.devin.tutorialmod.entity.RustEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface TutorialModEntities {
    Map<EntityType<? extends Entity>, Identifier> ENTITIES = new LinkedHashMap<>();

    EntityType<RustEntity> RUST_ENTITY = createEntity("rust_entity", FabricEntityTypeBuilder.<RustEntity>create(SpawnGroup.MISC, RustEntity::new).disableSaving().dimensions(EntityDimensions.changing(5.0f, 0.2f)).build());

    private static <T extends EntityType<? extends Entity>> T createEntity(String name, T entity) {
        ENTITIES.put(entity, new Identifier(TutorialMod.MOD_ID, name));
        return entity;
    }

    static void initialize() {
        ENTITIES.keySet().forEach(entityType -> Registry.register(Registries.ENTITY_TYPE, ENTITIES.get(entityType), entityType));
    }
}