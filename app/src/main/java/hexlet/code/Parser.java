package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;

public class Parser {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final YAMLMapper YAML_MAPPER = new YAMLMapper();
    private static final String JSON = "json";
    private static final String YML = "yml";

    public static JsonNode parseContent(String fileContent, String fileFormat) throws IOException {
        return switch (fileFormat) {
            case JSON -> OBJECT_MAPPER.readTree(fileContent);
            case YML -> YAML_MAPPER.readTree(fileContent);
            default -> throw new IllegalArgumentException("Unsupported file format: " + fileFormat);
        };
    }
}
