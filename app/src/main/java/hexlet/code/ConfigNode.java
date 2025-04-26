package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

public class ConfigNode {
    private String key;
    private NodeState state;
    private JsonNode oldValue;
    private JsonNode newValue;

    /***
     * Get key of a ConfigNode object.
     * @return key
     */
    public String getKey() {
        return key;
    }

    /***
     * Set key for a ConfigNode object.
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /***
     * Get state of a ConfigNode object.
     * @return state
     */
    public NodeState getState() {
        return state;
    }

    /***
     * Set status for a ConfigNode object.
     * @param state
     */
    public void setState(NodeState state) {
        this.state = state;
    }

    /***
     * Get oldValue of a ConfigNode object.
     * @return oldValue
     */
    public JsonNode getOldValue() {
        return oldValue;
    }

    /***
     * Set oldValue for a ConfigNode object.
     * @param oldValue
     */
    public void setOldValue(JsonNode oldValue) {
        this.oldValue = oldValue;
    }

    /***
     * Get newValue of a ConfigNode object.
     * @return newValue
     */
    public JsonNode getNewValue() {
        return newValue;
    }

    /***
     * Set newValue for a ConfigNode object.
     * @param newValue
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
