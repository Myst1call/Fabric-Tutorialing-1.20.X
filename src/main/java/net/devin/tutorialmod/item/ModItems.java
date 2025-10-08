package net.devin.tutorialmod.item;

import net.devin.tutorialmod.TutorialMod;
import net.devin.tutorialmod.enchantment.ModEnchantments;
import net.devin.tutorialmod.entity.RustEntity;
import net.devin.tutorialmod.index.TutorialDamageTypes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ModItems extends Item {
    public static final Item Sickle = registerItem("sickle",
            new HoeItem(ModToolMaterial.SICKLE, 0, -2.4f, new FabricItemSettings()));

    public ModItems(Settings settings) {
        super(settings);
    }

    private static void addItemstoIngredientsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(Sickle);
    }


    private static Item registerItem(String name, Item item) {
    return Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TutorialMod.LOGGER.info("Registing mod item for " + TutorialMod.MOD_ID);


    }



public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
    if (EnchantmentHelper.getEquipmentLevel(ModEnchantments.RUST, player) > 0) {
        float f = 1.0f;

        if (!world.isClient) {
            RustEntity rustEntity = new RustEntity(world, player);
            rustEntity.setOwner(player);
            rustEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, f * 3.0f, 1.0f);
            rustEntity.setDamage(rustEntity.getDamage());
            rustEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;

            ArrayList<StatusEffectInstance> statusEffectsHalved = new ArrayList<>();
            float absorption = player.getAbsorptionAmount();
            for (StatusEffectInstance statusEffect : player.getStatusEffects()) {
                StatusEffectInstance statusHalved = new StatusEffectInstance(statusEffect.getEffectType(), statusEffect.getDuration() / 2, statusEffect.getAmplifier(), statusEffect.isAmbient(), statusEffect.shouldShowParticles(), statusEffect.shouldShowIcon());
                rustEntity.addEffect(statusHalved);
                statusEffectsHalved.add(statusHalved);
            }
            player.clearStatusEffects();
            for (StatusEffectInstance statusEffectInstance : statusEffectsHalved) {
                player.addStatusEffect(statusEffectInstance);
            }
            player.setAbsorptionAmount(absorption);

            player.raycast(damage(new DamageSource(newDamageSource)) instanceof EntityHitResult ? ((EntityHitResult) player.raycast(ParticleTypes.ASH)) : null;
            player.getItemCooldownManager().set(this, 20);
            var hit = world.raycast(new RaycastContext(
                    origin, end,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    shooter
            ));

            Vec3d impact = (hit.getType() == HitResult.Type.MISS) ? end : hit.getPos();

            LivingEntity directTarget = null;
            List<LivingEntity> potential = serverWorld.getEntitiesByClass(
                    LivingEntity.class,
                    new Box(origin, end).expand(1.0),
                    e -> e != shooter && e.isAlive()
            );

            double minDist = Double.MAX_VALUE;
            for (LivingEntity e : potential) {
                double d = origin.distanceTo(e.getPos());
                if (d < minDist && d <= BEAM_LENGTH) {
                    minDist = d;
                    directTarget = e;
                }
            }

            if (directTarget != null) {
                directTarget.damage(serverWorld.getDamageSources().sonicBoom(shooter), BASE_DAMAGE);
                impact = directTarget.getPos();
            }

            List<LivingEntity> nearby = serverWorld.getEntitiesByClass(
                    LivingEntity.class,
                    new Box(impact.subtract(AOE_RADIUS, AOE_RADIUS, AOE_RADIUS),
                            impact.add(AOE_RADIUS, AOE_RADIUS, AOE_RADIUS)),
                    e -> e != shooter && e.isAlive()
            );

            for (LivingEntity target : nearby) {
                target.damage(serverWorld.getDamageSources().sonicBoom(shooter), BASE_DAMAGE);
                Vec3d push = target.getPos().subtract(impact).normalize().multiply(1.5);
                target.addVelocity(push.x, 0.6, push.z);
                target.velocityModified = true;

                serverWorld.spawnParticles(
                        ParticleTypes.EXPLOSION,
                        target.getX(), target.getY() + 1, target.getZ(),
                        6, 0.2, 0.2, 0.2, 0.05);
            }





