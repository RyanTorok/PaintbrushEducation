package terminal;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class LS extends Command {

    @Override
    protected String execute(String[] input) {
        int depth;
        try {
            depth = input.length <= 1 ? 1 : Integer.parseInt(input[1]);
        } catch (NumberFormatException e) {
            return "Unexpected Token: " + input[1] + ". For assistance, type 'help ls', without quotes.";
        }
        try {
            Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir"));
            Stream<Path> walk = Files.walk(path, depth, FileVisitOption.FOLLOW_LINKS);
        } catch (SecurityException e) {
            return "ls: Permission denied for folder: " + System.getProperty("user.dir");
        }
        catch (IOException e) {
            return "ls: an error occured when parsing folder: " + System.getProperty("user.dir");
        }
        return null;
    }
}
