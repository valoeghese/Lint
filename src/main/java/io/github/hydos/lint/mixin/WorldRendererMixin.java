package io.github.hydos.lint.mixin;

import io.github.hydos.lint.world.dimension.Dimensions;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    private static final Identifier COOLSUN = new Identifier("lint", "textures/environment/twin_sun.png");

    @Shadow
    @Final
    private static Identifier SUN;
    @Shadow
    private ClientWorld world;

    @Redirect(method = "renderSky", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;SUN:Lnet/minecraft/util/Identifier;"))
    private Identifier getCoolsun() {
        if (world.getDimension() == Dimensions.HAYKAM) {
            return COOLSUN;
        } else {
            return SUN;
        }
    }
}
