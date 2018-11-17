package pl.shopapp.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AdminPanel extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldTelephone;
	private JTextField textFieldEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPanel frame = new AdminPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelAddOperator = new JPanel();
		tabbedPane.addTab("Dodaj operatora", null, panelAddOperator, null);
		panelAddOperator.setLayout(null);
		
		JLabel lblDodawanieOperatora = new JLabel("Dodawanie operatora");
		lblDodawanieOperatora.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDodawanieOperatora.setBounds(10, 11, 145, 14);
		panelAddOperator.add(lblDodawanieOperatora);
		
		textFieldFirstName = new JTextField();
		textFieldFirstName.setBounds(107, 52, 120, 20);
		panelAddOperator.add(textFieldFirstName);
		textFieldFirstName.setColumns(10);
		
		JLabel lblNazwisko = new JLabel("Nazwisko:");
		lblNazwisko.setBounds(10, 86, 66, 14);
		panelAddOperator.add(lblNazwisko);
		
		JLabel lblImie = new JLabel("Imię:");
		lblImie.setBounds(10, 55, 46, 14);
		panelAddOperator.add(lblImie);
		
		textFieldLastName = new JTextField();
		textFieldLastName.setBounds(107, 83, 120, 20);
		panelAddOperator.add(textFieldLastName);
		textFieldLastName.setColumns(10);
		
		textFieldTelephone = new JTextField();
		textFieldTelephone.setBounds(107, 114, 120, 20);
		panelAddOperator.add(textFieldTelephone);
		textFieldTelephone.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(107, 145, 120, 20);
		panelAddOperator.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblTelefon = new JLabel("Telefon:");
		lblTelefon.setBounds(10, 117, 46, 14);
		panelAddOperator.add(lblTelefon);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 148, 46, 14);
		panelAddOperator.add(lblEmail);
		
		JButton btnAddOperator = new JButton("Dodaj");
		btnAddOperator.setBounds(138, 189, 89, 23);
		panelAddOperator.add(btnAddOperator);
		
		JPanel panelAddOtherAdmin = new JPanel();
		tabbedPane.addTab("Dodaj admina", null, panelAddOtherAdmin, null);
		panelAddOtherAdmin.setLayout(null);
		
		JLabel lblDodawanieNowegoAdministrtora = new JLabel("Dodawanie nowego administrtora");
		lblDodawanieNowegoAdministrtora.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDodawanieNowegoAdministrtora.setBounds(10, 11, 201, 14);
		panelAddOtherAdmin.add(lblDodawanieNowegoAdministrtora);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Ustawienia", null, panel, null);
		panel.setLayout(null);
	}

}
