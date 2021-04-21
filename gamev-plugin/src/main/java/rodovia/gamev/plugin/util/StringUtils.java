package rodovia.gamev.plugin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private StringUtils() {  }
	public static boolean isDigit(String content) {
		if (content == null)
			return false;
		Pattern regex = Pattern.compile("[0-9]+");
		Matcher matcher = regex.matcher(content);
		
		return matcher.matches();
	}
}
