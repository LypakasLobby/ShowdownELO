package com.lypaka.showdownelo.Handlers;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.showdownelo.API.QueueEvent;
import com.lypaka.showdownelo.ConfigGetters;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class TeamValidator {

    public static boolean passesGeneralChecks (ServerPlayerEntity player, String levelCap, boolean isQueueing) {

        PlayerPartyStorage storage = StorageProxy.getParty(player);
        if (isQueueing) {

            List<Pokemon> team = storage.getTeam();
            QueueEvent event = new QueueEvent(player, team, levelCap);
            MinecraftForge.EVENT_BUS.post(event);
            return !event.isCanceled();

        }
        if (storage.getFirstBattleReadyPokemon() == null) {

            player.sendMessage(FancyText.getFormattedText(ConfigGetters.partyFaintedMessage), player.getUUID());
            return false; // player has fainted party

        }

        return true;

    }

    public static boolean teamPassesLevelCapRequirements (List<Pokemon> team, String levelCap) {

        if (levelCap.equalsIgnoreCase("open-level") || levelCap.equalsIgnoreCase("open level")) return true;
        int level = Integer.parseInt(levelCap);

        boolean passes = true;
        for (Pokemon p : team) {

            if (p.getPokemonLevel() > level) {

                passes = false;
                break;

            }

        }

        return passes;

    }

    public static boolean teamPassesBlacklist (List<Pokemon> team) {

        boolean passes = true;
        for (Pokemon p : team) {

            if (ConfigGetters.blacklist.contains("legendaries") && p.isLegendary()) {

                passes = false;
                break;

            }
            if (ConfigGetters.blacklist.contains("mythicals") && p.isMythical()) {

                passes = false;
                break;

            }
            if (ConfigGetters.blacklist.contains("ultra beasts") && p.isUltraBeast()) {

                passes = false;
                break;

            }
            if (ConfigGetters.blacklist.contains("NBT:")) {

                for (String s : ConfigGetters.blacklist) {

                    if (s.contains("NBT:")) {

                        String tag = s.replace("NBT:", "");
                        if (p.getPersistentData().contains(tag)) {

                            passes = false;
                            break;

                        }

                    }

                }
                if (!passes) break;

            }
            if (ConfigGetters.blacklist.contains("species:") || ConfigGetters.blacklist.contains("Species:")) {

                for (String s : ConfigGetters.blacklist) {

                    if (s.contains("Species:") || s.contains("species:")) {

                        String species = s.replace("Species:", "").replace("species:", "");
                        if (p.getSpecies().getName().equalsIgnoreCase(species)) {

                            passes = false;
                            break;

                        }

                    }

                }

            }

        }

        return passes;

    }

}
