import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Statystyka extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollStatystyka;
    private DefaultTableModel modelTabeliStat;
    private JTable tabelaStat;
    private JPanel panelKalendarz;
    private JLabel wprowadzDateLabel;
    private JButton buttonStatOk;
    private JDateChooser wybierzDate;
    private Calendar cld;
    private SimpleDateFormat sdf;
    private String dataOut, dataKalendarz;
    private LocalDateTime dataTime;

    public Statystyka() {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        setContentPane(contentPane);
        setModal(true);
//        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x=0;
                for (int i=0; i<Wrapper.getRejestrParkowan().size(); i++){
                    tabelaStat.setValueAt(null, i,0);
                    tabelaStat.setValueAt(null, i,1);
                    tabelaStat.setValueAt(null, i,2);
                    tabelaStat.setValueAt(null, i,3);
                    tabelaStat.setValueAt(null, i,4);
                    if (Wrapper.getRejestrParkowan().get(i).getDataOut() != null && wybierzDate.getDate() != null){
                        if (Wrapper.getRejestrParkowan().get(i).getDataOut().getMonth().getValue() == (wybierzDate.getJCalendar().getMonthChooser().getMonth()+1)){
                            if (Wrapper.getRejestrParkowan().get(i).getDataOut().getDayOfMonth() == wybierzDate.getJCalendar().getDayChooser().getDay()){
                                tabelaStat.setValueAt(x+1, x, 0);
                                tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getNrRejString(), x, 1);
                                tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu(), x, 2);
                                tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getDataIn(), x, 3);
                                tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getDataOut(), x, 4);
                                x++;
                            }
                        }
                    }
                }
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

//        buttonStatOk.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                System.out.println(sdf.format(wybierzDate.getJCalendar().getDate().getDay()));
////                System.out.println(sdf.format(Wrapper.getRejestrParkowan().get(24).getDataIn().getDayOfYear()));
//                System.out.println(Wrapper.getRejestrParkowan().get(24).getDataIn().getMonth().getValue());
//                System.out.println(wybierzDate.getDate().getMonth());
//                System.out.println(DateFormat.getDateTimeInstance().format(wybierzDate.getDateEditor().getDate()));
////                System.out.println(wybierzDate.getJCalendar().getDate().equals(Wrapper.getRejestrParkowan().get(24).getDataIn()));
//            }
//        });

        setSize(900,400);
        setVisible(true);
    }

//    private void onOK() {
//        // add your code here
//        dispose();
//    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        wprowadzDateLabel = new JLabel();
        cld = Calendar.getInstance();
        wybierzDate = new JDateChooser(cld.getTime());
        wybierzDate.setPreferredSize(new Dimension(150,20));
        wybierzDate.setDateFormatString("yyyy-MM-dd");
        wprowadzDateLabel.setLabelFor(wybierzDate);
        panelKalendarz = new JPanel();
        panelKalendarz.setSize(200,30);
        panelKalendarz.add(wybierzDate);
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
//        for (int i=0; i<Wrapper.getRejestrParkowan().size(); i++){
//            tabelaStat.setValueAt(i+1, i, 0);
//            tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getNrRejString(), i, 1);
//            tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu(), i, 2);
//            tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getDataIn(), i, 3);
//            tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getDataOut(), i, 4);
//
//        }

    }
}
