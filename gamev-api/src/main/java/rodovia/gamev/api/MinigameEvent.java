package rodovia.gamev.api;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import rodovia.gamev.api.setting.OptionManager;
import rodovia.gamev.api.util.Vector3f;

/**
 * Representa um evento de minigames.
 * 
 * @author Rodovia
 */
public interface MinigameEvent {
	
	/**
,,k,k,,,,,k	 * <p>Recebe o gerenciador de opções.</p>
	 * A implementação padrão joga um {@link java.lang.UnsupportedOperationException}.
	 * @throws UnsupportedOperationException a classe atual não suporta configurações. 
	 */
	default OptionManager getOptionManager() {
		throw new UnsupportedOperationException("Current event doesn't support options.");
	}
	
	/**
	 * Inicia um evento. Normalmente essa função não deve ser chamada mais de uma vez.
	 */
	void start();
	
	/**
	 * Define o nome do evento, útil para reconhecer ele.
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
	 * @throws IllegalStateException Quando o evento não iniciou ou já finalizou e houve uma chamada subsequente
	 */
	void end() throws IllegalStateException;
	
	/**
	 * Verifica se o evento já começou.
	 * @return retorna "true", se já começou, se não, "false".
	 */
	boolean hasStarted();
	
	/**
	 * O servidor associado a esse evento.
	 * @return uma instância de {@link org.bukkit.Server}
	 */
	Server getServer();

	/**
	 * <p><strong style="color: yellow;">NOTA</strong>: não confundir com a Vector3f do JOML.</p>
	 * 
	 * Define a coordenada inicial dos jogadores, no começo do evento.
	 * 
	 * <p>A coordenada inicial é a posição para onde os jogadores serão
	 * teletransportados no começo do partida. Caso necessário, muda a 
	 * regra de jogo <i>maxEntityCramming</i></p>
	 * 
	 * @param coords uma instância de {@link rodovia.gamev.api.util.Vector3f}
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
}
