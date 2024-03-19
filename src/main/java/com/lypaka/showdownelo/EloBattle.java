package com.lypaka.showdownelo;

import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import net.minecraft.entity.player.ServerPlayerEntity;

public class EloBattle {

    private final BattleController battleController;
    private final ServerPlayerEntity player1;
    private final ServerPlayerEntity player2;
    private final String levelCap;

    public EloBattle (BattleController battleController, ServerPlayerEntity player1, ServerPlayerEntity player2, String levelCap) {

        this.battleController = battleController;
        this.player1 = player1;
        this.player2 = player2;
        this.levelCap = levelCap;

    }

    public BattleController getBattleController() {

        return this.battleController;

    }

    public ServerPlayerEntity getPlayer1() {

        return this.player1;

    }

    public ServerPlayerEntity getPlayer2() {

        return this.player2;

    }

    public String getLevelCap() {

        return this.levelCap;

    }

}
