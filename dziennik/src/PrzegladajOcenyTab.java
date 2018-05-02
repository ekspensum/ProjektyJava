import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;



public class PrzegladajOcenyTab extends JPanel {

    private JPanel panelPO;
    private JScrollPane scrollPane;
    private JTable tableOceny;
    private DefaultTableModel modelTabeliOceny;
    private JPanel dataOd;
    private JPanel dataDo;
    private JCalendar kalendarz;
    private JDateChooser chooserDataOd;
    private JDateChooser chooserDataDo;
    private JButton buttonWyswietl;
    private JTextField textFieldZalogowany;
    private BazaWpisow bw;


    public PrzegladajOcenyTab() {
        bw = new BazaWpisow();
        buttonWyswietl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer x = 0;
                for (int i = 0; i < bw.getWpisyDoDziennika().size(); i++) {
                    if (bw.getWpisyDoDziennika().get(i).getDataOceny().after(chooserDataOd.getDate()) && bw.getWpisyDoDziennika().get(i).getDataOceny().before(chooserDataDo.getDate())) {
                        if (LogowanieTab.idUczen.equals(bw.getWpisyDoDziennika().get(i).getIdUcznia())) {
                            x++;
                        }
                    }
                }
                modelTabeliOceny.setRowCount(0);
                if (x > 0) {
                    Integer y = 0;
                    modelTabeliOceny.setRowCount(x);
                    for (int i = 0; i < bw.getWpisyDoDziennika().size(); i++) {
                        if (bw.getWpisyDoDziennika().get(i).getDataOceny().after(chooserDataOd.getDate()) && bw.getWpisyDoDziennika().get(i).getDataOceny().before(chooserDataDo.getDate())) {
                            if (LogowanieTab.idUczen.equals(bw.getWpisyDoDziennika().get(i).getIdUcznia())) {
                                tableOceny.setValueAt(bw.getWpisyDoDziennika().get(i).getNazwaPrzedmiotu(), y, 0);
                                tableOceny.setValueAt(bw.getWpisyDoDziennika().get(i).getImieNauczyciela() + " " + bw.getWpisyDoDziennika().get(i).getNazwiskoNauczyciela(), y, 1);
                                tableOceny.setValueAt(bw.getWpisyDoDziennika().get(i).getOcena(), y, 2);
                                tableOceny.setValueAt(bw.getWpisyDoDziennika().get(i).getDataOceny(), y, 3);
                                tableOceny.setValueAt(bw.getWpisyDoDziennika().get(i).getKomentarzaNauczyciela(), y, 4);
                                y++;
                            }
                        }
                    }
                }
            }
        });

    }

    private void createUIComponents() {
        panelPO = new JPanel();
        add(panelPO);
        scrollPane = new JScrollPane(tableOceny);
        modelTabeliOceny = new DefaultTableModel();
        modelTabeliOceny.addColumn("Przedmiot");
        modelTabeliOceny.addColumn("Nauczyciel");
        modelTabeliOceny.addColumn("Ocena");
        modelTabeliOceny.addColumn("Data wystawienia");
        modelTabeliOceny.addColumn("Komentarz nauczyciela");
        tableOceny = new JTable(modelTabeliOceny);
        kalendarz = new JCalendar();
        chooserDataOd = new JDateChooser(kalendarz.getDate());
        chooserDataDo = new JDateChooser(kalendarz.getDate());
        dataOd = new JPanel();
        chooserDataOd.setPreferredSize(new Dimension(100, 26));
        dataOd.add(chooserDataOd);
        dataDo = new JPanel();
        chooserDataDo.setPreferredSize(new Dimension(100, 26));
        dataDo.add(chooserDataDo);

    }

    public void setTextFieldZalogowany(String imieNazwiskoUcznia) {
        textFieldZalogowany.setText(imieNazwiskoUcznia);
    }
}
