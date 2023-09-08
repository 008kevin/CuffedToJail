package me.kardoskevin07.cuffedtojail;

import com.earth2me.essentials.Essentials;
import me.kardoskevin07.cuffedtojail.commands.HandCuffCommand;
import me.kardoskevin07.cuffedtojail.commands.CarryCommand;
import me.kardoskevin07.cuffedtojail.eventlisteners.EventListeners;
import me.kardoskevin07.cuffedtojail.models.JailSign;
import net.ess3.api.IJails;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class CuffedToJail extends JavaPlugin {
    private static CuffedToJail instance;
    private  static PlayerStateManager pms;

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigurationSerialization.registerClass(JailSign.class, "JailSign");

        instance = this;
        pms = new PlayerStateManager();
        pms = pms.getInstance();

        Essentials essentials = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
        IJails essJails = essentials.getJails();

        JailSignManager.getInstance().createSignsConfig();

        this.getCommand("handcuff").setExecutor(new HandCuffCommand());
        this.getCommand("carry").setExecutor(new CarryCommand());
        this.getServer().getPluginManager().registerEvents(new EventListeners(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CuffedToJail getInstance() {
        return instance;
    }
    public static PlayerStateManager getPMS() { return pms; }
}
