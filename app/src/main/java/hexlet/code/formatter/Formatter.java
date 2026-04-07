package hexlet.code.formatter;

import hexlet.code.core.DiffNode;

import java.util.List;

public interface Formatter {
    String format(List<DiffNode> diff) throws Exception;

    static Formatter getFormatter(String format) {
        if ("stylish".equals(format)) {
            return new StylishFormatter();
        }
        if ("plain".equals(format)) {
            return new PlainFormatter();
        }
        if ("json".equals(format)) {
            return new JsonFormatter();
        }

        throw new IllegalArgumentException("Unknown format: " + format);
    }
}
