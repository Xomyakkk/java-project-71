package hexlet.code;

import hexlet.code.core.Differ;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.concurrent.Callable;

@Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Compares two configuration sources and shows a difference."
)
public class App implements Callable<Integer> {

    @Option(names = {"-f", "--format"},
            defaultValue = "stylish",
            description = "output format [default: stylish]",
            paramLabel = "format")
    private String format = "stylish";

    @Parameters(index = "0", description = "path to first source")
    private File firstPath;

    @Parameters(index = "1", description = "path to second source")
    private File secondPath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        String diff = Differ.generate(firstPath.getPath(), secondPath.getPath(), format);
        System.out.println(diff);
        return 0;
    }
}
