package rodovia.gamev.api;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import rodovia.gamev.api.setting.OptionManager;
import rodovia.gamev.api.util.Vector3f;
import rodovia.gamev.api.util.checkpoint.CheckpointManager;

/**
 * Representa um evento de minigames.
 * 
 * @author Rodovia
 */
public interface MinigameEvent {
	
	/**
     <p>Recebe o gerenciador de op��es.</p>
	 * A implementa��o padr�o joga um {@link java.lang.UnsupportedOperationException}.
	 * @throws UnsupportedOperationException a implementação atual n�o suporta configura��es. 
	 */
	default OptionManager getOptionManager() {
		throw new UnsupportedOperationException("Current impl doesn't support options.");
	}
	
	/**
	 * Inicia um evento. Normalmente essa fun��o n�o deve ser chamada mais de uma vez.
	 */
	void start();
	
	/**
	 * Define o nome do evento, �til para reconhecer ele.
	 * @param name o nome do evento
	 * @see MinigameEvent#getName()
	 */
	void withName(String name);
	
	Collection<Player> getPlayers();
	
	Player addPlayer(Player player);
	Player removePlayer(Player player);
	
	void addWinner(Player player);
	void removeWinner(Player player);
	Set<Player> getWinners();
	
	/**
	 * Recebe o nome o evento, pode ser <code>null</code>
	 * @return o nome do evento
	 */
	String getName();
	
	/**
	 * Finaliza um evento.
	 * 
	 * @throws IllegalStateException Quando o evento n�o iniciou ou j� finalizou e houve uma chamada subsequente
	 */
	void end() throws IllegalStateException;
	
	/**
	 * Verifica se o evento j� come�ou.
	 * @return retorna "true", se j� come�ou, se n�o, "false".
	 */
	boolean hasStarted();
	
	/**
	 * O servidor associado a esse evento.
	 * @return uma inst�ncia de {@link org.bukkit.Server}
	 */
	Server getServer();

	/**
	 * <p><strong style="color: yellow;">NOTA</strong>: n�o confundir com a Vector3f do JOML.</p>
	 * 
	 * Define a coordenada inicial dos jogadores, no come�o do evento.
	 * 
	 * <p>A coordenada inicial � a posi��o para onde os jogadores ser�o
	 * teletransportados no come�o do partida. Caso necess�rio, muda a 
	 * regra de jogo <i>maxEntityCramming</i></p>
	 * 
	 * @param coords uma inst�ncia de {@link rodovia.gamev.api.util.Vector3f}
	 * 
	 * @see {@link MinigameEvent#getEndCoordinates()}
	 */
	void setStartCoordinates(Vector3f coords);
	
	/**
	 * <p><strong style="color: yellow;">NOTA</strong>: não confundir com a Vector3f do JOML.</p>
	 * 
	 * Define a coordenada final dos jogadores.
	 * 
	 * <p>A coordenada final é o bloco onde os jogadores vão ficar no final da partida,
	 * para serem considerados <i>ganhadores</i></p>.
	 * @param coords uma instância de {@link rodovia.gamev.api.util.Vector3f}
	 * 
	 * @see {@link MinigameEvent#getStartCoordinates()}
	 */
	void setEndCoordinates(Vector3f coords); 
	Optional<Vector3f> getStartCoordinates();
	Optional<Vector3f> getEndCoordinates();
	
	int getDuration();
	void setDuration(int duration);
	
	default CheckpointManager getCheckpointManager() {
		throw new UnsupportedOperationException("The current implementation does not support checkpoints");
	}
}
