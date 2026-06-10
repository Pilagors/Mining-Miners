package fr.pilagors.miningminers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;

public class BasicControllerScreen extends AbstractContainerScreen<BasicControllerMenu> {
    private final BasicControllerMenu menu;

    private final int BACKGROUND = 0xFF8B8B8B;

    public BasicControllerScreen(BasicControllerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.menu = menu;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        // BACKGROUND
        graphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, BACKGROUND);
        graphics.fill(leftPos - 10, topPos, leftPos - 25, topPos + imageHeight, BACKGROUND);

        // SLOTS
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int x = leftPos + 71 + i * 18;
                int y = topPos + 26 + j * 18;
                graphics.fill(x - 1, y - 1, x + 17, y + 17, 0xFF373737);
                graphics.fill(x, y, x + 16, y + 16, 0xFF8B8B8B);
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int x = leftPos + 8 + col * 18;
                int y = topPos + 84 + row * 18;
                graphics.fill(x - 1, y - 1, x + 17, y + 17, 0xFF373737);
                graphics.fill(x, y, x + 16, y + 16, 0xFF8B8B8B);
            }
        }

        for (int col = 0; col < 9; col++) {
            int x = leftPos + 8 + col * 18;
            int y = topPos + 142;
            graphics.fill(x - 1, y - 1, x + 17, y + 17, 0xFF373737);
            graphics.fill(x, y, x + 16, y + 16, 0xFF8B8B8B);
        }

        graphics.fill(leftPos - 10, topPos + imageHeight - (menu.getProgress() * imageHeight / menu.getMaxProgress()), leftPos - 25, topPos + imageHeight, 0xFFFF0000);
    }
    
}
