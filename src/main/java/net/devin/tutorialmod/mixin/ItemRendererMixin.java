package net.devin.tutorialmod.mixin;

import net.devin.tutorialmod.TutorialMod;
import net.devin.tutorialmod.item.ModItems;
import net.devin.tutorialmod.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useSickleModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(ModItems.Sickle) && renderMode != ModelTransformationMode.GUI) {
            return ((net.devin.tutorialmod.mixin.ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(TutorialMod.MOD_ID, "sickle3d", "inventory"));
        }
        return value;
    }
}