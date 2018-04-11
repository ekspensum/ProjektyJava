import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;

public class OknoPojazdy extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable tabelaPojazdy;
    private JScrollPane scrollPojazdy;
    private JButton dodajPojazdButton;
    private JButton usunPojazdButton;
    private DefaultTableModel modelTabeliPojazdy;
    private Wrapper war;

    public OknoPojazdy() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        dodajPojazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DodajPojazd();
                wypelnijTabelePojazdy();
            }
        });

        usunPojazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean b = true;
                for (int i = 0; i < tabelaPojazdy.getRowCount(); i++) {
                    if (tabelaPojazdy.getValueAt(i, 4) != null) {
                        Boolean checked = Boolean.valueOf(tabelaPojazdy.getValueAt(i, 4).toString());
                        String nrRej = tabelaPojazdy.getValueAt(i, 1).toString();
                        if (checked) {
                            try {
                                war.usunPojazd(nrRej);
                            } catch (IOException ioe2){
                                System.out.println(ioe2);
                            }
                            wypelnijTabelePojazdy();
                            JOptionPane.showMessageDialog(null, "Usunięto pojazd o numerze rej.: "+nrRej);
                        }
                        b = false;
                        break;
                    }
                }
                if (b) JOptionPane.showMessageDialog(null, "Proszę zaznaczyć pojazd do usunięcia.");
            }
        });


        setTitle("Dodaj / usuń pojazd");
        setSize(700, 400);
        setVisible(true);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        modelTabeliPojazdy = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return Boolean.class;

                    default:
                        return String.class;
                }
            }
        };
        tabelaPojazdy = new JTable(modelTabeliPojazdy);
        scrollPojazdy = new JScrollPane(tabelaPojazdy);
        modelTabeliPojazdy.addColumn("L.p.");
        modelTabeliPojazdy.addColumn("Nr rej.");
        modelTabeliPojazdy.addColumn("Rodzaj pojazdu");
        modelTabeliPojazdy.addColumn("Cena parkow.");
        modelTabeliPojazdy.addColumn("Do usunięcia");
        tabelaPojazdy.getColumnModel().getColumn(0).setPreferredWidth(20);
        tabelaPojazdy.getColumnModel().getColumn(1).setPreferredWidth(70);
        tabelaPojazdy.getColumnModel().getColumn(2).setPreferredWidth(130);
        tabelaPojazdy.getColumnModel().getColumn(3).setPreferredWidth(130);
        tabelaPojazdy.getColumnModel().getColumn(4).setPreferredWidth(70);
        wypelnijTabelePojazdy();


    }

    public void wypelnijTabelePojazdy() {
        try {
            war = new Wrapper();
            war.przypiszRejestrPojazdow();
            modelTabeliPojazdy.setRowCount(Wrapper.getRejestrPojazdow().size());
            for (int i = 0; i < Wrapper.getRejestrPojazdow().size(); i++) {
                tabelaPojazdy.setValueAt(i + 1, i, 0);
                tabelaPojazdy.setValueAt(Wrapper.getRejestrPojazdow().get(i).getNrRejString(), i, 1);
                tabelaPojazdy.setValueAt(OknoGlowne.pojazdy[Wrapper.getRejestrPojazdow().get(i).getRodzajPojazdu()], i, 2);
                tabelaPojazdy.setValueAt(Wrapper.getRejestrPojazdow().get(i).getCena(), i, 3);
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}
