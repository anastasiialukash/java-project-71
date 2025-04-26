package hexlet.code.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class NodeDTO {
    JsonNode oldValue;
    JsonNode newValue;

    public NodeDTO(JsonNode oldValue, JsonNode newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
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
}
