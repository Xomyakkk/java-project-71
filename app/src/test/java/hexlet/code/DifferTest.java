package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hexlet.code.util.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

class DifferTest {

    private static String resourcePath(String fileName) throws Exception {
        var url = Objects.requireNonNull(
                DifferTest.class.getClassLoader().getResource(fileName),
                "Resource not found: " + fileName);
        return Path.of(url.toURI()).toString();
    }

    private static String readResource(String fileName) throws Exception {
        return Files.readString(Path.of(resourcePath(fileName)), StandardCharsets.UTF_8);
    }

    private static Map<String, Object> parse(String fileName) throws Exception {
        String content = Files.readString(Path.of(resourcePath(fileName)), StandardCharsets.UTF_8);
        return Parser.parse(content, fileName.endsWith(".json") ? "json" : "yaml");
    }

    @Nested
    @DisplayName("JSON input")
    class JsonInput {

        @Test
        void stylishDiffers() throws Exception {
            String expected = readResource("expected/stylish.txt");
            String actual   = Differ.generate(resourcePath("file1.json"), resourcePath("file2.json"), "stylish");

            System.out.println("EXPECTED LENGTH: " + expected.length());
            System.out.println("ACTUAL LENGTH:   " + actual.length());

            Assertions.assertEquals(expected, actual);
        }

        @Test
        void stylish() throws Exception {
            assertEquals(readResource("expected/stylish.txt"),
                    Differ.generate(resourcePath("file1.json"), resourcePath("file2.json"), "stylish"));
        }

        @Test
        void plain() throws Exception {
            assertEquals(readResource("expected/plain.txt"),
                    Differ.generate(resourcePath("file1.json"), resourcePath("file2.json"), "plain"));
        }

        @Test
        void json() throws Exception {
            assertEquals(readResource("expected/json.txt"),
                    Differ.generate(resourcePath("file1.json"), resourcePath("file2.json"), "json"));
        }

        @Test
        void defaultFormatUsesStylish() throws Exception {
            assertEquals(readResource("expected/stylish.txt"),
                    Differ.generate(parse("file1.json"), parse("file2.json")));
        }
    }

    @Nested
    @DisplayName("YAML input")
    class YamlInput {

        @Test
        void stylish() throws Exception {
            assertEquals(readResource("expected/stylish.txt"),
                    Differ.generate(resourcePath("file1.yaml"), resourcePath("file2.yaml"), "stylish"));
        }

        @Test
        void plain() throws Exception {
            assertEquals(readResource("expected/plain.txt"),
                    Differ.generate(resourcePath("file1.yaml"), resourcePath("file2.yaml"), "plain"));
        }

        @Test
        void json() throws Exception {
            assertEquals(readResource("expected/json.txt"),
                    Differ.generate(resourcePath("file1.yaml"), resourcePath("file2.yaml"), "json"));
        }

        @Test
        void defaultFormatUsesStylish() throws Exception {
            assertEquals(readResource("expected/stylish.txt"),
                    Differ.generate(parse("file1.yaml"), parse("file2.yaml")));
        }
    }

    @Test
    void unknownFormatThrows() throws Exception {
        assertThrows(IllegalArgumentException.class,
                () -> Differ.generate(resourcePath("file1.json"), resourcePath("file2.json"), "unknown"));
    }
}
