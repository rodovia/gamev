package rodovia.gamev.plugin;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import net.md_5.bungee.api.ChatColor;
import rodovia.gamev.api.MinigameEvent;
import rodovia.gamev.api.util.Vector3f;
import rodovia.gamev.plugin.util.PlayerUtils;

public class EventListener implements Listener {
	
	private GamevPlugin plugin;
	
	public EventListener(GamevPlugin plugin) {
		this.plugin = plugin;
	}
	
	
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		MinigameEvent evt = PlayerUtils.getAssociatedEvent(event.getPlayer(), this.plugin);
		if (evt == null || !evt.hasStarted())
			return;
		
		Location loc = event.getPlayer().getLocation();
		Vector3f blockVec = new Vector3f(loc.getBlock().getX(),
								loc.getBlock().getY(),
								loc.getBlock().getZ());
		
		Optional<Vector3f> vec = evt.getEndCoordinates();
				
		if (blockVec.equals(vec.get())) {
			if (!evt.getWinners().contains(event.getPlayer())) {
				spawnFirework(event.getPlayer(), loc);
				String message = ChatColor.YELLOW + "%s" + ChatColor.GREEN + " chegou até o final! [%sº lugar]";
				
				Bukkit.broadcastMessage(String.format(message, 
						event.getPlayer().getName(), evt.getWinners().size() + 1));
				evt.addWinner(event.getPlayer());
				
			}
		}
	}
	
	private void spawnFirework(Player player, Location loc) {
		Firework fw = (Firework) player.getWorld().spawnEntity(loc, EntityType.FIREWORK);
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

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		MinigameEvent evt = PlayerUtils.getAssociatedEvent(event.getPlayer(), plugin);
		if (evt == null)
			return;
		
		evt.removePlayer(event.getPlayer());
	}
	
}
