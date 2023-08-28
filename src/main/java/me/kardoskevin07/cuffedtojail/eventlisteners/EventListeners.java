package me.kardoskevin07.cuffedtojail.eventlisteners;

import me.kardoskevin07.cuffedtojail.CuffedToJail;
import me.kardoskevin07.cuffedtojail.PlayerStateManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EventListeners implements Listener {

    private final CuffedToJail main = CuffedToJail.getInstance();
    private final PlayerStateManager psm = CuffedToJail.getPMS();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.getPlayer().isInsideVehicle()) {
            event.setCancelled(psm.isHandcuffed(event.getPlayer()));
        }
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        if (psm.isRiding((Player) event.getEntity())) {
            event.setCancelled(true);
        }
    }
}
