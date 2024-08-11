package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    Logger logger = Logger.getLogger(Differ.class.getName());
    Differ differ = new Differ();

    @Test
    public void differJsonTest() {
        logger.info("Starting test");
        String firstPath = "src/test/java/hexlet/resources/file1.json";
        String secondPath = "src/test/java/hexlet/resources/file2.json";

        String expectedResult = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String methodResult = differ.generate(firstPath, secondPath);
        assertEquals(expectedResult, methodResult);
        logger.info("Finished test");
    }

    @Test
    public void differYAMLTest() {
        logger.info("Starting test");
        String firstPath = "src/test/java/hexlet/resources/file1.yml";
        String secondPath = "src/test/java/hexlet/resources/file2.yml";

        String expectedResult = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String methodResult = differ.generate(firstPath, secondPath);
        assertEquals(expectedResult, methodResult);
        logger.info("Finished test");
    }
}
