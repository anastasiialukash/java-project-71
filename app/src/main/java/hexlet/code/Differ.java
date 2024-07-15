package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Differ {
    public String generate(String filePath1, String filePath2) {
        StringBuilder sb = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Set<String> params = new HashSet<>();
            Path firstFilePath = Paths.get(filePath1).toAbsolutePath().normalize();
            Path secondFilePath = Paths.get(filePath2).toAbsolutePath().normalize();

            String fileContent1 = Files.readString(firstFilePath);
            String fileContent2 = Files.readString(secondFilePath);

            Map<String, Object> changed = new HashMap<>();
            Map<String, Object> added = new HashMap<>();
            Map<String, Object> removed = new HashMap<>();
            Map<String, Object> unchanged = new HashMap<>();

            Map<String, Object> firstFileJsonObj = objectMapper.readValue(fileContent1, new TypeReference<>() {

            });

            Map<String, Object> secondFileJsonObj = objectMapper.readValue(fileContent2, new TypeReference<>() {

            });

            for (Map.Entry<String, Object> entry : firstFileJsonObj.entrySet()) {
                params.add(entry.getKey());
                String key = entry.getKey();
                Object value = entry.getValue();
                if (secondFileJsonObj.containsKey(key)) {
                    if (secondFileJsonObj.get(key).equals(value)) {
                        unchanged.put(key, value);
                    }
                    if (!secondFileJsonObj.get(key).equals(value)) {
                        changed.put(key, value);
                    }
                } else {
                    removed.put(key, value);
                }
            }

            for (Map.Entry<String, Object> entry : secondFileJsonObj.entrySet()) {
                params.add(entry.getKey());
                String key = entry.getKey();
                Object value = entry.getValue();
                if (!firstFileJsonObj.containsKey(key)) {
                    added.put(key, value);
                }
            }

            Stream<String> sortedParams = params.stream().sorted();

            sb.append("{\n");
            sortedParams.forEach(param -> {
                if (changed.containsKey(param)) {
                    String changedFirst = "  - " + param + ": " + firstFileJsonObj.get(param);
                    String changedSecond = "  + " + param + ": " + secondFileJsonObj.get(param);
                    sb.append(changedFirst);
                    sb.append("\n");
                    sb.append(changedSecond);
                    sb.append("\n");
                    //System.out.println("  + " + param + ": " + secondFileJsonObj.get(param));
                }
                if (unchanged.containsKey(param)) {
                    sb.append("    ").append(param).append(": ").append(unchanged.get(param)).append("\n");
                }
                if (removed.containsKey(param)) {
                    sb.append("  - ").append(param).append(": ").append(removed.get(param)).append("\n");
                }
                if (added.containsKey(param)) {
                    sb.append("  + ").append(param).append(": ").append(added.get(param)).append("\n");
                }
            });
            sb.append("}");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(sb);
        return sb.toString();
    }
}
