package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.Map;

import static hexlet.code.util.JsonFileReader.readJson;

/**
 * Точка входа в консольное приложение «gendiff».
 *
 * <p>Приложение использует библиотеку {@link picocli.CommandLine} для распознавания
 * аргументов командной строки и отображения справки/версии.  По умолчанию
 * вывод форматируется в стиле “stylish”.</p>
 *
 * @see App#run()
 */
@Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Compares two configuration files and shows a difference."
)
public class App implements Runnable {

    /**
     * Формат вывода результата.
     *
     * @apiNote По умолчанию используется «stylish».
     */
    @Option(names = {"-f", "--format"},
            description = "output format [default: stylish]",
            paramLabel = "format")
    private String format = "stylish";   // значение по умолчанию

    /**
     * Путь к первому файлу конфигурации.
     *
     * <p>Аргумент имеет индекс {@code 0} и обязательно должен быть указан.</p>
     */
    @Parameters(index = "0", description = "path to first file")
    private File filepath1;

    /**
     * Путь ко второму файлу конфигурации.
     *
     * <p>Аргумент имеет индекс {@code 1} и обязательно должен быть указан.</p>
     */
    @Parameters(index = "1", description = "path to second file")
    private File filepath2;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            Map<String, Object> data1 = readJson(filepath1.getPath());
            Map<String, Object> data2 = readJson(filepath2.getPath());
        } catch (Exception e) {
            System.err.println("Ошибка при чтении файлов: " + e.getMessage());
        }
        System.out.println("Сравниваем файлы…");
    }
}
