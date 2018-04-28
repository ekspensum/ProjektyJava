import javax.swing.*;

public class WystawOceneTab extends JPanel {
    JPanel panelWO;
    Double [] oceny;
    JComboBox comboBoxOceny;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;

    private void createUIComponents() {
        panelWO = new JPanel();
        add(panelWO);
        oceny = new Double[] {3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0};
        comboBoxOceny = new JComboBox(oceny);
    }
}
