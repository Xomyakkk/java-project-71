package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

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

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Сравниваем файлы…");
    }
}
