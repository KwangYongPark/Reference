package dev.binz.reference.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonModifierJava6 {
	
    public static void main(String[] args) {
        String jsonString = "[{\"id\": 1, \"Name\": \"상품A\"}, {\"id\": 2, \"Name\": \"상품B\"}, {\"id\": 3, \"Name\": \"상품C\"}]";

        JsonModifier(jsonString,"Name");
    }

	private static void JsonModifier(String jsonString, String tagName) {
		String regex = "\"Name\":\\s*\"([^\"]*)\"";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jsonString);

        StringBuffer modifiedJsonBuffer = new StringBuffer();
        
        while (matcher.find()) {
            String originalValue = matcher.group(1);
            String newValue = originalValue.toLowerCase();

            String replacement = "\""+tagName+"\": \"" + Matcher.quoteReplacement(newValue) + "\"";
            
            matcher.appendReplacement(modifiedJsonBuffer, replacement);
        }

        matcher.appendTail(modifiedJsonBuffer);
        
        String modifiedJson = modifiedJsonBuffer.toString();

        System.out.println("원본 JSON: " + jsonString);
        System.out.println("수정된 JSON: " + modifiedJson);
	}
}
