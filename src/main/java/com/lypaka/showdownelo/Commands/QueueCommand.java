package com.lypaka.showdownelo.Commands;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PixelmonHelpers;
import com.lypaka.showdownelo.ConfigGetters;
import com.lypaka.showdownelo.EloPlayer;
import com.lypaka.showdownelo.Handlers.TeamValidator;
import com.lypaka.showdownelo.ShowdownELO;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.List;

public class QueueCommand {

    public QueueCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : ShowdownELOCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("queue")
                                            .then(
                                                    Commands.argument("levelcap", StringArgumentType.string())
                                                            .suggests(
                                                                    (context, builder) -> ISuggestionProvider.suggest(ConfigGetters.levelCaps, builder)
                                                            )
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    EloPlayer eloPlayer = ShowdownELO.playerMap.get(player.getUUID());
                                                                    if (eloPlayer.isQueued()) {

                                                                        player.sendMessage(FancyText.getFormattedText(ConfigGetters.alreadyQueuedMessage), player.getUUID());
                                                                        return 0;

                                                                    } else {

                                                                        String levelCap = StringArgumentType.getString(c, "levelcap");
                                                                        List<Pokemon> team = PixelmonHelpers.getTeam(player);

                                                                        if (!TeamValidator.teamPassesLevelCapRequirements(team, levelCap)) {

                                                                            player.sendMessage(FancyText.getFormattedText(ConfigGetters.levelCapMessage), player.getUUID());
                                                                            return 0;

                                                                        }



                                                                    }

                                                                }

                                                            })
                                            )
                            )
            )

        }

    }

}
