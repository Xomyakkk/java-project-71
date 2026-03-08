package hexlet.code.core;

public record DiffNode(
        String key,
        Status status,
        Object oldValue,
        Object newValue
) {
}
