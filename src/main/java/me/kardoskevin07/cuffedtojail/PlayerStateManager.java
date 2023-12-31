package me.kardoskevin07.cuffedtojail;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class PlayerStateManager {

    private List<Player> handCuffedPlayerList = new ArrayList<>();
    private HashMap<Player, Player> ridingMap = new HashMap<>();
    private final PlayerStateManager instance;
    private final CuffedToJail main = CuffedToJail.getInstance();
    private Scoreboard scoreboard = null;
    private final String teamName = "Players";

    public PlayerStateManager() {
        instance = this;
    }

    public PlayerStateManager getInstance() {
        return instance;
    }

    public void setHandcuffed(Player player, Boolean state) {
        if(Boolean.TRUE.equals(state)) {
            // if list doesn't contain name already
            if (handCuffedPlayerList.stream().noneMatch(o -> o.equals(player))) {
                handCuffedPlayerList.add(player);
                main.getLogger().info(player.getName() + "not found in list");
            }
            main.getLogger().info("adding" + player.getName() + "as handcuffed");
        }else {
            handCuffedPlayerList.remove(player);
            main.getLogger().info("removing" + player.getName() + "as handcuffed");
        }
    }
    public boolean isHandcuffed(Player player) {
        return handCuffedPlayerList.stream().anyMatch(o -> o.equals(player));
    }



    public void setRiding(Player vehicle, Player rider, Boolean state) {
        if (Boolean.TRUE.equals(state)) {
            if (ridingMap.containsValue(rider)) {
                return;
            }
            if (ridingMap.containsKey(vehicle)) {
                return;
            }


            if (scoreboard == null) {
                ScoreboardManager sbm = Bukkit.getScoreboardManager();
                scoreboard = sbm.getNewScoreboard();
                main.getLogger().info("scoreboard did not exist");
            }
            if (scoreboard.getTeam(teamName) == null) {
                scoreboard.registerNewTeam(teamName);
                main.getLogger().info("team did not exist");
            }
            Team team = scoreboard.getTeam(teamName);
            team.setCanSeeFriendlyInvisibles(true);
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                p.setScoreboard(scoreboard);
                team.addEntry(p.getName());
                main.getLogger().info(p.getName());
            }

            vehicle.addPassenger(rider);
            ridingMap.put(vehicle,rider);

            // TODO: packet based invisibility

            rider.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15));

        } else {
            if (ridingMap.get(vehicle).equals(rider)) {
                ridingMap.remove(vehicle);
                vehicle.removePassenger(rider);
                rider.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
        }
    }
    public boolean isRiding(Player rider) {
        return ridingMap.containsValue(rider);
    }
    public boolean isRidden(Player vehicle) {
        return ridingMap.containsKey(vehicle);
    }
    public Player getRider(Player vehicle) {
        if (ridingMap.containsKey(vehicle)) {
            return ridingMap.get(vehicle);
        }
        return null;
    }

}