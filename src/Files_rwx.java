import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Files_rwx {
    public static void main(String[] args) throws IOException {

        //Einlesen
        String myffilePath = "resources/worktime.csv";
        File myFile = new File(myffilePath);

        System.out.println(myFile.getAbsoluteFile());

        //Ausgeben
        List<String> allLines = Files.readAllLines(myFile.toPath());

        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            System.out.println(line);
        }

        //Schreiben
        FileWriter writer = new FileWriter(myFile, true);
        writer.write("\n neue Eingabe!?");
        writer.flush();

    }
}
