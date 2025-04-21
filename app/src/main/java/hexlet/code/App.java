package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.concurrent.Callable;

import static hexlet.code.Differ.generate;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<String> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @CommandLine.Parameters(index = "0", description = "path to first file", hideParamSyntax = true)
    private String filepath1;

    @CommandLine.Parameters(index = "1", description = "path to second file", hideParamSyntax = true)
    private String filepath2;

    @CommandLine.Option(names = {"-f", "--format"}, defaultValue = "stylish",
            description = "output format [default: stylish]")
    private String format;

    /***
     * @return diff between two files
     * @throws IOException
     */
    @Override
    public String call() throws IOException {
        String result = generate(filepath1, filepath2, format);
        OutputOperations.printResult(result);
        return result;
    }
}
