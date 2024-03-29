package com.lypaka.showdownelo.Commands;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.showdownelo.EloPlayer;
import com.lypaka.showdownelo.Handlers.BattleHandler;
import com.lypaka.showdownelo.Handlers.TimerHandlers;
import com.lypaka.showdownelo.ShowdownELO;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.List;

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
                                                        BattleHandler.levelCapQueueMap.entrySet().removeIf(entry -> {

                                                            List<EloPlayer> players = entry.getValue();
                                                            players.removeIf(p -> p.getUUID().toString().equalsIgnoreCase(player.getUUID().toString()));
                                                            entry.setValue(players);
                                                            return false;

                                                        });
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
