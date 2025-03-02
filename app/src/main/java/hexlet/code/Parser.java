package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Parser {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final YAMLMapper YAML_MAPPER = new YAMLMapper();

    public static JsonNode parseFiles(String fileName) throws IOException {

        if (fileName == null) {
            return null;
        }

        Path filePath = Paths.get(fileName).toAbsolutePath().normalize();
        String fileContent = Files.readString(filePath);

        if (fileName.endsWith(".json")) {
            return OBJECT_MAPPER.readTree(fileContent);
        }

        if (fileName.endsWith(".yml")) {
            return YAML_MAPPER.readTree(fileContent);
        }

        return null;
    }
}
