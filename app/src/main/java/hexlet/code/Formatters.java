package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.models.DiffModel;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Formatters {

    static String getResultString(DiffModel diff, String format) throws IOException {
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(diff.getRemovedItems().keySet());
        allKeys.addAll(diff.getAddedItems().keySet());
        allKeys.addAll(diff.getChangedItems().keySet());
        allKeys.addAll(diff.getUnchangedItems().keySet());
        Stream<String> sortedKeys = allKeys.stream().sorted();

        return switch (format) {
            case "plain" -> getResultInPlainFormat(sortedKeys, diff);
            case "json" -> getResultInJsonFormat(diff);
            default -> getResultInStylishFormat(sortedKeys, diff);
        };
    }

    private static String getResultInStylishFormat(Stream<String> sortedKeys, DiffModel diff) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sortedKeys.forEach(fieldName -> {
            if (diff.getRemovedItems().containsKey(fieldName)) {
                sb.append(String.format("   - %s: %s%n", fieldName,
                        nodeToSingleLine(diff.getRemovedItems().get(fieldName))));
            }
            if (diff.getChangedItems().containsKey(fieldName)) {
                sb.append(String.format("   - %s: %s%n", fieldName,
                        nodeToSingleLine(diff.getChangedItems().get(fieldName).oldValue())));
                sb.append(String.format("   + %s: %s%n", fieldName,
                        nodeToSingleLine(diff.getChangedItems().get(fieldName).newValue())));
            }
            if (diff.getUnchangedItems().containsKey(fieldName)) {
                sb.append(String.format("     %s: %s%n", fieldName,
                        nodeToSingleLine(diff.getUnchangedItems().get(fieldName))));
            }
            if (diff.getAddedItems().containsKey(fieldName)) {
                sb.append(String.format("   + %s: %s%n", fieldName,
                        nodeToSingleLine(diff.getAddedItems().get(fieldName))));
            }
        });
        sb.append(" }");

        return sb.toString();
    }

    private static String getResultInPlainFormat(Stream<String> sortedKeys, DiffModel diff) {
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

        return sb.toString();
    }

    private static String nodeToSingleLine(JsonNode node) {
        if (node == null || node.isNull()) {
            return "null";
        }

        if (node.isObject()) {
            StringBuilder sb = new StringBuilder("{");
            boolean first = true;
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                if (!first) {
                    sb.append(", ");
                }
                first = false;
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                sb.append(key)
                        .append("=")
                        .append(nodeToSingleLine(value));
            }
            sb.append("}");
            return sb.toString();
        }

        if (node.isArray()) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < node.size(); i++) {
                if (i > 0) {
                    sb.append(", "); // <--- Space after comma for arrays
                }
                sb.append(nodeToSingleLine(node.get(i)));
            }
            sb.append("]");
            return sb.toString();
        }

        return node.isTextual() ? node.asText() : node.toString();
    }

    private static String getResultInJsonFormat(DiffModel diff) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(diff);
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
