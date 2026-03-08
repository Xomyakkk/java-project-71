package hexlet.code.core;

import hexlet.code.formatter.Formatter;
import hexlet.code.formatter.JsonFormatter;
import hexlet.code.formatter.PlainFormatter;
import hexlet.code.formatter.StylishFormatter;

import java.util.List;
import java.util.Map;

/**
 * Класс, реализующий построение и форматирование различий между двумя структурами данных.
 *
 * <p>Внутри используется абстрактное представление «DiffNode», которое хранит
 * информацию о ключе, статусе изменения (добавлен, удалён,
 * изменён или не менялся) и значениях из обеих структур.</p>
 *
 * <p>Для форматирования результата реализованы различные {@link Formatter}‑ы.
 * В зависимости от переданного имени формата возвращается строковое представление
 * разницы. По умолчанию используется стиль «stylish».</p>
 */
public class Differ {

    /**
     * Генерирует строку с описанием различий между двумя структурами данных в указанном формате.
     *
     * @param data1   первая карта, содержащая исходные данные
     * @param data2   вторая карта, содержащая обновлённые данные
     * @param format  имя формата (например, {@code "stylish"}, {@code "plain"} или {@code "json"})
     * @return строковое представление различий согласно выбранному форматёру
     */
    public static String generate(Map<String, Object> data1,
                                  Map<String, Object> data2,
                                  String format) {

        List<DiffNode> diff = buildDiff(data1, data2);

        Formatter formatter;
        if ("stylish".equals(format)) {
            formatter = new StylishFormatter();
        } else if ("plain".equals(format)) {
            formatter = new PlainFormatter();
        } else if ("json".equals(format)) {
            formatter = new JsonFormatter();
        } else {
            throw new IllegalArgumentException("Unknown format: " + format);
        }

        return formatter.format(diff);
    }

    /**
     * Перегрузка метода {@link #generate(Map, Map, String)} с форматом по умолчанию.
     *
     * <p>Если формат не указан, используется стиль «stylish».</p>
     *
     * @param data1 первая карта
     * @param data2 вторая карта
     * @return строковое представление различий в формате {@code "stylish"}
     */
    public static String generate(Map<String, Object> data1,
                                  Map<String, Object> data2) {
        return generate(data1, data2, "stylish");
    }

    /**
     * Внутренний вспомогательный метод, который строит абстрактное представление
     * различий между двумя картами. Для каждого ключа создаётся объект {@link DiffNode},
     * в котором хранится статус изменения и соответствующие значения.
     *
     * @param data1 первая карта
     * @param data2 вторая карта
     * @return список узлов разницы, отсортированный по возрастанию имени ключа
     */
    private static List<DiffNode> buildDiff(Map<String, Object> data1,
                                            Map<String, Object> data2) {

        var keys = new java.util.TreeSet<String>();
        keys.addAll(data1.keySet());
        keys.addAll(data2.keySet());

        List<DiffNode> diff = new java.util.ArrayList<>();

        for (String key : keys) {
            if (!data1.containsKey(key)) {
                diff.add(new DiffNode(key, Status.ADDED, null, data2.get(key)));
            } else if (!data2.containsKey(key)) {
                diff.add(new DiffNode(key, Status.REMOVED, data1.get(key), null));
            } else if (java.util.Objects.equals(data1.get(key), data2.get(key))) {
                diff.add(new DiffNode(key, Status.UNCHANGED, data1.get(key), data2.get(key)));
            } else {
                diff.add(new DiffNode(key, Status.UPDATED, data1.get(key), data2.get(key)));
            }
        }

        return diff;
    }
}