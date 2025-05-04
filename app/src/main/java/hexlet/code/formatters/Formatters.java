package hexlet.code.formatters;

import hexlet.code.ConfigNode;

import java.io.IOException;
import java.util.List;

import static hexlet.code.formatters.JsonFormatter.getResultInJsonFormat;
import static hexlet.code.formatters.PlainFormatter.getResultInPlainFormat;
import static hexlet.code.formatters.StylishFormatter.getResultInStylishFormat;

public class Formatters {
    public static final String PLAIN_FORMAT = "plain";
    public static final String STYLISH_FORMAT = "stylish";
    public static final String JSON_FORMAT = "json";

    public static String getResultString(List<ConfigNode> diff, String format) throws IOException {
        return switch (format) {
            case PLAIN_FORMAT -> getResultInPlainFormat(diff);
            case JSON_FORMAT -> getResultInJsonFormat(diff);
            case STYLISH_FORMAT -> getResultInStylishFormat(diff);
            default -> throw new IllegalStateException("Unexpected value: " + format);
        };
    }
}
