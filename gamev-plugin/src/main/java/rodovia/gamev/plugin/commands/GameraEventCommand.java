package rodovia.gamev.plugin.commands;

import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rodovia.gamev.api.Event;
import rodovia.gamev.api.MinigameEvent;
import rodovia.gamev.plugin.GamevPlugin;

public class GameraEventCommand implements CommandExecutor {

	private GamevPlugin plugin;
	
	public GameraEventCommand(GamevPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, 
			String label, String[] args) {
		
		boolean result = true;
		
		switch (args[0]) {
		case "criar":
		case "create":
			result = createEvent((Player) sender, args);
			break;
		case "delete":
			result = deleteEvent((Player) sender, args);
			break;
		case "option":
			result = setEventOption((Player) sender, args);
			break;
		}
		
		return result;
	}
	
	private boolean createEvent(Player player, String[] args) {
		String name;
		try {
			name = args[1];
		} catch (ArrayIndexOutOfBoundsException err) {
			name = "Evento-" + plugin.getEvents().size() + 1;
		}
		
		MinigameEvent event = new Event(plugin);
		event.withName(name);
		player.sendMessage(String.format(ChatColor.GREEN + "Um evento com o nome %s foi criado.", name));
		
		plugin.addEvent(event);
		return true;
	}
	
	private boolean deleteEvent(Player player, String[] args) {
		if (args.length <= 1) {
			player.sendMessage(ChatColor.RED + "Você precisa informar o nome do comando à ser removido.");
			return true;
		}
		Optional<MinigameEvent> evnt = plugin.getEvent(args[1]);
		if (evnt.isEmpty()) {
			player.sendMessage(ChatColor.RED + "O evento '" + args[1] + "' não existe.");
			return true;
		}
		
		evnt.ifPresent((event) -> {
			plugin.removeEvent(event.getName());
		});
		player.sendMessage(ChatColor.GREEN + "Evento '" + args[1] + "' Removido");
		return true;
	}
	
	private boolean setEventOption(Player player, String[] args) {
		return true;
	}

}

