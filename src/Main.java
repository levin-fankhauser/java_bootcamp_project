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

        String username = Terminal.askString("Username: ");

        //Abfrage des Benutzers
        String[] lineArray = new String[4];

        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            lineArray = line.split(";");
            if (lineArray[0].equals(username)) {
                break;
            }
        }



    }

    public static void addWorktime() {

    }

    public static void showDiagram() {

    }
}