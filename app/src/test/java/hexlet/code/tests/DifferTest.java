package hexlet.code.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static hexlet.code.Differ.generate;
import static hexlet.code.Formatters.JSON_FORMAT;
import static hexlet.code.Formatters.PLAIN_FORMAT;
import static hexlet.code.Formatters.STYLISH_FORMAT;
import static hexlet.code.tests.providers.TestDataProvider.getExpectedResult;
import static hexlet.code.tests.providers.TestDataProvider.getResourceFilePath;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    private final Logger logger = Logger.getLogger(DifferTest.class.getName());

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void shouldGenerateExpectedDiff(
            String firstPath,
            String secondPath,
            String expectedDiff,
            String format) throws IOException {
        logger.info("Comparing two files");
        String methodResult = generate(firstPath, secondPath, format);
        assertEquals(expectedDiff, methodResult, "The generated diff did not match the expected output.");
    }

    private static Stream<Arguments> dataProvider() throws IOException {
        String expectedDiffForStylishFormat = getExpectedResult(STYLISH_FORMAT);
        String expectedDiffForPlainFormat = getExpectedResult(PLAIN_FORMAT);
        String expectedDiffForJsonFormat = getExpectedResult(JSON_FORMAT);
        return Stream.of(
                Arguments.of(
                        getResourceFilePath("nestedJsonFirst.json"),
                        getResourceFilePath("nestedJsonSecond.json"),
                        expectedDiffForStylishFormat,
                        "stylish"
                ),
                Arguments.of(
                        getResourceFilePath("nestedYmlFirst.yml"),
                        getResourceFilePath("nestedYmlSecond.yml"),
                        expectedDiffForStylishFormat,
                        "stylish"
                ),
                Arguments.of(
                        getResourceFilePath("nestedJsonFirst.json"),
                        getResourceFilePath("nestedJsonSecond.json"),
                        expectedDiffForPlainFormat,
                        "plain"
                ),
                Arguments.of(
                        getResourceFilePath("nestedYmlFirst.yml"),
                        getResourceFilePath("nestedYmlSecond.yml"),
                        expectedDiffForPlainFormat,
                        "plain"
                ),
                Arguments.of(
                        getResourceFilePath("nestedJsonFirst.json"),
                        getResourceFilePath("nestedJsonSecond.json"),
                        expectedDiffForJsonFormat,
                        "json"
                ),
                Arguments.of(
                        getResourceFilePath("nestedYmlFirst.yml"),
                        getResourceFilePath("nestedYmlSecond.yml"),
                        expectedDiffForJsonFormat,
                        "json"
                ),
                Arguments.of(
                        getResourceFilePath("nestedJsonFirst.json"),
                        getResourceFilePath("nestedJsonSecond.json"),
                        expectedDiffForStylishFormat,
                        ""
                )
        );
    }
}
