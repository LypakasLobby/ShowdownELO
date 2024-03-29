package com.lypaka.showdownelo.API;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

@Cancelable
public class QueueEvent extends Event {

    private final ServerPlayerEntity player;
    private final List<Pokemon> team;
    private final String levelCap;

    public QueueEvent (ServerPlayerEntity player, List<Pokemon> team, String levelCap) {

        this.player = player;
        this.team = team;
        this.levelCap = levelCap;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public List<Pokemon> getTeam() {

        return this.team;

    }

    public String getLevelCap() {

        return this.levelCap;

    }

}
