package com.ukper.whohitme.event;

import com.ukper.whohitme.hud.DamageMessageRenderer;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public class  HitAnalyzerHUD {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public void onRenderOverlay(RenderGuiEvent.Post event) {
        if(mc.player == null && event.getGuiGraphics() != null) return;
        DamageMessageRenderer.render(event.getGuiGraphics());
    }

}
