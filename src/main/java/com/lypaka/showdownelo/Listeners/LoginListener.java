package com.lypaka.showdownelo.Listeners;

import com.google.common.reflect.TypeToken;
import com.lypaka.showdownelo.EloPlayer;
import com.lypaka.showdownelo.Handlers.BattleHandler;
import com.lypaka.showdownelo.Handlers.TimerHandlers;
import com.lypaka.showdownelo.ShowdownELO;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class LoginListener {

    @SubscribeEvent
    public void onPlayerJoin (PlayerEvent.PlayerLoggedInEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        ShowdownELO.playerConfigManager.loadPlayer(player.getUUID());

        Map<String, Integer> ladderMap = ShowdownELO.playerConfigManager.getPlayerConfigNode(player.getUUID(), "Ladder").getValue(new TypeToken<Map<String, Integer>>() {});
        Map<String, Map<String, Integer>> winLoseRatioMap = ShowdownELO.playerConfigManager.getPlayerConfigNode(player.getUUID(), "Ratio").getValue(new TypeToken<Map<String, Map<String, Integer>>>() {});
        String currentPlayerName = ShowdownELO.playerConfigManager.getPlayerConfigNode(player.getUUID(), "Player-Name").getString();

        EloPlayer eloPlayer = new EloPlayer(player.getUUID(), ladderMap, winLoseRatioMap, currentPlayerName);
        ShowdownELO.playerMap.put(player.getUUID(), eloPlayer);

        if (!currentPlayerName.equalsIgnoreCase(player.getName().getString())) {

            eloPlayer.updatePlayerName(player.getName().getString());

        }

    }

    @SubscribeEvent
    public void onPlayerLeave (PlayerEvent.PlayerLoggedOutEvent event) {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        EloPlayer eloPlayer = ShowdownELO.playerMap.get(player.getUUID());

        eloPlayer.setQueued(false);
        TimerHandlers.useUnfairPairingMap.entrySet().removeIf(e -> e.getKey() == eloPlayer);
        TimerHandlers.playerQueueTimerMap.entrySet().removeIf(e -> e.getKey() == eloPlayer);
        BattleHandler.levelCapQueueMap.entrySet().removeIf(e -> {

            List<EloPlayer> players = e.getValue();
            players.removeIf(entry -> entry.getUUID().toString().equalsIgnoreCase(player.getUUID().toString()));
            e.setValue(players);
            return false;

        });
        ShowdownELO.playerMap.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(eloPlayer.getUUID().toString()));

    }

}
