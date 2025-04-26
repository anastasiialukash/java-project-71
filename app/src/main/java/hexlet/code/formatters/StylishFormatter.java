package hexlet.code.formatters;

import com.fasterxml.jackson.databind.JsonNode;
import hexlet.code.ConfigNode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StylishFormatter {

    static String getResultInStylishFormat(List<ConfigNode> diffList) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        diffList.forEach(node -> sb.append(getFormattedLineForNode(node)));
        sb.append("}");
        return sb.toString().trim();
    }

    private static String getFormattedLineForNode(ConfigNode node) {
        return switch (node.getState()) {
            case ADDED -> String.format("  + %s: %s%n", node.getKey(), nodeToSingleLine(node.getNewValue()));
            case REMOVED -> String.format("  - %s: %s%n", node.getKey(), nodeToSingleLine(node.getOldValue()));
            case CHANGED -> getFormattedLineForAddedNode(node);
            case UNCHANGED -> String.format("    %s: %s%n", node.getKey(), nodeToSingleLine(node.getOldValue()));
        };
    }

    private static String getFormattedLineForAddedNode(ConfigNode node) {
        return String.format("  - %s: %s%n", node.getKey(), nodeToSingleLine(node.getOldValue()))
                + String.format("  + %s: %s%n", node.getKey(), nodeToSingleLine(node.getNewValue()));
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
