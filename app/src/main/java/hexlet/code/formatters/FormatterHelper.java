package hexlet.code.formatters;

import com.fasterxml.jackson.databind.JsonNode;

public class FormatterHelper {
    public static boolean isObjectOrArray(JsonNode jsonNode) {
        return jsonNode.isObject() || jsonNode.isArray();
    }
}
