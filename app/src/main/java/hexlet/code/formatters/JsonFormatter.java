package hexlet.code.formatters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.ConfigNode;
import hexlet.code.NodeState;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatter {

    static String getResultInJsonFormat(List<ConfigNode> diff) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getFieldsGroupedByState(diff));
    }

    private static Map<String, Map<String, Object>> getFieldsGroupedByState(List<ConfigNode> diff) {
        Map<String, String> stateNameMapping = Map.of(
                NodeState.ADDED.toString(), "addedItems",
                NodeState.UNCHANGED.toString(), "unchangedItems",
                NodeState.CHANGED.toString(), "changedItems",
                NodeState.REMOVED.toString(), "removedItems"
        );

        Map<String, Map<String, Object>> result = new LinkedHashMap<>();

        for (ConfigNode node : diff) {
            String blockName = stateNameMapping.get(node.getState().toString());
            result.putIfAbsent(blockName, new LinkedHashMap<>());

            Map<String, Object> block = result.get(blockName);

            if (NodeState.CHANGED.equals(node.getState())) {
                Map<String, JsonNode> changeInfo = new LinkedHashMap<>();
                changeInfo.put("oldValue", node.getOldValue());
                changeInfo.put("newValue", node.getNewValue());
                block.put(node.getKey(), changeInfo);
            } else if (NodeState.ADDED.equals(node.getState())) {
                block.put(node.getKey(), node.getNewValue());
            } else {
                block.put(node.getKey(), node.getOldValue());
            }
        }

        return result;
    }
}
