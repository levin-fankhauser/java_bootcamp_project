import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        while (true) {

            System.out.println("Your options: \n1 - Log in \n2 - Add User \n3 - Close program");
            int selection = Terminal.askInt("Selection (only write the number): ");

            switch (selection) {
                case 1:
                    login();
                    break;
                case 2:
                    createUser();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please enter a valid option! \n");
                    break;
            }

        }
    }


    public static void createUser() {

    }

    public static void login() throws IOException {

        String username = Terminal.askString("Username: ");
        String password = Terminal.askString("Password: ");

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