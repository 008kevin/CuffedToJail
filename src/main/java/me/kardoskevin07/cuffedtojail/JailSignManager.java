package me.kardoskevin07.cuffedtojail;

import me.kardoskevin07.cuffedtojail.models.JailSign;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class JailSignManager {

    static JailSignManager INSTANCE = null;
    private final CuffedToJail main = CuffedToJail.getInstance();

    private File signsConfigFile;
    private FileConfiguration signsConfig;
    private List<JailSign> signList = new ArrayList<>();


    private JailSignManager() {
    }

    public static JailSignManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new JailSignManager();
        }

        return INSTANCE;
    }



    public void createJailSign(Location location, String jail) {
        signList.add(new JailSign(location, jail));
        signsConfig.set("signs", signList);

        saveSignsConfig();
    }
    public void removeJailSign(Location location) {
        for (JailSign jailSign : signList) {
            if (jailSign.getLocation().equals(location)) {
                signList.remove(jailSign);
                signsConfig.set("signs", signList);

                saveSignsConfig();
                break;
            }
        }
    }
    public Boolean isJailSign(Location location) {
        for (JailSign jailSign : signList) {
            if (jailSign.getLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }



    public FileConfiguration getSignsConfig() {
        return this.signsConfig;
    }
    public void createSignsConfig() {
        signsConfigFile = new File(main.getDataFolder(),"signs.yml");
        if (!signsConfigFile.exists()) {
            signsConfigFile.getParentFile().mkdirs();
            main.saveResource("signs.yml", false);
        }

        signsConfig = new YamlConfiguration();
        signsConfig = YamlConfiguration.loadConfiguration(signsConfigFile);

        // loadSignConfig();
    }
    public void saveSignsConfig() {
        try {
            signsConfig.save(signsConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadSignConfig() {
        main.getLogger().severe("loading signs");
        List<JailSign> jailSignList = (List<JailSign>) getSignsConfig().getList("signs");
        for (JailSign jailSign : jailSignList) {
            main.getLogger().info(jailSign.getJailName());
        }
        main.getLogger().severe("finished loading signs " + jailSignList.size());
    }

}
