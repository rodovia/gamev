package rodovia.gamev.api.setting;

import java.util.LinkedHashMap;
import java.util.Map;

public class OptionManager {
	private Map<String, Object> data;
	
	protected OptionManager() {
		data = new LinkedHashMap<>(5);
	}
	
	public static OptionManager empty() {
		return new OptionManager();
	}
	
	public void put(String name, Object value) {
		data.put(name, value);
	}
	
	public Object get(String name) {
		return data.get(name);
	}
}