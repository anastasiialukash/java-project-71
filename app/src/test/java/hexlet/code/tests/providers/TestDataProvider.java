package hexlet.code.tests.providers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static hexlet.code.formatters.Formatters.JSON_FORMAT;
import static hexlet.code.formatters.Formatters.PLAIN_FORMAT;
import static hexlet.code.formatters.Formatters.STYLISH_FORMAT;

public class TestDataProvider {
    private static final String TEST_DATA_PATH = "src/test/java/hexlet/resources";
    private static final String STYLISH_EXPECTED_RESULT = TEST_DATA_PATH + "/expectedResults/stylishExpectedResult";
    private static final String PLAIN_EXPECTED_RESULT = TEST_DATA_PATH + "/expectedResults/plainExpectedResult";
    private static final String JSON_EXPECTED_RESULT = TEST_DATA_PATH + "/expectedResults/jsonExpectedResult.json";

    public static String getResourceFilePath(String fileName) {
        return Paths.get(TEST_DATA_PATH, fileName).toString();
    }

    public static String getExpectedResult(String format) throws IOException {
        return switch (format) {
            case PLAIN_FORMAT -> getFileContent(PLAIN_EXPECTED_RESULT);
            case STYLISH_FORMAT -> getFileContent(STYLISH_EXPECTED_RESULT);
            case JSON_FORMAT -> getFileContent(JSON_EXPECTED_RESULT);
            default -> throw new IllegalStateException("Unexpected value: " + format);
        };
    }

    private static String getFileContent(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath).toAbsolutePath().normalize());
    }
}
