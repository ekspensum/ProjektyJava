import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.SwingConstants.LEFT;

public class OknoStartowe extends JFrame {
    private JPanel panelGlowny;
    private static JTabbedPane tabbedPane;
    private PrzegladajOcenyTab przegladajOcenyTab;
    private WystawOceneTab wystawOceneTab;
    private Uczniowie uczniowie;
    private Nauczyciele nauczyciele;

    public OknoStartowe() {
        panelGlowny = new JPanel();
        setContentPane(panelGlowny);
        tabbedPane = new JTabbedPane();
        panelGlowny.add(tabbedPane);
        przegladajOcenyTab = new PrzegladajOcenyTab();
        wystawOceneTab = new WystawOceneTab();
        tabbedPane.addTab("Logowanie", new LogowanieTab());
        tabbedPane.addTab("Przeglądaj oceny", przegladajOcenyTab);
        tabbedPane.addTab("Wystaw ocenę", wystawOceneTab);
        tabbedPane.addTab("Administracja", new AdministracjaTab());

        tabbedPane.setTabPlacement(LEFT);
        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(2, false);
        tabbedPane.setEnabledAt(3, false);
        tabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (LogowanieTab.idUczen != null) {
                    uczniowie = new Uczniowie();
                    for (int i = 0; i < uczniowie.getUczniowie().size(); i++) {
                        if (LogowanieTab.idUczen.equals(uczniowie.getUczniowie().get(i).getIdUcznia()))
                            przegladajOcenyTab.setTextFieldZalogowany(uczniowie.getUczniowie().get(i).getImieUcznia() + " " + uczniowie.getUczniowie().get(i).getNazwiskoUcznia());
                    }
                }
                if (LogowanieTab.idNauczyciel != null){
                    nauczyciele = new Nauczyciele();
                    for (int i=0; i<nauczyciele.getNauczyciele().size(); i++)
                        if (LogowanieTab.idNauczyciel.equals(nauczyciele.getNauczyciele().get(i).getIdNauczyciela()))
                            wystawOceneTab.setTextFieldZalogowany(nauczyciele.getNauczyciele().get(i).getImieNauczyciela()+" "+nauczyciele.getNauczyciele().get(i).getNazwiskoNauczyciela());
                }
            }
        });

        tabbedPane.setPreferredSize(new Dimension(880, 555));
        setDefaultCloseOperation(3);
        setTitle("Dziennik nauczycielski");
        setSize(tabbedPane.getPreferredSize().width + 20, tabbedPane.getPreferredSize().height + 45);
        setVisible(true);
    }

    public static void main(String[] args) {
        new OknoStartowe();
    }

    public static void setTabbedPane(Integer tab, Boolean edit) {
        tabbedPane.setEnabledAt(tab, edit);
    }
}
