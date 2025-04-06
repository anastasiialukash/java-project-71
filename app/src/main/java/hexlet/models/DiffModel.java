package hexlet.models;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

public class DiffModel {
    private final Map<String, ChangedValue> changedItems = new HashMap<>();
    private final Map<String, JsonNode> unchangedItems = new HashMap<>();
    private final Map<String, JsonNode> addedItems = new HashMap<>();
    private final Map<String, JsonNode> removedItems = new HashMap<>();

    /***
     * @return added items
     */
    public Map<String, JsonNode> getAddedItems() {
        return addedItems;
    }

    /***
     * @return removed items
     */
    public Map<String, JsonNode> getRemovedItems() {
        return removedItems;
    }

    /***
     * @return changed items
     */
    public Map<String, ChangedValue> getChangedItems() {
        return changedItems;
    }

    /***
     * @return unchanged items
     */
    public Map<String, JsonNode> getUnchangedItems() {
        return unchangedItems;
    }

    public record ChangedValue(JsonNode oldValue, JsonNode newValue) {
    }
}
