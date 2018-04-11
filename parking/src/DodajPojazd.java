import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class DodajPojazd extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldNrRejestr;
    private JComboBox comboPojazd;
    private JButton odczytButton;
    private JTextField textFieldCena;
    private Wrapper wr;

    public DodajPojazd() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        wr = new Wrapper();


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

        odczytButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    wr.przypiszRejestrPojazdow();
                } catch (IOException exPoj){
                    System.out.println(exPoj);
                }
                for (int i = 0; i < Wrapper.getRejestrPojazdow().size(); i++)
                    System.out.println(i + ". " + Wrapper.getRejestrPojazdow().get(i).getNrRejString() + " " + Wrapper.getRejestrPojazdow().get(i).getRodzajPojazdu());
            }
        });

        comboPojazd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboPojazd.getSelectedIndex() == 0) textFieldCena.setText("1.20");
                if (comboPojazd.getSelectedIndex() == 1) textFieldCena.setText("2.77");
                if (comboPojazd.getSelectedIndex() == 2) textFieldCena.setText("7.80");
            }
        });

        setTitle("Dodawanie pojazdu");
        setSize(400, 200);
        setVisible(true);
    }

    private void onOK() {
        if (!textFieldNrRejestr.getText().isEmpty()){
            try {
                wr.setRejestrPojazdow(textFieldNrRejestr.getText(), comboPojazd.getSelectedIndex(), Double.valueOf(textFieldCena.getText()));
            } catch (IOException ePoj){
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
        comboPojazd.setSelectedIndex(1);
        textFieldCena = new JTextField("2.77");

    }
}
