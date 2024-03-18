package com.lypaka.showdownelo.Utils;

import com.lypaka.showdownelo.ConfigGetters;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

import java.util.List;

public class TeamValidator {

    public static boolean teamPasses (List<Pokemon> team) {

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

        }

        return passes;

    }

}
