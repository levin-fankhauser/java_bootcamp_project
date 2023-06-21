import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Testing {
    public static void main(String[] args) throws IOException {
        String myffilePath = "resources/worktime.csv";
        File myFile = new File(myffilePath);
        List<String> allLines = Files.readAllLines(myFile.toPath());

        FileWriter writer = new FileWriter(myFile, true);
        allLines.set(1, "Test");
        Files.write(myFile.toPath(), allLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
