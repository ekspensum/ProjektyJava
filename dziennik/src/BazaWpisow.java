import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BazaWpisow extends JFrame {

    private ArrayList<Nauczyciel> nauczyciele = new ArrayList<>();
    private ArrayList<Klasa<Uczniowie, Przedmioty>> klasy = new ArrayList<>();
    private ArrayList<Dziennik> wpisyDoDziennika = new ArrayList<>();
    private Double [] oceny;
    private Dziennik dziennik;
    private JPanel panelGlowny;
    private JTabbedPane tabbedPane;
    private JPanel wystawOcene;
    private JPanel zmienDane;
    private JPanel przegladajOceny;
    private JScrollPane scrollPanePrzegladOcen;
    private JComboBox comboBoxOceny;

    public BazaWpisow() {
        setContentPane(panelGlowny);
        setDefaultCloseOperation(3);
        setTitle("Dziennik nauczycielski");
        setSize(900,600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BazaWpisow();
    }

    private void createUIComponents(){
        panelGlowny = new JPanel();
        tabbedPane = new JTabbedPane();
        wystawOcene = new JPanel();
        zmienDane = new JPanel();
        przegladajOceny = new JPanel();
        tabbedPane.add(wystawOcene);
        tabbedPane.add(zmienDane);
        tabbedPane.add(przegladajOceny);

        scrollPanePrzegladOcen = new JScrollPane();
        oceny = new Double[] {3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0};
        comboBoxOceny = new JComboBox(oceny);
        tabbedPane.setSelectedComponent(przegladajOceny);
        System.out.println(tabbedPane.getSelectedIndex());
    }

    public void setWpisyDoDziennika(){
        dziennik = new Dziennik();
        dziennik.setDataWpisu(LocalDateTime.now());
//        dziennik.setDataOceny();
//        dziennik.setIdUcznia();
//        dziennik.setImieUcznia();
//        dziennik.setNazwiskoUcznia();




        wpisyDoDziennika.add(dziennik);
    }


}
