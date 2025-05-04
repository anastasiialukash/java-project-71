package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.ConfigNode;

import java.io.IOException;
import java.util.List;

public class JsonFormatter {

    static String getResultInJsonFormat(List<ConfigNode> diff) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(diff);
    }
}
