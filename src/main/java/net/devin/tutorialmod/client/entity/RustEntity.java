package net.devin.tutorialmod.client.entity;

import com.google.common.collect.Sets;
import net.devin.tutorialmod.index.TutorialDamageTypes;
import net.devin.tutorialmod.index.TutorialModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RustEntity extends PersistentProjectileEntity {
    private final Set<StatusEffectInstance> effects = Sets.newHashSet();
    public int ticksUntilRemove = 5;
    public final List<LivingEntity> hitEntities = new ArrayList<>();

    public RustEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public RustEntity(World world, LivingEntity owner) {
        super(TutorialModEntities.RUST_ENTITY, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    public void addEffect(StatusEffectInstance effect) {
        this.effects.add(effect);
    }

    @Override
    public void tick() {
        super.tick();

        for (float x = -3; x <= 3; x += 0.1f) {
            this.getWorld().addParticle(ParticleTypes.GLOW, this.getX() + x * Math.cos(this.getYaw()), this.getY(), this.getZ() + x * Math.sin(this.getYaw()), this.getVelocity().getX(), this.getVelocity().getY(), this.getVelocity().getZ());
        }

        if (this.inGround || this.age > 20) {
            for (int i = 0; i < 50; i++) {
                this.getWorld().addParticle(ParticleTypes.GLOW, this.getX() + (this.random.nextGaussian() * 2) * Math.cos(this.getYaw()), this.getY(), this.getZ() + (this.random.nextGaussian() * 2) * Math.sin(this.getYaw()), this.random.nextGaussian() / 10, this.random.nextFloat() / 2, this.random.nextGaussian() / 10);
            }
            this.ticksUntilRemove--;
        }

        if (this.ticksUntilRemove <= 0) {
            this.discard();
        }

        if (!this.getWorld().isClient) {
            for (LivingEntity livingEntity : this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox(), livingEntity -> this.getOwner() != livingEntity)) {
                if (!hitEntities.contains(livingEntity)) {
                    livingEntity.damage(this.getWorld().getDamageSources().create(TutorialDamageTypes.RUST_ENTITY, this, this.getOwner()), 12.0f);
                    for (StatusEffectInstance effect : this.effects) {
                        livingEntity.addStatusEffect(effect);
                    }
                    hitEntities.add(livingEntity);
                }
            }
        }
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ENTITY_ARROW_SHOOT;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
    }
}