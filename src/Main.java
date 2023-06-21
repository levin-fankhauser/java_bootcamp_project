import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLOutput;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        while (true) {
            System.out.println("\nYour options: \n1 - Log in \n2 - Add User \n3 - Close program");
            int selection = Terminal.askInt("Selection (only write the number): ");

            switch (selection) {
                case 1:
                    login();
                    break;
                case 2:
                    createUser1();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please enter a valid option!");
                    break;
            }
        }
    }


    public static void createUser1() throws IOException {
        System.out.println("Do you want to add a new user?");
        char selection = Terminal.askChar("Selection (y or n): ");

        switch (selection) {
            case 'n':
                return;
            case 'y':
                createUser2();
                break;
            default:
                System.out.println("Please enter a valid option! \n");
                createUser1();
                break;
        }
    }

        public static void createUser2() throws IOException {
            String myffilePath = "resources/worktime.csv";
            File myFile = new File(myffilePath);

            String username = Terminal.askString("New username: ");
            String password = Terminal.askString("New password: ");

            FileWriter writer = new FileWriter(myFile, true);
            writer.write(username+";"+password+";0;0;0\n");
            writer.flush();

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
            } else {
                System.out.println("Username or Password not valid!");
                return;
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