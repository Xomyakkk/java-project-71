package hexlet.code.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Files;
import java.util.Map;

/**
 * Чтение JSON-файла.
 */
public class JsonFileReader {


    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Читает JSON‑файл и преобразует его в {@link java.util.Map}.
     *
     * Метод принимает путь к файлу (относительный или абсолютный) и использует Jackson
     * для парсинга содержимого. Если файл не найден, либо JSON некорректен,
     * будет выброшено {@link java.io.IOException} или {@link com.fasterxml.jackson.core.JsonProcessingException}.
     *
     * @param path путь к файлу (может быть относительным от текущей рабочей директории)
     * @return     объект {@code Map<String,Object>} с данными из JSON
     * @throws Exception если чтение файла или парсинг не удалось
     */
    public static Map<String, Object> readJson(String path) throws Exception {
        //Читаем все байты из файла в строку
        byte[] bytes = Files.readAllBytes(new File(path).toPath());
        String content = new String(bytes);

        //Парсим JSON строку в Map
        return MAPPER.readValue(content, Map.class);
    }
}
