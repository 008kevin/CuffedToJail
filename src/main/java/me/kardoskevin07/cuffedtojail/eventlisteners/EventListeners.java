package me.kardoskevin07.cuffedtojail.eventlisteners;

import me.kardoskevin07.cuffedtojail.CuffedToJail;
import me.kardoskevin07.cuffedtojail.JailSignManager;
import me.kardoskevin07.cuffedtojail.PlayerStateManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSignOpenEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.Objects;

public class EventListeners implements Listener {

    private final CuffedToJail main = CuffedToJail.getInstance();
    private final PlayerStateManager psm = CuffedToJail.getPMS();
    private final JailSignManager jsm = JailSignManager.getInstance();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.getPlayer().isInsideVehicle()) {
            event.setCancelled(psm.isHandcuffed(event.getPlayer()));
        }
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        // TODO: Verify if entity is player

        if (psm.isRiding((Player) event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Block block = event.getPlayer().getTargetBlock(null, 5);
        BlockData blockData = block.getBlockData();

        if (blockData instanceof Sign || blockData instanceof WallSign) {
            Location location = block.getLocation();
            main.getLogger().info("Sign clicked");

            if (!jsm.isJailSign(location)) return;

            main.getLogger().info("jailsign clicked");
        }
        main.getLogger().info(event.getPlayer().getTargetBlock(null, 5).getBlockData().getAsString());
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.getLine(0).equals("[JailSign]")) {
            if (event.getLine(1).equals("")) {
                main.getLogger().info("Second line null");
                return;
            }
            main.getLogger().info("JailSign placed");
            JailSignManager.getInstance().createJailSign(event.getBlock().getLocation(), event.getLine(1));
            return;
        }
        main.getLogger().info("not jail sign");
    }

    @EventHandler
    public void onSignChange(PlayerSignOpenEvent event) {
        if(jsm.isJailSign(event.getSign().getBlock().getLocation())) {
            main.getLogger().info("Jail sign interaction tried, cancelled");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        if (jsm.isJailSign(location)) {
            jsm.removeJailSign(location);
            main.getLogger().info("Jailsign removed");
        }
    }
}
