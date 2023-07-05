import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MainGUI extends JFrame implements ActionListener {
    private static final String FILE_PATH = "resources/worktime.csv";

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createUserButton;

    public MainGUI() {
        setTitle("Zeiterfassung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(650, 75));
        setLayout(new FlowLayout());

        usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField(20);
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        add(passwordField);

        loginButton = new JButton("Log in");
        loginButton.addActionListener(this);
        add(loginButton);

        createUserButton = new JButton("Add User");
        createUserButton.addActionListener(this);
        add(createUserButton);

        pack();
        setVisible(true);

        // Set the Look and Feel to the system's dark mode
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainGUI();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                login(username, password);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == createUserButton) {
            createUser();
        }
    }

    public void login(String username, String password) throws IOException {
        File myFile = new File(FILE_PATH);
        List<String> allLines = Files.readAllLines(myFile.toPath());

        String checkUsername = username;
        String checkPassword = password;

        String[] lineArray = new String[5];
        int actualLine = 0;
        int loginSuccessful = 0;

        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            lineArray = line.split(";");
            if (lineArray[0].equals(checkUsername) && lineArray[1].equals(checkPassword)) {
                actualLine = i;
                loginSuccessful = 1;
                break;
            }
        }

        if (loginSuccessful == 0) {
            JOptionPane.showMessageDialog(this, "Username or password not valid!");
            return;
        }

        String loginMessage = "Activity from user " + username + "\nTask1: " + lineArray[2] + "h\nTask2: " + lineArray[3] + "h\nTask3: " + lineArray[4] + "h";

        int option = JOptionPane.showConfirmDialog(this, loginMessage + "\nDo you want to add worktime?", "Login Successful", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            addWorktime(username, Double.parseDouble(lineArray[2]), Double.parseDouble(lineArray[3]), Double.parseDouble(lineArray[4]), actualLine, password);
        }

        option = JOptionPane.showConfirmDialog(this, "Do you want to see your statistics?", "Login Successful", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            showStatistics(Double.parseDouble(lineArray[2]), Double.parseDouble(lineArray[3]), Double.parseDouble(lineArray[4]));
        }
    }

    public void createUser() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to add a new user?", "Add User", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String username = JOptionPane.showInputDialog(this, "New username:");
            String password = JOptionPane.showInputDialog(this, "New password:");

            try {
                FileWriter writer = new FileWriter(FILE_PATH, true);
                writer.write(username + ";" + password + ";0;0;0\n");
                writer.flush();
                writer.close();
                JOptionPane.showMessageDialog(this, "User created successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addWorktime(String username, double task1_old, double task2_old, double task3_old, int actualLine, String password) {
        double task1 = Double.parseDouble(JOptionPane.showInputDialog(this, "How many hours did you work on task 1: "));
        task1 = task1_old + task1;

        double task2 = Double.parseDouble(JOptionPane.showInputDialog(this, "How many hours did you work on task 2: "));
        task2 = task2_old + task2;

        double task3 = Double.parseDouble(JOptionPane.showInputDialog(this, "How many hours did you work on task 3: "));
        task3 = task3_old + task3;

        String promt = (username + ";" + password + ";" + task1 + ";" + task2 + ";" + task3);

        try {
            File myFile = new File(FILE_PATH);
            List<String> allLines = Files.readAllLines(myFile.toPath());

            allLines.set(actualLine, promt);
            Files.write(myFile.toPath(), allLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            JOptionPane.showMessageDialog(this, "Worktime added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStatistics(double task1, double task2, double task3) {
        double total = task1 + task2 + task3;
        String statisticsMessage = "Task 1: " + Math.round(100 / total * task1) + "%\n" +
                "Task 2: " + Math.round(100 / total * task2) + "%\n" +
                "Task 3: " + Math.round(100 / total * task3) + "%";
        JOptionPane.showMessageDialog(this, statisticsMessage, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }
}
