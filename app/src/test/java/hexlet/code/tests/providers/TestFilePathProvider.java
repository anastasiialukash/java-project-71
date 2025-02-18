package hexlet.code.tests.providers;

import java.nio.file.Paths;

public class TestFilePathProvider {
    public static String getResourceFilePath(String fileName) {
        return Paths.get("src/test/java/hexlet/resources", fileName).toString();
    }
}
