package hexlet.code.core;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.TreeMap;

/**
 * Класс, создающий строковое представление различий между двумя Map‑объектами.
 *
 * <p>Метод {@link #generate(Map, Map)} принимает два {@code Map<String,Object>} и
 * генерирует «diff» в виде строки. В выводе:
 * <ul>
 *     <li><b>-</b> — элемент присутствует только в первом мапе;</li>
 *     <li><b>+</b> — элемент присутствует только во втором мапе;</li>
 *     <li>без префикса — ключи одинаковы и значения совпадают.</li>
 * </ul>
 *
 * <p>Ключи в результирующей строке отсортированы лексикографически, чтобы
 * вывод был предсказуемым независимо от порядка входных мапов.
 */
public class Differ {

    /**
     * Генерирует diff‑строку между двумя Map-объектами.
     *
     * @param map1 первый исходный мап (значения из него сохраняются при конфликте ключей)
     * @param map2 второй исходный мап
     * @return строка, представляющая различия в формате:
     * <pre>
     * {
     *   key: value          // одинаковые значения
     *   - key: oldValue     // изменённые или удалённые
     *   + key: newValue
     * }
     * </pre>
     */
    public static String generate(Map<String, Object> map1, Map<String, Object> map2) {

        // Получаем новый сортированный Map из map1 и map2. При совпадении ключей оставляем значение из map1
        Map<String, Object> sortedMap = Stream.concat(
                map1.entrySet().stream(),
                map2.entrySet().stream()
                )
                .sorted(Map.Entry.comparingByKey())
        .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (v1, v2) -> v1,     // если ключ встречается в обоих мапах – берём из map1
                TreeMap::new    // TreeMap сортирует автоматом по ключу в алфавитном порядке
        ));

        // StringJoiner поможет собрать строку с нужным разделителем и скобками
        StringJoiner result = new StringJoiner("\n", "{\n", "\n}");

        /* Проходимся по всем ключам в отсортированном Map.
         * В зависимости от наличия ключа в исходных мапах добавляем
         * соответствующие строки с префиксами. */
        sortedMap.forEach((key, values) -> {
            if (map1.containsKey(key) && map2.containsKey(key)) {
                Object value1 = map1.get(key);
                Object value2 = map2.get(key);

                // Если значения совпадают – просто выводим ключ и значение
                if (Objects.equals(value1, value2)) {
                    result.add("    " + key + ": " + value1);
                } else { // иначе показываем обе версии
                    result.add("  - " + key + ": " + value1);
                    result.add("  + " + key + ": " + value2);
                }
            } else if (map1.containsKey(key)) {
                // Ключ присутствует только в первом мапе – удалён
                result.add("  - " + key + ": " + map1.get(key));
            } else {
                // Ключ присутствует только во втором мапе – добавлен
                result.add("  + " + key + ": " + map2.get(key));
            }
        });

        return result.toString();
    }
}
