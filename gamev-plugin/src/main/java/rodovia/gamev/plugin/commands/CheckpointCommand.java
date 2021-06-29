package rodovia.gamev.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rodovia.gamev.api.MinigameEvent;
import rodovia.gamev.plugin.GamevPlugin;
import rodovia.gamev.plugin.util.MinigameUtils;

public class CheckpointCommand implements CommandExecutor {

	private GamevPlugin plugin;
	
	public CheckpointCommand(GamevPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args[1]) {
		case "set":
			setCheckpoint(sender, args);
			break;
		}
		return true;
	}
	
	private void setCheckpoint(CommandSender sender, String[] args) {
		if (args.length < 4) {
			sender.sendMessage(ChatColor.RED + "Uso: /gcheckpoint set <jogador> <evento> <id do checkpoint>");
			return;
		}

		if (!sender.isOp()) return;

		Player player = Bukkit.getServer().getPlayer(args[3]);
		if (player == null) {
			String content = String.format("O jogador %s não existe.", args[2]);
			sender.sendMessage(ChatColor.RED + content);
			return;
		}

		MinigameEvent event = MinigameUtils.attemptToFetchEvent(plugin.getEvent(args[2]));
		if (event == null) {
			String content = String.format("Nada mudou. Não existe nenhum evento chamado '%s'", args[2]); 
			sender.sendMessage(ChatColor.RED + content);
			return;
		}
		
		event.getCheckpointManager().removePlayerCheckpoint(player);
		event.getCheckpointManager().addPlayerCheckpoint(Integer.parseInt(args[4]), player);
	}
}
