package hexlet.code.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    // Constants to avoid magic numbers in tests
    private static final int EXPECTED_SIZE_FILE1_JSON = 4;
    private static final int TIMEOUT_FILE1_JSON = 50;
    private static final int EXPECTED_SIZE_FILE2_JSON = 3;
    private static final int TIMEOUT_FILE2_JSON = 20;

    private String getResourcePath(String name) throws Exception {
        var url = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource(name),
                "Resource not found: " + name);
        return Paths.get(url.toURI()).toString();
    }

    private String readResource(String name) throws Exception {
        return Files.readString(Paths.get(getResourcePath(name)));
    }

    @Test
    @DisplayName("Reading JSON text should return a correct map")
    void testReadJsonOne() throws Exception {
        Map<String, Object> map = Parser.parse(readResource("file1.json"), "json");

        assertNotNull(map);
        assertEquals(EXPECTED_SIZE_FILE1_JSON, map.size());
        assertEquals("hexlet.io", map.get("host"));
        assertEquals(TIMEOUT_FILE1_JSON, ((Number) map.get("timeout")).intValue());
        assertEquals("123.234.53.22", map.get("proxy"));
        assertEquals(false, map.get("follow"));
    }

    @Test
    @DisplayName("Reading JSON text should return a correct map")
    void testReadJsonTwo() throws Exception {
        Map<String, Object> map = Parser.parse(readResource("file2.json"), "json");

        assertNotNull(map);
        assertEquals(EXPECTED_SIZE_FILE2_JSON, map.size());
        assertEquals("hexlet.io", map.get("host"));
        assertEquals(TIMEOUT_FILE2_JSON, ((Number) map.get("timeout")).intValue());
        assertEquals(true, map.get("verbose"));
    }

    @Test
    @DisplayName("Unsupported format should throw an exception")
    void testUnsupportedFormat() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.parse("{}", "xml"));
    }

    @Test
    @DisplayName("Reading YAML text should return a correct map")
    void testReadYamlOne() throws Exception {
        Map<String, Object> map = Parser.parse(readResource("file1.yaml"), "yaml");

        assertNotNull(map);
        assertEquals(EXPECTED_SIZE_FILE1_JSON, map.size());
        assertEquals("hexlet.io", map.get("host"));
        assertEquals(TIMEOUT_FILE1_JSON, ((Number) map.get("timeout")).intValue());
        assertEquals("123.234.53.22", map.get("proxy"));
        assertEquals(false, map.get("follow"));
    }

    @Test
    @DisplayName("Reading YAML text should return a correct map")
    void testReadYamlTwo() throws Exception {
        Map<String, Object> map = Parser.parse(readResource("file2.yaml"), "yaml");

        assertNotNull(map);
        assertEquals(EXPECTED_SIZE_FILE2_JSON, map.size());
        assertEquals("hexlet.io", map.get("host"));
        assertEquals(TIMEOUT_FILE2_JSON, ((Number) map.get("timeout")).intValue());
        assertEquals(true, map.get("verbose"));
    }
}
