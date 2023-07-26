import javax.swing.*;
import java.time.LocalTime;

public class MainGUI {
    public static void main(String[] args) {
        JFrame window=new JFrame("Work Logging App");

        JButton login=new JButton("LOGIN");
        login.setBounds(130,100,100, 40);

        window.add(login);

        window.setSize(400,500);
        window.setLayout(null);
        window.setVisible(true);
    }
}
