package hexlet.code.tests;

import hexlet.code.Differ;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static hexlet.code.tests.providers.TestDataProvider.getResourceFilePath;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    Logger logger = Logger.getLogger(DifferTest.class.getName());
    Differ differ = new Differ();

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void shouldGenerateExpectedDiff(
            String firstPath,
            String secondPath,
            String expectedDiff,
            String format) throws IOException {
        logger.info("Comparing two files");
        String methodResult = differ.generate(firstPath, secondPath, format);
        assertEquals(expectedDiff, methodResult, "The generated diff did not match the expected output.");
    }

    private static Stream<Arguments> dataProvider() {
        String expectedDiffForStylishFormat = getExpectedResultForStylishFormat();
        String expectedDiffForPlainFormat = getExpectedResultForPlainFormat();
        String expectedDiffForJsonFormat = getExpectedResultForJsonFormat();
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
                )

        );
    }

    private static String getExpectedResultForStylishFormat() {
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

    private static String getExpectedResultForPlainFormat() {
        return """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'
                """;
    }

    public static String getExpectedResultForJsonFormat() {
        return """
                {
                  "changedItems" : {
                    "setting2" : {
                      "oldValue" : 200,
                      "newValue" : 300
                    },
                    "setting3" : {
                      "oldValue" : true,
                      "newValue" : "none"
                    },
                    "default" : {
                      "oldValue" : null,
                      "newValue" : [ "value1", "value2" ]
                    },
                    "chars2" : {
                      "oldValue" : [ "d", "e", "f" ],
                      "newValue" : false
                    },
                    "setting1" : {
                      "oldValue" : "Some value",
                      "newValue" : "Another value"
                    },
                    "numbers2" : {
                      "oldValue" : [ 2, 3, 4, 5 ],
                      "newValue" : [ 22, 33, 44, 55 ]
                    },
                    "checked" : {
                      "oldValue" : false,
                      "newValue" : true
                    },
                    "id" : {
                      "oldValue" : 45,
                      "newValue" : null
                    }
                  },
                  "unchangedItems" : {
                    "chars1" : [ "a", "b", "c" ],
                    "numbers1" : [ 1, 2, 3, 4 ]
                  },
                  "addedItems" : {
                    "key2" : "value2",
                    "numbers4" : [ 4, 5, 6 ],
                    "obj1" : {
                      "nestedKey" : "value",
                      "isNested" : true
                    }
                  },
                  "removedItems" : {
                    "key1" : "value1",
                    "numbers3" : [ 3, 4, 5 ]
                  }
                }""";
    }
}
