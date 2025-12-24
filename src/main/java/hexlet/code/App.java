package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Compares two configuration files and shows a difference."
)
public class App implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Сравниваем файлы…");
    }
}
