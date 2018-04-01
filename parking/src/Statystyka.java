import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class Statystyka extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollStatystyka;
    private DefaultTableModel modelTabeliStat;
    private JTable tabelaStat;

    public Statystyka() {
        setContentPane(contentPane);
        setModal(true);
//        getRootPane().setDefaultButton(buttonOK);

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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                onCancel();
//            }
//        });

        // call onCancel() on ESCAPE
//        contentPane.registerKeyboardAction(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setSize(900,400);
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
        modelTabeliStat = new DefaultTableModel();
        modelTabeliStat.addColumn("L.p.");
        modelTabeliStat.addColumn("Nr rej.");
        modelTabeliStat.addColumn("Rodzaj pojazdu");
        modelTabeliStat.addColumn("Data parkowania");
        modelTabeliStat.addColumn("Data wyjazdu");
        modelTabeliStat.addColumn("Dodatek za mycie");
        modelTabeliStat.addColumn("Opłata razem");
        tabelaStat = new JTable(modelTabeliStat);
        scrollStatystyka = new JScrollPane(tabelaStat);
        tabelaStat.getColumnModel().getColumn(0).setPreferredWidth(5);
        tabelaStat.getColumnModel().getColumn(1).setPreferredWidth(30);
        tabelaStat.getColumnModel().getColumn(2).setPreferredWidth(70);
        tabelaStat.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabelaStat.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabelaStat.getColumnModel().getColumn(5).setPreferredWidth(50);
        tabelaStat.getColumnModel().getColumn(6).setPreferredWidth(50);
        modelTabeliStat.setRowCount(Wrapper.getRejestrParkowan().size());
        for (int i=0; i<Wrapper.getRejestrParkowan().size(); i++){
            tabelaStat.setValueAt(i+1, i, 0);
            tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getNrRejString(), i, 1);
            tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu(), i, 2);
            tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getDataIn(), i, 3);
            tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getDataOut(), i, 4);

        }
    }
}
