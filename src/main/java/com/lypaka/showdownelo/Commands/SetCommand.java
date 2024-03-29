package com.lypaka.showdownelo.Commands;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.lypaka.showdownelo.ConfigGetters;
import com.lypaka.showdownelo.EloPlayer;
import com.lypaka.showdownelo.ShowdownELO;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Arrays;

public class SetCommand {

    public SetCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : ShowdownELOCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("set")
                                            .then(
                                                    Commands.argument("player", EntityArgument.players())
                                                            .then(
                                                                    Commands.argument("tier", StringArgumentType.word())
                                                                            .suggests(
                                                                                    ((context, builder) -> ISuggestionProvider.suggest(ConfigGetters.levelCaps, builder))
                                                                            )
                                                                            .then(
                                                                                    Commands.argument("elo", IntegerArgumentType.integer(1000))
                                                                                            .executes(c -> {

                                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                                    if (!PermissionHandler.hasPermission(player, "showdownelo.command.admin")) {

                                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUUID());
                                                                                                        return 0;

                                                                                                    }

                                                                                                }

                                                                                                ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                                String tier = StringArgumentType.getString(c, "tier");
                                                                                                int elo = IntegerArgumentType.getInteger(c, "elo");
                                                                                                EloPlayer eloPlayer = ShowdownELO.playerMap.get(target.getUUID());
                                                                                                eloPlayer.updateELO(tier, elo);
                                                                                                c.getSource().sendSuccess(FancyText.getFormattedText("&aSuccessfully set " + target.getName().getString() + "'s ELO in " + tier + " to " + elo + "!"), true);
                                                                                                return 1;

                                                                                            })
                                                                            )
                                                            )
                                            )
                            )
            );

        }

    }

}
