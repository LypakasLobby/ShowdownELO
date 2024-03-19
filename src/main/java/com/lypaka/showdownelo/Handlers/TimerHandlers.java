package com.lypaka.showdownelo.Handlers;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.showdownelo.ConfigGetters;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.*;

public class TimerHandlers {

    public static Timer queueExpirationTimer;
    public static Timer unfairPairingTimer;

    public static Map<ServerPlayerEntity, Integer> playerQueueTimerMap = new HashMap<>();

    public static void startTimers() {

        queueExpirationTimer = new Timer();
        queueExpirationTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                playerQueueTimerMap.entrySet().removeIf(e -> {

                    int amount = e.getValue();
                    if (amount >= ConfigGetters.queueExpirationTime) {

                        e.getKey().sendMessage(FancyText.getFormattedText(ConfigGetters.queueExpiredMessage), e.getKey().getUUID());
                        return true;

                    } else {

                        e.setValue(amount + 1);

                    }

                    return false;

                });

            }

        }, 0, 1000L);

    }

}
