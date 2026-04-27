package hexlet.code;

import hexlet.code.core.DiffBuilder;
import hexlet.code.core.DiffNode;
import hexlet.code.formatter.Formatter;
import hexlet.code.parser.Parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Differ {

    public static String generate(String firstPath, String secondPath) throws Exception {
        return generate(firstPath, secondPath, "stylish");
    }

    public static String generate(String firstPath, String secondPath, String formatName) throws Exception {
        String firstContent = Files.readString(Path.of(firstPath));
        String secondContent = Files.readString(Path.of(secondPath));

        int firstPathLastDot = firstPath.lastIndexOf('.');
        String firstFormat = firstPathLastDot == -1 || firstPathLastDot == firstPath.length() - 1
                ? firstPath
                : firstPath.substring(firstPathLastDot + 1).toLowerCase();
        int secondPathLastDot = secondPath.lastIndexOf('.');
        String secondFormat = secondPathLastDot == -1 || secondPathLastDot == secondPath.length() - 1
                ? secondPath
                : secondPath.substring(secondPathLastDot + 1).toLowerCase();

        Parser parser = new Parser();
        Map<String, Object> data1 = parser.parse(firstContent, firstFormat);
        Map<String, Object> data2 = parser.parse(secondContent, secondFormat);

        List<DiffNode> diff = DiffBuilder.build(data1, data2);
        Formatter formatter = Formatter.getFormatter(formatName);
        return formatter.format(diff);
    }

}
