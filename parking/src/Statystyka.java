import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.Calendar;

import static java.time.temporal.ChronoUnit.HOURS;

public class Statystyka extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollStatystyka;
    private DefaultTableModel modelTabeliStat;
    private JTable tabelaStat;
    private JPanel panelKalendarz;
    private JLabel wprowadzDateLabel;
    private JTextField textFieldRazem;
    private JDateChooser wybierzDate;
    private Calendar cld;

    public Statystyka() {
        setContentPane(contentPane);
        setModal(true);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = 0;
                Double wartosc, razem = 0.0;
                for (int i = 0; i < Wrapper.getRejestrParkowan().size(); i++) {
                    tabelaStat.setValueAt(null, i, 0);
                    tabelaStat.setValueAt(null, i, 1);
                    tabelaStat.setValueAt(null, i, 2);
                    tabelaStat.setValueAt(null, i, 3);
                    tabelaStat.setValueAt(null, i, 4);
                    tabelaStat.setValueAt(null, i, 5);
                    tabelaStat.setValueAt(null, i, 6);
                    tabelaStat.setValueAt(null, i, 7);
                    tabelaStat.setValueAt(null, i, 8);
                    tabelaStat.setValueAt(null, i, 9);
                    if (Wrapper.getRejestrParkowan().get(i).getDataOut() != null && wybierzDate.getDate() != null) {
                        if (Wrapper.getRejestrParkowan().get(i).getDataOut().getMonth().getValue() == (wybierzDate.getJCalendar().getMonthChooser().getMonth() + 1)) {
                            if (Wrapper.getRejestrParkowan().get(i).getDataOut().getDayOfMonth() == wybierzDate.getJCalendar().getDayChooser().getDay()) {
                                tabelaStat.setValueAt(x + 1, x, 0);
                                tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getNrRejString(), x, 1);
                                tabelaStat.setValueAt(OknoGlowne.pojazdy[Wrapper.getRejestrParkowan().get(i).getRodzajPojazdu()], x, 2);
                                tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getDataIn(), x, 3);
                                tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getDataOut(), x, 4);
                                tabelaStat.setValueAt(godzinParkowania(Wrapper.getRejestrParkowan().get(i).getDataIn(), Wrapper.getRejestrParkowan().get(i).getDataOut()), x, 5);
                                tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getCena(), x, 6);
                                tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getMycie(), x, 7);
                                tabelaStat.setValueAt(Wrapper.getRejestrParkowan().get(i).getChlodnia(), x, 8);
                                wartosc = oplata(godzinParkowania(Wrapper.getRejestrParkowan().get(i).getDataIn(), Wrapper.getRejestrParkowan().get(i).getDataOut()), Wrapper.getRejestrParkowan().get(i).getCena(), Wrapper.getRejestrParkowan().get(i).getMycie(), Wrapper.getRejestrParkowan().get(i).getChlodnia());
                                tabelaStat.setValueAt(wartosc, x, 9);
                                razem += wartosc;
                                x++;
                            }
                        }
                    }
                }
                textFieldRazem.setText(razem.toString());
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 400);
        setTitle("Statystyka");
        setVisible(true);
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        wprowadzDateLabel = new JLabel();
        cld = Calendar.getInstance();
        wybierzDate = new JDateChooser(cld.getTime());
        wybierzDate.setPreferredSize(new Dimension(150, 20));
        wybierzDate.setDateFormatString("yyyy-MM-dd");
        wprowadzDateLabel.setLabelFor(wybierzDate);
        panelKalendarz = new JPanel();
        panelKalendarz.setSize(200, 30);
        panelKalendarz.add(wybierzDate);
        modelTabeliStat = new DefaultTableModel();
        modelTabeliStat.addColumn("L.p.");
        modelTabeliStat.addColumn("Nr rej.");
        modelTabeliStat.addColumn("Rodzaj pojazdu");
        modelTabeliStat.addColumn("Data parkowania");
        modelTabeliStat.addColumn("Data wyjazdu");
        modelTabeliStat.addColumn("Godz. parkowania");
        modelTabeliStat.addColumn("Cena");
        modelTabeliStat.addColumn("Dod. za mycie");
        modelTabeliStat.addColumn("Zasil. chłodni");
        modelTabeliStat.addColumn("Opłata");
        tabelaStat = new JTable(modelTabeliStat);
        tabelaStat.getColumnModel().getColumn(0).setPreferredWidth(5);
        tabelaStat.getColumnModel().getColumn(1).setPreferredWidth(30);
        tabelaStat.getColumnModel().getColumn(2).setPreferredWidth(70);
        tabelaStat.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabelaStat.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabelaStat.getColumnModel().getColumn(5).setPreferredWidth(30);
        tabelaStat.getColumnModel().getColumn(6).setPreferredWidth(30);
        tabelaStat.getColumnModel().getColumn(7).setPreferredWidth(30);
        tabelaStat.getColumnModel().getColumn(8).setPreferredWidth(30);
        tabelaStat.getColumnModel().getColumn(9).setPreferredWidth(50);
        scrollStatystyka = new JScrollPane(tabelaStat);
        modelTabeliStat.setRowCount(Wrapper.getRejestrParkowan().size());

    }

    public long godzinParkowania(LocalDateTime dateIn, LocalDateTime dateOut){
        long godzinPark = dateIn.until(dateOut, HOURS);
        return godzinPark + 1;
    }

    public double oplata(long godzPark, Double cena, Double mycie, Double chlodnia) {
        if (mycie == null) mycie = 0.0;
        if (chlodnia == null) chlodnia = 0.0;
        return mycie + cena * godzPark + chlodnia * godzPark;
    }
}
