import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PythonImport {
    private JFrame window;
    private JPanel currentPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PythonImport().createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        window = new JFrame("Work Logging App");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 300);
        window.setLocationRelativeTo(null);

        startProgram();

        window.setVisible(true);
    }

    private void clearScreen() {
        if (currentPanel != null) {
            window.remove(currentPanel);
            currentPanel = null;
        }
    }

    private void startProgram() {
        clearScreen();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        panel.add(loginButton);

        JButton newUserButton = new JButton("Create New User");
        newUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });
        panel.add(newUserButton);

        currentPanel = panel;
        window.getContentPane().add(panel);
    }

    private void login() {
        clearScreen();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Username:");
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        panel.add(nameField);

        JLabel pwLabel = new JLabel("Password:");
        panel.add(pwLabel);

        JPasswordField pwField = new JPasswordField();
        panel.add(pwField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkCredentials(nameField.getText(), new String(pwField.getPassword()));
            }
        });
        panel.add(confirmButton);

        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startProgram();
            }
        });
        panel.add(homeButton);

        currentPanel = panel;
        window.getContentPane().add(panel);
        window.revalidate();
    }

    private void checkCredentials(String username, String password) {
        int loginSuccessful = 0;
        int actualLine = -1;
        String[] lineArray = null;

        try (BufferedReader br = new BufferedReader(new FileReader("resources/worktime.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                actualLine++;
                String[] lineParts = line.split(";");
                if (lineParts[0].equals(username) && lineParts[1].equals(password)) {
                    loginSuccessful = 1;
                    lineArray = lineParts;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (loginSuccessful == 0) {
            clearScreen();

            JButton secretButton = new JButton();
            secretButton.setBackground(Color.decode("#444444"));
            secretButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    easterEgg();
                }
            });
            currentPanel.add(secretButton);

            JLabel errorLabel = new JLabel("Username or password not valid! Try again!");
            currentPanel.add(errorLabel);

            currentPanel.revalidate();
        } else {
            loggedIn(lineArray, actualLine, username, password);
        }
    }

    private void loggedIn(String[] lineArray, int actualLine, String username, String password) {
        clearScreen();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        double task1 = Double.parseDouble(lineArray[2]);
        double task2 = Double.parseDouble(lineArray[3]);
        double task3 = Double.parseDouble(lineArray[4]);

        String activityOutput = "Activity from user " + username + ":\nTask1: " + task1 + "h\nTask2: " + task2 + "h\nTask3: " + task3 + "h\n";

        JLabel activityLabel = new JLabel(activityOutput);
        panel.add(activityLabel);

        JButton addWTButton = new JButton("Add Worktime");
        addWTButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addWorktime(actualLine, lineArray, username, password);
            }
        });
        panel.add(addWTButton);

        JButton showStatisticsButton = new JButton("Show Statistics");
        showStatisticsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showStatistics(task1, task2, task3, lineArray, actualLine, username, password);
            }
        });
        panel.add(showStatisticsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startProgram();
            }
        });
        panel.add(logoutButton);

        currentPanel = panel;
        window.getContentPane().add(panel);
        window.revalidate();
    }

    private void addWorktime(int actualLine, String[] lineArray, String username, String password) {
        clearScreen();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel task1Label = new JLabel("Add hours to Task1:");
        panel.add(task1Label);

        JTextField task1Field = new JTextField();
        panel.add(task1Field);

        JLabel task2Label = new JLabel("Add hours to Task2:");
        panel.add(task2Label);

        JTextField task2Field = new JTextField();
        panel.add(task2Field);

        JLabel task3Label = new JLabel("Add hours to Task3:");
        panel.add(task3Label);

        JTextField task3Field = new JTextField();
        panel.add(task3Field);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double task1 = Double.parseDouble(lineArray[2]) + Double.parseDouble(task1Field.getText());
                double task2 = Double.parseDouble(lineArray[3]) + Double.parseDouble(task2Field.getText());
                double task3 = Double.parseDouble(lineArray[4]) + Double.parseDouble(task3Field.getText());

                List<String> lines = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader("resources/worktime.csv"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                String newLine = lineArray[0] + ";" + lineArray[1] + ";" + task1 + ";" + task2 + ";" + task3;
                lines.set(actualLine, newLine);

                try (FileWriter fw = new FileWriter("resources/worktime.csv")) {
                    for (String line : lines) {
                        fw.write(line + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                clearScreen();

                JLabel successLabel = new JLabel("Worktime successfully added!");
                panel.add(successLabel);

                JButton menuButton = new JButton("Menu");
                menuButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        checkCredentials(username, password);
                    }
                });
                panel.add(menuButton);

                panel.revalidate();
            }
        });
        panel.add(confirmButton);

        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkCredentials(username, password);
            }
        });
        panel.add(menuButton);

        currentPanel = panel;
        window.getContentPane().add(panel);
        window.revalidate();
    }

    private void showStatistics(double task1, double task2, double task3, String[] lineArray, int actualLine, String username, String password) {
        clearScreen();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        double total = task1 + task2 + task3;
        String output = "Your Statistics: \nTask 1: " + Math.round(100 / total * task1) + "%" + "\nTask 2: " + Math.round(100 / total * task2) + "%" + "\nTask 3: " + Math.round(100 / total * task3) + "%";

        JLabel statisticsLabel = new JLabel(output);
        panel.add(statisticsLabel);

        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loggedIn(lineArray, actualLine, username, password);
            }
        });
        panel.add(menuButton);

        currentPanel = panel;
        window.getContentPane().add(panel);
        window.revalidate();
    }

    private void createUser() {
        clearScreen();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("") || password.equals("")) {
                    startProgram();
                } else {
                    String line = username + ";" + password + ";0;0;0\n";

                    try (FileWriter fw = new FileWriter("resources/worktime.csv", true)) {
                        fw.write(line);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    clearScreen();

                    JLabel successLabel = new JLabel("User successfully created!");
                    panel.add(successLabel);

                    JButton homeButton = new JButton("Home");
                    homeButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            startProgram();
                        }
                    });
                    panel.add(homeButton);

                    panel.revalidate();
                }
            }
        });
        panel.add(confirmButton);

        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startProgram();
            }
        });
        panel.add(homeButton);

        currentPanel = panel;
        window.getContentPane().add(panel);
        window.revalidate();
    }

    private void easterEgg() {
        clearScreen();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JLabel easterEggLabel = new JLabel("Wow, you found the easter egg!");
        panel.add(easterEggLabel);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        panel.add(loginButton);

        currentPanel = panel;
        window.getContentPane().add(panel);
        window.revalidate();
    }
}
