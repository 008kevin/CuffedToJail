package me.kardoskevin07.cuffedtojail;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class PlayerStateManager {

    private List<Player> handCuffedPlayerList = new ArrayList<>();
    private HashMap<Player, Player> ridingMap = new HashMap<>();
    private final PlayerStateManager instance;
    private final CuffedToJail main = CuffedToJail.getInstance();

    public PlayerStateManager() {
        instance = this;
    }

    public PlayerStateManager getInstance() {
        return instance;
    }

    public void setHandcuffed(Player player, Boolean state) {
        if(state) {
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
        if (state) {
            if (ridingMap.containsValue(rider)) {
                return;
            }
            if (ridingMap.containsKey(vehicle)) {
                return;
            }
            vehicle.addPassenger(rider);
            ridingMap.put(vehicle,rider);
        } else {
            if (ridingMap.get(vehicle).equals(rider)) {
                ridingMap.remove(vehicle);
                vehicle.removePassenger(rider);
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