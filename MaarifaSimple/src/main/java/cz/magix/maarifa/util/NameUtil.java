package cz.magix.maarifa.util;

public class NameUtil {
	/**
	 * TODO: doc it
	 * 
	 * @param camelCaseString
	 * @return
	 */
	public static String normalizeCamelCase(final String camelCaseString) {
		String str = camelCaseString.trim();
		StringBuilder output = new StringBuilder();
		
		for (int i = 0; i < str.length(); i++) {
			if (i == 0) {
				output.append(Character.toUpperCase(str.charAt(i))); 
			} else {
				if (Character.isUpperCase(str.charAt(i))) {
					output.append(' ');
				}
				
				output.append(str.charAt(i));
			}
		}
		
		return output.toString(); 
	}
	
	/**
	 * TODO: doc it
	 * 
	 * @param upperCaseString
	 * @return
	 */
	public static String normalizeUpperCase(final String upperCaseString) {
		String str = upperCaseString.trim();
		StringBuilder output = new StringBuilder();
		
		for (int i = 0; i < str.length(); i++) {
			if (i == 0) {
				output.append(Character.toUpperCase(str.charAt(i))); 
			} else {
				if (str.charAt(i) == '_') {
					output.append(' ');
					
					if (i < str.length()) {
						output.append(Character.toUpperCase(str.charAt(i)));
						i++;
					} else {
						continue;
					}
				} else {
					output.append(Character.toLowerCase(str.charAt(i)));
				}
			}
		}
		
		return output.toString(); 
	}
}
