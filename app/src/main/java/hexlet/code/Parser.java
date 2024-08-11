package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {
    public static Map<String, Object> parseFiles(String fileName) throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectMapper yamlMapper = new YAMLMapper();

        if (fileName == null) {
            return null;
        }

        Path filePath = Paths.get(fileName).toAbsolutePath().normalize();
        String fileContent = Files.readString(filePath);

        if (fileName.endsWith(".json")) {
            return jsonMapper.readValue(fileContent, new TypeReference<>() {
            });
        }

        if (fileName.endsWith(".yml")) {
            return yamlMapper.readValue(fileContent, new TypeReference<>() {
            });
        }

        return null;
    }
}
