package com.lypaka.showdownelo.Listeners;

import com.lypaka.showdownelo.Handlers.TimerHandlers;
import com.lypaka.showdownelo.ShowdownELO;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@Mod.EventBusSubscriber(modid = ShowdownELO.MOD_ID)
public class ServerStartedListener {

    @SubscribeEvent
    public static void onServerStarted (FMLServerStartedEvent event) {

        MinecraftForge.EVENT_BUS.register(new LoginListener());
        Pixelmon.EVENT_BUS.register(new BattleEndListener());

        TimerHandlers.startTimers();

    }

}
