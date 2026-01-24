package hexlet.code.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParserTest {

    /**
     * Получаем абсолютный путь к файлу из ресурсов.
     *
     * @param fileName имя файла в src/test/resources
     * @return строковый путь, пригодный для Parser.readJson(...)
     */
    private String getResourcePath(String fileName) throws Exception {
        // ClassLoader берёт файл из class‑path (src/test/resources)
        var url = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource(fileName),
                "Файл «" + fileName + "» не найден в resources");
        return Paths.get(url.toURI()).toString();
    }

    @Test
    @DisplayName("Чтение file1.json должно вернуть корректный Map")
    void testReadFile1Json() throws Exception {
        Map<String, Object> map = Parser.parse(getResourcePath("file1.json"));

        assertNotNull(map);
        assertEquals(4, map.size(), "Количество ключей в файле1");

        assertEquals("hexlet.io", map.get("host"));
        // Jackson парсит числа как Integer
        assertEquals(50, ((Number) map.get("timeout")).intValue());
        assertEquals("123.234.53.22", map.get("proxy"));
        assertEquals(false, map.get("follow"));
    }

    @Test
    @DisplayName("Чтение file2.json должно вернуть корректный Map")
    void testReadFile2Json() throws Exception {
        Map<String, Object> map = Parser.parse(getResourcePath("file2.json"));

        assertNotNull(map);
        assertEquals(3, map.size(), "Количество ключей во втором файле");

        assertEquals("hexlet.io", map.get("host"));
        assertEquals(20, ((Number) map.get("timeout")).intValue());
        assertEquals(true, map.get("verbose"));
    }

    @Test
    @DisplayName("Несуществующий файл вызывает IOException")
    void testReadNonExistingFile() {
        // readJson бросает IOException, если файл не найден
        assertThrows(java.io.IOException.class,
                () -> Parser.parse("nonexistent.json"),
                "Ожидается IOException для отсутствующего файла");
    }

    @Test
    @DisplayName("Чтение file1.yaml должно вернуть корректный Map")
    void testReadFile1Yaml() throws Exception {
        Map<String, Object> map = Parser.parse(getResourcePath("file1.yaml"));

        assertNotNull(map);
        assertEquals(4, map.size(), "Количество ключей в файле1");

        assertEquals("hexlet.io", map.get("host"));
        // Jackson парсит числа как Integer
        assertEquals(50, ((Number) map.get("timeout")).intValue());
        assertEquals("123.234.53.22", map.get("proxy"));
        assertEquals(false, map.get("follow"));
    }

    @Test
    @DisplayName("Чтение file2.yaml должно вернуть корректный Map")
    void testReadFile2Yaml() throws Exception {
        Map<String, Object> map = Parser.parse(getResourcePath("file2.yaml"));

        assertNotNull(map);
        assertEquals(3, map.size(), "Количество ключей во втором файле");

        assertEquals("hexlet.io", map.get("host"));
        assertEquals(20, ((Number) map.get("timeout")).intValue());
        assertEquals(true, map.get("verbose"));
    }
}
