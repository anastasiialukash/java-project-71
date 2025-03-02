package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hexlet.models.DiffModel;

import java.io.IOException;
import java.util.Iterator;


import static hexlet.code.Formatters.getResultString;

public class Differ {
    public String generate(String filePath1, String filePath2, String format) {
        JsonNode firstFileJsonObj;
        JsonNode secondFileJsonObj;

        try {
            firstFileJsonObj = Parser.parseFiles(filePath1);
            secondFileJsonObj = Parser.parseFiles(filePath2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DiffModel diff = calculateDiff(firstFileJsonObj, secondFileJsonObj);

        String result = getResultString(diff, "stylish");
        OutputOperations.printResult(result);

        return result;
    }

    private DiffModel calculateDiff(JsonNode firstFileJsonObj, JsonNode secondFileJsonObj) {
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
}
