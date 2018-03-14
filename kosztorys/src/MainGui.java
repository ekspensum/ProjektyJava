import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class MainGui extends JFrame {

    private JPanel panelGlowny;
    private DefaultTableModel modelTabeli;
    private JTable tabelaKosztorys;
    private JScrollPane scrollPane;
    private JButton wstawWierszButton;
    private JButton usunWierszButton;
    private JButton dodajWierszButton;
    private JTextField podsumowanie;
    private Vector<String> linia;
    private String sciezkaPliku, ilosc, cena;
    private Double wartosc, suma;
    private JFileChooser wybierzPlik;
    private FileNameExtensionFilter filtrRozszerzenia;
    private JMenuItem podMenuNowy;
    private JMenuItem podMenuOtworz;
    private JMenuItem podMenuZapisz;
    private JMenuItem podMenuZapiszJako;
    private JMenuItem podMenuZamknij;
    private CellEditorListener sluchaczKomorki;
    private JComboBox comboJm;
    private String[] jm = {"kpl", "szt", "m", "m2", "m3", "..."};
    private Filtr f;


    public MainGui() {
        JFrame frame = new JFrame();
        frame.setContentPane(panelGlowny);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Kosztorys");
        frame.setSize(1200, 700);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuPlik = new JMenu("Plik");
        JMenu menuUstawienia = new JMenu("Ustawienia");
        menuBar.add(menuPlik);
        menuBar.add(menuUstawienia);
        frame.setJMenuBar(menuBar);
        podMenuNowy = new JMenuItem("Nowy");
        podMenuOtworz = new JMenuItem("Otwórz");
        podMenuZapisz = new JMenuItem("Zapisz");
        podMenuZapiszJako = new JMenuItem("Zapisz jako...");
        podMenuZamknij = new JMenuItem("Zamknij bieżący plik");
        menuPlik.add(podMenuNowy);
        menuPlik.add(podMenuOtworz);
        menuPlik.add(podMenuZapisz);
        menuPlik.add(podMenuZapiszJako);
        menuPlik.add(podMenuZamknij);

        linia = new Vector<>();
        flagaOtwartyPlik(false);
        f = new Filtr();

        podMenuNowy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelTabeli.setRowCount(5);
                for (int i = 0; i < 5; i++) {
                    linia.add(i, "0");
                    tabelaKosztorys.setValueAt(comboJm.getItemAt(0), i, 2);
                    tabelaKosztorys.setValueAt(linia.get(i), i, 3);
                    tabelaKosztorys.setValueAt(linia.get(i), i, 4);
                }
                flagaOtwartyPlik(true);
                podMenuZapisz.setEnabled(false);
                podMenuOtworz.setEnabled(false);
                podMenuNowy.setEnabled(false);
                obliczKosztorys();
            }
        });
        podMenuOtworz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odczyt();
                flagaOtwartyPlik(true);
            }
        });
        podMenuZapisz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zapis();
            }
        });
        podMenuZapiszJako.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zapiszJako();
            }
        });
        podMenuZamknij.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linia.removeAllElements();
                modelTabeli.setRowCount(0);
                sciezkaPliku = "";
                flagaOtwartyPlik(false);
                podMenuNowy.setEnabled(true);
                podMenuOtworz.setEnabled(true);
            }
        });
        wstawWierszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabelaKosztorys.getSelectedRow() > -1) {
                    linia.add(tabelaKosztorys.getSelectedRow(), "0");
                    modelTabeli.insertRow(tabelaKosztorys.getSelectedRow(), new Object[]{"", "", comboJm.getItemAt(0), "0", "0"});
                    obliczKosztorys();
                } else JOptionPane.showMessageDialog(null, "Zaznacz wiersz do wstawienia...");
            }
        });
        dodajWierszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linia.add("");
                modelTabeli.addRow(new Object[]{"", "", comboJm.getItemAt(0), "0", "0"});
                obliczKosztorys();
            }
        });
        usunWierszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabelaKosztorys.getSelectedRow() > -1) {
                    linia.remove(tabelaKosztorys.getSelectedRow());
                    modelTabeli.removeRow(tabelaKosztorys.getSelectedRow());
                } else JOptionPane.showMessageDialog(null, "Zaznacz wiersz do usunięcia...");

            }
        });
        sluchaczKomorki = new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                obliczKosztorys();
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        };
        tabelaKosztorys.getDefaultEditor(String.class).addCellEditorListener(sluchaczKomorki);
//        comboJm.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JComboBox comboJmKopia = (JComboBox)e.getSource();
////                String petName = (String)comboJmKopia.getSelectedItem();
//                System.out.println((String)comboJmKopia.getSelectedItem());
//            }
//        });
    }

    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setContentPane(new MainGui().panelGlowny);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JMenuBar menuBar = new JMenuBar();
//        JMenu menu1 = new JMenu("Plik");
//        menuBar.add(menu1);
//        frame.setJMenuBar(menuBar);
//        JMenuItem podMenuOtworz = new JMenuItem("Otwórz");
//        JMenuItem podMenuZapisz = new JMenuItem("Zapisz");
//        menu1.add(podMenuOtworz);
//        menu1.add(podMenuZapisz);
//        frame.setVisible(true);
//        frame.setTitle("Kosztorys");
//        frame.setSize(1200, 700);
        new MainGui();
    }

    private void createUIComponents() {
        modelTabeli = new DefaultTableModel();
        modelTabeli.addColumn("Norma");
        modelTabeli.addColumn("Opis");
        modelTabeli.addColumn("J.m.");
        modelTabeli.addColumn("Ilość");
        modelTabeli.addColumn("Cena");
        modelTabeli.addColumn("Wartość");
        tabelaKosztorys = new JTable(modelTabeli);
        scrollPane = new JScrollPane(tabelaKosztorys);
        tabelaKosztorys.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabelaKosztorys.getColumnModel().getColumn(1).setPreferredWidth(400);
        tabelaKosztorys.getColumnModel().getColumn(2).setPreferredWidth(30);
        tabelaKosztorys.getColumnModel().getColumn(3).setPreferredWidth(50);
        tabelaKosztorys.getColumnModel().getColumn(4).setPreferredWidth(50);
        tabelaKosztorys.getColumnModel().getColumn(5).setPreferredWidth(70);
        podsumowanie = new JTextField();

        comboJm = new JComboBox(jm);
        comboJm.setSelectedIndex(0);
        javax.swing.table.TableColumn jmColumn = tabelaKosztorys.getColumnModel().getColumn(2);
        jmColumn.setCellEditor(new DefaultCellEditor(comboJm));

    }

    public void odczyt() {
        wybierzPlik = new JFileChooser();
        filtrRozszerzenia = new FileNameExtensionFilter("Pliki *.txt", "txt");
        wybierzPlik.setFileFilter(filtrRozszerzenia);
        int czyWybrano = wybierzPlik.showOpenDialog(this);
        if (czyWybrano == JFileChooser.APPROVE_OPTION) {
            try {
                sciezkaPliku = wybierzPlik.getSelectedFile().getPath();
                File plik = new File(sciezkaPliku);
                Scanner we = new Scanner(plik);
                int i = 0;
                while (we.hasNextLine()) {
                    linia.add(i, we.nextLine());
                    i++;
                }
                modelTabeli.setNumRows(linia.size());
                for (int j = 0; j < linia.size(); j++) {
                    tabelaKosztorys.setValueAt(linia.get(j).split("#")[0], j, 0);
                    tabelaKosztorys.setValueAt(linia.get(j).split("#")[1], j, 1);
                    tabelaKosztorys.setValueAt(linia.get(j).split("#")[2], j, 2);
                    tabelaKosztorys.setValueAt(linia.get(j).split("#")[3], j, 3);
                    tabelaKosztorys.setValueAt(linia.get(j).split("#")[4], j, 4);
                    tabelaKosztorys.setValueAt(linia.get(j).split("#")[5], j, 5);
                }
                we.close();
                obliczKosztorys();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Brak pliku.");
            }
        }
    }

    public void zapis() {
        if (sprawdzItem()) {
            try {
                FileWriter wy = new FileWriter(sciezkaPliku, false);
                for (int i = 0; i < linia.size(); i++)
                    wy.write(tabelaKosztorys.getValueAt(i, 0) + "#" + tabelaKosztorys.getValueAt(i, 1) + "#" + tabelaKosztorys.getValueAt(i, 2) + "#" + tabelaKosztorys.getValueAt(i, 3) + "#" + tabelaKosztorys.getValueAt(i, 4) + "#" + tabelaKosztorys.getValueAt(i, 5) + "\r\n");
                wy.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Zapisanie pliku nie powiodło się.");
            }
        }
    }

    public void zapiszJako() {
        if (sprawdzItem()) {
            wybierzPlik = new JFileChooser();
            filtrRozszerzenia = new FileNameExtensionFilter("Pliki *.txt", "txt");
            wybierzPlik.setFileFilter(filtrRozszerzenia);
            int czyZapisano = wybierzPlik.showSaveDialog(this);
            if (czyZapisano == JFileChooser.APPROVE_OPTION) {
                try {
                    sciezkaPliku = wybierzPlik.getSelectedFile().getPath() + ".txt";
                    FileWriter wy = new FileWriter(sciezkaPliku, false);
                    for (int i = 0; i < linia.size(); i++)
                        wy.write(tabelaKosztorys.getValueAt(i, 0) + "#" + tabelaKosztorys.getValueAt(i, 1) + "#" + tabelaKosztorys.getValueAt(i, 2) + "#" + tabelaKosztorys.getValueAt(i, 3) + "#" + tabelaKosztorys.getValueAt(i, 4) + "#" + tabelaKosztorys.getValueAt(i, 5) + "\r\n");
                    wy.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Zapisanie pliku nie powiodło się.");
                }
            }
            podMenuZapisz.setEnabled(true);
        }
    }

    public void flagaOtwartyPlik(boolean b) {
        wstawWierszButton.setEnabled(b);
        usunWierszButton.setEnabled(b);
        dodajWierszButton.setEnabled(b);
        podMenuZapisz.setEnabled(b);
        podMenuZapiszJako.setEnabled(b);
        podMenuZamknij.setEnabled(b);
    }

    public void obliczKosztorys() {
        suma = 0.0;
        for (int i = 0; i < linia.size(); i++) {
            ilosc = modelTabeli.getValueAt(i, 3).toString();
            cena = modelTabeli.getValueAt(i, 4).toString();
            wartosc = Double.valueOf(ilosc) * Double.valueOf(cena);
            suma += wartosc;
            modelTabeli.setValueAt(wartosc.toString(), i, 5);
        }
        podsumowanie.setText(suma.toString());
    }

    public boolean sprawdzItem() {
        String tekst;
        boolean dobryZnak = true;
        for (int i = 0; i < tabelaKosztorys.getRowCount(); i++)
            for (int j = 0; j < tabelaKosztorys.getColumnCount(); j++) {
                if (tabelaKosztorys.getValueAt(i, j) != null) {
                    tekst = tabelaKosztorys.getValueAt(i, j).toString();
                    tekst.trim();
//                    dobryZnak = f.filtrZnakow(tekst);
                    if (!f.filtrZnakow(tekst)) {
                        JOptionPane.showMessageDialog(null, "Niedozwolony znak: #");
                        tabelaKosztorys.editCellAt(i, j);
                        dobryZnak = false;
                        break;
                    }
                }
            }
        return dobryZnak;
    }
}
