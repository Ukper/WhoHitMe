package com.ukper.whohitme.menu;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class SimpleButton extends Button {
    public SimpleButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, (btn) -> Component.literal(""));
    }
}
