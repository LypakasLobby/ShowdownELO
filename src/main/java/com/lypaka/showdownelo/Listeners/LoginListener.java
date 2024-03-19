package com.lypaka.showdownelo.Listeners;

import com.google.common.reflect.TypeToken;
import com.lypaka.showdownelo.EloPlayer;
import com.lypaka.showdownelo.ShowdownELO;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

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

}
