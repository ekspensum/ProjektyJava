import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LogowanieTab extends JPanel {
    JPanel panelL;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;
    private JButton buttonZaloguj;
    private Dziennik<Nauczyciele, Klasy<Uczniowie, Przedmioty>> dziennik;
    static Integer idNauczyciel;
    static Integer idUczen;

    public LogowanieTab() {
        dziennik = new Dziennik<>(new Nauczyciele(), new Klasy<>(new Uczniowie(), new Przedmioty()));
        buttonZaloguj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean zalogowany = false;
                boolean nauczyciel = false;
                for (int i = 0; i < dziennik.getNauczycieleObiekt().getNauczyciele().size(); i++) {
                    if (textFieldLogin.getText().equals(dziennik.getNauczycieleObiekt().getNauczyciele().get(i).getLoginNauczyciela()) &&
                            Arrays.equals(passwordField.getPassword(), dziennik.getNauczycieleObiekt().getNauczyciele().get(i).getHaslowNauczyciela())) {
                        idNauczyciel = dziennik.getNauczycieleObiekt().getNauczyciele().get(i).getIdNauczyciela();
                        OknoStartowe.setTabbedPane(2, true);
                        OknoStartowe.setTabbedPane(3, true);
                        OknoStartowe.setTabbedPane(0, false);
                        nauczyciel = true;
                        zalogowany = true;
                        break;
                    }
                }
                if (!nauczyciel) {
                    for (int i = 0; i < dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().size(); i++) {
                        if (textFieldLogin.getText().equals(dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getLoginUcznia()) &&
                                Arrays.equals(passwordField.getPassword(), dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getHasloUcznia())) {
                            idUczen = dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getIdUcznia();
                            OknoStartowe.setTabbedPane(1, true);
                            OknoStartowe.setTabbedPane(0, false);
                            zalogowany = true;
                            break;
                        }
                    }
                }
                if (!zalogowany) JOptionPane.showMessageDialog(null, "Niewłaściwy login lub hasło.");
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
