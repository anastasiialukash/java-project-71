package hexlet.code.formatters;

import hexlet.models.DiffModel;

import java.util.stream.Stream;

import static hexlet.code.formatters.FormatterHelper.nodeToSingleLine;

public class StylishFormatter {

    static String getResultInStylishFormat(Stream<String> sortedKeys, DiffModel diff) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sortedKeys.forEach(fieldName -> {
            if (diff.getRemovedItems().containsKey(fieldName)) {
                sb.append(String.format("  - %s: %s%n", fieldName,
                        nodeToSingleLine(diff.getRemovedItems().get(fieldName))));
            }
            if (diff.getChangedItems().containsKey(fieldName)) {
                sb.append(String.format("  - %s: %s%n", fieldName,
                        nodeToSingleLine(diff.getChangedItems().get(fieldName).oldValue())));
                sb.append(String.format("  + %s: %s%n", fieldName,
                        nodeToSingleLine(diff.getChangedItems().get(fieldName).newValue())));
            }
            if (diff.getUnchangedItems().containsKey(fieldName)) {
                sb.append(String.format("    %s: %s%n", fieldName,
                        nodeToSingleLine(diff.getUnchangedItems().get(fieldName))));
            }
            if (diff.getAddedItems().containsKey(fieldName)) {
                sb.append(String.format("  + %s: %s%n", fieldName,
                        nodeToSingleLine(diff.getAddedItems().get(fieldName))));
            }
        });
        sb.append("}");

        return sb.toString().trim();
    }
}
