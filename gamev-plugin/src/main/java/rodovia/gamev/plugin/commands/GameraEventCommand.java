package rodovia.gamev.plugin.commands;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rodovia.gamev.api.Event;
import rodovia.gamev.api.MinigameEvent;
import rodovia.gamev.api.setting.OptionManager;
import rodovia.gamev.plugin.GamevPlugin;
import rodovia.gamev.plugin.util.StringUtils;

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
		MinigameEvent event = attemptToFetchEvent(evnt);
		if (event == null) {
			player.sendMessage(ChatColor.RED + "Nada mudou, O evento '" + args[1] + "' não existe.");
			return true;
		}
		
		plugin.removeEvent(event.getName());
		player.sendMessage(ChatColor.GREEN + "Evento '" + args[1] + "' Removido");
		
		return true;
	}
	
	private boolean setEventOption(Player player, String[] args) {
		Optional<MinigameEvent> event = plugin.getEvent(args[1]);
		MinigameEvent minigame = attemptToFetchEvent(event);
		if (minigame == null) {
			player.sendMessage(ChatColor.RED + "Nada mudou, O evento '" + args[1] + "' não existe.");
			return true;
		}
		
		OptionManager manager = minigame.getOptionManager();
		Object value = args[3];
		if (StringUtils.isDigit(args[3]))
			value = Integer.valueOf(args[3]);
		
		manager.put(args[2], value);
		String formatted = String.format("A configuração '%s' do evento %s agora tem o valor %s",
					args[2], args[1], args[3]);
		player.sendMessage(ChatColor.GREEN + formatted);
		return true;
	}
	
	private MinigameEvent attemptToFetchEvent(Optional<MinigameEvent> ev)  {
		MinigameEvent event;
		try {
			event = ev.get();
			return event;
		}
		catch (NoSuchElementException err) {
			return null;
		}
		
	}

}

