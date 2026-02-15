package hexlet.code.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DifferTest {

    // Тестовые данные
    private static Map<String, Object> data1() {
        var map = new HashMap<String, Object>();

        map.put("setting1", "Some value");
        map.put("setting2", 200);
        map.put("setting3", true);
        map.put("key1",     "value1");
        map.put("numbers1", List.of(1, 2, 3, 4));
        map.put("numbers2", List.of(2, 3, 4, 5));
        map.put("id",        45);
        map.put("default",   null);
        map.put("checked",   false);
        map.put("numbers3", List.of(3, 4, 5));
        map.put("chars1",   List.of("a", "b", "c"));
        map.put("chars2",   List.of("d", "e", "f"));

        return map;
    }

    private static Map<String, Object> data2() {
        var map = new HashMap<String, Object>();
        map.put("setting1", "Another value");
        map.put("setting2", 300);
        map.put("setting3", "none");
        map.put("key2", "value2");
        map.put("numbers1", List.of(1, 2, 3, 4));
        map.put("numbers2", List.of(22, 33, 44, 55));
        map.put("id", null);
        map.put("default", List.of("value1", "value2"));
        map.put("checked", true);
        map.put("numbers4", List.of(4, 5, 6));
        map.put("chars1", List.of("a", "b", "c"));
        map.put("chars2", false);
        map.put("obj1", Map.of(
                "nestedKey", "value",
                "isNested", true
        ));

        return map;
    }

    @Nested
    @DisplayName("Базовые свойства diff")
    class BasicProperties {

        @Test
        @DisplayName("Список с ответом, нужного размера")
        void size() {
            String diff = Differ.generate(data1(), data2());
            // В объединении ключей из data1 и data2 15 уникальных ключей.
            // Предположим, что формат stylish добавляет две строки для скобок.
            // Проверяем, что строк больше либо равно количеству ключей.
            long lineCount = diff.lines().count();
            assertTrue(lineCount >= 15,
                    "Количество строк должно быть не меньше количества ключей");
        }

        @Test
        @DisplayName("Должен вернуть корректный stylish‑формат для простых map")
        void simpleCase() {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("a", 1);
            map1.put("b", 2);

            Map<String, Object> map2 = new HashMap<>();
            map2.put("a", 1);
            map2.put("b", 3);
            map2.put("c", 4);

            String diff = Differ.generate(map1, map2);

            // Проверяем наличие изменений ключей
            assertTrue(diff.contains("a: 1"));
            assertTrue(diff.contains("- b: 2"));
            assertTrue(diff.contains("+ b: 3"));
            assertTrue(diff.contains("+ c: 4"));
        }

        @Test
        @DisplayName("Выброс исключения при неизвестном формате")
        void unknownFormat() {
            assertThrows(IllegalArgumentException.class, () -> {
                Differ.generate(data1(), data2(), "unknown");
            });
        }

        @Test
        @DisplayName("Формат по умолчанию должен быть stylish")
        void defaultFormat() {
            String diffDefault = Differ.generate(data1(), data2());
            String diffStylish = Differ.generate(data1(), data2(), "stylish");

            assertEquals(diffDefault, diffStylish);
        }
    }

    @Nested
    @DisplayName("Plain формат")
    class PlainFormat {

        @Test
        @DisplayName("Должен вернуть корректный plain‑формат")
        void plainFormat() {
            String diff = Differ.generate(data1(), data2(), "plain");

            assertTrue(diff.contains("Property 'chars2' was updated. From [complex value] to false"));
            assertTrue(diff.contains("Property 'checked' was updated. From false to true"));
            assertTrue(diff.contains("Property 'default' was updated. From null to [complex value]"));
            assertTrue(diff.contains("Property 'id' was updated. From 45 to null"));
            assertTrue(diff.contains("Property 'key1' was removed"));
            assertTrue(diff.contains("Property 'key2' was added with value: 'value2'"));
            assertTrue(diff.contains("Property 'numbers2' was updated. From [complex value] to [complex value]"));
            assertTrue(diff.contains("Property 'numbers3' was removed"));
            assertTrue(diff.contains("Property 'numbers4' was added with value: [complex value]"));
            assertTrue(diff.contains("Property 'obj1' was added with value: [complex value]"));
            assertTrue(diff.contains("Property 'setting1' was updated. From 'Some value' to 'Another value'"));
            assertTrue(diff.contains("Property 'setting2' was updated. From 200 to 300"));
            assertTrue(diff.contains("Property 'setting3' was updated. From true to 'none'"));
        }
    }

    @Nested
    @DisplayName("Json формат")
    class JsonFormat {

        @Test
        @DisplayName("Должен вернуть корректный json‑формат")
        void jsonFormat() {
            String diff = Differ.generate(data1(), data2(), "json");

            assertTrue(diff.contains("\"key\":\"chars1\""));
            assertTrue(diff.contains("\"status\":\"UNCHANGED\""));
            assertTrue(diff.contains("\"key\":\"chars2\""));
            assertTrue(diff.contains("\"status\":\"UPDATED\""));
            assertTrue(diff.contains("\"key\":\"checked\""));
            assertTrue(diff.contains("\"status\":\"UPDATED\""));
            assertTrue(diff.contains("\"key\":\"default\""));
            assertTrue(diff.contains("\"status\":\"UPDATED\""));
            assertTrue(diff.contains("\"key\":\"id\""));
            assertTrue(diff.contains("\"status\":\"UPDATED\""));
            assertTrue(diff.contains("\"key\":\"key1\""));
            assertTrue(diff.contains("\"status\":\"REMOVED\""));
            assertTrue(diff.contains("\"key\":\"key2\""));
            assertTrue(diff.contains("\"status\":\"ADDED\""));
            assertTrue(diff.contains("\"key\":\"numbers1\""));
            assertTrue(diff.contains("\"status\":\"UNCHANGED\""));
            assertTrue(diff.contains("\"key\":\"numbers2\""));
            assertTrue(diff.contains("\"status\":\"UPDATED\""));
            assertTrue(diff.contains("\"key\":\"numbers3\""));
            assertTrue(diff.contains("\"status\":\"REMOVED\""));
            assertTrue(diff.contains("\"key\":\"numbers4\""));
            assertTrue(diff.contains("\"status\":\"ADDED\""));
            assertTrue(diff.contains("\"key\":\"obj1\""));
            assertTrue(diff.contains("\"status\":\"ADDED\""));
            assertTrue(diff.contains("\"key\":\"setting1\""));
            assertTrue(diff.contains("\"status\":\"UPDATED\""));
            assertTrue(diff.contains("\"key\":\"setting2\""));
            assertTrue(diff.contains("\"status\":\"UPDATED\""));
            assertTrue(diff.contains("\"key\":\"setting3\""));
            assertTrue(diff.contains("\"status\":\"UPDATED\""));
        }
    }
}
