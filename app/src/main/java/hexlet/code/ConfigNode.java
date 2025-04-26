package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

public class ConfigNode {
    String key;
    NodeState state;
    JsonNode oldValue;
    JsonNode newValue;

    public void setKey(String key) {
        this.key = key;
    }

    public NodeState getState() {
        return state;
    }

    public void setState(NodeState state) {
        this.state = state;
    }

    public JsonNode getOldValue() {
        return oldValue;
    }

    public void setOldValue(JsonNode oldValue) {
        this.oldValue = oldValue;
    }

    public JsonNode getNewValue() {
        return newValue;
    }

    public void setNewValue(JsonNode newValue) {
        this.newValue = newValue;
    }

    public String getKey() {
        return key;
    }

    ConfigNode(String key, NodeState state, JsonNode oldValue, JsonNode newValue) {
        this.key = key;
        this.state = state;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
