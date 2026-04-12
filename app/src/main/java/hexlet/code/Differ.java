package hexlet.code;

import hexlet.code.core.DiffBuilder;
import hexlet.code.core.DiffNode;
import hexlet.code.formatter.Formatter;
import hexlet.code.util.Parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Differ {

    public static String generate(Map<String, Object> data1,
                                  Map<String, Object> data2) throws Exception {
        return generate(data1, data2, "stylish");
    }

    public static String generate(Map<String, Object> data1,
                                  Map<String, Object> data2,
                                  String formatName) throws Exception {
        List<DiffNode> diff = DiffBuilder.build(data1, data2);

        Formatter formatter = Formatter.getFormatter(formatName);
        return formatter.format(diff);
    }

    public static String generate(String firstPath, String secondPath) throws Exception {
        return generate(firstPath, secondPath, "stylish");
    }

    public static String generate(String firstPath, String secondPath, String formatName) throws Exception {
        String firstContent = readContent(firstPath);
        String secondContent = readContent(secondPath);

        Map<String, Object> data1 = Parser.parse(firstContent, detectFormat(firstPath));
        Map<String, Object> data2 = Parser.parse(secondContent, detectFormat(secondPath));
        return generate(data1, data2, formatName);
    }

    private static String readContent(String path) throws Exception {
        return Files.readString(Path.of(path));
    }

    private static String detectFormat(String path) {
        int lastDotIndex = path.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == path.length() - 1) {
            return path;
        }
        return path.substring(lastDotIndex + 1).toLowerCase();
    }

}
