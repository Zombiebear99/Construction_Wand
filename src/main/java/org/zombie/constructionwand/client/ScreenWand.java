package org.zombie.constructionwand.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.zombie.constructionwand.basics.option.IOption;
import org.zombie.constructionwand.basics.option.WandOptions;
import org.zombie.constructionwand.network.ModMessages;
import org.zombie.constructionwand.network.PacketWandOption;

import javax.annotation.Nonnull;

public class ScreenWand extends Screen {
    private final ItemStack wand;
    private final WandOptions wandOptions;

    private static final int BUTTON_WIDTH = 160;
    private static final int BUTTON_HEIGHT = 20;
    private static final int SPACING_WIDTH = 50;
    private static final int SPACING_HEIGHT = 30;
    private static final int N_COLS = 2;
    private static final int N_ROWS = 3;

    private static final int FIELD_WIDTH = N_COLS * (BUTTON_WIDTH + SPACING_WIDTH) - SPACING_WIDTH;
    private static final int FIELD_HEIGHT = N_ROWS * (BUTTON_HEIGHT + SPACING_HEIGHT) - SPACING_HEIGHT;

    public ScreenWand(ItemStack wand) {
        super(Component.literal("ScreenWand"));
        this.wand = wand;
        wandOptions = new WandOptions(wand);
    }

    @Override
    protected void init() {
        createButton(0, 0, wandOptions.cores);
        createButton(0, 1, wandOptions.lock);
        createButton(0, 2, wandOptions.direction);
        createButton(1, 0, wandOptions.replace);
        createButton(1, 1, wandOptions.match);
        createButton(1, 2, wandOptions.random);
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawCenteredString(font, wand.getDisplayName(), width / 2, height / 2 - FIELD_HEIGHT / 2 - SPACING_HEIGHT, 16777215);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (Minecraft.getInstance().options.keyInventory.matches(keyCode, scanCode)) {
            this.onClose();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    private void createButton(int cx, int cy, IOption<?> option) {
        Button button = Button.builder(getButtonLabel(option), bt -> clickButton(bt, option))
                .pos(getX(cx), getY(cy))
                .size(BUTTON_WIDTH, BUTTON_HEIGHT)
                .tooltip(getButtonTooltip(option))
                .build();

        button.active = option.isEnabled();
        addRenderableWidget(button);
    }

    private void clickButton(Button button, IOption<?> option) {
        option.next();
        ModMessages.sendToServer(new PacketWandOption(option, false));
        button.setMessage(getButtonLabel(option));
        button.setTooltip(getButtonTooltip(option));
    }

    private int getX(int n) {
        return width / 2 - FIELD_WIDTH / 2 + n * (BUTTON_WIDTH + SPACING_WIDTH);
    }

    private int getY(int n) {
        return height / 2 - FIELD_HEIGHT / 2 + n * (BUTTON_HEIGHT + SPACING_HEIGHT);
    }

    private Component getButtonLabel(IOption<?> option) {
        return Component.translatable(option.getKeyTranslation()).append(Component.translatable(option.getValueTranslation()));
    }

    private Tooltip getButtonTooltip(IOption<?> option) {
        return Tooltip.create(Component.translatable(option.getDescTranslation()));
    }
}
