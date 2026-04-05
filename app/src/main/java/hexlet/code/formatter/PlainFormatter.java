package hexlet.code.formatter;

import hexlet.code.core.DiffNode;

import java.util.List;
import java.util.Map;

/**
 * Форматирует список изменений в «plain»-стиле.
 *
 * <p>Каждый элемент {@link DiffNode} преобразуется в строку,
 * описывающую изменение свойства:
 * <ul>
 *     <li><b>ADDED</b> – свойство добавлено;</li>
 *     <li><b>REMOVED</b> – свойство удалено;</li>
 *     <li><b>UPDATED</b> – значение изменилось (от старого к новому);</li>
 *     <li><b>UNCHANGED</b> – пропускается, так как в plain‑выводе не отражается.</li>
 * </ul></p>
 *
 * @see hexlet.code.formatter.Formatter
 */
public class PlainFormatter implements Formatter {

    /**
     * Форматирует список узлов diff в текстовый вид «plain».
     *
     * <p>Для каждого {@link DiffNode} создаётся строка,
     * описывающая изменение. Если свойство не изменилось (status = UNCHANGED),
     * строка генерируется как пустая и пропускается.</p>
     *
     * @param diff список узлов, представляющих различия
     * @return строку с результатом форматирования без завершающей новой строки
     */
    @Override
    public String format(List<DiffNode> diff) {
        // Храним результат в StringBuilder для конкатенации
        StringBuilder result = new StringBuilder();

        for (DiffNode node : diff) {
            // Используем switch‑выражение, чтобы получить строку в зависимости от статуса
            String line = switch (node.status()) {
                case ADDED ->
                    // Добавлено новое свойство: выводим ключ и значение нового значения
                    String.format("Property '%s' was added with value: %s",
                        node.key(), stringify(node.newValue()));
                case REMOVED ->
                        // Свойство удалено: только имя свойства
                    String.format("Property '%s' was removed", node.key());
                case UPDATED ->
                        // Значение изменилось: выводим старое и новое значение
                    String.format("Property '%s' was updated. From %s to %s",
                        node.key(), stringify(node.oldValue()), stringify(node.newValue()));
                case UNCHANGED -> "";
            };

            // Если строка не пустая, добавляем её в результат и завершаем переводом строки
            if (!line.isEmpty()) {
                result.append(line).append("\n");
            }
        }

        /* Удаляем лишний символ новой строки,
         * если хотя бы одна строка была добавлена. */
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }

    /**
     * Преобразует произвольный объект в строку, пригодную для вывода.
     *
     * <p>Сложные структуры (списки и карты) заменяются на
     * фиксированную метку "[complex value]". Пустые строки оборачиваются в кавычки,
     * а {@code null} выводится как «null».</p>
     *
     * @param value объект, который нужно представить как строку
     * @return строковое представление значения
     */
    private String stringify(Object value) {
        if (value == null) {
            return "null";
        }
        // Для сложных типов выводим заглушку – их внутреннее содержимое не требуется в plain‑выводе
        if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        }
        // Строки помещаем в одинарные кавычки, чтобы явно показать границы значения
        if (value instanceof String) {
            return "'" + value + "'";
        }
        // Для остальных типов используем их обычный toString()
        return value.toString();
    }
}
