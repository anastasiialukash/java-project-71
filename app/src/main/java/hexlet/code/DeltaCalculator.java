package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DeltaCalculator {
    public static List<ConfigNode> calculateDiff(JsonNode firstFileJsonObj, JsonNode secondFileJsonObj) {
        ObjectNode firstObject = (ObjectNode) firstFileJsonObj;
        ObjectNode secondObject = (ObjectNode) secondFileJsonObj;
        List<ConfigNode> listOfConfigNodes = new ArrayList<>();

        Iterator<String> firstObjectFields = firstObject.fieldNames();
        Iterator<String> secondObjectFields = secondObject.fieldNames();
        Iterator<String> combinedFields = combineIterators(firstObjectFields, secondObjectFields);

        while (combinedFields.hasNext()) {
            String fieldName = combinedFields.next();
            JsonNode firstVal = firstObject.get(fieldName);
            JsonNode secondVal = secondObject.get(fieldName);

            if (!secondObject.has(fieldName)) {
                ConfigNode removedNode = new ConfigNode(fieldName, NodeState.REMOVED, firstVal, null);
                listOfConfigNodes.add(removedNode);
            } else if (!firstObject.has(fieldName)) {
                JsonNode value = secondObject.get(fieldName);
                ConfigNode addedNode = new ConfigNode(fieldName, NodeState.ADDED, null, value);
                listOfConfigNodes.add(addedNode);
            } else {
                if (firstVal.equals(secondVal)) {
                    ConfigNode unchangedNode = new ConfigNode(fieldName, NodeState.UNCHANGED, firstVal, null);
                    listOfConfigNodes.add(unchangedNode);
                } else {
                    ConfigNode changedNode = new ConfigNode(fieldName, NodeState.CHANGED, firstVal, secondVal);
                    listOfConfigNodes.add(changedNode);
                }
            }
        }

        listOfConfigNodes.sort(Comparator.comparing(ConfigNode::getKey));
        return listOfConfigNodes;
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
