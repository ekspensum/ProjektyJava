import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class WystawOceneTab extends JPanel {
    JPanel panelWO;
    Double[] oceny;
    JComboBox comboBoxOceny;
    private JComboBox comboBoxKlasy;
    private JComboBox comboBoxNauczyciele;
    private JComboBox comboBoxUczniowie;
    private Dziennik<Nauczyciele, Klasy<Uczniowie, Przedmioty>> dziennik = new Dziennik<>();

    public WystawOceneTab() {

        comboBoxUczniowie.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                for (int i = 0; i < dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().size(); i++) {
                    if (comboBoxUczniowie.getItemCount() < dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().size())
                        comboBoxUczniowie.addItem(dziennik.getKlasyObiekt().getUczniowieObiekt().getUczniowie().get(i).getNazwiskoUcznia());
                }
                comboBoxUczniowie.setMaximumRowCount(15);
            }
        });

    }

    private void createUIComponents() {
        panelWO = new JPanel();
        add(panelWO);
        oceny = new Double[]{1.0, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 6.0};
        comboBoxOceny = new JComboBox(oceny);
        comboBoxNauczyciele = new JComboBox();
        comboBoxUczniowie = new JComboBox();

    }

//    public String[] wyswietlNauczycieli() {
//        bw = new BazaWpisow();
//        String[] nauczyciele = new String[bw.getNauczyciele().size()];
//        for (int i = 0; i < bw.getNauczyciele().size(); i++) {
//            nauczyciele[i] = bw.getNauczyciele().get(i).getImieNauczyciela() + " " + bw.getNauczyciele().get(i).getNazwiskoNauczyciela();
//        }
//        return nauczyciele;
//    }
//
//    public String[] wyswietlUczniow() {
//        bw = new BazaWpisow();
//        String[] uczniowie = new String[bw.getUczniowieWszyscy().size()];
//        for (int i = 0; i < bw.getUczniowieWszyscy().size(); i++) {
//            uczniowie[i] = bw.getUczniowieWszyscy().get(i).getImieUcznia() + " " + bw.getUczniowieWszyscy().get(i).getNazwiskoUcznia();
//        }
//        return uczniowie;
//    }
}
