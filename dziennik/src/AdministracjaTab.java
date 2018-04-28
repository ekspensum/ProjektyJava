import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministracjaTab extends JPanel {
    JPanel panelA;
    private JButton buttonDodajUcznia;
    private JTextField textField1;
    private JFormattedTextField formattedTextField1;

    public AdministracjaTab() {
        buttonDodajUcznia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Dodano");
            }
        });
    }

    private void createUIComponents() {
        panelA = new JPanel();
        add(panelA);
    }
}
