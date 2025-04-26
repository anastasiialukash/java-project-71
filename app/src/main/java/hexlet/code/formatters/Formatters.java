package hexlet.code.formatters;

import hexlet.models.DiffModel;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static hexlet.code.formatters.JsonFormatter.getResultInJsonFormat;
import static hexlet.code.formatters.PlainFormatter.getResultInPlainFormat;
import static hexlet.code.formatters.StylishFormatter.getResultInStylishFormat;

public class Formatters {
    public static final String PLAIN_FORMAT = "plain";
    public static final String STYLISH_FORMAT = "stylish";
    public static final String JSON_FORMAT = "json";

    public static String getResultString(DiffModel diff, String format) throws IOException {
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(diff.getRemovedItems().keySet());
        allKeys.addAll(diff.getAddedItems().keySet());
        allKeys.addAll(diff.getChangedItems().keySet());
        allKeys.addAll(diff.getUnchangedItems().keySet());
        Stream<String> sortedKeys = allKeys.stream().sorted();

        return switch (format) {
            case PLAIN_FORMAT -> getResultInPlainFormat(sortedKeys, diff);
            case JSON_FORMAT -> getResultInJsonFormat(diff);
            default -> getResultInStylishFormat(sortedKeys, diff);
        };
    }
}
