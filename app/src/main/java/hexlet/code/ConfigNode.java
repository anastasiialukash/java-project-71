package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

public class ConfigNode {
    private String key;
    private NodeState state;
    private JsonNode oldValue;
    private JsonNode newValue;

    /***
     * Get key of a ConfigNode object
     */
    public String getKey() {
        return key;
    }

    /***
     * Set key for a ConfigNode object
     */
    public void setKey(String key) {
        this.key = key;
    }

    /***
     * Get state of a ConfigNode object
     */
    public NodeState getState() {
        return state;
    }

    /***
     * Set status for a ConfigNode object
     */
    public void setState(NodeState state) {
        this.state = state;
    }

    /***
     * Get oldValue of a ConfigNode object
     */
    public JsonNode getOldValue() {
        return oldValue;
    }

    /***
     * Set oldValue for a ConfigNode object
     */
    public void setOldValue(JsonNode oldValue) {
        this.oldValue = oldValue;
    }

    /***
     * Get newValue of a ConfigNode object
     */
    public JsonNode getNewValue() {
        return newValue;
    }

    /***
     * Set newValue for a ConfigNode object
     */
    public void setNewValue(JsonNode newValue) {
        this.newValue = newValue;
    }

    ConfigNode(String key, NodeState state, JsonNode oldValue, JsonNode newValue) {
        this.key = key;
        this.state = state;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
