package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static hexlet.code.DeltaCalculator.calculateDiff;
import static hexlet.code.formatters.Formatters.getResultString;

public class Differ {
    public static String generate(String filePath1, String filePath2, String format) throws IOException {
        JsonNode firstFileJsonObj;
        JsonNode secondFileJsonObj;

        firstFileJsonObj = getParsedObject(filePath1);
        secondFileJsonObj = getParsedObject(filePath2);

        //DiffModel diff = calculateDiff(firstFileJsonObj, secondFileJsonObj);
        List<ConfigNode> diff = calculateDiff(firstFileJsonObj, secondFileJsonObj);

        return getResultString(diff, format);
    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }

    private static JsonNode getParsedObject(String filePath) {
        JsonNode parsedObject;
        try {
            String fileContent = Files.readString(Paths.get(filePath).toAbsolutePath().normalize());
            String[] fileNameSubstrings = filePath.split("\\.");
            String format = fileNameSubstrings[fileNameSubstrings.length - 1];
            parsedObject = Parser.parseContent(fileContent, format);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return parsedObject;
    }
}
