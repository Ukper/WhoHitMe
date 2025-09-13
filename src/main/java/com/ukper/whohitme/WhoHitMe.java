package com.ukper.whohitme;

import com.mojang.logging.LogUtils;
import com.ukper.whohitme.event.HitAnalyzerHUD;
import com.ukper.whohitme.menu.ModConfigScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(WhoHitMe.MODID)
public class WhoHitMe {
    public static final String MODID = "whohitme";

    private static final Logger LOGGER = LogUtils.getLogger();

    public WhoHitMe(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new HitAnalyzerHUD());

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ModConfigScreen::new);
        modContainer.registerConfig(ModConfig.Type.CLIENT, WhoHitMeConfig.CLIENT);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}