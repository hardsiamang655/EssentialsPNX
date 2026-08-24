package cn.yescallop.essentialsnk;

import org.powernukkitx.Player;
import org.powernukkitx.block.Block;
import org.powernukkitx.command.CommandSender;
import org.powernukkitx.event.EventHandler;
import org.powernukkitx.event.Listener;
import org.powernukkitx.event.player.*;
import org.powernukkitx.level.Location;

import java.util.Iterator;

public class EventListener implements Listener {

    private final EssentialsAPI api;

    public EventListener(EssentialsAPI api) {
        this.api = api;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        api.setLastLocation(event.getPlayer(), event.getFrom());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Block bed = event.getBed();
        api.setHome(event.getPlayer(), "bed", Location.fromObject(bed, bed.level, 0, 0));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean vanished = api.isVanished(player);
        for (Player p : api.getServer().getOnlinePlayers().values()) {
            if (api.isVanished(p)) {
                player.hidePlayer(p);
            }
            if (vanished) {
                p.hidePlayer(player);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        api.removeTPRequest(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        Iterator<CommandSender> iter = event.getRecipients().iterator();

        while (iter.hasNext()) {
            CommandSender sender = iter.next();
            if (!(sender instanceof Player)) {
                continue;
            }

            if (api.isIgnoring(((Player) sender).getUniqueId(), player.getUniqueId())) {
                iter.remove();
            }
        }

        if (api.isMuted(player)) {
            event.setCancelled();
            player.sendMessage(Language.translate("commands.generic.muted", api.getUnmuteTimeMessage(player)));
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (api.isMuted(player)) {
            event.setCancelled();
            player.sendMessage(Language.translate("commands.generic.muted", api.getUnmuteTimeMessage(player)));
        }
    }
}