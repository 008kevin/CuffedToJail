package me.kardoskevin07.cuffedtojail.commands;

import me.kardoskevin07.cuffedtojail.CuffedToJail;
import me.kardoskevin07.cuffedtojail.PlayerStateManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CarryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        CuffedToJail main = CuffedToJail.getInstance();
        PlayerStateManager psm = CuffedToJail.getPMS();

        // arg length test
        if (strings.length != 1) {
            return false;
        }

        // Player is null, self, and online test
        Player rider = main.getServer().getPlayer(strings[0]);
        Player sender = (Player) commandSender;
        if (rider == null) {
            return false;
        }
        if (rider.equals(sender)) {
            return false;
        }
        if (!rider.isOnline()) {
            return false;
        }

        //location check
        if (sender.getLocation().distance(rider.getLocation()) > 10) {
            return false;
        }

        // is being ridden and is that person 'rider'
        if (psm.isRidden(sender)) {
            main.getLogger().info("vehicle is ridden");
            if (psm.getRider(sender).equals(rider)) {
                psm.setRiding(sender, rider, false);
                main.getLogger().info("Rider equals 'rider'");
                return true;
            }
            main.getLogger().info("Rider doesn't equal 'rider' ");
            return false;
        }

        psm.setRiding(sender, rider, true);
        return true;
    }
}
