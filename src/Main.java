import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

    }

    public static void createUser() {

    }

    public static void login() throws IOException {
        String myffilePath = "resources/worktime.csv";

        File myFile = new File(myffilePath);

        List<String> allLines = Files.readAllLines(myFile.toPath());

        Terminal.askString("");

        //Abfrage des Benutzers
        String[] lineArray = new String[4];

        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            lineArray = line.split(";");
            if (lineArray[0].equals("Levin")) {
                break;
            }
        }

        for (int i = 0; i < lineArray.length; i++) {
            System.out.println(lineArray[i]);
        }
    }

    public static void addWorktime() {

    }

    public static void showDiagram() {

    }
}