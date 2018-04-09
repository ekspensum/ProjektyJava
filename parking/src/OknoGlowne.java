import javafx.scene.control.CheckBox;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class OknoGlowne {
    static String[] pojazdy = {"Motocykl", "Sam. osobowy", "Sam. dostawczy"};
    private JPanel panelGlowny;
    private JScrollPane scrollPane;
    private DefaultTableModel modelTabeli;
    private JTable tabelaParking;
    private JTextField textFieldNrRej;
    private JTextField textFieldCenaMotocykl;
    private JTextField textFieldCenaDostawczy;
    private JTextField textFieldCenaOsobowy;
    private JTextField textFieldMycie;
    private JButton parkujButton;
    private JComboBox comboPojazd;
    private JButton dodajPojazdButton;
    private JButton odczytBazyButton;
    private JCheckBox motocyklTrzykolowyCheckBox;
    private JCheckBox mycieCheckBox;
    private JButton wyjazdButton;
    private JButton usunPojazdButton;
    private JButton statystykaButton;
    private JCheckBox chlodniaCheckBox;
    private JTextField textFieldChlodnia;
    private JButton odczytPojazdButton;
    private Pojazd[] p;
    private Wrapper wo;
    private LocalDateTime dataIn, dataOut;
    private boolean trzyKola;
    private String cenaMotocykl_1;
    private String cenaMotocykl_2;
    private Integer x, y;

    public OknoGlowne() {
        this.trzyKola = false;
        JFrame frame = new JFrame("Obsługa parkingu");
        frame.setContentPane(panelGlowny);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(900, 500);

        p = new Pojazd[3];
        wo = new Wrapper();

        odczytMiejsc();
//        try {
//            wo.przypiszRejestrPojazdow();
//        } catch (IOException b){
//            System.out.println(b);
//        }

        motocyklTrzykolowyCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trzyKola = motocyklTrzykolowyCheckBox.isSelected();
                cenaMotocykl_1 = textFieldCenaMotocykl.getText();
                if (trzyKola) {
                    Double x = Double.valueOf(textFieldCenaMotocykl.getText());
                    x *= 100 * 1.5;
                    Math.round(x);
                    x /= 100;
                    textFieldCenaMotocykl.setText(Double.toString(x));
                    cenaMotocykl_2 = cenaMotocykl_1;
                } else if (!trzyKola) textFieldCenaMotocykl.setText(cenaMotocykl_2);
            }
        });
        parkujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldNrRej.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Proszę wprowadzić nr rejestracyjny pojazdu");
                else if (sprawdzCzyNieZaparkowany()) {
                    ustawMiejsce(comboPojazd.getSelectedIndex());
                    if (comboPojazd.getSelectedIndex() == 0)
                        p[0] = new Motocykl(Double.valueOf(textFieldCenaMotocykl.getText()), dataIn.now(), null, textFieldNrRej.getText(), x, y, comboPojazd.getSelectedIndex(), trzyKola);
                    else if (comboPojazd.getSelectedIndex() == 1)
                        p[1] = new Osobowy(Double.valueOf(textFieldCenaOsobowy.getText()), dataIn.now(), null, textFieldNrRej.getText(), x, y, comboPojazd.getSelectedIndex(), Double.valueOf(textFieldMycie.getText()));
                    else if (comboPojazd.getSelectedIndex() == 2)
                        p[2] = new Dostawczy(Double.valueOf(textFieldCenaDostawczy.getText()), dataIn.now(), null, textFieldNrRej.getText(), x, y, comboPojazd.getSelectedIndex(), Double.valueOf(textFieldChlodnia.getText()));

                    p[comboPojazd.getSelectedIndex()].parkowanie();
                }
            }
        });
        dodajPojazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    wo.setRejestrPojazdow(textFieldNrRej.getText(), comboPojazd.getSelectedIndex());
                } catch (IOException ePoj){
                    System.out.println(ePoj);
                }
            }
        });
        odczytBazyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    wo.przypiszRejestrParkowan();
                    for (int i = 0; i < wo.getRejestrParkowan().size(); i++) {
                        System.out.println(i + ". " + Wrapper.getRejestrParkowan().get(i).getNrRejString() + " " + Wrapper.getRejestrParkowan().get(i).getCena() + " " + Wrapper.getRejestrParkowan().get(i).getDataIn() + " " + Wrapper.getRejestrParkowan().get(i).getDataOut() + " " + Wrapper.getRejestrParkowan().get(i).isTrzyKola() + " " + Wrapper.getRejestrParkowan().get(i).getMycie() + " " + Wrapper.getRejestrParkowan().get(i).getX() + " " + Wrapper.getRejestrParkowan().get(i).getY() + " " + Wrapper.getRejestrParkowan().get(i).getChlodnia());
                    }
                } catch (IOException f) {
                    System.out.println(f.toString());
                }
            }
        });
        tabelaParking.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tabelaParking.getValueAt(tabelaParking.getSelectedRow(), tabelaParking.getSelectedColumn()) != null)
                    textFieldNrRej.setText(tabelaParking.getValueAt(tabelaParking.getSelectedRow(), tabelaParking.getSelectedColumn()).toString());
            }
        });
        mycieCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mycieCheckBox.isSelected()) {
                    textFieldMycie.setEnabled(true);
                    textFieldMycie.setText("40");
                } else {
                    textFieldMycie.setEnabled(false);
                    textFieldMycie.setText("0");
                }
            }
        });
        wyjazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldNrRej.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Proszę wprowadzić nr rejestracyjny pojazdu lub wskazać myszką i kliknąć");
                else {
                    for (int i = 0; i < Wrapper.getRejestrParkowan().size(); i++) {

                        if (Wrapper.getRejestrParkowan().get(i).getNrRejString().equals(textFieldNrRej.getText()) && Wrapper.getRejestrParkowan().get(i).getDataOut() == null) {
                            if (Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu().equals(0))
                                p[0] = new Motocykl(Double.valueOf(Wrapper.getRejestrParkowan().get(i).getCena()), Wrapper.getRejestrParkowan().get(i).getDataIn(), dataOut.now(), Wrapper.getRejestrParkowan().get(i).getNrRejString(), Wrapper.getRejestrParkowan().get(i).getX(), Wrapper.getRejestrParkowan().get(i).getY(), Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu(), Wrapper.getRejestrParkowan().get(i).isTrzyKola());
                            else if (Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu().equals(1))
                                p[1] = new Osobowy(Double.valueOf(Wrapper.getRejestrParkowan().get(i).getCena()), Wrapper.getRejestrParkowan().get(i).getDataIn(), dataOut.now(), Wrapper.getRejestrParkowan().get(i).getNrRejString(), Wrapper.getRejestrParkowan().get(i).getX(), Wrapper.getRejestrParkowan().get(i).getY(), Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu(), Double.valueOf(Wrapper.getRejestrParkowan().get(i).getMycie()));
                            else if (Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu().equals(2))
                                p[2] = new Dostawczy(Double.valueOf(Wrapper.getRejestrParkowan().get(i).getCena()), Wrapper.getRejestrParkowan().get(i).getDataIn(), dataOut.now(), Wrapper.getRejestrParkowan().get(i).getNrRejString(), Wrapper.getRejestrParkowan().get(i).getX(), Wrapper.getRejestrParkowan().get(i).getY(), Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu(), Double.valueOf(Wrapper.getRejestrParkowan().get(i).getChlodnia()));

                            p[Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu()].wyjazd(i);
                            tabelaParking.setValueAt(null, Wrapper.getRejestrParkowan().get(i).getY(), Wrapper.getRejestrParkowan().get(i).getX());
                            break;
                        }
                    }
                }
            }
        });
        statystykaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Statystyka();
            }
        });
        chlodniaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chlodniaCheckBox.isSelected()) {
                    textFieldChlodnia.setEnabled(true);
                    textFieldChlodnia.setText("10.80");
                } else {
                    textFieldChlodnia.setEnabled(false);
                    textFieldChlodnia.setText("0");
                }
            }
        });
        odczytPojazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    wo.przypiszRejestrPojazdow();
                } catch (IOException exPoj){
                    System.out.println(exPoj);
                }
                for (int i = 0; i < wo.getRejestrPojazdow().size(); i++)
                    System.out.println(i + ". " + wo.getRejestrPojazdow().get(i).getNrRejString() + " " + wo.getRejestrPojazdow().get(i).getRodzajPojazdu());
            }
        });
    }

    public static void main(String[] args) {
        new OknoGlowne();
    }

    private void createUIComponents() {
        panelGlowny = new JPanel();
        modelTabeli = new DefaultTableModel();
        modelTabeli.addColumn("1 M");
        modelTabeli.addColumn("2 M");
        modelTabeli.addColumn("3 M");
        modelTabeli.addColumn("1 O");
        modelTabeli.addColumn("2 O");
        modelTabeli.addColumn("3 O");
        modelTabeli.addColumn("4 O");
        modelTabeli.addColumn("5 O");
        modelTabeli.addColumn("6 O");
        modelTabeli.addColumn("7 O");
        modelTabeli.addColumn("1 D");
        modelTabeli.addColumn("2 D");
        modelTabeli.addColumn("3 D");
        tabelaParking = new JTable(modelTabeli);
        scrollPane = new JScrollPane(tabelaParking);
        modelTabeli.setRowCount(5);
        parkujButton = new JButton();
        dodajPojazdButton = new JButton();
        comboPojazd = new JComboBox(pojazdy);
        comboPojazd.setSelectedIndex(1);
        textFieldCenaMotocykl = new JTextField();
        textFieldCenaOsobowy = new JTextField();
        textFieldCenaDostawczy = new JTextField();
        textFieldMycie = new JTextField();
        motocyklTrzykolowyCheckBox = new JCheckBox();
        textFieldChlodnia = new JTextField();
        chlodniaCheckBox = new JCheckBox();


    }

    public void ustawMiejsce(int p) {
        boolean wyjscie = false;
        if (p == 0) {
            for (int i = 0; i < tabelaParking.getRowCount(); i++) {
                for (int j = 0; j < 3; j++) {
                    if (tabelaParking.getValueAt(i, j) == null) {
                        tabelaParking.setValueAt(textFieldNrRej.getText(), i, j);
                        x = j;
                        y = i;
                        wyjscie = true;
                        break;
                    }
                }
                if (wyjscie) break;
            }
        }
        if (p == 1) {
            for (int i = 0; i < tabelaParking.getRowCount(); i++) {
                for (int j = 3; j < 10; j++) {
                    if (tabelaParking.getValueAt(i, j) == null) {
                        tabelaParking.setValueAt(textFieldNrRej.getText(), i, j);
                        x = j;
                        y = i;
                        wyjscie = true;
                        break;
                    }
                }
                if (wyjscie) break;
            }
        }
        if (p == 2) {
            for (int i = 0; i < tabelaParking.getRowCount(); i++) {
                for (int j = 10; j < tabelaParking.getColumnCount(); j++) {
                    if (tabelaParking.getValueAt(i, j) == null) {
                        tabelaParking.setValueAt(textFieldNrRej.getText(), i, j);
                        x = j;
                        y = i;
                        wyjscie = true;
                        break;
                    }
                }
                if (wyjscie) break;
            }
        }
    }

    public void odczytMiejsc() {
        try {
            wo.przypiszRejestrParkowan();
            for (int i = 0; i < Wrapper.getRejestrParkowan().size(); i++) {
                if (Wrapper.getRejestrParkowan().get(i).getDataOut() == null)
                    tabelaParking.setValueAt(Wrapper.getRejestrParkowan().get(i).getNrRejString(), Wrapper.getRejestrParkowan().get(i).getY(), Wrapper.getRejestrParkowan().get(i).getX());
            }
        } catch (IOException msc) {
            System.out.println(msc);
        }
    }

    public boolean sprawdzCzyNieZaparkowany() {
        boolean nr = true;
        try {
            wo.przypiszRejestrParkowan();
            for (int i = 0; i < Wrapper.getRejestrParkowan().size(); i++) {
                if (Wrapper.getRejestrParkowan().get(i).getNrRejString().equals(textFieldNrRej.getText()) && Wrapper.getRejestrParkowan().get(i).getDataOut() == null) {
                    nr = false;
                    break;
                }
            }
        } catch (IOException p) {
            JOptionPane.showMessageDialog(null, "Błąd pliku");
        }
        if (!nr) JOptionPane.showMessageDialog(null, "Pojazd o podanym numerze rejestracyjnym jest już zaparkowany");
        return nr;
    }

    public String[] getPojazdy() {
        return pojazdy;
    }
}
