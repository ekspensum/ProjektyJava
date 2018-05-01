import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;


public class AdministracjaTab extends JPanel {
//    Uczniowie uczniowie = new Uczniowie();
//    Nauczyciele nauczyciele = new Nauczyciele();
//    Przedmioty przedmioty = new Przedmioty();
    Klasy<Uczniowie, Przedmioty> klasy = new Klasy<>(new Uczniowie(), new Przedmioty());
    Dziennik<Nauczyciele, Klasy<Uczniowie, Przedmioty>> dziennik = new Dziennik<>(new Nauczyciele(), new Klasy<>(new Uczniowie(), new Przedmioty()));
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
                try {
                    dziennik.getKlasyObiekt().getUczniowieObiekt().dodajUcznia(nowyIdUcznia(), comboBoxKlasyUczniowie.getSelectedIndex(), textFieldImieUcznia.getText(), textFieldNazwiskoUcznia.getText(), textFieldLoginUcznia.getText(), passwordField1Ucznia.getSelectedText(), wybierzDateUczen.getDate());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                for (int i=0; i<uczniowie.getUczniowie().size(); i++)
//                System.out.println(uczniowie.getUczniowie().get(i).getNazwiskoUcznia());
//                System.out.println(uczniowie.getUczniowie().size());
            }
        });
        buttonDodajNauczyciela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dziennik.getNauczycieleObiekt().dodajNauczyciela(nowyIdNauczyciela(), textFieldImieNauczyciela.getText(), textFieldNazwiskoNauczyciela.getText(), textFieldLoginNauczyciela.getText(), passwordField1Nauczyciela.getSelectedText(), wybierzDateNauczyciel.getDate());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
                    klasy.dodajKlase(nowyIdKlasy(), textFieldKlasa.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println(dziennik.getKlasyObiekt().getKlasy().size());
            }
        });
        comboBoxKlasyPrzedmiot.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                for (int i = 0; i < klasy.getKlasy().size(); i++) {
                    if (comboBoxKlasyPrzedmiot.getItemCount() < klasy.getKlasy().size())
                        comboBoxKlasyPrzedmiot.addItem(klasy.getKlasy().get(i).getNazwaKlasy());
                }
//                comboBoxKlasyPrzedmiot.setSelectedIndex(0);
                comboBoxKlasyPrzedmiot.setMaximumRowCount(10);
                for (int i=0; i<klasy.getKlasy().size(); i++)
                    System.out.println(klasy.getKlasy().get(i).getNazwaKlasy());
            }
        });
        comboBoxKlasyUczniowie.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                for (int i = 0; i < dziennik.getKlasyObiekt().getKlasy().size(); i++) {
                    if (comboBoxKlasyUczniowie.getItemCount() < dziennik.getKlasyObiekt().getKlasy().size())
                        comboBoxKlasyUczniowie.addItem(dziennik.getKlasyObiekt().getKlasy().get(i).getNazwaKlasy());
                }
                comboBoxKlasyUczniowie.setSelectedIndex(0);
                comboBoxKlasyUczniowie.setMaximumRowCount(10);
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
