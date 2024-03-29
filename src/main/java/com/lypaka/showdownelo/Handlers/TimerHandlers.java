package com.lypaka.showdownelo.Handlers;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.Listeners.JoinListener;
import com.lypaka.lypakautils.MiscHandlers.PixelmonHelpers;
import com.lypaka.showdownelo.ConfigGetters;
import com.lypaka.showdownelo.EloBattle;
import com.lypaka.showdownelo.EloPlayer;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.api.rules.BattleRules;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.*;

public class TimerHandlers {

    public static Timer queueExpirationTimer;
    public static Timer pairingTimer;
    public static Map<EloPlayer, Boolean> useUnfairPairingMap = new HashMap<>();
    public static Map<EloPlayer, Integer> playerQueueTimerMap = new HashMap<>();

    public static void startTimers() {

        pairingTimer = new Timer();
        pairingTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                for (Map.Entry<String, List<EloPlayer>> entry : BattleHandler.levelCapQueueMap.entrySet()) {

                    String levelCap = entry.getKey();
                    List<EloPlayer> players = entry.getValue();

                    ArrayList<EloPlayer> copy = new ArrayList<>(players);
                    if (copy.size() < ConfigGetters.minimumQueueSize) continue;
                    do {

                        EloPlayer random1 = RandomHelper.getRandomElementFromList(copy);
                        ArrayList<EloPlayer> copy2 = new ArrayList<>(copy);
                        copy2.removeIf(e -> e == random1);
                        EloPlayer random2 = RandomHelper.getRandomElementFromList(copy2);
                        boolean useUnfairPairing = useUnfairPairingMap.containsKey(random1) && useUnfairPairingMap.get(random1) && useUnfairPairingMap.containsKey(random2) && useUnfairPairingMap.get(random2);
                        if (Math.abs(random1.getELORating(levelCap) - random2.getELORating(levelCap)) <= 150 || useUnfairPairing) {

                            ServerPlayerEntity p1 = JoinListener.playerMap.get(random1.getUUID());
                            List<Pokemon> team1 = PixelmonHelpers.getTeam(p1);
                            ServerPlayerEntity p2 = JoinListener.playerMap.get(random2.getUUID());
                            List<Pokemon> team2 = PixelmonHelpers.getTeam(p2);

                            if (!TeamValidator.passesGeneralChecks(p1, levelCap, false)) {

                                p1.sendMessage(FancyText.getFormattedText(ConfigGetters.partyFaintedMessage), p1.getUUID());
                                continue;

                            }
                            if (!TeamValidator.passesGeneralChecks(p2, levelCap, false)) {

                                p2.sendMessage(FancyText.getFormattedText(ConfigGetters.partyFaintedMessage), p2.getUUID());
                                continue;

                            }

                            if (TeamValidator.teamPassesLevelCapRequirements(team1, levelCap) && TeamValidator.teamPassesLevelCapRequirements(team2, levelCap)) {

                                if (TeamValidator.teamPassesBlacklist(team1) && TeamValidator.teamPassesBlacklist(team2)) {

                                    if (BattleRegistry.getBattle(p1) == null && BattleRegistry.getBattle(p2) == null) {

                                        PlayerPartyStorage storage1 = StorageProxy.getParty(p1);
                                        PlayerPartyStorage storage2 = StorageProxy.getParty(p2);
                                        if (ConfigGetters.healBeforeBattle) {

                                            storage1.heal();
                                            storage2.heal();

                                        }
                                        BattleParticipant[] bp1 = new BattleParticipant[]{new PlayerParticipant(p1, storage1.getFirstBattleReadyPokemon())};
                                        BattleParticipant[] bp2 = new BattleParticipant[]{new PlayerParticipant(p2, storage2.getFirstBattleReadyPokemon())};
                                        BattleController battle = new BattleController(bp1, bp2, new BattleRules());
                                        BattleHandler.createEloBattle(battle, p1, p2, levelCap);
                                        BattleRegistry.startBattle(bp1, bp2, new BattleRules());
                                        copy.removeIf(e -> e == random1);
                                        copy.removeIf(e -> e == random2);

                                        // remove from queue
                                        random1.setQueued(false);
                                        random2.setQueued(false);
                                        useUnfairPairingMap.entrySet().removeIf(e -> e.getKey() == random1);
                                        useUnfairPairingMap.entrySet().removeIf(e -> e.getKey() == random2);
                                        playerQueueTimerMap.entrySet().removeIf(e -> e.getKey() == random1);
                                        playerQueueTimerMap.entrySet().removeIf(e -> e.getKey() == random2);


                                    } else {

                                        if (BattleRegistry.getBattle(p1) != null) {

                                            p1.sendMessage(FancyText.getFormattedText(ConfigGetters.alreadyBattlingMessage), p1.getUUID());

                                        }
                                        if (BattleRegistry.getBattle(p2) != null) {

                                            p2.sendMessage(FancyText.getFormattedText(ConfigGetters.alreadyBattlingMessage), p2.getUUID());

                                        }

                                    }

                                } else {

                                    if (!TeamValidator.teamPassesBlacklist(team1)) {

                                        p1.sendMessage(FancyText.getFormattedText(ConfigGetters.blacklistedPokemonMessage), p1.getUUID());

                                    }
                                    if (!TeamValidator.teamPassesBlacklist(team2)) {

                                        p2.sendMessage(FancyText.getFormattedText(ConfigGetters.blacklistedPokemonMessage), p2.getUUID());

                                    }

                                }

                            } else {

                                if (!TeamValidator.teamPassesLevelCapRequirements(team1, levelCap)) {

                                    p1.sendMessage(FancyText.getFormattedText(ConfigGetters.levelCapMessage), p1.getUUID());

                                }
                                if (!TeamValidator.teamPassesLevelCapRequirements(team2, levelCap)) {

                                    p2.sendMessage(FancyText.getFormattedText(ConfigGetters.levelCapMessage), p2.getUUID());

                                }

                            }

                        }

                    } while (copy.size() % 2 == 0);

                    // update the levelCapQueueMap
                    BattleHandler.levelCapQueueMap.put(entry.getKey(), copy);

                }

            }

        }, 0, 1000L);

        queueExpirationTimer = new Timer();
        queueExpirationTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                playerQueueTimerMap.entrySet().removeIf(e -> {

                    int amount = e.getValue();
                    if (amount >= ConfigGetters.unfairPairingTime) {

                        if (!useUnfairPairingMap.containsKey(e.getKey())) {

                            useUnfairPairingMap.put(e.getKey(), true);

                        }

                    }
                    if (amount >= ConfigGetters.queueExpirationTime) {

                        ServerPlayerEntity player = JoinListener.playerMap.get(e.getKey().getUUID());
                        player.sendMessage(FancyText.getFormattedText(ConfigGetters.queueExpiredMessage), e.getKey().getUUID());
                        return true;

                    } else {

                        e.setValue(amount + 1);

                    }

                    return false;

                });

            }

        }, 0, 1000L);

    }

}
