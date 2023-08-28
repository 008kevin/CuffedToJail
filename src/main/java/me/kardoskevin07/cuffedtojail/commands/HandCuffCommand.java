package me.kardoskevin07.cuffedtojail.commands;

import me.kardoskevin07.cuffedtojail.CuffedToJail;
import me.kardoskevin07.cuffedtojail.PlayerStateManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HandCuffCommand implements CommandExecutor {
    CuffedToJail main = CuffedToJail.getInstance();
    PlayerStateManager pms = CuffedToJail.getPMS();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // arg length test
        if (strings.length != 1) {
            return false;
        }

        // Player is null, self, and online test
        Player handcuffed = main.getServer().getPlayer(strings[0]);
        Player sender = (Player) commandSender;
        if (handcuffed == null) {
            return false;
        }
        if (handcuffed.equals(sender)) {
            return false;
        }
        if (!handcuffed.isOnline()) {
            return false;
        }

        //location check
        if (sender.getLocation().distance(handcuffed.getLocation()) > 10) {
            return false;
        }

        pms.setHandcuffed(handcuffed, !pms.isHandcuffed(handcuffed));
        main.getLogger().info(handcuffed.getDisplayName() + "is online and nearby");

        return true;
    }
}
