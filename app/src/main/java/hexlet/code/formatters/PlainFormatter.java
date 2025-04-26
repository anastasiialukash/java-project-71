package hexlet.code.formatters;

import com.fasterxml.jackson.databind.JsonNode;
import hexlet.code.ConfigNode;
import hexlet.code.NodeState;

import java.util.List;

import static hexlet.code.formatters.FormatterHelper.isObjectOrArray;

public class PlainFormatter {
    private static final String CHANGED_LINE_PATTERN = "Property '%s' was updated. From %s to %s";
    private static final String REMOVED_LINE_PATTERN = "Property '%s' was removed";
    private static final String ADDED_LINE_PATTERN = "Property '%s' was added with value: %s";

    public static String getResultInPlainFormat(List<ConfigNode> diff) {
        StringBuilder sb = new StringBuilder();
        diff.forEach(node -> {
            if (node.getState() != NodeState.UNCHANGED) {
                sb.append(getLineForPlainFormat(node));
                sb.append("\n");
            }
        });
        return sb.toString().trim();
    }

    private static String getLineForPlainFormat(ConfigNode node) {
        return switch (node.getState()) {
            case CHANGED ->
                    String.format(CHANGED_LINE_PATTERN, node.getKey(),
                                    getFormattedNodeValue(node.getOldValue()),
                                    getFormattedNodeValue(node.getNewValue()))
                            .replace("\"", "'");
            case REMOVED -> String.format(REMOVED_LINE_PATTERN, node.getKey());
            case ADDED -> String.format(ADDED_LINE_PATTERN, node.getKey(),
                            getFormattedNodeValue(node.getNewValue()))
                    .replace("\"", "'");
            default -> throw new IllegalStateException("Unexpected state: " + node.getState());
        };
    }

    private static String getFormattedNodeValue(JsonNode node) {
        String complexValueStr = "[complex value]";
        return isObjectOrArray(node) ? complexValueStr : node.toString();
    }
}
