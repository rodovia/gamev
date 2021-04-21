package rodovia.gamev.api;

import java.util.Optional;

import org.bukkit.Server;

import rodovia.gamev.api.setting.OptionManager;
import rodovia.gamev.api.util.Vector3f;

/**
 * Representa um evento de minigames.
 * 
 * @author Rodovia
 */
public interface MinigameEvent {
	
	/**
	 * <p>Recebe o gerenciador de op��es.</p>
	 * A implementa��o padr�o joga um {@link java.lang.UnsupportedOperationException}.
	 * @throws UnsupportedOperationException a classe atual n�o suporta configura��es. 
	 */
	default OptionManager getOptionManager() {
		throw new UnsupportedOperationException("Current event doesn't support options.");
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
	 * <p><strong style="color: yellow;">NOTA</strong>: n�o confundir com a Vector3f do JOML.</p>
	 * 
	 * Define a coordenada final dos jogadores.
	 * 
	 * <p>A coordenada final � o bloco onde os jogadores v�o ficar no final da partida,
	 * para serem considerados <i>ganhadores</i></p>.
	 * @param coords uma inst�ncia de {@link rodovia.gamev.api.util.Vector3f}
	 * 
	 * @see {@link MinigameEvent#getStartCoordinates()}
	 */
	void setEndCoordinates(Vector3f coords); 
	Optional<Vector3f> getStartCoordinates();
	Optional<Vector3f> getEndCoordinates();
}
