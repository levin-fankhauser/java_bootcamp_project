import java.io.*;
import java.nio.file.*;
import java.util.List;

public class Main {

    private static final String FILE_PATH = "resources/worktime.csv";

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
        String username = Terminal.askString("New username: ");
        String password = Terminal.askString("New password: ");

        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(username + ";" + password + ";0;0;0\n");
        }
    }

    public static void login() throws IOException {
        File myFile = new File(FILE_PATH);
        if (!myFile.exists() || myFile.length() == 0) {
            System.out.println("No users found. Please add a user first.");
            return;
        }

        List<String> allLines = Files.readAllLines(myFile.toPath());

        String checkUsername = Terminal.askString("Username: ");
        String checkPassword = Terminal.askString("Password: ");

        int actualLine = -1;
        int loginSuccessful = 0;

        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            String[] lineArray = line.split(";");
            if (lineArray[0].equals(checkUsername) && lineArray[1].equals(checkPassword)) {
                actualLine = i;
                loginSuccessful = 1;
                break;
            }
        }

        if (loginSuccessful == 0) {
            System.out.println("Username or password not valid!");
            return;
        }

        String[] lineArray = allLines.get(actualLine).split(";");
        String username = lineArray[0];
        double task1 = Double.parseDouble(lineArray[2]);
        double task2 = Double.parseDouble(lineArray[3]);
        double task3 = Double.parseDouble(lineArray[4]);

        System.out.println("Activity from user " + username + "\nTask1: " + task1 + "h\nTask2: " + task2 + "h\nTask3: " + task3 + "h");

        String checkWorktime = Terminal.askString("Do you want to add worktime: [y/n]");

        if (checkWorktime.equalsIgnoreCase("y")) {
            addWorktime(username, task1, task2, task3, actualLine, lineArray[1]);
            return;
        }

        String checkStatistics = Terminal.askString("Do you want to see your statistics: [y/n]");

        if (checkStatistics.equalsIgnoreCase("y")) {
            showStatistics(task1, task2, task3);
        }
    }

    public static void addWorktime(String username, double task1_old, double task2_old, double task3_old, int actualLine, String password) throws IOException {
        List<String> allLines = Files.readAllLines(new File(FILE_PATH).toPath());

        double task1 = Terminal.askDouble("How many hours did you work on task 1: ");
        task1 = task1_old + task1;

        double task2 = Terminal.askDouble("How many hours did you work on task 2: ");
        task2 = task2_old + task2;

        double task3 = Terminal.askDouble("How many hours did you work on task 3: ");
        task3 = task3_old + task3;

        String prompt = (username + ";" + password + ";" + task1 + ";" + task2 + ";" + task3);

        allLines.set(actualLine, prompt);
        Files.write(new File(FILE_PATH).toPath(), allLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void showStatistics(double task1, double task2, double task3) {
        double total = task1 + task2 + task3;
        System.out.println("Task 1: " + Math.round(100 / total * task1) + "%");
        System.out.println("Task 2: " + Math.round(100 / total * task2) + "%");
        System.out.println("Task 3: " + Math.round(100 / total * task3) + "%");
    }
}
