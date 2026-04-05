package hexlet.code.formatter;

import hexlet.code.core.DiffNode;

import java.util.List;

public interface Formatter {
    String format(List<DiffNode> diff) throws Exception;
}
