import javax.swing.*;

public class PrzegladajOcenyTab extends JPanel {

    private JPanel panelPO;
    private JScrollPane scrollPane;
    private JTable tableOceny;
    private JComboBox comboBox1;
    private JComboBox comboBox2;


    private void createUIComponents() {

        panelPO = new JPanel();
        add(panelPO);
        tableOceny = new JTable();
        scrollPane = new JScrollPane(tableOceny);

    }
}
