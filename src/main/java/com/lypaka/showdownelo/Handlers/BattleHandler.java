package com.lypaka.showdownelo.Handlers;

import com.lypaka.showdownelo.EloBattle;
import com.lypaka.showdownelo.EloPlayer;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleHandler {

    public static List<EloBattle> eloBattles = new ArrayList<>();
    public static Map<String, List<EloPlayer>> levelCapQueueMap = new HashMap<>();
    public static Map<String, Map<EloPlayer, EloPlayer>> battleQueueMap = new HashMap<>();

    public static boolean isELOBattle (BattleController bc) {

        boolean is = false;
        for (EloBattle battle : eloBattles) {

            if (battle.getBattleController() == bc) {

                is = true;
                break;

            }

        }

        return is;

    }

    public static EloBattle getELOBattleFromBattleController (BattleController bc) {

        EloBattle battle = null;
        for (EloBattle b : eloBattles) {

            if (b.getBattleController() == bc) {

                battle = b;
                break;

            }

        }

        return battle;

    }

    public static void createEloBattle (BattleController bc, ServerPlayerEntity player1, ServerPlayerEntity player2, String levelCap) {

        EloBattle battle = new EloBattle(bc, player1, player2, levelCap);
        eloBattles.add(battle);

    }

    public static void removeEloBattle (BattleController bc) {

        eloBattles.removeIf(e -> e.getBattleController() == bc);

    }

}
