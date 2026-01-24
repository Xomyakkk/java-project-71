package hexlet.code.core;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        // Создаем отсортированное множество строк
        var keys = new TreeSet<String>();

        // Добавляем только ключи из двух мап
        keys.addAll(map1.keySet());
        keys.addAll(map2.keySet());

        // StringJoiner поможет собрать строку с нужным разделителем и скобками
        StringJoiner result = new StringJoiner("\n", "{\n", "\n}");


        /* Проходимся по всем ключам в отсортированном Map.
         * В зависимости от наличия ключа в исходных мапах добавляем
         * соответствующие строки с префиксами. */
        for (var key : keys) {
            boolean inFirst = map1.containsKey(key);
            boolean inSecond = map2.containsKey(key);

            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            // Если значения совпадают – просто выводим ключ и значение
            if (inFirst && inSecond) {
                if (Objects.equals(value1, value2)) {
                    result.add("    " + key + ": " + value1);
                } else { // иначе показываем обе версии
                    result.add("  - " + key + ": " + value1);
                    result.add("  + " + key + ": " + value2);
                }
            } else if (inFirst) {
                // Ключ присутствует только в первом мапе – удалён
                result.add("  - " + key + ": " + value1);
            } else {
                // Ключ присутствует только во втором мапе – добавлен
                result.add("  + " + key + ": " + value2);
            }
        }

        return result.toString();
    }
}
