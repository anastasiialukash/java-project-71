package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hexlet.models.DiffModel;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DeltaCalculator {
    public static DiffModel calculateDiff(JsonNode firstFileJsonObj, JsonNode secondFileJsonObj) {
        ObjectNode firstObject = (ObjectNode) firstFileJsonObj;
        ObjectNode secondObject = (ObjectNode) secondFileJsonObj;
        DiffModel diffResult = new DiffModel();

        Iterator<String> firstObjectFields = firstObject.fieldNames();
        Iterator<String> secondObjectFields = secondObject.fieldNames();
        Iterator<String> combinedFields = combineIterators(firstObjectFields, secondObjectFields);

        while (combinedFields.hasNext()) {
            String fieldName = combinedFields.next();
            JsonNode firstVal = firstObject.get(fieldName);
            JsonNode secondVal = secondObject.get(fieldName);

            if (!secondObject.has(fieldName)) {
                diffResult.getRemovedItems().put(fieldName, firstVal);
            } else if (!firstObject.has(fieldName)) {
                JsonNode value = secondObject.get(fieldName);
                diffResult.getAddedItems().put(fieldName, value);
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

        return diffResult;
    }

    public static Iterator<String> combineIterators(Iterator<String> it1, Iterator<String> it2) {
        return Stream.concat(
                        iteratorToStream(it1),
                        iteratorToStream(it2)
                )
                .distinct()
                .iterator();
    }

    private static <T> Stream<T> iteratorToStream(Iterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
