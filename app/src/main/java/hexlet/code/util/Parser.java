package hexlet.code.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

/**
 * Универсальный парсер JSON и YAML файлов.
 */
public class Parser {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    /**
     * Парсит файл (JSON или YAML) и возвращает данные в виде Map.
     *
     * @param path путь к файлу
     * @return Map с данными файла
     * @throws Exception если формат не поддерживается или парсинг не удался
     */
    public static Map<String, Object> parse(String path) throws Exception {
        byte[] bytes = Files.readAllBytes(new File(path).toPath());
        String content = new String(bytes);

        ObjectMapper mapper = getMapper(path);
        return mapper.readValue(content, Map.class);
    }

    // Определение формата файла по расширению
    private static ObjectMapper getMapper(String path) {
        if (path.endsWith(".json")) {
            return JSON_MAPPER;
        }
        if (path.endsWith(".yml") || path.endsWith(".yaml")) {
            return YAML_MAPPER;
        }

        throw new IllegalArgumentException("Unsupported file format: " + path);
    }
}