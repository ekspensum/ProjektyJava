import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;

public class Okna {
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
    private String[] pojazdy = {"Motocykl", "Sam. osobowy", "Sam. dostawczy"};
    private Pojazd[] p;
    private Wrapper wo;
    private LocalDateTime dataIn, dataOut;
    private boolean trzyKola;
    private String cenaMotocykl_1;
    private String cenaMotocykl_2;
    private Integer x, y;

    public Okna() {
        this.trzyKola = false;
        JFrame frame = new JFrame("Obsługa parkingu");
        frame.setContentPane(panelGlowny);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(900, 500);

        p = new Pojazd[3];
        wo = new Wrapper();

        odczytMiejsc();

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
                ustawMiejsce(comboPojazd.getSelectedIndex());
                p[0] = new Motocykl(Double.valueOf(textFieldCenaMotocykl.getText()), dataIn.now(), null, textFieldNrRej.getText(), x, y, comboPojazd.getSelectedIndex(), trzyKola);
                p[1] = new Osobowy(Double.valueOf(textFieldCenaOsobowy.getText()), dataIn.now(), null, textFieldNrRej.getText(), x, y, comboPojazd.getSelectedIndex(), Double.valueOf(textFieldMycie.getText()));
                p[2] = new Dostawczy(Double.valueOf(textFieldCenaDostawczy.getText()), dataIn.now(), null, textFieldNrRej.getText(), x, y, comboPojazd.getSelectedIndex());
                try {
                    Wrapper.getRejestrParkowan();
                } catch (IOException park) {
                    System.out.println(park.toString());
                }

                p[comboPojazd.getSelectedIndex()].parkowanie();
            }
        });
        dodajPojazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wo.setRejestrPojazdow(textFieldNrRej.toString(), comboPojazd.getSelectedIndex());

                for (int i = 0; i < wo.getRejestrPojazdow().size(); i++)
                    System.out.println(i + ". " + wo.getRejestrPojazdow().get(i).getNrRejString() + " " + wo.getRejestrPojazdow().get(i).getRodzajPojazdu());
            }
        });
        odczytBazyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    for (int i = 0; i < wo.getRejestrParkowan().size(); i++) {
                        odczytMiejsc();
                        System.out.println(i + ". " + Wrapper.getRejestrParkowan().get(i).getNrRejString() + " " + Wrapper.getRejestrParkowan().get(i).getCena() + " " + Wrapper.getRejestrParkowan().get(i).getDataIn() + " " + Wrapper.getRejestrParkowan().get(i).getDataOut() + " " + Wrapper.getRejestrParkowan().get(i).isTrzyKola() + " " + Wrapper.getRejestrParkowan().get(i).getMycie() + " " + Wrapper.getRejestrParkowan().get(i).getX() + " " + Wrapper.getRejestrParkowan().get(i).getY());
                    }
                } catch (IOException f) {
                    System.out.println(f.toString());
                }
            }
        });

    }

    public static void main(String[] args) {
        new Okna();
    }

    private void createUIComponents() {
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
        textFieldCenaMotocykl = new JTextField();
        textFieldCenaOsobowy = new JTextField();
        textFieldCenaDostawczy = new JTextField();
        textFieldMycie = new JTextField();
        motocyklTrzykolowyCheckBox = new JCheckBox();


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
            for (int i = 0; i < Wrapper.getRejestrParkowan().size(); i++) {
                tabelaParking.setValueAt(Wrapper.getRejestrParkowan().get(i).getNrRejString(), Wrapper.getRejestrParkowan().get(i).getY(), Wrapper.getRejestrParkowan().get(i).getX());
            }
        } catch (IOException msc) {
            System.out.println(msc);
        }
    }
}
