package rodovia.gamev.api.util;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Representa um vector de 3 flutuantes (X, Y e Z).
 * <p>Essa classe foi feita pra ser um clone da Vector3f do JOML,
 * já que não é possível ter dependências de aplicações que não são plugins.</p>
 * 
 * @author rodovia
 * @see org.joml.Vector3f
 */
public class Vector3f {
	public float x, y, z;
	
	/**
	 * Cria um vetor vazio, com todos os valores definidos para 0.
	 */
	public Vector3f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Location asLocation(World world) {
		return new Location(world, x, y, z);
	}
	
	public boolean equals(Vector3f obj) {
		return (x == obj.x &&
				y == obj.y &&
				z == obj.z);
	}
}
