import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.util.Arrays;


public class AdministracjaTab extends JPanel {
    private Dziennik<Nauczyciele, Klasy<Uczniowie, Przedmioty>> dziennik = new Dziennik<>(new Nauczyciele(), new Klasy<>(new Uczniowie(), new Przedmioty()));
    private JPanel panelAdmin, dataUrodzenia, dataZatrudnienia;
    private JButton buttonDodajUcznia;
    private JButton buttonDodajNauczyciela;
    private JButton buttonDodajPrzedmiot;
    private JButton buttonDodajKlase;
    private JTextField textFieldImieUcznia;
    private JTextField textFieldNazwiskoUcznia;
    private JTextField textFieldLoginUcznia;
    private JPasswordField passwordField1Ucznia;
    private JPasswordField passwordField2Ucznia;
    private JTextField textFieldImieNauczyciela;
    private JTextField textFieldNazwiskoNauczyciela;
    private JTextField textFieldLoginNauczyciela;
    private JPasswordField passwordField1Nauczyciela;
    private JPasswordField passwordField2Nauczyciela;
    private JTextField textFieldKlasa;
    private JTextField textFieldPrzedmiot;
    private JComboBox comboBoxKlasyUczniowie;
    private JComboBox comboBoxKlasyPrzedmiot;
    private JDateChooser wybierzDateUczen, wybierzDateNauczyciel;
    private JCalendar kalendarz;


    public AdministracjaTab() {
        buttonDodajUcznia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Arrays.equals(passwordField1Ucznia.getPassword(), passwordField2Ucznia.getPassword())){
                    try {
                        dziennik.getKlasyObiekt().getUczniowieObiekt().dodajUcznia(nowyIdUcznia(), comboBoxKlasyUczniowie.getSelectedIndex(), textFieldImieUcznia.getText(), textFieldNazwiskoUcznia.getText(), textFieldLoginUcznia.getText(), passwordField1Ucznia.getPassword(), wybierzDateUczen.getDate());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else JOptionPane.showMessageDialog(null, "Niezgodne pola haseł.");
            }
        });
        buttonDodajNauczyciela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Arrays.equals(passwordField1Nauczyciela.getPassword(), passwordField2Nauczyciela.getPassword())){
                    try {
                        dziennik.getNauczycieleObiekt().dodajNauczyciela(nowyIdNauczyciela(), textFieldImieNauczyciela.getText(), textFieldNazwiskoNauczyciela.getText(), textFieldLoginNauczyciela.getText(), passwordField1Nauczyciela.getPassword(), wybierzDateNauczyciel.getDate());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else JOptionPane.showMessageDialog(null, "Niezgodne pola haseł.");

            }
        });
        buttonDodajPrzedmiot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dziennik.getKlasyObiekt().getPrzedmiotyObiekt().dodajPrzedmiot(textFieldPrzedmiot.getText(), nowyIdPrzedmiotu(), comboBoxKlasyPrzedmiot.getSelectedIndex());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonDodajKlase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dziennik.getKlasyObiekt().dodajKlase(nowyIdKlasy(), textFieldKlasa.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        comboBoxKlasyPrzedmiot.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                comboBoxKlasyPrzedmiot.removeAllItems();
                for (int i = 0; i < dziennik.getKlasyObiekt().getKlasy().size(); i++)
                        comboBoxKlasyPrzedmiot.addItem(dziennik.getKlasyObiekt().getKlasy().get(i).getNazwaKlasy());
                comboBoxKlasyPrzedmiot.setSelectedIndex(0);
                comboBoxKlasyPrzedmiot.setMaximumRowCount(comboBoxKlasyPrzedmiot.getItemCount());
            }
        });
        comboBoxKlasyUczniowie.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                comboBoxKlasyUczniowie.removeAllItems();
                for (int i = 0; i < dziennik.getKlasyObiekt().getKlasy().size(); i++)
                        comboBoxKlasyUczniowie.addItem(dziennik.getKlasyObiekt().getKlasy().get(i).getNazwaKlasy());
                comboBoxKlasyUczniowie.setSelectedIndex(0);
                comboBoxKlasyUczniowie.setMaximumRowCount(comboBoxKlasyUczniowie.getItemCount());
            }
        });
    }

    private void createUIComponents() {
        panelAdmin = new JPanel();
        add(panelAdmin);
        dataUrodzenia = new JPanel();
        dataZatrudnienia = new JPanel();
        kalendarz = new JCalendar();
        wybierzDateUczen = new JDateChooser(kalendarz.getDate());
        wybierzDateUczen.setPreferredSize(new Dimension(150, 23));
        wybierzDateUczen.setDateFormatString("yyyy-MM-dd");
        dataUrodzenia.setSize(150, 23);
        dataUrodzenia.add(wybierzDateUczen);
        wybierzDateNauczyciel = new JDateChooser(kalendarz.getDate());
        wybierzDateNauczyciel.setPreferredSize(new Dimension(150, 23));
        wybierzDateNauczyciel.setDateFormatString("yyyy-MM-dd");
        dataZatrudnienia.setSize(150, 23);
        dataZatrudnienia.add(wybierzDateNauczyciel);
        comboBoxKlasyUczniowie = new JComboBox();
        comboBoxKlasyPrzedmiot = new JComboBox();
    }

    private Integer nowyIdUcznia(){
        Integer max=0;
        for (int i=0; i<dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().size(); i++){
            if (dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getIdUcznia()>max)
            max = dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getIdUcznia();
        }
        return max+1;
    }

    private Integer nowyIdNauczyciela(){
        Integer max=0;
        for (int i=0; i<dziennik.getNauczycieleObiekt().getNauczyciele().size(); i++){
            if (dziennik.getNauczycieleObiekt().getNauczyciele().get(i).getIdNauczyciela()>max)
                max = dziennik.getNauczycieleObiekt().getNauczyciele().get(i).getIdNauczyciela();
        }
        return max+1;
    }

    private Integer nowyIdPrzedmiotu(){
        Integer max=0;
        for (int i=0; i<dziennik.getKlasyObiekt().getPrzedmiotyObiekt().getPrzedmioty().size(); i++){
            if (dziennik.getKlasyObiekt().getPrzedmiotyObiekt().getPrzedmioty().get(i).getIdPrzedmiotu()>max)
                max = dziennik.getKlasyObiekt().getPrzedmiotyObiekt().getPrzedmioty().get(i).getIdPrzedmiotu();
        }
        return max+1;
    }

    private Integer nowyIdKlasy(){
        Integer max=0;
        for (int i=0; i<dziennik.getKlasyObiekt().getKlasy().size(); i++){
            if (dziennik.getKlasyObiekt().getKlasy().get(i).getIdKlasy()>max)
                max = dziennik.getKlasyObiekt().getKlasy().get(i).getIdKlasy();
        }
        return max+1;
    }

}
