package com.lypaka.showdownelo.Handlers;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.showdownelo.API.ELOUpdateEvent;
import com.lypaka.showdownelo.EloPlayer;
import com.lypaka.showdownelo.ConfigGetters;
import com.lypaka.showdownelo.ShowdownELO;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ELOHandler {

    public static void updateELOs (ServerPlayerEntity winner, ServerPlayerEntity loser, String levelCap, boolean draw) {

        EloPlayer winnerELO = ShowdownELO.playerMap.get(winner.getUUID());
        EloPlayer loserELO = ShowdownELO.playerMap.get(loser.getUUID());

        int currentWinnerELORating = winnerELO.getELORating(levelCap);
        int currentLoserELORating = loserELO.getELORating(levelCap);
        int winnerScale = getScale(currentWinnerELORating, true);
        int loserScale = getScale(currentLoserELORating, false);

        Expression winnerExpression = new ExpressionBuilder(ConfigGetters.expectedOutcomeEquation
                .replace("%oldRatePlayerA%", String.valueOf(currentWinnerELORating))
                .replace("%oldRatePlayerB%", String.valueOf(currentLoserELORating))
        ).build();
        double winnerExpectedOutcome = winnerExpression.evaluate();
        double winnerActualOutcome = draw ? 0.5 : 1;

        Expression loserExpression = new ExpressionBuilder(ConfigGetters.expectedOutcomeEquation
                .replace("%oldRatePlayerA%", String.valueOf(currentLoserELORating))
                .replace("%oldRatePlayerB%", String.valueOf(currentWinnerELORating))
        ).build();
        double loserExpectedOutcome = loserExpression.evaluate();
        double loserActualOutcome = draw ? 0.5 : 0;

        Expression winnerELOExpression = new ExpressionBuilder(ConfigGetters.eloEquation
                .replace("%oldRate%", String.valueOf(currentWinnerELORating))
                .replace("%scale%", String.valueOf(winnerScale))
                .replace("%actualOutcome%", String.valueOf(winnerActualOutcome))
                .replace("%expectedOutcome%", String.valueOf(winnerExpectedOutcome))
        ).build();
        int newWinnerELO = (int) winnerELOExpression.evaluate();
        Expression loserELOExpression = new ExpressionBuilder(ConfigGetters.eloEquation
                .replace("%oldRate%", String.valueOf(currentLoserELORating))
                .replace("%scale%", String.valueOf(loserScale))
                .replace("%actualOutcome%", String.valueOf(loserActualOutcome))
                .replace("%expectedOutcome%", String.valueOf(loserExpectedOutcome))
        ).build();
        int newLoserELO = Math.max(0, (int) loserELOExpression.evaluate());

        winnerELO.updateELO(levelCap, newWinnerELO);
        loserELO.updateELO(levelCap, newLoserELO);
        if (!draw) {

            winnerELO.increaseWins(levelCap);
            loserELO.increaseLoss(levelCap);

        }

        winnerELO.save();
        loserELO.save();

        ELOUpdateEvent winnerUpdateEvent = new ELOUpdateEvent(winner, currentWinnerELORating, newWinnerELO);
        MinecraftForge.EVENT_BUS.post(winnerUpdateEvent);
        ELOUpdateEvent loserUpdateEvent = new ELOUpdateEvent(loser, currentLoserELORating, newLoserELO);
        MinecraftForge.EVENT_BUS.post(loserUpdateEvent);

        winner.sendMessage(FancyText.getFormattedText("&eELO: " + currentWinnerELORating + " --> " + newWinnerELO), winner.getUUID());
        loser.sendMessage(FancyText.getFormattedText("&eELO: " + currentLoserELORating + " --> " + newLoserELO), loser.getUUID());

    }

    private static int getScale (int elo, boolean winner) {

        int scale;
        if (elo == 1000) {

            scale = winner ? 80 : 20;

        } else if (elo >= 1001 && elo <= 1099) {

            scale = getComplexScale(elo, winner);

        } else if (elo >= 1100 && elo <= 1299) {

            scale = 50;

        } else if (elo >= 1300 && elo <= 1599) {

            scale = 40;

        } else {

            scale = 32;

        }

        return scale;

    }

    private static int getComplexScale (int elo, boolean winner) {

        int scale = winner ? 80 : 20;
        switch (elo) {

            case 1007:
            case 1008:
            case 1009:
                scale = winner ? 79 : 21;
                break;

            case 1010:
            case 1011:
            case 1012:
                scale = winner ? 78 : 22;
                break;

            case 1013:
            case 1014:
            case 1015:
                scale = winner ? 77 : 23;
                break;

            case 1016:
            case 1017:
            case 1018:
                scale = winner ? 76 : 24;
                break;

            case 1019:
            case 1020:
            case 1021:
                scale = winner ? 75 : 25;
                break;

            case 1022:
            case 1023:
            case 1024:
                scale = winner ? 74 : 26;
                break;

            case 1025:
            case 1026:
            case 1027:
                scale = winner ? 73 : 27;
                break;

            case 1028:
            case 1029:
            case 1030:
                scale = winner ? 72 : 28;
                break;

            case 1031:
            case 1032:
            case 1033:
                scale = winner ? 71 : 29;
                break;

            case 1034:
            case 1035:
            case 1036:
                scale = winner ? 70 : 30;
                break;

            case 1037:
            case 1038:
            case 1039:
                scale = winner ? 69 : 31;
                break;

            case 1040:
            case 1041:
            case 1042:
                scale = winner ? 68 : 32;
                break;

            case 1043:
            case 1044:
            case 1045:
                scale = winner ? 67 : 33;
                break;

            case 1046:
            case 1047:
            case 1048:
                scale = winner ? 66 : 34;
                break;

            case 1049:
            case 1050:
            case 1051:
                scale = winner ? 65 : 35;
                break;

            case 1052:
            case 1053:
            case 1054:
                scale = winner ? 64 : 36;
                break;

            case 1055:
            case 1056:
            case 1057:
                scale = winner ? 63 : 37;
                break;

            case 1058:
            case 1059:
            case 1060:
                scale = winner ? 62 : 38;
                break;

            case 1061:
            case 1062:
            case 1063:
                scale = winner ? 61 : 39;
                break;

            case 1064:
            case 1065:
            case 1066:
                scale = winner ? 60 : 40;
                break;

            case 1067:
            case 1068:
            case 1069:
                scale = winner ? 59 : 41;
                break;

            case 1070:
            case 1071:
            case 1072:
                scale = winner ? 58 : 42;
                break;

            case 1073:
            case 1074:
            case 1075:
                scale = winner ? 57 : 43;
                break;

            case 1076:
            case 1077:
            case 1078:
                scale = winner ? 56 : 44;
                break;

            case 1079:
            case 1080:
            case 1081:
                scale = winner ? 55 : 45;
                break;

            case 1082:
            case 1083:
            case 1084:
                scale = winner ? 54 : 46;
                break;

            case 1085:
            case 1086:
            case 1087:
                scale = winner ? 53 : 47;
                break;

            case 1088:
            case 1089:
            case 1090:
                scale = winner ? 52 : 48;
                break;

            case 1091:
            case 1092:
            case 1093:
                scale = winner ? 51 : 49;
                break;

            case 1094:
            case 1095:
            case 1096:
            case 1097:
            case 1098:
            case 1099:
                scale = 50;
                break;

        }

        return scale;

    }

}
