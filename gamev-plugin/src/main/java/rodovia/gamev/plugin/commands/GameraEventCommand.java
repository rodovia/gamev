package rodovia.gamev.plugin.commands;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rodovia.gamev.api.Event;
import rodovia.gamev.api.MinigameEvent;
import rodovia.gamev.api.setting.OptionManager;
import rodovia.gamev.api.util.Vector3f;
import rodovia.gamev.plugin.GamevPlugin;
import rodovia.gamev.plugin.util.MinigameUtils;
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
		case "join":
			result = joinEvent((Player) sender, args);
			break;
		case "setstartblock":
			result = setStartBlock((Player) sender, args);
			break;
		case "setendblock":
			result = setEndBlock((Player) sender, args);
			break;
		case "sair":
		case "leave":
			result = leaveEvent((Player) sender, args);
			break;
		case "start":
			result = startEvent((Player) sender, args);
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
	
	private boolean startEvent(Player player, String[] args) {
		if (!player.isOp()) {
			return true;
		}
		
		MinigameEvent event = attemptToFetchEvent(plugin.getEvent(args[1]));
		if (event == null) {
			player.sendMessage(ChatColor.RED + "Nada mudou, o evento '" + args[1] + "' n�o existe.");
			return true;
		}
		
		int duration = parseDuration(event);
		event.setDuration(duration);
		event.start();
		
		Optional<Vector3f> opvec = event.getStartCoordinates();
		Vector3f vec;
		try {
			vec = opvec.get();
		} catch (NoSuchElementException err) {
			Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + 
					"O evento não possui um bloco inicial!");
			return true;
		}
		Player lastPlayer = null;
		Bukkit.broadcastMessage(ChatColor.GREEN + "O evento começou!");
		for (Player ply : event.getPlayers()) {
			ply.teleport(vec.asLocation(ply.getWorld()));
			ply.setGameMode(GameMode.ADVENTURE);
			lastPlayer = ply;
		}
		
		Location loc = vec.asLocation(lastPlayer.getWorld());
		lastPlayer.getWorld().spawnParticle(Particle.PORTAL, loc, 5);
		
		return true;
	}
	
	private int parseDuration(MinigameEvent event) {
		Integer duration;
		try {
			duration = (Integer) event.getOptionManager().get("duration");
			if (duration == null) 
				return 120;
		} catch (ClassCastException err) {
			String message = "AVISO: a opção 'duration' não possui o tipo esperado (um número).\n"
					+ "Ignorando e usando o valor padr�o (120)";
			duration = 120;
			Bukkit.broadcastMessage(ChatColor.YELLOW + message);
		}
		
		return duration;
	}
	
	private boolean setStartBlock(Player player, String[] args) {
		MinigameEvent event = attemptToFetchEvent(plugin.getEvent(args[1]));
		if (event == null) {
			player.sendMessage(ChatColor.RED + "Nada mudou, o evento '" + args[1] + "' n�o existe.");
			return true;
		}
		Location loc = player.getLocation();
		Block block = loc.getBlock();
		Vector3f vec = new Vector3f(block.getX(), 
									block.getY(), 
									block.getZ());
		event.setStartCoordinates(vec);
		String formatted = String.format("As coordenadas %s %s %s foram definidas como a posi��o inicial.", 
										block.getX(), 
										block.getY(), 
										block.getZ());
		player.sendMessage(ChatColor.GREEN + formatted);
		return true;
	}
	
	private boolean setEndBlock(Player player, String[] args) {
		MinigameEvent event = attemptToFetchEvent(plugin.getEvent(args[1]));
		if (event == null) {
			player.sendMessage(ChatColor.RED + "Nada mudou, o evento '" + args[1] + "' n�o existe.");
			return true;
		}
		Location loc = player.getLocation();
		Block block = loc.getBlock();
		Vector3f vec = new Vector3f(block.getX(), 
									block.getY(), 
									block.getZ());
		event.setEndCoordinates(vec);
		String formatted = String.format("As coordenadas %s %s %s foram definidas como a posi��o final.", 
										block.getX(), 
										block.getY(), 
										block.getZ());
		player.sendMessage(ChatColor.GREEN + formatted);
		return true;
	}
	
	private boolean deleteEvent(Player player, String[] args) {
		if (args.length <= 1) {
			player.sendMessage(ChatColor.RED + "Voc� precisa informar o nome do comando � ser removido.");
			return true;
		}
		Optional<MinigameEvent> evnt = plugin.getEvent(args[1]);
		MinigameEvent event = attemptToFetchEvent(evnt);
		if (event == null) {
			player.sendMessage(ChatColor.RED + "Nada mudou, O evento '" + args[1] + "' n�o existe.");
			return true;
		}
		
		plugin.removeEvent(event.getName());
		player.sendMessage(ChatColor.GREEN + "Evento '" + args[1] + "' Removido");
		
		return true;
	}
	
	private boolean joinEvent(Player player, String[] args) {
		MinigameEvent event = attemptToFetchEvent(plugin.getEvent(args[1]));
		if (event == null) {
			player.sendMessage(ChatColor.RED + "Nada mudou, o evento '" + args[1] + "' n�o existe.");
			return true;
		}
		if (!canJoin(event)) {
			player.sendMessage(ChatColor.RED + "Nada mudou, o evento est� lotado.");
			return true;
		}
				
		Player pl = event.addPlayer(player);
		
		
		if (pl == null) {
			player.sendMessage(ChatColor.RED + "Nada mudou, voc� j� est� no evento.");
			return true;
		}
		
		player.sendMessage(ChatColor.GREEN + "Voc� entrou para o evento " + args[0]);
		return true;
	}
	
	private boolean canJoin(MinigameEvent event) {
		Integer in;
		try {
			in = (Integer) event.getOptionManager().get("max-members");
			if (in == null) {
				return true;
			}
			return event.getPlayers().size() < in.intValue();
		} catch (ClassCastException err) {
			String message = "AVISO: a op��o 'max-members' n�o possui o tipo esperado (um n�mero). Ignorando a op��o.\n" + 
																	err.getLocalizedMessage();
			Bukkit.broadcastMessage(ChatColor.YELLOW + message);
			return true;
		}
	}
	
	private boolean setEventOption(Player player, String[] args) {
		Optional<MinigameEvent> event = plugin.getEvent(args[1]);
		MinigameEvent minigame = attemptToFetchEvent(event);
		if (minigame == null) {
			player.sendMessage(ChatColor.RED + "Nada mudou, O evento '" + args[1] + "' n�o existe.");
			return true;
		}
		
		OptionManager manager = minigame.getOptionManager();
		Object value = args[3];
		if (StringUtils.isDigit(args[3]))
			value = Integer.valueOf(args[3]);
		
		manager.put(args[2], value);
		String formatted = String.format("A configura��o '%s' do evento %s agora tem o valor %s",
					args[2], args[1], args[3]);
		player.sendMessage(ChatColor.GREEN + formatted);
		return true;
	}
	
	private boolean leaveEvent(Player player, String[] args) {
		MinigameEvent event = attemptToFetchEvent(plugin.getEvent(args[0]));
		if (event == null) {
			player.sendMessage(ChatColor.RED + "O evento '" + args[0] + "' n�o existe.");
			return true;
		}
		
		if (!event.getPlayers().contains(player)) {
			player.sendMessage(ChatColor.RED + "Nada mudou, voc� n�o est� participando do evento.");
			return true;
		}
		
		event.removePlayer(player);
		player.sendMessage(ChatColor.GREEN + "Voc� saiu do evento '" + args[0] + "'.");
		return true;
	}
	
	private MinigameEvent attemptToFetchEvent(Optional<MinigameEvent> ev)  {
		return MinigameUtils.attemptToFetchEvent(ev);
	}

}

