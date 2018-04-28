import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.LEFT;

public class OknoStartowe extends JFrame {
    private JPanel panelGlowny;
    private JTabbedPane tabbedPane;

    public OknoStartowe() {
        panelGlowny = new JPanel();
        setContentPane(panelGlowny);
        tabbedPane = new JTabbedPane();
        panelGlowny.add(tabbedPane);
        tabbedPane.addTab("Logowanie", new LogowanieTab());
        tabbedPane.addTab("Przeglądaj oceny", new PrzegladajOcenyTab());
        tabbedPane.addTab("Wystaw ocenę", new WystawOceneTab());
        tabbedPane.addTab("Administracja", new AdministracjaTab());

        tabbedPane.setTabPlacement(LEFT);
//        tabbedPane

//        tabbedPane.setEnabledAt(1, false);
//        tabbedPane.setEnabledAt(2, false);
//        tabbedPane.setEnabledAt(3, false);

        tabbedPane.setPreferredSize(new Dimension(880,555));
        setDefaultCloseOperation(3);
        setTitle("Dziennik nauczycielski");
        setSize(tabbedPane.getPreferredSize().width+20,tabbedPane.getPreferredSize().height+45);
        setVisible(true);
    }

    public static void main(String[] args) {
        new OknoStartowe();
    }

}
