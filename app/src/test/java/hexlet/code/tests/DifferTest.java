package hexlet.code.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static hexlet.code.Differ.generate;
import static hexlet.code.formatters.Formatters.JSON_FORMAT;
import static hexlet.code.formatters.Formatters.PLAIN_FORMAT;
import static hexlet.code.formatters.Formatters.STYLISH_FORMAT;
import static hexlet.code.tests.providers.TestDataProvider.getExpectedResult;
import static hexlet.code.tests.providers.TestDataProvider.getResourceFilePath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    private final Logger logger = Logger.getLogger(DifferTest.class.getName());

    @ParameterizedTest
    @MethodSource("dataProvider")
    void shouldGenerateExpectedDiff(
            String testCase,
            String firstPath,
            String secondPath,
            String expectedDiff,
            String format) throws IOException {
        logger.info("Comparing two files - case: " + testCase);

        String actualDiff = generate(firstPath, secondPath, format);

        assertDiffsEqual(expectedDiff, actualDiff, format);
    }

    @Test
    void shouldGenerateExpectedDiffWithDefaultFormat() throws IOException {
        String firstPath = getResourceFilePath("nestedJsonFirst.json");
        String secondPath = getResourceFilePath("nestedJsonSecond.json");
        String expectedDiff = getExpectedResult(STYLISH_FORMAT);

        String actualDiff = generate(firstPath, secondPath);

        assertDiffsEqual(expectedDiff, actualDiff, STYLISH_FORMAT);
    }

    private static Stream<Arguments> dataProvider() throws IOException {
        String expectedDiffForStylishFormat = getExpectedResult(STYLISH_FORMAT);
        String expectedDiffForPlainFormat = getExpectedResult(PLAIN_FORMAT);
        String expectedDiffForJsonFormat = getExpectedResult(JSON_FORMAT);
        return Stream.of(
                Arguments.of(
                        "case: json -> stylish format",
                        getResourceFilePath("nestedJsonFirst.json"),
                        getResourceFilePath("nestedJsonSecond.json"),
                        expectedDiffForStylishFormat,
                        "stylish"
                ),
                Arguments.of(
                        "case: yml -> stylish format",
                        getResourceFilePath("nestedYmlFirst.yml"),
                        getResourceFilePath("nestedYmlSecond.yml"),
                        expectedDiffForStylishFormat,
                        "stylish"
                ),
                Arguments.of(
                        "case: json -> plain format",
                        getResourceFilePath("nestedJsonFirst.json"),
                        getResourceFilePath("nestedJsonSecond.json"),
                        expectedDiffForPlainFormat,
                        "plain"
                ),
                Arguments.of(
                        "case: yml -> plain format",
                        getResourceFilePath("nestedYmlFirst.yml"),
                        getResourceFilePath("nestedYmlSecond.yml"),
                        expectedDiffForPlainFormat,
                        "plain"
                ),
                Arguments.of(
                        "case: json -> json format",
                        getResourceFilePath("nestedJsonFirst.json"),
                        getResourceFilePath("nestedJsonSecond.json"),
                        expectedDiffForJsonFormat,
                        "json"
                ),
                Arguments.of("case: yml -> json format",
                        getResourceFilePath("nestedYmlFirst.yml"),
                        getResourceFilePath("nestedYmlSecond.yml"),
                        expectedDiffForJsonFormat,
                        "json"
                )
        );
    }

    private static void assertObjectsAreEqual(String expectedDiff, String actualDiff) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> expected = mapper.readValue(expectedDiff, new TypeReference<>() { });
        List<Map<String, Object>> actual = mapper.readValue(actualDiff, new TypeReference<>() { });
        assertThat(actual).isEqualTo(expected);
    }

    private static void assertDiffsEqual(String expectedDiff, String actualDiff, String format)
            throws JsonProcessingException {
        if (format != null && format.equals(JSON_FORMAT)) {
            assertObjectsAreEqual(actualDiff, expectedDiff);
        } else {
            assertEquals(expectedDiff, actualDiff, "The generated diff did not match the expected output.");
        }
    }
}
