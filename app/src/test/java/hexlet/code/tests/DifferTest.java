package hexlet.code.tests;

import hexlet.code.Differ;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static hexlet.code.tests.providers.TestFilePathProvider.getResourceFilePath;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    Logger logger = Logger.getLogger(DifferTest.class.getName());
    Differ differ = new Differ();

    @Test
    public void shouldGenerateExpectedJsonDiffWhenComparingTwoFiles() {
        String firstPath = getResourceFilePath("simpleJsonFirst.json");
        String secondPath = getResourceFilePath("simpleJsonSecond.json");

        logger.info("Comparing two files");
        String methodResult = differ.generate(firstPath, secondPath);
        assertEquals(getExpectedResultForSimpleFiles(), methodResult,
                "The generated JSON diff did not match the expected output.");
    }

    @Test
    public void shouldGenerateExpectedYmlDiffWhenComparingTwoFiles() {
        String firstPath = getResourceFilePath("simpleYmlFirst.yml");
        String secondPath = getResourceFilePath("simpleYmlSecond.yml");

        logger.info("Comparing two files");
        String methodResult = differ.generate(firstPath, secondPath);
        assertEquals(getExpectedResultForSimpleFiles(), methodResult,
                "The generated JSON diff did not match the expected output.");
    }

    private static String getExpectedResultForSimpleFiles() {
        return """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
    }
}
