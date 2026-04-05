package hexlet.code.formatter;

import hexlet.code.core.DiffNode;

import java.util.List;

/**
 * <p>Форматирует список изменений {@link DiffNode} в читаемый вид «stylish».</p>
 *
 * <p>В результате работы метода получается строка, где каждая строка представляет
 * один элемент изменения. Формат соответствует следующей схеме:</p>
 *
 * <ul>
 *     <li><b>unchanged</b> – ключ и значение не изменились, выводятся без префикса;</li>
 *     <li><b>added</b> – добавлен новый ключ, строка начинается с « + »;</li>
 *     <li><b>removed</b> – удалён ключ, строка начинается с « - »;</li>
 *     <li><b>updated</b> – ключ был изменен: сначала выводится старое значение
 *         (префикс « - »), затем новое значение (префикс « + »).</li>
 * </ul>
 *
 * <p>Скобки «{…}» используются для обозначения начала и конца объекта.</p>
 */
public class StylishFormatter implements Formatter {

    /**
     * Преобразует список узлов различий в строку формата «stylish».
     *
     * @param diff список объектов {@link DiffNode}, представляющих изменения
     * @return строка с отформатированным представлением изменений
     */
    @Override
    public String format(List<DiffNode> diff) {
        // Инициализируем результат, открывая объект фигурной скобкой
        StringBuilder result = new StringBuilder("{\n");

        /* Проходим по каждому узлу различий. В зависимости от статуса
         * (unchanged, added, removed, updated) формируем строку с нужным
         * префиксом и значением */
        for (DiffNode node : diff) {
            switch (node.status()) {
                /* Если ключ не изменился – выводим его без символов,
                 * но сохраняем отступы для читабельности. */
                case UNCHANGED ->
                    result.append("    ")
                                .append(node.key())
                                .append(": ")
                                .append(node.oldValue())
                                .append("\n");

                /* Для добавленных ключей ставим «+» и выводим новое значение */
                case ADDED ->
                    result.append("  + ")
                                .append(node.key())
                                .append(": ")
                                .append(node.newValue())
                                .append("\n");

                /* Удалённые ключи помечаем «-» и выводим старое значение */
                case REMOVED ->
                    result.append("  - ")
                                .append(node.key())
                                .append(": ")
                                .append(node.oldValue())
                                .append("\n");

                /* Обновлённый ключ выводится в двух строках: сначала
                 * старая версия (с «-»), затем новая версия (с «+») */
                case UPDATED -> {
                    result.append("  - ")
                            .append(node.key())
                            .append(": ")
                            .append(node.oldValue())
                            .append("\n");
                    result.append("  + ")
                            .append(node.key())
                            .append(": ")
                            .append(node.newValue())
                            .append("\n");
                }
            }
        }

        // Закрываем объект фигурной скобкой
        result.append("}");
        return result.toString();
    }
}
