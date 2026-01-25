package hexlet.code.core;

import static hexlet.code.util.Parser.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class DifferTest {

    private static final Path JSON1 =
            Path.of("src", "test", "resources", "include1.json");
    private static final Path JSON2 =
            Path.of("src", "test", "resources", "include2.json");

    private static final Path YAML1 =
            Path.of("src", "test", "resources", "include1.yaml");
    private static final Path YAML2 =
            Path.of("src", "test", "resources", "include2.yaml");

    private static final String EXPECTED = """
{
    chars1: [a, b, c]
  - chars2: [d, e, f]
  + chars2: false
  - checked: false
  + checked: true
  - default: null
  + default: [value1, value2]
  - id: 45
  + id: null
  - key1: value1
  + key2: value2
    numbers1: [1, 2, 3, 4]
  - numbers2: [2, 3, 4, 5]
  + numbers2: [22, 33, 44, 55]
  - numbers3: [3, 4, 5]
  + numbers4: [4, 5, 6]
  + obj1: {nestedKey=value, isNested=true}
  - setting1: Some value
  + setting1: Another value
  - setting2: 200
  + setting2: 300
  - setting3: true
  + setting3: none
}
""";

    @Test
    @DisplayName("Проверяется полная логика diff")
    public void testGenerate() {
        // Данные для теста
        Map<String, Object> data1 = Map.of(
                "host", "hexlet.io",
                "timeout", 50,
                "proxy", "123.234.53.22",
                "follow", false);
        Map<String, Object> data2 = Map.of(
                "timeout", 20,
                "verbose", true,
                "host", "hexlet.io");

        // Ожидаемый результат
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate(data1, data2);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Оба Map пустые. Возвращается строка‑шаблон без элементов")
    void emptyMaps() {
        Map<String, Object> a = Map.of();
        Map<String, Object> b = Map.of();

        String expected = "{\n\n}";
        assertEquals(expected, Differ.generate(a, b));
    }

    @Test
    @DisplayName("Maps идентичны. Выводит только «одинаковые» строки, без префиксов")
    void identicalMaps() {
        Map<String, Object> a = Map.of("k", "v");
        Map<String, Object> b = Map.of("k", "v");

        String expected = """
                {
                    k: v
                }""";
        assertEquals(expected, Differ.generate(a, b));
    }

    @Test
    @DisplayName("Только ключ из первого Map. Появляется только строка с - ")
    void onlyInFirst() {
        Map<String, Object> a = Map.of("only", 1);
        Map<String, Object> b = Map.of();

        String expected = """
                {
                  - only: 1
                }""";
        assertEquals(expected, Differ.generate(a, b));
    }

    @Test
    @DisplayName("Только ключ из второго Map. Появляется только строка с + ")
    void onlyInSecond() {
        Map<String, Object> a = Map.of();
        Map<String, Object> b = Map.of("only", 2);

        String expected = """
                {
                  + only: 2
                }""";
        assertEquals(expected, Differ.generate(a, b));
    }

    @Test
    @DisplayName("Ключи совпадают, но значения различаются. Два элемента: - key: + + key:")
    void differentValues() {
        Map<String, Object> a = Map.of("x", 10);
        Map<String, Object> b = Map.of("x", 20);

        String expected = """
                {
                  - x: 10
                  + x: 20
                }""";
        assertEquals(expected, Differ.generate(a, b));
    }

    @Test
    @DisplayName("Нулевые значения (null). null корректно выводится и учитывается при сравнении")
    void nullValues() {
        Map<String, Object> a = new HashMap<>();
        a.put("k", null);
        Map<String, Object> b = Map.of("k", "notNull");

        String expected = """
                {
                  - k: null
                  + k: notNull
                }""";
        assertEquals(expected, Differ.generate(a, b));
    }

    @Test
    @DisplayName("Порядок входных ключей неважен. Вывод всегда сортирован по алфавиту")
    void orderIndependence() {
        // map1: {"b":2,"a":1}
        Map<String, Object> a = new LinkedHashMap<>();
        a.put("b", 2);
        a.put("a", 1);

        // map2: {"c":3,"a":1}
        Map<String, Object> b = new LinkedHashMap<>();
        b.put("c", 3);
        b.put("a", 1);

        String expected = """
                {
                    a: 1
                  - b: 2
                  + c: 3
                }""";
        assertEquals(expected, Differ.generate(a, b));
    }

    @Test
    @DisplayName("Null как аргумент. Метод бросает NullPointerException (или можно изменить поведение")
    void nullArgument() {
        assertThrows(NullPointerException.class,
                () -> Differ.generate(null, Map.of()));
        assertThrows(NullPointerException.class,
                () -> Differ.generate(Map.of(), null));
    }

    @Test
    @DisplayName("Тест вложенных структур в json")
    void testJsonNestedDiffDefaultFormatter() throws Exception {
        var actual = Differ.generate(parse(JSON1.toString()), parse(JSON2.toString()));
        assertEquals(EXPECTED, actual);
    }

    @Test
    @DisplayName("Тест вложенных структур в yaml")
    void testYamlNestedDiffDefaultFormatter() throws Exception {
        var actual = Differ.generate(parse(YAML1.toString()), parse(YAML2.toString()));
        assertEquals(EXPECTED, actual);
    }
}
