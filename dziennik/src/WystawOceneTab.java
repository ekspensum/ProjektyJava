import javax.swing.*;
import java.awt.event.*;

public class WystawOceneTab extends JPanel {
    JPanel panelWO;
    Double[] oceny;
    JComboBox comboBoxOceny;
    private JComboBox comboBoxKlasy;
    private JComboBox comboBoxPrzedmioty;
    private JComboBox comboBoxUczniowie;
    private Dziennik<Nauczyciele, Klasy<Uczniowie, Przedmioty>> dziennik = new Dziennik<>(new Nauczyciele(), new Klasy<>(new Uczniowie(), new Przedmioty()));

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
                comboBoxUczniowie.setMaximumRowCount(comboBoxUczniowie.getItemCount());
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
                comboBoxPrzedmioty.setMaximumRowCount(comboBoxPrzedmioty.getItemCount());
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

    }

}
