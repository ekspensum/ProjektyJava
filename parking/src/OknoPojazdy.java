import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class OknoPojazdy extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable tabelaPojazdy;
    private JScrollPane scrollPojazdy;
    private JButton dodajPojazdButton;
    private JButton usunPojazdButton;
    private DefaultTableModel modelTabeliPojazdy;

    public OknoPojazdy() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

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
            }
        });

        usunPojazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<tabelaPojazdy.getRowCount();i++)
                {
                    if (tabelaPojazdy.getValueAt(i, 4) != null) {
                        Boolean checked=Boolean.valueOf(tabelaPojazdy.getValueAt(i, 4).toString());
//                        String col=tabelaPojazdy.getValueAt(i, 1).toString();

                        if(checked)
                        {
//                        JOptionPane.showMessageDialog(null, col);
                            System.out.println("Do usunięcia");
                        }
                    }

                }
            }
        });


        setTitle("Dodaj / usuń pojazd");
        setSize(700,500);
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {

        modelTabeliPojazdy = new DefaultTableModel()
        {
            public Class<?> getColumnClass(int column)
            {
                switch(column)
                {
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
        modelTabeliPojazdy.setRowCount(10);
    }
}
