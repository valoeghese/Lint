package io.github.hydos.lint.container.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.hydos.lint.container.LilTaterInteractContainer;
import io.github.hydos.lint.entity.tater.LilTaterEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

public class LilTaterContainerScreen extends AbstractInventoryScreen<LilTaterInteractContainer> {

    public final LilTaterInteractContainer container;
    public final Identifier backgroundIdentifier = new Identifier("lint", "textures/gui/container/lil_tater_inventory.png");
    public int mouseX;
    public int mouseY;

    public LilTaterContainerScreen(LilTaterInteractContainer container, PlayerInventory playerInventory, Text name) {
        super(container, playerInventory, name);
        this.container = container;
    }

    public static void drawTater(int x, int y, int size, float mouseX, float mouseY, LilTaterEntity entity) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float) x, (float) y, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(0.0D, 0.0D, 1000.0D);
        matrixStack.scale((float) size, (float) size, (float) size);
        Quaternion quaternion = Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        matrixStack.multiply(quaternion);
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack, immediate, 15728880);
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        RenderSystem.popMatrix();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        renderBackground(matrices);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float delta) {
        if (container != null) {
            super.render(stack, mouseX, mouseY, delta);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        } else {
            this.onClose();
            client.openScreen(null);
        }
    }

    public void renderBackground(MatrixStack matrix) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.client != null;
        this.client.getTextureManager().bindTexture(backgroundIdentifier);
        drawTexture(matrix, x, y - 20, 0, 0, this.backgroundWidth, this.backgroundHeight + 65);
        assert this.client.player != null;

        LilTaterEntity tater = (LilTaterEntity) this.client.world.getEntityById(container.taterId);

        if (tater == null) {
            onClose();
            return;
        }

        drawTater(x + 51, y + 20, 60, (float) (x + 51) - this.mouseX, (float) (y + 75 - 50) - this.mouseY, tater);
    }
}