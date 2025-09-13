package com.ukper.whohitme.menu;

import com.ukper.whohitme.WhoHitMeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ModConfigScreen extends Screen implements IConfigScreenFactory {
    public static final int SCREEN_WIDTH = Minecraft.getInstance().getWindow().getGuiScaledWidth();
    public static final int WIDTH = 150;
    public static final int HEIGHT = 20;
    public ModConfigScreen() {
        super(Component.literal("Who Hit Me Settings"));
    }

    @Override
    protected void init() {
        int y = 20;

        // Кнопка для стилю повідомлень
        this.addRenderableWidget(new SimpleButton((SCREEN_WIDTH / 2) - WIDTH - 10, y, WIDTH, HEIGHT,
                Component.translatable("whohitme:style", Component.translatable(WhoHitMeConfig.style.get().getKey())),
                btn -> {
                    WhoHitMeConfig.style.set(WhoHitMeConfig.style.get().next());
                    WhoHitMeConfig.CLIENT.save();
                    btn.setMessage(Component.translatable("whohitme:style", Component.translatable(WhoHitMeConfig.style.get().getKey())));
                }));

        // Кнопка чи показувати дамаг
        this.addRenderableWidget(new SimpleButton((SCREEN_WIDTH / 2) + 10, y, WIDTH, HEIGHT,
                Component.translatable("whohitme:show_damage", Component.translatable(trueOrFalse(WhoHitMeConfig.showDamage.get()))),
                btn -> {
                    WhoHitMeConfig.showDamage.set(!WhoHitMeConfig.showDamage.get());
                    WhoHitMeConfig.CLIENT.save();
                    btn.setMessage(Component.translatable("whohitme:show_damage", Component.translatable(trueOrFalse(WhoHitMeConfig.showDamage.get()))));
                }));
        y += 30;

        // Кнопка розташування (ліво, право)
        this.addRenderableWidget(new SimpleButton((SCREEN_WIDTH / 2) - WIDTH - 10, y, WIDTH, HEIGHT,
                Component.translatable("whohitme:hor_pos", Component.translatable(WhoHitMeConfig.sideHorizontal.get().getKey())),
                btn -> {
                    WhoHitMeConfig.sideHorizontal.set(WhoHitMeConfig.sideHorizontal.get().next());
                    WhoHitMeConfig.CLIENT.save();
                    btn.setMessage(Component.translatable("whohitme:hor_pos", Component.translatable(WhoHitMeConfig.sideHorizontal.get().getKey())));
                }));

        // Кнопка розташування (верх, низ)
        this.addRenderableWidget(new SimpleButton((SCREEN_WIDTH / 2) + 10, y, WIDTH, HEIGHT,
                Component.translatable("whohitme:vert_pos", Component.translatable(WhoHitMeConfig.sideVertical.get().getKey())),
                btn -> {
                    WhoHitMeConfig.sideVertical.set(WhoHitMeConfig.sideVertical.get().next());
                    WhoHitMeConfig.CLIENT.save();
                    btn.setMessage(Component.translatable("whohitme:vert_pos", Component.translatable(WhoHitMeConfig.sideVertical.get().getKey())));
                }));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public Screen createScreen(ModContainer modContainer, Screen screen) {
        return new ModConfigScreen();
    }

    public String trueOrFalse(boolean b) {
        return b ? "whohitme:true" : "whohitme:false";
    }
}
