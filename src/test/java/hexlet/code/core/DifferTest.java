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
}
