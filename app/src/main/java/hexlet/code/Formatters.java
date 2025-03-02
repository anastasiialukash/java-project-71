package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import hexlet.models.DiffModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Formatters {

    static String getResultString(DiffModel diff, String format) {
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(diff.getRemovedItems().keySet());
        allKeys.addAll(diff.getAddedItems().keySet());
        allKeys.addAll(diff.getChangedItems().keySet());
        allKeys.addAll(diff.getUnchangedItems().keySet());
        Stream<String> sortedKeys = allKeys.stream().sorted();

        return switch (format) {
            case "simple" -> ""; //todo: add some other format
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
}
