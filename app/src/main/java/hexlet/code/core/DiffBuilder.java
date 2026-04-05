package hexlet.code.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

public final class DiffBuilder {

    private DiffBuilder() {
    }

    public static List<DiffNode> build(Map<String, Object> data1, Map<String, Object> data2) {
        var keys = new TreeSet<String>();
        keys.addAll(data1.keySet());
        keys.addAll(data2.keySet());

        List<DiffNode> diff = new ArrayList<>();

        for (String key : keys) {
            if (!data1.containsKey(key)) {
                diff.add(new DiffNode(key, Status.ADDED, null, data2.get(key)));
            } else if (!data2.containsKey(key)) {
                diff.add(new DiffNode(key, Status.REMOVED, data1.get(key), null));
            } else if (Objects.equals(data1.get(key), data2.get(key))) {
                diff.add(new DiffNode(key, Status.UNCHANGED, data1.get(key), data2.get(key)));
            } else {
                diff.add(new DiffNode(key, Status.UPDATED, data1.get(key), data2.get(key)));
            }
        }

        return diff;
    }
}
