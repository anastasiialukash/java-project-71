package hexlet.code.tests.providers;

import java.nio.file.Paths;

public class TestFilePathProvider {
    private static final String TEST_DATA_PATH = "src/test/java/hexlet/resources";
    public static String getResourceFilePath(String fileName) {
        return Paths.get(TEST_DATA_PATH, fileName).toString();
    }
}
