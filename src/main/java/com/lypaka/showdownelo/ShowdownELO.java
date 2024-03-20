package com.lypaka.showdownelo;

import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import com.lypaka.lypakautils.ConfigurationLoaders.PlayerConfigManager;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("showdownelo")
public class ShowdownELO {

    public static final String MOD_ID = "showdownelo";
    public static final String MOD_NAME = "ShowdownELO";
    public static final Logger logger = LogManager.getLogger(MOD_NAME);
    public static BasicConfigManager configManager;
    public static PlayerConfigManager playerConfigManager;
    public static Map<UUID, EloPlayer> playerMap = new HashMap<>();

    /**
     * TODO
     *
     * Look into ***POTENTIALLY*** making a scoreboard/GUI for viewing top players in each level cap/tier
     *
     */

    public ShowdownELO() throws ObjectMappingException {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/showdownelo"));
        String[] files = new String[]{"showdownelo.conf", "ladder.conf"};
        configManager = new BasicConfigManager(files, dir, ShowdownELO.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        playerConfigManager = new PlayerConfigManager("account.conf", "player-accounts", dir, ShowdownELO.class, MOD_NAME, MOD_ID, logger);
        playerConfigManager.init();
        ConfigGetters.load();

    }

}
