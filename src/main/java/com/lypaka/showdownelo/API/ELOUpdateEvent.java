package com.lypaka.showdownelo.API;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class ELOUpdateEvent extends Event {

    private final ServerPlayerEntity player;
    private final int eloFrom;
    private final int eloTo;

    public ELOUpdateEvent (ServerPlayerEntity player, int from, int to) {

        this.player = player;
        this.eloFrom = from;
        this.eloTo = to;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public int getPreviousELO() {

        return this.eloFrom;

    }

    public int getNewELO() {

        return this.eloTo;

    }

}
