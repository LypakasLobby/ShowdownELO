package com.lypaka.showdownelo.Commands;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.showdownelo.EloPlayer;
import com.lypaka.showdownelo.Handlers.TimerHandlers;
import com.lypaka.showdownelo.ShowdownELO;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CancelCommand {

    public CancelCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : ShowdownELOCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("cancel")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    EloPlayer eloPlayer = ShowdownELO.playerMap.get(player.getUUID());
                                                    if (!eloPlayer.isQueued()) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou're not currently in queue!"), player.getUUID());

                                                    } else {

                                                        TimerHandlers.useUnfairPairingMap.entrySet().removeIf(e -> e.getKey().getUUID().toString().equalsIgnoreCase(player.getUUID().toString()));
                                                        TimerHandlers.playerQueueTimerMap.entrySet().removeIf(e -> e.getKey().getUUID().toString().equalsIgnoreCase(player.getUUID().toString()));
                                                        player.sendMessage(FancyText.getFormattedText("&aSuccessfully removed you from the queue!"), player.getUUID());

                                                    }
                                                    return 0;

                                                }

                                                return 0;

                                            })
                            )
            );

        }

    }

}
