package com.lypaka.showdownelo.Commands;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.showdownelo.ConfigGetters;
import com.lypaka.showdownelo.EloPlayer;
import com.lypaka.showdownelo.ShowdownELO;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;

public class ELOCommand {

    public ELOCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : ShowdownELOCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("check")
                                            .then(
                                                    Commands.argument("levelCap", StringArgumentType.string())
                                                            .suggests(
                                                                    ((context, builder) -> ISuggestionProvider.suggest(ConfigGetters.levelCaps, builder))
                                                            )
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    String levelCap = StringArgumentType.getString(c, "levelCap");
                                                                    EloPlayer eloPlayer = ShowdownELO.playerMap.get(player.getUUID());
                                                                    int elo = eloPlayer.getELORating(levelCap);
                                                                    player.sendMessage(FancyText.getFormattedText("&eYour current ELO rating in the " + levelCap + " level cap is: " + elo), player.getUUID());
                                                                    return 0;

                                                                }

                                                                return 1;

                                                            })
                                            )
                            )
            );

        }

    }

}
