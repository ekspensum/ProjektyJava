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

    public Okna() {
        this.trzyKola=false;
        JFrame frame = new JFrame("Obsługa parkingu");
        frame.setContentPane(panelGlowny);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800, 500);
        try {
            Wrapper.getRejestrParkowan();
        } catch (IOException park){
            System.out.println(park.toString());
        }
        p = new Pojazd[3];
        p[0] = new Motocykl();
        p[1] = new Osobowy();
        p[2] = new Dostawczy();
        wo = new Wrapper();

        motocyklTrzykolowyCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trzyKola = motocyklTrzykolowyCheckBox.isSelected();
            }
        });
        parkujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboPojazd.getSelectedIndex()==0) p[0] = new Motocykl(trzyKola);
                p[0] = new Motocykl(trzyKola);
                try {
                    Wrapper.getRejestrParkowan();
                } catch (IOException park){
                    System.out.println(park.toString());
                }
                String[] textFildCena = new String[3];
                textFildCena[0] = textFieldCenaMotocykl.getText();
                textFildCena[1] = textFieldCenaOsobowy.getText();
                textFildCena[2] = textFieldCenaDostawczy.getText();

                p[comboPojazd.getSelectedIndex()].setCena(Double.valueOf(textFildCena[comboPojazd.getSelectedIndex()]));
                p[comboPojazd.getSelectedIndex()].setNrRejString(textFieldNrRej.getText());
                p[comboPojazd.getSelectedIndex()].setRodzajPojazdu(comboPojazd.getSelectedIndex());
                p[comboPojazd.getSelectedIndex()].setX(0);
                p[comboPojazd.getSelectedIndex()].setY(0);
                p[comboPojazd.getSelectedIndex()].setDataIn(dataIn.now());
                p[comboPojazd.getSelectedIndex()].setDataOut(null);

                p[comboPojazd.getSelectedIndex()].parkowanie();
            }
        });
        dodajPojazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p[comboPojazd.getSelectedIndex()].setNrRejString(textFieldNrRej.getText());
                p[comboPojazd.getSelectedIndex()].setRodzajPojazdu(comboPojazd.getSelectedIndex());
                wo.setRejestrPojazdow(p[comboPojazd.getSelectedIndex()].nrRejString, p[comboPojazd.getSelectedIndex()].rodzajPojazdu);

                for (int i=0; i<wo.getRejestrPojazdow().size(); i++)
                    System.out.println(i+". "+wo.getRejestrPojazdow().get(i).getNrRejString()+" "+wo.getRejestrPojazdow().get(i).getRodzajPojazdu());
            }
        });
        odczytBazyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    for (int i = 0; i < wo.getRejestrParkowan().size(); i++)
                        System.out.println(i + ". " + Wrapper.getRejestrParkowan().get(i).getNrRejString() + " " + Wrapper.getRejestrParkowan().get(i).getCena() + " " + Wrapper.getRejestrParkowan().get(i).getDataIn()+" "+ Wrapper.getRejestrParkowan().get(i).getDataOut()+" "+Wrapper.getRejestrParkowan().get(i).isTrzyKola());
                } catch (IOException f){
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
        modelTabeli.setRowCount(10);
        parkujButton = new JButton();
        dodajPojazdButton = new JButton();
        comboPojazd = new JComboBox(pojazdy);
        textFieldCenaMotocykl = new JTextField();
        textFieldCenaOsobowy = new JTextField();
        textFieldCenaDostawczy = new JTextField();
        motocyklTrzykolowyCheckBox = new JCheckBox();


    }
}
