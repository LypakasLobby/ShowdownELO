package com.lypaka.showdownelo.Listeners;

import com.lypaka.showdownelo.EloBattle;
import com.lypaka.showdownelo.Handlers.BattleHandler;
import com.lypaka.showdownelo.Handlers.ELOHandler;
import com.pixelmonmod.pixelmon.api.battles.BattleResults;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BattleEndListener {

    @SubscribeEvent
    public void onBattleEnd (BattleEndEvent event) {

        PlayerParticipant pp1 = null;
        PlayerParticipant pp2 = null;
        BattleController bc = event.getBattleController();

        if (bc.participants.get(0) instanceof PlayerParticipant && bc.participants.get(1) instanceof PlayerParticipant) {

            pp1 = (PlayerParticipant) bc.participants.get(0);
            pp2 = (PlayerParticipant) bc.participants.get(1);

        }

        if (pp1 == null || pp2 == null) return;
        if (!BattleHandler.isELOBattle(bc)) return;

        EloBattle eloBattle = BattleHandler.getELOBattleFromBattleController(bc);
        if (eloBattle == null) return;
        String levelCap = eloBattle.getLevelCap();
        ServerPlayerEntity winner;
        ServerPlayerEntity loser;

        if (pp1.isDefeated) {

            loser = pp1.player;
            winner = pp2.player;

        } else {

            loser = pp2.player;
            winner = pp1.player;

        }

        boolean draw = event.getResult(winner).isPresent() && event.getResult(winner).get() == BattleResults.DRAW;
        ELOHandler.updateELOs(winner, loser, levelCap, draw, bc);

    }

}
