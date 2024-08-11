package hexlet.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Differ {
    public String generate(String filePath1, String filePath2) {
        Set<String> params = new HashSet<>();

        Map<String, Object> changed = new HashMap<>();
        Map<String, Object> added = new HashMap<>();
        Map<String, Object> removed = new HashMap<>();
        Map<String, Object> unchanged = new HashMap<>();
        Map<String, Object> firstFileJsonObj;
        Map<String, Object> secondFileJsonObj;

        try {
            firstFileJsonObj = Parser.parseFiles(filePath1);
            secondFileJsonObj = Parser.parseFiles(filePath2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        String result = getResultString(params, changed, unchanged, added, removed,
                firstFileJsonObj, secondFileJsonObj);
        OutputOperations.printResult(result);
        return result;
    }

    private String getResultString(Set<String> params,
                                   Map<String, Object> changedItems,
                                   Map<String, Object> unchangedItems,
                                   Map<String, Object> addedItems,
                                   Map<String, Object> removedItems,
                                   Map<String, Object> firstInitialObj,
                                   Map<String, Object> secondInitialObj) {
        StringBuilder sb = new StringBuilder();
        Stream<String> sortedParams = params.stream().sorted();

        sb.append("{\n");
        sortedParams.forEach(param -> {
            if (changedItems.containsKey(param)) {
                String changedFirst = "  - " + param + ": " + firstInitialObj.get(param);
                String changedSecond = "  + " + param + ": " + secondInitialObj.get(param);
                sb.append(changedFirst);
                sb.append("\n");
                sb.append(changedSecond);
                sb.append("\n");
            }
            if (unchangedItems.containsKey(param)) {
                sb.append("    ").append(param).append(": ").append(unchangedItems.get(param)).append("\n");
            }
            if (removedItems.containsKey(param)) {
                sb.append("  - ").append(param).append(": ").append(removedItems.get(param)).append("\n");
            }
            if (addedItems.containsKey(param)) {
                sb.append("  + ").append(param).append(": ").append(addedItems.get(param)).append("\n");
            }
        });
        sb.append("}");

        return sb.toString();
    }
}
