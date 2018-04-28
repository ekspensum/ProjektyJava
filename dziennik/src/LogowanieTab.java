import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogowanieTab extends JPanel {
    JPanel panelL;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;
    private JButton buttonZaloguj;

    public LogowanieTab() {
        buttonZaloguj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Logowanie");
            }
        });
    }

    private void createUIComponents() {
        panelL = new JPanel();
        add(panelL);
        textFieldLogin = new JTextField();
        passwordField = new JPasswordField();
        buttonZaloguj = new JButton();
    }
}
