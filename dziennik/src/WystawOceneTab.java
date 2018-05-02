import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class WystawOceneTab extends JPanel {
    private JPanel panelWO;
    private JPanel panelDataOceny;
    private Double[] oceny;
    private JComboBox comboBoxOceny;
    private JComboBox comboBoxKlasy;
    private JComboBox comboBoxPrzedmioty;
    private JComboBox comboBoxUczniowie;
    private JButton wystawOcenęButton;
    private JTextArea textAreaKomentarz;
    private LocalDateTime dataOperacji;
    private JTextField textFieldDataOperacji;
    private JTextField textFieldZalogowany;
    private JDateChooser dataOceny;
    private JCalendar kalendarz;
    private Dziennik<Nauczyciele, Klasy<Uczniowie, Przedmioty>> dziennik = new Dziennik<>(new Nauczyciele(), new Klasy<>(new Uczniowie(), new Przedmioty()));
    private BazaWpisow bw = new BazaWpisow();

    public WystawOceneTab() {

        comboBoxUczniowie.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                comboBoxUczniowie.removeAllItems();
                for (int i = 0; i < dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().size(); i++) {
                    if (comboBoxKlasy.getSelectedIndex() == dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getIdKlasy())
                        comboBoxUczniowie.addItem(dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getImieUcznia()+" "+dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getNazwiskoUcznia());
                }
                comboBoxUczniowie.setSelectedIndex(0);
                comboBoxUczniowie.setMaximumRowCount(comboBoxUczniowie.getItemCount()+1);
            }
        });
        comboBoxPrzedmioty.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                comboBoxPrzedmioty.removeAllItems();
                for (int i = 0; i < dziennik.getKlasyObiekt().getPrzedmiotyObiekt().getPrzedmioty().size(); i++) {
                    if (comboBoxKlasy.getSelectedIndex() == dziennik.getKlasyObiekt().getPrzedmiotyObiekt().getPrzedmioty().get(i).getIdKlasy())
                            comboBoxPrzedmioty.addItem(dziennik.getKlasyObiekt().getPrzedmiotyObiekt().getPrzedmioty().get(i).getNazwaPrzedmiotu());
                }
                comboBoxPrzedmioty.setSelectedIndex(0);
                comboBoxPrzedmioty.setMaximumRowCount(comboBoxPrzedmioty.getItemCount()+1);
            }
        });
        comboBoxKlasy.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                comboBoxKlasy.removeAllItems();
                comboBoxPrzedmioty.removeAllItems();
                comboBoxUczniowie.removeAllItems();
                for (int i = 0; i < dziennik.getKlasyObiekt().getKlasy().size(); i++) {
                        comboBoxKlasy.addItem(dziennik.getKlasyObiekt().getKlasy().get(i).getNazwaKlasy());
                }
                comboBoxKlasy.setSelectedIndex(0);
                comboBoxKlasy.setMaximumRowCount(comboBoxKlasy.getItemCount());
            }
        });
        wystawOcenęButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bw.setWpisyDoDziennika(comboBoxKlasy.getSelectedIndex(),
                            comboBoxKlasy.getSelectedItem().toString(),
                            LogowanieTab.idNauczyciel,
                            getImieNauczyciela(LogowanieTab.idNauczyciel),
                            getNazwiskoNauczyciela(LogowanieTab.idNauczyciel),
                            getIdUcznia(comboBoxUczniowie.getSelectedItem().toString()),
                            getImieUcznia(getIdUcznia(comboBoxUczniowie.getSelectedItem().toString())),
                            getNazwiskoUcznia(getIdUcznia(comboBoxUczniowie.getSelectedItem().toString())),
                            comboBoxPrzedmioty.getSelectedItem().toString(),
                            LocalDateTime.now(),
                            dataOceny.getDate(),
                            Double.valueOf(comboBoxOceny.getSelectedItem().toString()),
                            textAreaKomentarz.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        panelWO = new JPanel();
        add(panelWO);
        oceny = new Double[]{1.0, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 6.0};
        comboBoxOceny = new JComboBox(oceny);
        comboBoxOceny.setSelectedIndex(4);
        comboBoxOceny.setMaximumRowCount(oceny.length);
        comboBoxKlasy = new JComboBox();
        comboBoxPrzedmioty = new JComboBox();
        comboBoxUczniowie = new JComboBox();
        textFieldDataOperacji = new JTextField(dataOperacji.now().toString());
        kalendarz = new JCalendar();
        dataOceny = new JDateChooser(kalendarz.getDate());
        dataOceny.setPreferredSize(new Dimension(120, 26));
        dataOceny.setDateFormatString("yyyy-MM-dd");
        panelDataOceny = new JPanel();
        panelDataOceny.add(dataOceny);
        panelDataOceny.setSize(120, 26);
        textAreaKomentarz = new JTextArea();
    }

    private String getImieNauczyciela(Integer idNauczyciela){
        for (int i=0; i<dziennik.getNauczycieleObiekt().getNauczyciele().size(); i++)
            if (idNauczyciela.equals(dziennik.getNauczycieleObiekt().getNauczyciele().get(i).getIdNauczyciela()))
                return dziennik.getNauczycieleObiekt().getNauczyciele().get(i).getImieNauczyciela();
        return "Nie znaleziono danych nauczyciela";
    }
    private String getNazwiskoNauczyciela(Integer idNauczyciela){
        for (int i=0; i<dziennik.getNauczycieleObiekt().getNauczyciele().size(); i++)
            if (idNauczyciela.equals(dziennik.getNauczycieleObiekt().getNauczyciele().get(i).getIdNauczyciela()))
            return dziennik.getNauczycieleObiekt().getNauczyciele().get(i).getNazwiskoNauczyciela();
        return "Nie znaleziono danych nauczyciela";
    }
    private Integer getIdUcznia(String imieNazwiskoUcznia){
        String [] temp = imieNazwiskoUcznia.split("\\s");
        for (int i=0; i<dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().size(); i++)
            if (temp[0].equals(dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getImieUcznia()) && temp[1].equals(dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getNazwiskoUcznia()))
                return dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getIdUcznia();
        return -1;
    }
    private String getImieUcznia(Integer idUcznia){
        for (int i=0; i<dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().size(); i++)
            if (idUcznia.equals(dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getIdUcznia()))
                return dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getImieUcznia();
        return "Nie znaleziono danych Ucznia";
    }
    private String getNazwiskoUcznia(Integer idUcznia){
        for (int i=0; i<dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().size(); i++)
            if (idUcznia.equals(dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getIdUcznia()))
                return dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getNazwiskoUcznia();
        return "Nie znaleziono danych Ucznia";
    }

    public void setTextFieldZalogowany(String daneNauczyciela) {
        this.textFieldZalogowany.setText(daneNauczyciela);
    }
}
