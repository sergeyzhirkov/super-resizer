package mts.teta.resizer;

import mts.teta.resizer.attributevalidator.ConsoleAttributes;
import mts.teta.resizer.attributevalidator.Validator;
import mts.teta.resizer.imageprocessor.ImageProcessor;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "resizer",
        version = "Version: name version https://gitlab.com/link/",
        abbreviateSynopsis = true,
        customSynopsis = "convert input-file [options...] output-file",
        description = "Available formats: jpeg png",
        optionListHeading = "Options Settings:\n",
        sortOptions = false,
        separator = " "
)
public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {

    private static final int MAX_WIDTH = 50;

    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        try {
            CommandLine helpAndVersionCL = new CommandLine(new ResizerApp());
            helpAndVersionCL.printVersionHelp(System.out);
            helpAndVersionCL
                    .setUsageHelpLongOptionsMaxWidth(MAX_WIDTH)
                    .execute(new String[]{"--help", "--version"});
            return new CommandLine(new ResizerApp()).execute(args);
        } catch (Exception e) {
            System.out.println(e);
            return 1;
        }
    }

    @Override
    public Integer call() throws Exception {
        new Validator(this).validateAllFields();
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(inputFile), this);
        return 0;
    }
}
