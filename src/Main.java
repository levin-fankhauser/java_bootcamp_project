import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLOutput;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        login();
    }

    public static void createUser() {

    }

    public static void login() throws IOException {
        String myffilePath = "resources/worktime.csv";
        File myFile = new File(myffilePath);
        List<String> allLines = Files.readAllLines(myFile.toPath());

        String checkUsername = Terminal.askString("Username: ");
        String checkPassword = Terminal.askString("Password: ");

        //Abfrage des Benutzers
        String[] lineArray = new String[5];

        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            lineArray = line.split(";");
            if (lineArray[0].equals(checkUsername) && lineArray[1].equals(checkPassword)) {
                break;
            }
        }

        String username = lineArray[0];
        double task1 = Double.parseDouble(lineArray[2]);
        double task2 = Double.parseDouble(lineArray[3]);
        double task3 = Double.parseDouble(lineArray[4]);

        System.out.println("Activity from user " + username + "\nTask1: " + task1 + "h\nTask2: " + task2 + "h\nTask3: " + task3 + "h");

    }

    public static void addWorktime() {

    }

    public static void showDiagram() {

    }
}