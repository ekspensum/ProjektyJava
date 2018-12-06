package pl.shopapp.client;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBeanRemote;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.User;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Color;

public class AdminPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldTelephone;
	private JTextField textFieldEmail;
	private JTextField textFieldPassword;
	private JTextField textFieldLogin;
	private DefaultTableModel tableModelOperator;
	private JTable tableOperator;

	/**
	 * Create the frame.
	 */

	public AdminPanel(UserBeanRemote ubr, SessionData sd) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 886, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panelAddOperator = new JPanel();
		tabbedPane.addTab("Dodaj operatora", null, panelAddOperator, null);
		panelAddOperator.setLayout(null);

		JLabel lblDodawanieOperatora = new JLabel("Dodawanie operatora:");
		lblDodawanieOperatora.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDodawanieOperatora.setBounds(10, 11, 145, 14);
		panelAddOperator.add(lblDodawanieOperatora);

		textFieldFirstName = new JTextField();
		textFieldFirstName.setBounds(117, 98, 120, 20);
		panelAddOperator.add(textFieldFirstName);
		textFieldFirstName.setColumns(10);

		JLabel lblNazwisko = new JLabel("Nazwisko:");
		lblNazwisko.setBounds(20, 132, 66, 14);
		panelAddOperator.add(lblNazwisko);

		JLabel lblImie = new JLabel("Imię:");
		lblImie.setBounds(20, 101, 46, 14);
		panelAddOperator.add(lblImie);

		textFieldLastName = new JTextField();
		textFieldLastName.setBounds(117, 129, 120, 20);
		panelAddOperator.add(textFieldLastName);
		textFieldLastName.setColumns(10);

		textFieldTelephone = new JTextField();
		textFieldTelephone.setBounds(117, 160, 120, 20);
		panelAddOperator.add(textFieldTelephone);
		textFieldTelephone.setColumns(10);

		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(117, 191, 120, 20);
		panelAddOperator.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		JLabel lblTelefon = new JLabel("Telefon:");
		lblTelefon.setBounds(20, 163, 46, 14);
		panelAddOperator.add(lblTelefon);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(20, 194, 46, 14);
		panelAddOperator.add(lblEmail);

		JLabel lblMsg = new JLabel("");
		lblMsg.setBackground(Color.GRAY);
		lblMsg.setVerticalAlignment(SwingConstants.TOP);
		lblMsg.setBounds(10, 275, 227, 55);
		panelAddOperator.add(lblMsg);

		JButton btnAddOperator = new JButton("Dodaj");
		btnAddOperator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validOK = true;
				Validation valid = new Validation();
				if (textFieldLogin.getText() == "" || !valid.loginValidation(textFieldLogin.getText())) {
					validOK = false;
					lblMsg.setText("<HTML>Pole login jest puste lub zawiera niepoprawne znaki!1</HTML>");
				}
				if (textFieldPassword.getText() == "" || !valid.passwordValidation(textFieldPassword.getText())) {
					validOK = false;
					lblMsg.setText("<HTML>Pole hasło jest puste lub zawiera niepoprawne znaki!2</HTML>");
				}
				if (textFieldFirstName.getText() == "" || !valid.nameValidation(textFieldFirstName.getText())) {
					validOK = false;
					lblMsg.setText("<HTML>Pole imię jest puste lub zawiera niepoprawne znaki!3</HTML>");
				}
				if (textFieldLastName.getText() == "" || !valid.nameValidation(textFieldLastName.getText())) {
					validOK = false;
					lblMsg.setText("<HTML>Pole nazwisko jest puste lub zawiera niepoprawne znaki!4</HTML>");
				}
				if (textFieldTelephone.getText() == "" || !valid.telephoneNoValidation(textFieldTelephone.getText())) {
					validOK = false;
					lblMsg.setText("<HTML>Pole nazwisko jest puste lub zawiera niepoprawne znaki!5</HTML>");
				}

				if (textFieldEmail.getText() == "" || !valid.emailValidation(textFieldEmail.getText())) {
					validOK = false;
					lblMsg.setText("<HTML>Pole nazwisko jest puste lub zawiera niepoprawne znaki!6</HTML>");
				}

				if (validOK) {
					User u = new User();
					u.setLogin(textFieldLogin.getText());
					u.setPassword(valid.passwordToCode(textFieldPassword.getText()));
					u.setActive(true);

					Operator op = new Operator();
					op.setFirstName(textFieldFirstName.getText());
					op.setLastName(textFieldLastName.getText());
					op.setPhoneNo(textFieldTelephone.getText());
					op.setEmail(textFieldEmail.getText());
					op.setUser(u);

					ubr.addOperator(op, u, sd.getIdUser());
					lblMsg.setText("<HTML>Dodano nowego operatora!</HTML>");
					textFieldLogin.setText("");
					textFieldPassword.setText("");
					textFieldFirstName.setText("");
					textFieldLastName.setText("");
					textFieldTelephone.setText("");
					textFieldEmail.setText("");
				}
			}
		});
		btnAddOperator.setBounds(148, 222, 89, 23);
		panelAddOperator.add(btnAddOperator);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(20, 40, 46, 14);
		panelAddOperator.add(lblLogin);

		JLabel lblHaso = new JLabel("Hasło:");
		lblHaso.setBounds(20, 71, 46, 14);
		panelAddOperator.add(lblHaso);

		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(117, 68, 120, 20);
		panelAddOperator.add(textFieldPassword);
		textFieldPassword.setColumns(10);

		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(117, 37, 120, 20);
		panelAddOperator.add(textFieldLogin);
		textFieldLogin.setColumns(10);

		JLabel lblEdycjaDanychOperatora = new JLabel("Edycja danych operatora:");
		lblEdycjaDanychOperatora.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEdycjaDanychOperatora.setBounds(272, 11, 191, 14);
		panelAddOperator.add(lblEdycjaDanychOperatora);

		JScrollPane scrollPaneOperator = new JScrollPane();
		scrollPaneOperator.setBounds(272, 37, 573, 307);
		panelAddOperator.add(scrollPaneOperator);

		tableModelOperator = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return Boolean.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				case 5:
					return String.class;
				case 6:
					return Boolean.class;

				default:
					return String.class;
				}
			}
		};
		tableModelOperator.addColumn("Login");
		tableModelOperator.addColumn("Aktywny");

		tableModelOperator.addColumn("Imię");
		tableModelOperator.addColumn("Nazwisko");
		tableModelOperator.addColumn("Telefon");
		tableModelOperator.addColumn("Email");
		tableModelOperator.addColumn("Edytuj");
		tableModelOperator.setRowCount(ubr.getOperatorsData().size());
		tableOperator = new JTable(tableModelOperator);
		scrollPaneOperator.setViewportView(tableOperator);
		for (int i = 0; i < ubr.getOperatorsData().size(); i++) {
			tableOperator.setValueAt(ubr.getUsersData().get(i).getLogin(), i, 0);
			tableOperator.setValueAt(ubr.getUsersData().get(i).getActive(), i, 1);
			tableOperator.setValueAt(ubr.getOperatorsData().get(i).getFirstName(), i, 2);
			tableOperator.setValueAt(ubr.getOperatorsData().get(i).getLastName(), i, 3);
			tableOperator.setValueAt(ubr.getOperatorsData().get(i).getPhoneNo(), i, 4);
			tableOperator.setValueAt(ubr.getOperatorsData().get(i).getEmail(), i, 5);
		}

		JButton btnZapiszZmiany = new JButton("Zapisz zmiany");
		btnZapiszZmiany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMsg.setText("");
				int checked = 0;
				String login = null, firtName = null, lastName = null, phoneNo = null, email = null;
				boolean active = false;
				int idOperator = 0;
				for (int i = 0; i < tableOperator.getRowCount(); i++) {
					if (tableOperator.getValueAt(i, 6) != null)
						if ((boolean) tableOperator.getValueAt(i, 6) == true) {
							idOperator = ubr.getOperatorsData().get(i).getId();
							login = (String) tableOperator.getValueAt(i, 0);
							active = (boolean) tableOperator.getValueAt(i, 1);
							firtName =  (String) tableOperator.getValueAt(i, 2);
							lastName =  (String) tableOperator.getValueAt(i, 3);
							phoneNo =  (String) tableOperator.getValueAt(i, 4);
							email =  (String) tableOperator.getValueAt(i, 5);
							checked += 1;
						}
				}
				if (checked == 1) {
					ubr.updateOperatorData(idOperator, login, active, firtName, lastName, phoneNo, email);
				} else
					lblMsg.setText("<HTML>Proszę zaznaczyć jeden wiersz do edycji!</HTML>");
			}
		});
		btnZapiszZmiany.setBounds(725, 355, 120, 23);
		panelAddOperator.add(btnZapiszZmiany);

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

		JLabel lblZalogowanyJako = new JLabel("Zalogowany jako: ");
		contentPane.add(lblZalogowanyJako, BorderLayout.NORTH);
		lblZalogowanyJako
				.setText("Zalogowany jako: " + sd.getFirstName() + " " + sd.getLastName() + " " + sd.getRoleName());

	}

}
