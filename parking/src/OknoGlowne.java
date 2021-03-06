import javafx.scene.control.CheckBox;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
    private JTextField textFieldChlodnia;
    private JTextField textFieldWspTrzyKola;
    private JButton parkujButton;
    private JButton odczytBazyButton;
    private JCheckBox motocyklTrzykolowyCheckBox;
    private JCheckBox mycieCheckBox;
    private JButton wyjazdButton;
    private JButton pojazdyButton;
    private JButton statystykaButton;
    private JCheckBox chlodniaCheckBox;
    private JButton cennikButton;
    private Pojazd[] p;
    private Wrapper wo;
    private LocalDateTime dataIn, dataOut;
    private boolean trzyKola;
    private String cenaMotocykl_1;
    private String cenaMotocykl_2;
    private Integer x, y;
    static ArrayList<String> cennik = new ArrayList<>();

    public OknoGlowne() {
        this.trzyKola = false;
        JFrame frame = new JFrame("Obsługa parkingu");
        frame.setContentPane(panelGlowny);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1000, 500);

        p = new Pojazd[3];
        wo = new Wrapper();

        try {
            wo.przypiszRejestrPojazdow();
        } catch (IOException e) {
            e.printStackTrace();
        }
        odczytMiejsc();

        try {
            przypiszCennik();
        } catch (IOException gCen) {
            System.out.println(gCen);
        }
        if (!cennik.isEmpty()) {
            textFieldCenaMotocykl.setText(cennik.get(0));
            textFieldCenaOsobowy.setText(cennik.get(1));
            textFieldCenaDostawczy.setText(cennik.get(2));
            textFieldWspTrzyKola.setText(cennik.get(3));
            textFieldMycie.setText("0.00");
            textFieldChlodnia.setText("0.00");
        }

        motocyklTrzykolowyCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trzyKola = motocyklTrzykolowyCheckBox.isSelected();
                cenaMotocykl_1 = textFieldCenaMotocykl.getText();
                if (trzyKola) {
                    Double x = Double.valueOf(textFieldCenaMotocykl.getText());
                    x *= 100 * Double.valueOf(textFieldWspTrzyKola.getText());
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
                else if (!sprawdzCzyZarejestrowany()) {
                    DodajPojazd.nrRej = textFieldNrRej.getText();
                    new DodajPojazd();
                    if (sprawdzCzyZarejestrowany()) {
                        parkuj(textFieldNrRej.getText());
                        textFieldNrRej.setText(null);
                    }
                } else if (sprawdzCzyNieZaparkowany()) {
                    parkuj(textFieldNrRej.getText());
                    textFieldNrRej.setText(null);
                }
            }
        });
        odczytBazyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    wo.przypiszRejestrParkowan();
                    wo.przypiszRejestrPojazdow();
                } catch (IOException f) {
                    System.out.println(f.toString());
                }
                for (int i = 0; i < wo.getRejestrParkowan().size(); i++)
                    System.out.println(i + ". " + Wrapper.getRejestrParkowan().get(i).getNrRejString() + " " + Wrapper.getRejestrParkowan().get(i).getCena() + " " + Wrapper.getRejestrParkowan().get(i).getDataIn() + " " + Wrapper.getRejestrParkowan().get(i).getDataOut() + " " + Wrapper.getRejestrParkowan().get(i).isTrzyKola() + " " + Wrapper.getRejestrParkowan().get(i).getMycie() + " " + Wrapper.getRejestrParkowan().get(i).getX() + " " + Wrapper.getRejestrParkowan().get(i).getY() + " " + Wrapper.getRejestrParkowan().get(i).getChlodnia());

                for (int i = 0; i < cennik.size(); i++)
                    System.out.println(i + ". " + cennik.get(i));

                for (int i = 0; i < Wrapper.getRejestrPojazdow().size(); i++)
                    System.out.println(i + ". " + Wrapper.getRejestrPojazdow().get(i).getNrRejString() + " " + Wrapper.getRejestrPojazdow().get(i).getRodzajPojazdu() + " " + Wrapper.getRejestrPojazdow().get(i).getCena());
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
                    textFieldMycie.setText(cennik.get(4));
                } else {
                    textFieldMycie.setEnabled(false);
                    textFieldMycie.setText("0.00");
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
                                p[1] = new Osobowy(Double.valueOf(Wrapper.getRejestrParkowan().get(i).getCena()), Wrapper.getRejestrParkowan().get(i).getDataIn(), dataOut.now(), Wrapper.getRejestrParkowan().get(i).getNrRejString(), Wrapper.getRejestrParkowan().get(i).getX(), Wrapper.getRejestrParkowan().get(i).getY(), Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu(), Double.valueOf(textFieldMycie.getText()));
                            else if (Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu().equals(2))
                                p[2] = new Dostawczy(Double.valueOf(Wrapper.getRejestrParkowan().get(i).getCena()), Wrapper.getRejestrParkowan().get(i).getDataIn(), dataOut.now(), Wrapper.getRejestrParkowan().get(i).getNrRejString(), Wrapper.getRejestrParkowan().get(i).getX(), Wrapper.getRejestrParkowan().get(i).getY(), Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu(), Double.valueOf(textFieldChlodnia.getText()));
                            p[Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu()].wyjazd(i);
                            tabelaParking.setValueAt(null, Wrapper.getRejestrParkowan().get(i).getY(), Wrapper.getRejestrParkowan().get(i).getX());
                            textFieldNrRej.setText(null);
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
                    textFieldChlodnia.setText(cennik.get(5));
                } else {
                    textFieldChlodnia.setEnabled(false);
                    textFieldChlodnia.setText("0.00");
                }
            }
        });
        pojazdyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OknoPojazdy();
            }
        });
        cennikButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCennik(0, textFieldCenaMotocykl.getText());
                setCennik(1, textFieldCenaOsobowy.getText());
                setCennik(2, textFieldCenaDostawczy.getText());
                setCennik(3, textFieldWspTrzyKola.getText());
                if (mycieCheckBox.isSelected())
                    setCennik(4, textFieldMycie.getText());
                if (chlodniaCheckBox.isSelected())
                    setCennik(5, textFieldChlodnia.getText());
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
        textFieldNrRej = new JTextField();
        textFieldCenaMotocykl = new JTextField();
        textFieldCenaOsobowy = new JTextField();
        textFieldCenaDostawczy = new JTextField();
        textFieldMycie = new JTextField();
        textFieldWspTrzyKola = new JTextField();
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

    public boolean sprawdzCzyZarejestrowany() {
        boolean nr = false;
        try {
            wo.przypiszRejestrPojazdow();
            for (int i = 0; i < Wrapper.getRejestrPojazdow().size(); i++) {
                if (Wrapper.getRejestrPojazdow().get(i).getNrRejString().equals(textFieldNrRej.getText())) {
                    nr = true;
                    break;
                }
            }
        } catch (IOException p) {
            JOptionPane.showMessageDialog(null, "Błąd pliku");
        }
        if (!nr)
            JOptionPane.showMessageDialog(null, "Pojazd o podanym numerze rejestracyjnym nie jest zarejestrowany. Proszę dokonać rejestracji.");
        return nr;
    }

    public static void setCennik(int index, String cena) {
        try {
            FileOutputStream fosCen = new FileOutputStream("cennik.p");
            ObjectOutputStream oosCen = new ObjectOutputStream(fosCen);
            cennik.set(index, cena);
            oosCen.writeObject(cennik);
            oosCen.close();
            fosCen.close();
        } catch (IOException eCen) {
            System.out.println(eCen);
        }
    }

    public static void przypiszCennik() throws IOException {
        if (cennik.isEmpty()) {
            for (int i = 0; i < 6; i++)
                cennik.add(i, "0.00");
        }
        FileInputStream fisCen = new FileInputStream("cennik.p");
        ObjectInputStream oisCen = new ObjectInputStream(fisCen);
        try {
            cennik = (ArrayList<String>) oisCen.readObject();
        } catch (ClassNotFoundException fCen) {
            System.out.println(fCen.getException());
        }
    }

    public void parkuj(String nrRej) {
        Integer index = wo.znajdzIndeksPojazdu(nrRej);
        ustawMiejsce(index);
        if (index == 0)
            p[0] = new Motocykl(Double.valueOf(textFieldCenaMotocykl.getText()), dataIn.now(), null, nrRej, x, y, index, trzyKola);
        else if (index == 1)
            p[1] = new Osobowy(Double.valueOf(textFieldCenaOsobowy.getText()), dataIn.now(), null, nrRej, x, y, index, Double.valueOf(textFieldMycie.getText()));
        else if (index == 2)
            p[2] = new Dostawczy(Double.valueOf(textFieldCenaDostawczy.getText()), dataIn.now(), null, nrRej, x, y, index, Double.valueOf(textFieldChlodnia.getText()));
        p[index].parkowanie();
    }
}
