package hexlet.code.formatters;

import com.fasterxml.jackson.databind.JsonNode;
import hexlet.models.DiffModel;

import java.util.stream.Stream;

public class PlainFormatter {
    public static String getResultInPlainFormat(Stream<String> sortedKeys, DiffModel diff) {
        StringBuilder sb = new StringBuilder();
        String complexValueStr = "[complex value]";
        sortedKeys.forEach(fieldName -> {
            if (diff.getRemovedItems().containsKey(fieldName)) {
                sb.append(getLineForPlainFormat(fieldName, null, null, "remove"));
                sb.append("\n");
            }
            if (diff.getChangedItems().containsKey(fieldName)) {
                JsonNode newValueNode = diff.getChangedItems().get(fieldName).newValue();
                JsonNode oldValueNode = diff.getChangedItems().get(fieldName).oldValue();
                String oldValue = isObjectOrArray(oldValueNode) ? complexValueStr : oldValueNode.toString();
                String newValue = isObjectOrArray(newValueNode) ? complexValueStr : newValueNode.toString();

                sb.append(getLineForPlainFormat(fieldName, oldValue, newValue, "update"));
                sb.append("\n");
            }
            if (diff.getAddedItems().containsKey(fieldName)) {
                JsonNode valueNode = diff.getAddedItems().get(fieldName);
                String value = isObjectOrArray(valueNode) ? complexValueStr : valueNode.toString();
                sb.append(getLineForPlainFormat(fieldName, value, value, "add"));
                sb.append("\n");
            }
        });

        return sb.toString().trim();
    }

    private static String getLineForPlainFormat(
            String property,
            String valueBefore,
            String valueAfter,
            String operation) {
        return switch (operation) {
            case "update" ->
                    String.format("Property '%s' was updated. From %s to %s", property, valueBefore, valueAfter)
                            .replace("\"", "'");
            case "remove" -> String.format("Property '%s' was removed", property);
            case "add" -> String.format("Property '%s' was added with value: %s", property, valueAfter)
                    .replace("\"", "'");
            default -> "Unknown operation " + operation;
        };
    }

    private static boolean isObjectOrArray(JsonNode jsonNode) {
        return jsonNode.isObject() || jsonNode.isArray();
    }
}
