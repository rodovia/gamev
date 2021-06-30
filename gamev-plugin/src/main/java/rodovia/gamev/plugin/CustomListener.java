package rodovia.gamev.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

import net.md_5.bungee.api.ChatColor;
import rodovia.gamev.plugin.event.PlayerWinEvent;

/**
 * Listener of custom events
 */
public class CustomListener implements Listener {
	private GamevPlugin plugin;
	
	public CustomListener(GamevPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onWin(PlayerWinEvent event) {
		Location loc = event.getPlayer().getLocation();
		spawnFirework(event.getPlayer(), loc);
		String message = ChatColor.YELLOW + "%s" + ChatColor.GREEN + " chegou até o final! [%sº lugar]";
		
		Bukkit.broadcastMessage(String.format(message, 
				event.getPlayer().getName(), event.getEvent().getWinners().size() + 1));
	}
	
	private void spawnFirework(Player player, Location loc) {
		Location oc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		oc.setY(oc.getBlockY() + 10);
		
		Firework fw = (Firework) player.getWorld().spawnEntity(oc, EntityType.FIREWORK);
		FireworkMeta meta = fw.getFireworkMeta();
		
		meta.setPower(4);
		
		FireworkEffect eff = FireworkEffect.builder().
				flicker(true)
				.withColor(Color.LIME, Color.GREEN)
				.trail(true)
				.withFade(Color.RED)
				.build();
		
		meta.addEffect(eff);
		fw.setFireworkMeta(meta);
		fw.detonate();
	}
	
	
}
