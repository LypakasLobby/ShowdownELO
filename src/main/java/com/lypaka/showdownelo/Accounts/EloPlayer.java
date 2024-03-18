package com.lypaka.showdownelo.Accounts;

import com.lypaka.showdownelo.ShowdownELO;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EloPlayer {

    private final UUID uuid;
    private final Map<String, Integer> ladderMap;
    private final Map<String, Map<String, Integer>> winLoseRatioMap;
    private String currentPlayerName;

    public EloPlayer (UUID uuid, Map<String, Integer> ladderMap, Map<String, Map<String, Integer>> winLoseRatioMap, String currentPlayerName) {

        this.uuid = uuid;
        this.ladderMap = ladderMap;
        this.winLoseRatioMap = winLoseRatioMap;
        this.currentPlayerName = currentPlayerName;

    }

    public UUID getUUID() {

        return this.uuid;

    }

    public Map<String, Integer> getLadderMap() {

        return this.ladderMap;

    }

    public Map<String, Map<String, Integer>> getWinLoseRatioMap() {

        return this.winLoseRatioMap;

    }

    public String getCurrentPlayerName() {

        return this.currentPlayerName;

    }

    public void setCurrentPlayerName (String name) {

        this.currentPlayerName = name;

    }

    public int getELORating (String levelCap) {

        int elo = 1000;
        if (this.ladderMap.containsKey(levelCap)) {

            elo = this.ladderMap.get(levelCap);

        }
        return elo;

    }

    public int getWins (String levelCap) {

        int wins = 0;
        if (this.winLoseRatioMap.containsKey(levelCap)) {

            wins = this.winLoseRatioMap.get(levelCap).get("Win");

        }
        return wins;

    }

    public int getLoses (String levelCap) {

        int losses = 0;
        if (this.winLoseRatioMap.containsKey(levelCap)) {

            losses = this.winLoseRatioMap.get(levelCap).get("Loss");

        }
        return losses;

    }

    public void updateELO (String levelCap, int value) {

        this.ladderMap.put(levelCap, value);

    }

    public void increaseWins (String levelCap) {

        int wins = getWins(levelCap);
        int next = wins + 1;
        Map<String, Integer> map;
        if (this.winLoseRatioMap.containsKey(levelCap)) {

            map = this.winLoseRatioMap.get(levelCap);

        } else {

            map = new HashMap<>();
            map.put("Win", 0);
            map.put("Loss", 0);

        }
        map.put("Win", next);

    }

    public void increaseLoss (String levelCap) {

        int losses = getLoses(levelCap);
        int next = losses + 1;
        Map<String, Integer> map;
        if (this.winLoseRatioMap.containsKey(levelCap)) {

            map = this.winLoseRatioMap.get(levelCap);

        } else {

            map = new HashMap<>();
            map.put("Win", 0);
            map.put("Loss", 0);

        }
        map.put("Loss", next);

    }

    public void save() {

        ShowdownELO.playerConfigManager.getPlayerConfigNode(this.uuid, "Ladder").setValue(this.ladderMap);
        ShowdownELO.playerConfigManager.getPlayerConfigNode(this.uuid, "Ratio").setValue(this.winLoseRatioMap);
        ShowdownELO.playerConfigManager.getPlayerConfigNode(this.uuid, "Player-Name").setValue(this.currentPlayerName);
        ShowdownELO.playerConfigManager.savePlayer(this.uuid);

    }

    public void updatePlayerName (String newName) {

        setCurrentPlayerName(newName);
        ShowdownELO.playerConfigManager.getPlayerConfigNode(this.uuid, "Player-Name").setValue(newName);
        ShowdownELO.playerConfigManager.savePlayer(this.uuid);

    }

}
