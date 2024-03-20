package com.lypaka.showdownelo;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;

public class ConfigGetters {

    public static List<String> blacklist;
    public static String defaultTier;
    public static boolean healBeforeBattle;
    public static List<String> levelCaps;
    public static String alreadyBattlingMessage;
    public static String alreadyQueuedMessage;
    public static String blacklistedPokemonMessage;
    public static String eloLoseMessage;
    public static String eloWinMessage;
    public static String levelCapMessage;
    public static String queueExpiredMessage;
    public static String partyFaintedMessage;
    public static String successfulQueueMessage;
    public static int minimumPlayerAmount;
    public static int minimumQueueSize;
    public static int queueExpirationTime;
    public static int unfairPairingTime;

    public static String eloEquation;
    public static String expectedOutcomeEquation;

    public static void load() throws ObjectMappingException {

        blacklist = ShowdownELO.configManager.getConfigNode(0, "Blacklist").getList(TypeToken.of(String.class));
        defaultTier = ShowdownELO.configManager.getConfigNode(0, "Default-Tier").getString();
        healBeforeBattle = ShowdownELO.configManager.getConfigNode(0, "Heal").getBoolean();
        levelCaps = ShowdownELO.configManager.getConfigNode(0, "Level-Caps").getList(TypeToken.of(String.class));
        alreadyBattlingMessage = ShowdownELO.configManager.getConfigNode(0, "Messages", "Already-Battling").getString();
        alreadyQueuedMessage = ShowdownELO.configManager.getConfigNode(0, "Messages", "Already-Queued").getString();
        blacklistedPokemonMessage = ShowdownELO.configManager.getConfigNode(0, "Messages", "Blacklisted-Pokemon").getString();
        eloLoseMessage = ShowdownELO.configManager.getConfigNode(0, "Messages", "ELO-Lose").getString();
        eloWinMessage = ShowdownELO.configManager.getConfigNode(0, "Messages", "ELO-Win").getString();
        levelCapMessage = ShowdownELO.configManager.getConfigNode(0, "Messages", "Level-Cap").getString();
        queueExpiredMessage = ShowdownELO.configManager.getConfigNode(0, "Messages", "Queue-Expired").getString();
        partyFaintedMessage = ShowdownELO.configManager.getConfigNode(0, "Messages", "Party-Fainted").getString();
        successfulQueueMessage = ShowdownELO.configManager.getConfigNode(0, "Messages", "Successfully-Queued").getString();
        minimumPlayerAmount = ShowdownELO.configManager.getConfigNode(0, "Minimum-Player-Amount").getInt();
        minimumQueueSize = ShowdownELO.configManager.getConfigNode(0, "Minimum-Queue-Size").getInt();
        queueExpirationTime = ShowdownELO.configManager.getConfigNode(0, "Queue-Expiration").getInt();
        unfairPairingTime = ShowdownELO.configManager.getConfigNode(0, "Unfair-Pairing").getInt();

        eloEquation = ShowdownELO.configManager.getConfigNode(1, "ELO-Equation").getString();
        expectedOutcomeEquation = ShowdownELO.configManager.getConfigNode(1, "Expected-Outcome-Equation").getString();

    }

}
