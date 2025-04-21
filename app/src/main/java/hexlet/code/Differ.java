package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hexlet.models.DiffModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;


import static hexlet.code.Formatters.getResultString;

public class Differ {
    public static String generate(String filePath1, String filePath2, String format) throws IOException {
        JsonNode firstFileJsonObj;
        JsonNode secondFileJsonObj;

        firstFileJsonObj = getParsedObject(filePath1);
        secondFileJsonObj = getParsedObject(filePath2);

        DiffModel diff = calculateDiff(firstFileJsonObj, secondFileJsonObj);

        return getResultString(diff, format);
    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }

    private static DiffModel calculateDiff(JsonNode firstFileJsonObj, JsonNode secondFileJsonObj) {
        ObjectNode firstObject = (ObjectNode) firstFileJsonObj;
        ObjectNode secondObject = (ObjectNode) secondFileJsonObj;
        DiffModel diffResult = new DiffModel();

        Iterator<String> firstObjectFields = firstObject.fieldNames();
        while (firstObjectFields.hasNext()) {
            String fieldName = firstObjectFields.next();
            JsonNode firstVal = firstObject.get(fieldName);
            JsonNode secondVal = secondObject.get(fieldName);

            if (!secondObject.has(fieldName)) {
                diffResult.getRemovedItems().put(fieldName, firstVal);
            } else {
                if (firstVal.equals(secondVal)) {
                    diffResult.getUnchangedItems().put(fieldName, firstVal);
                } else {
                    DiffModel.ChangedValue changedVal =
                            new DiffModel.ChangedValue(firstVal, secondVal);
                    diffResult.getChangedItems().put(fieldName, changedVal);
                }
            }
        }

        Iterator<String> secondObjectFields = secondObject.fieldNames();
        while (secondObjectFields.hasNext()) {
            String secondObjectFieldName = secondObjectFields.next();
            if (!firstObject.has(secondObjectFieldName)) {
                JsonNode value = secondObject.get(secondObjectFieldName);
                diffResult.getAddedItems().put(secondObjectFieldName, value);
            }
        }

        return diffResult;
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
