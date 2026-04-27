package hexlet.code.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    public static Map<String, Object> parse(String content, String format) throws Exception {
        ObjectMapper mapper = getMapper(format);
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

    private static ObjectMapper getMapper(String format) {
        String normalizedFormat = format.toLowerCase();
        if ("json".equals(normalizedFormat)) {
            return JSON_MAPPER;
        }
        if ("yml".equals(normalizedFormat) || "yaml".equals(normalizedFormat)) {
            return YAML_MAPPER;
        }

        throw new IllegalArgumentException("Unsupported format: " + format);
    }
}
