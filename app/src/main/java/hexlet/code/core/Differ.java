package hexlet.code.core;

import hexlet.code.formatter.Formatter;
import hexlet.code.formatter.JsonFormatter;
import hexlet.code.formatter.PlainFormatter;
import hexlet.code.formatter.StylishFormatter;
import hexlet.code.util.Parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

public class Differ {

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

    public static String generate(String firstPath, String secondPath, String formatName) throws Exception {
        String firstContent = readContent(firstPath);
        String secondContent = readContent(secondPath);

        Map<String, Object> data1 = Parser.parse(firstContent, detectFormat(firstPath));
        Map<String, Object> data2 = Parser.parse(secondContent, detectFormat(secondPath));
        return generate(data1, data2, formatName);
    }

    public static String generate(Map<String, Object> data1,
                                  Map<String, Object> data2) {
        return generate(data1, data2, "stylish");
    }

    private static String readContent(String path) throws Exception {
        return Files.readString(Path.of(path));
    }

    private static String detectFormat(String path) {
        String lowerCasePath = path.toLowerCase();
        if (lowerCasePath.endsWith(".json")) {
            return "json";
        }
        if (lowerCasePath.endsWith(".yml")) {
            return "yml";
        }
        if (lowerCasePath.endsWith(".yaml")) {
            return "yaml";
        }

        throw new IllegalArgumentException("Unsupported format: " + path);
    }

    private static List<DiffNode> buildDiff(Map<String, Object> data1,
                                            Map<String, Object> data2) {

        var keys = new TreeSet<String>();
        keys.addAll(data1.keySet());
        keys.addAll(data2.keySet());

        List<DiffNode> diff = new java.util.ArrayList<>();

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