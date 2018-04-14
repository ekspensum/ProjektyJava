import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class DodajPojazd extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldNrRejestr;
    private JComboBox comboPojazd;
    private JTextField textFieldCena;
    private Wrapper wr;
    static String nrRej;
    static int indexPojazdu = 1;

    public DodajPojazd() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        wr = new Wrapper();
        textFieldNrRejestr.setText(nrRej);
        comboPojazd.setSelectedIndex(indexPojazdu);

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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        comboPojazd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboPojazd.getSelectedIndex() == 0) textFieldCena.setText(OknoGlowne.cennik.get(0));
                if (comboPojazd.getSelectedIndex() == 1) textFieldCena.setText(OknoGlowne.cennik.get(1));
                if (comboPojazd.getSelectedIndex() == 2) textFieldCena.setText(OknoGlowne.cennik.get(2));
            }
        });

        setTitle("Dodawanie pojazdu");
        setSize(400, 200);
        setVisible(true);
    }

    private void onOK() {
        if (!textFieldNrRejestr.getText().isEmpty()) {
            try {
                wr.setRejestrPojazdow(textFieldNrRejestr.getText(), comboPojazd.getSelectedIndex(), Double.valueOf(textFieldCena.getText()));
            } catch (IOException ePoj) {
                System.out.println(ePoj);
            }
            dispose();
        } else JOptionPane.showMessageDialog(null, "Proszę wprowadzić nr rejestracyjny pojazdu.");
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        comboPojazd = new JComboBox(OknoGlowne.pojazdy);
        comboPojazd.setSelectedIndex(indexPojazdu);
        textFieldCena = new JTextField(OknoGlowne.cennik.get(indexPojazdu));
        textFieldNrRejestr = new JTextField();
    }
}
