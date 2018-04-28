import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdministracjaTab extends JPanel {
    BazaWpisow bw = new BazaWpisow();
    private JPanel panelA, dataUrodzenia;
    private JButton buttonDodajUcznia;
    private JTextField textFieldImieUcznia;
    private JTextField textFieldNazwiskoUcznia;
    private JTextField textFieldLoginUcznia;
    private JPasswordField passwordField1Ucznia;
    private JPasswordField passwordField2Ucznia;
    private JDateChooser wybierzDateUczen;
    private JCalendar kalendarz;


    public AdministracjaTab() {
        buttonDodajUcznia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bw.dodajUcznia(textFieldImieUcznia.getText(), textFieldNazwiskoUcznia.getText(), textFieldLoginUcznia.getText(), passwordField1Ucznia.getSelectedText(), wybierzDateUczen.getDate());
                System.out.println(bw.getUczniowieWszyscy().get(0).getNazwiskoUcznia());
                System.out.println(bw.getUczniowieWszyscy().size());
            }
        });
    }

    private void createUIComponents() {
        panelA = new JPanel();
        add(panelA);
        dataUrodzenia = new JPanel();
        kalendarz = new JCalendar();
        wybierzDateUczen = new JDateChooser(kalendarz.getDate());
        wybierzDateUczen.setPreferredSize(new Dimension(150, 23));
        wybierzDateUczen.setDateFormatString("yyyy-MM-dd");
        dataUrodzenia.setSize(150, 23);
        dataUrodzenia.add(wybierzDateUczen);
    }
}
