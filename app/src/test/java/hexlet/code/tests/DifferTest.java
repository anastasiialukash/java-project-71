package hexlet.code.tests;

import hexlet.code.Differ;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.logging.Logger;
import java.util.stream.Stream;

import static hexlet.code.tests.providers.TestDataProvider.getResourceFilePath;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    Logger logger = Logger.getLogger(DifferTest.class.getName());
    Differ differ = new Differ();

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void shouldGenerateExpectedDiff(String firstPath, String secondPath, String expectedDiff) {
        logger.info("Comparing two files");
        String methodResult = differ.generate(firstPath, secondPath, "stylish");
        assertEquals(expectedDiff, methodResult, "The generated diff did not match the expected output.");
    }

    private static Stream<Arguments> dataProvider() {
        String expectedNestedFilesDiff = getExpectedResultForNestedFiles();
        return Stream.of(
                Arguments.of(
                        getResourceFilePath("nestedJsonFirst.json"),
                        getResourceFilePath("nestedJsonSecond.json"),
                        expectedNestedFilesDiff
                ),
                Arguments.of(
                        getResourceFilePath("nestedYmlFirst.yml"),
                        getResourceFilePath("nestedYmlSecond.yml"),
                        expectedNestedFilesDiff
                )
        );
    }

    private static String getExpectedResultForNestedFiles() {
        return """
                {
                     chars1: [a, b, c]
                   - chars2: [d, e, f]
                   + chars2: false
                   - checked: false
                   + checked: true
                   - default: null
                   + default: [value1, value2]
                   - id: 45
                   + id: null
                   - key1: value1
                   + key2: value2
                     numbers1: [1, 2, 3, 4]
                   - numbers2: [2, 3, 4, 5]
                   + numbers2: [22, 33, 44, 55]
                   - numbers3: [3, 4, 5]
                   + numbers4: [4, 5, 6]
                   + obj1: {nestedKey=value, isNested=true}
                   - setting1: Some value
                   + setting1: Another value
                   - setting2: 200
                   + setting2: 300
                   - setting3: true
                   + setting3: none
                 }""";
    }
}
