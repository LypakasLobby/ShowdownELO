package com.lypaka.showdownelo.Commands;

import com.lypaka.showdownelo.ShowdownELO;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = ShowdownELO.MOD_ID)
public class ShowdownELOCommand {

    public static final List<String> ALIASES = Arrays.asList("showdownelo", "showdown", "elo");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new ELOCommand(event.getDispatcher());
        new QueueCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
