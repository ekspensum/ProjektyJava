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
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldFirstNameOperator;
	private JTextField textFieldLastNameOperator;
	private JTextField textFieldTelephoneOperator;
	private JTextField textFieldEmailOperator;
	private JTextField textFieldPasswordOperator;
	private JTextField textFieldLoginOperator;
	private DefaultTableModel tableModelOperator;
	private DefaultTableModel tableModelAdmin;
	private DefaultTableModel tableModelRole;
	private DefaultTableModel tableModelCustomer;
	private JTable tableOperator;
	private JTable tableAdmin;
	private JTable roleTable;
	private JTable tableCustomer;
	private JTextField textFieldLoginAdmin;
	private JTextField textFieldPasswordAdmin;
	private JTextField textFieldFirstNameAdmin;
	private JTextField textFieldLastNameAdmin;
	private JTextField textFieldTelephoneAdmin;
	private JTextField textFieldEmailAdmin;
	private JTextField textFieldMinCharInPass;
	private JTextField textFieldUpperCaseCharInPass;
	private JTextField textFieldMaxCharInPass;
	private JTextField textFieldSessionTime;
	private JTextField textFieldNumbersInPass;
	private JTextField textFieldMinCharInLogin;
	private JTextField textFieldMaxCharInLogin;
	private JTextField textFieldNewRole;
	private JTextField textFieldSerchByLastName;
	private JTextField textFieldSearchByPesel;
	private int sizeCustomerList;

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
		tabbedPane.addTab("Dodaj / Edytuj operatora", null, panelAddOperator, null);
		panelAddOperator.setLayout(null);

		JLabel lblDodawanieOperatora = new JLabel("Dodawanie operatora:");
		lblDodawanieOperatora.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDodawanieOperatora.setBounds(10, 11, 145, 14);
		panelAddOperator.add(lblDodawanieOperatora);

		textFieldFirstNameOperator = new JTextField();
		textFieldFirstNameOperator.setBounds(117, 98, 120, 20);
		panelAddOperator.add(textFieldFirstNameOperator);
		textFieldFirstNameOperator.setColumns(10);

		JLabel lblNazwisko = new JLabel("Nazwisko:");
		lblNazwisko.setBounds(20, 132, 66, 14);
		panelAddOperator.add(lblNazwisko);

		JLabel lblImie = new JLabel("Imię:");
		lblImie.setBounds(20, 101, 46, 14);
		panelAddOperator.add(lblImie);

		textFieldLastNameOperator = new JTextField();
		textFieldLastNameOperator.setBounds(117, 129, 120, 20);
		panelAddOperator.add(textFieldLastNameOperator);
		textFieldLastNameOperator.setColumns(10);

		textFieldTelephoneOperator = new JTextField();
		textFieldTelephoneOperator.setBounds(117, 160, 120, 20);
		panelAddOperator.add(textFieldTelephoneOperator);
		textFieldTelephoneOperator.setColumns(10);

		textFieldEmailOperator = new JTextField();
		textFieldEmailOperator.setBounds(117, 191, 120, 20);
		panelAddOperator.add(textFieldEmailOperator);
		textFieldEmailOperator.setColumns(10);

		JLabel lblTelefon = new JLabel("Telefon:");
		lblTelefon.setBounds(20, 163, 46, 14);
		panelAddOperator.add(lblTelefon);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(20, 194, 46, 14);
		panelAddOperator.add(lblEmail);

		JLabel lblMsgOperator = new JLabel("");
		lblMsgOperator.setBackground(Color.GRAY);
		lblMsgOperator.setVerticalAlignment(SwingConstants.TOP);
		lblMsgOperator.setBounds(10, 275, 227, 55);
		panelAddOperator.add(lblMsgOperator);

		JButton btnAddOperator = new JButton("Dodaj");
		btnAddOperator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validOK = true;
				Validation valid = new Validation(ubr.getSettingsApp());
				if (textFieldLoginOperator.getText() == ""
						|| !valid.loginValidation(textFieldLoginOperator.getText())) {
					validOK = false;
					lblMsgOperator.setText("<HTML>Pole login jest puste lub zawiera niepoprawne znaki!1</HTML>");
				}
				if (ubr.findUserLogin(textFieldLoginOperator.getText())) {
					validOK = false;
					lblMsgOperator.setText("<HTML>Login o nazwie: " + textFieldLoginOperator.getText()
							+ " jest już w użyciu. Proszę podać inny login!</HTML>");
				}
				if (textFieldPasswordOperator.getText() == ""
						|| !valid.passwordValidation(textFieldPasswordOperator.getText())) {
					validOK = false;
					lblMsgOperator.setText("<HTML>Pole hasło jest puste lub zawiera niepoprawne znaki!2</HTML>");
				}
				if (textFieldFirstNameOperator.getText() == ""
						|| !valid.nameValidation(textFieldFirstNameOperator.getText())) {
					validOK = false;
					lblMsgOperator.setText("<HTML>Pole imię jest puste lub zawiera niepoprawne znaki!3</HTML>");
				}
				if (textFieldLastNameOperator.getText() == ""
						|| !valid.nameValidation(textFieldLastNameOperator.getText())) {
					validOK = false;
					lblMsgOperator.setText("<HTML>Pole nazwisko jest puste lub zawiera niepoprawne znaki!4</HTML>");
				}
				if (textFieldTelephoneOperator.getText() == ""
						|| !valid.telephoneNoValidation(textFieldTelephoneOperator.getText())) {
					validOK = false;
					lblMsgOperator.setText("<HTML>Pole telefon jest puste lub zawiera niepoprawne znaki!5</HTML>");
				}
				if (textFieldEmailOperator.getText() == ""
						|| !valid.emailValidation(textFieldEmailOperator.getText())) {
					validOK = false;
					lblMsgOperator.setText("<HTML>Pole email jest puste lub zawiera niepoprawne znaki!6</HTML>");
				}

				if (validOK) {
					if(ubr.addOperator(textFieldFirstNameOperator.getText(), textFieldLastNameOperator.getText(), textFieldTelephoneOperator.getText(), textFieldEmailOperator.getText(), textFieldLoginOperator.getText(), valid.stringToCode(textFieldPasswordOperator.getText()), sd.getIdUser())) {
						lblMsgOperator.setText("<HTML>Dodano nowego operatora!</HTML>");
						textFieldLoginOperator.setText("");
						textFieldPasswordOperator.setText("");
						textFieldFirstNameOperator.setText("");
						textFieldLastNameOperator.setText("");
						textFieldTelephoneOperator.setText("");
						textFieldEmailOperator.setText("");
						loadTableOperatorData(ubr);
					} else
						lblMsgOperator.setText("<HTML>Nie udało się dodać nowego operatora!</HTML>");

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

		textFieldPasswordOperator = new JTextField();
		textFieldPasswordOperator.setBounds(117, 68, 120, 20);
		panelAddOperator.add(textFieldPasswordOperator);
		textFieldPasswordOperator.setColumns(10);

		textFieldLoginOperator = new JTextField();
		textFieldLoginOperator.setBounds(117, 37, 120, 20);
		panelAddOperator.add(textFieldLoginOperator);
		textFieldLoginOperator.setColumns(10);

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
		scrollPaneOperator.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { tableOperator }));
		loadTableOperatorData(ubr);

		JButton btnZapiszZmianyOperator = new JButton("Zapisz zmiany");
		btnZapiszZmianyOperator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMsgOperator.setText("");
				int checked = 0;
				String login = null, firstName = null, lastName = null, phoneNo = null, email = null;
				boolean active = false;
				int idOperator = 0, idUser = 0;
				for (int i = 0; i < tableOperator.getRowCount(); i++) {
					if (tableOperator.getValueAt(i, 6) != null)
						if ((boolean) tableOperator.getValueAt(i, 6) == true) {
							idOperator = ubr.getOperatorsData().get(i).getId();
							idUser = ubr.getUsersOperatorData().get(i).getId();
							login = (String) tableOperator.getValueAt(i, 0);
							active = (boolean) tableOperator.getValueAt(i, 1);
							firstName = (String) tableOperator.getValueAt(i, 2);
							lastName = (String) tableOperator.getValueAt(i, 3);
							phoneNo = (String) tableOperator.getValueAt(i, 4);
							email = (String) tableOperator.getValueAt(i, 5);
							checked += 1;
						}
				}
				if (checked == 1) {
					if (ubr.updateOperatorData(idUser, idOperator, login, active, firstName, lastName, phoneNo,
							email)) {
						lblMsgOperator.setText("<HTML>Dokonano edycji usera o loginie: " + login + "!</HTML>");
						loadTableOperatorData(ubr);
					} else {
						lblMsgOperator
								.setText("<HTML>Nie udało sie dokonać edeycji usera o loginie: " + login + "!</HTML>");
						loadTableOperatorData(ubr);
					}
				} else
					lblMsgOperator.setText("<HTML>Proszę zaznaczyć jeden wiersz do edycji!</HTML>");
			}
		});

		btnZapiszZmianyOperator.setBounds(725, 355, 120, 23);
		panelAddOperator.add(btnZapiszZmianyOperator);
		panelAddOperator.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { textFieldLoginOperator,
				textFieldPasswordOperator, textFieldFirstNameOperator, textFieldLastNameOperator,
				textFieldTelephoneOperator, textFieldEmailOperator, btnAddOperator, scrollPaneOperator }));

		JPanel panelAddOtherAdmin = new JPanel();
		tabbedPane.addTab("Dodaj / Edytuj admina", null, panelAddOtherAdmin, null);
		panelAddOtherAdmin.setLayout(null);

		JLabel lblDodawanieNowegoAdministratora = new JLabel("Dodawanie nowego administratora:");
		lblDodawanieNowegoAdministratora.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDodawanieNowegoAdministratora.setBounds(10, 11, 227, 14);
		panelAddOtherAdmin.add(lblDodawanieNowegoAdministratora);

		JLabel lblEdycjaDanychAdministrtora = new JLabel("Edycja danych administrtora:");
		lblEdycjaDanychAdministrtora.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEdycjaDanychAdministrtora.setBounds(272, 11, 191, 14);
		panelAddOtherAdmin.add(lblEdycjaDanychAdministrtora);

		textFieldLoginAdmin = new JTextField();
		textFieldLoginAdmin.setColumns(10);
		textFieldLoginAdmin.setBounds(117, 37, 120, 20);
		panelAddOtherAdmin.add(textFieldLoginAdmin);

		JLabel label_2 = new JLabel("Login:");
		label_2.setBounds(20, 40, 46, 14);
		panelAddOtherAdmin.add(label_2);

		JLabel label_3 = new JLabel("Hasło:");
		label_3.setBounds(20, 71, 46, 14);
		panelAddOtherAdmin.add(label_3);

		textFieldPasswordAdmin = new JTextField();
		textFieldPasswordAdmin.setColumns(10);
		textFieldPasswordAdmin.setBounds(117, 68, 120, 20);
		panelAddOtherAdmin.add(textFieldPasswordAdmin);

		textFieldFirstNameAdmin = new JTextField();
		textFieldFirstNameAdmin.setColumns(10);
		textFieldFirstNameAdmin.setBounds(117, 98, 120, 20);
		panelAddOtherAdmin.add(textFieldFirstNameAdmin);

		JLabel label_4 = new JLabel("Imię:");
		label_4.setBounds(20, 101, 46, 14);
		panelAddOtherAdmin.add(label_4);

		JLabel label_5 = new JLabel("Nazwisko:");
		label_5.setBounds(20, 132, 66, 14);
		panelAddOtherAdmin.add(label_5);

		textFieldLastNameAdmin = new JTextField();
		textFieldLastNameAdmin.setColumns(10);
		textFieldLastNameAdmin.setBounds(117, 129, 120, 20);
		panelAddOtherAdmin.add(textFieldLastNameAdmin);

		textFieldTelephoneAdmin = new JTextField();
		textFieldTelephoneAdmin.setColumns(10);
		textFieldTelephoneAdmin.setBounds(117, 160, 120, 20);
		panelAddOtherAdmin.add(textFieldTelephoneAdmin);

		JLabel label_6 = new JLabel("Telefon:");
		label_6.setBounds(20, 163, 46, 14);
		panelAddOtherAdmin.add(label_6);

		JLabel label_7 = new JLabel("Email:");
		label_7.setBounds(20, 194, 46, 14);
		panelAddOtherAdmin.add(label_7);

		textFieldEmailAdmin = new JTextField();
		textFieldEmailAdmin.setColumns(10);
		textFieldEmailAdmin.setBounds(117, 191, 120, 20);
		panelAddOtherAdmin.add(textFieldEmailAdmin);

		JLabel lblMsgAdmin = new JLabel("");
		lblMsgAdmin.setVerticalAlignment(SwingConstants.TOP);
		lblMsgAdmin.setBackground(Color.GRAY);
		lblMsgAdmin.setBounds(10, 277, 227, 55);
		panelAddOtherAdmin.add(lblMsgAdmin);

		JScrollPane scrollPaneAdmin = new JScrollPane();
		scrollPaneAdmin.setBounds(272, 37, 573, 307);
		panelAddOtherAdmin.add(scrollPaneAdmin);

		tableModelAdmin = new DefaultTableModel() {
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
		tableModelAdmin.addColumn("Login");
		tableModelAdmin.addColumn("Aktywny");

		tableModelAdmin.addColumn("Imię");
		tableModelAdmin.addColumn("Nazwisko");
		tableModelAdmin.addColumn("Telefon");
		tableModelAdmin.addColumn("Email");
		tableModelAdmin.addColumn("Edytuj");
		tableModelAdmin.setRowCount(ubr.getAdminsData().size());
		tableAdmin = new JTable(tableModelAdmin);
		scrollPaneAdmin.setViewportView(tableAdmin);
		scrollPaneAdmin.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { tableAdmin }));
		loadTableAdminData(ubr);
		
		JButton btnAddAdmin = new JButton("Dodaj");
		btnAddAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validOK = true;
				Validation valid = new Validation(ubr.getSettingsApp());
				if (textFieldLoginAdmin.getText() == "" || !valid.loginValidation(textFieldLoginAdmin.getText())) {
					validOK = false;
					lblMsgAdmin.setText("<HTML>Pole login jest puste lub zawiera niepoprawne znaki!1</HTML>");
				}
				if (ubr.findUserLogin(textFieldLoginAdmin.getText())) {
					validOK = false;
					lblMsgAdmin.setText("<HTML>Login o nazwie: " + textFieldLoginAdmin.getText()
							+ " jest już w użyciu. Proszę podać inny login!</HTML>");
				}
				if (textFieldPasswordAdmin.getText() == ""
						|| !valid.passwordValidation(textFieldPasswordAdmin.getText())) {
					validOK = false;
					lblMsgAdmin.setText("<HTML>Pole hasło jest puste lub zawiera niepoprawne znaki!2</HTML>");
				}
				if (textFieldFirstNameAdmin.getText() == ""
						|| !valid.nameValidation(textFieldFirstNameAdmin.getText())) {
					validOK = false;
					lblMsgAdmin.setText("<HTML>Pole imię jest puste lub zawiera niepoprawne znaki!3</HTML>");
				}
				if (textFieldLastNameAdmin.getText() == "" || !valid.nameValidation(textFieldLastNameAdmin.getText())) {
					validOK = false;
					lblMsgAdmin.setText("<HTML>Pole nazwisko jest puste lub zawiera niepoprawne znaki!4</HTML>");
				}
				if (textFieldTelephoneAdmin.getText() == ""
						|| !valid.telephoneNoValidation(textFieldTelephoneAdmin.getText())) {
					validOK = false;
					lblMsgAdmin.setText("<HTML>Pole telefon jest puste lub zawiera niepoprawne znaki!5</HTML>");
				}
				if (textFieldEmailAdmin.getText() == "" || !valid.emailValidation(textFieldEmailAdmin.getText())) {
					validOK = false;
					lblMsgAdmin.setText("<HTML>Pole email jest puste lub zawiera niepoprawne znaki!6</HTML>");
				}

				if (validOK) {
					if(ubr.addAdmin(textFieldFirstNameAdmin.getText(), textFieldLastNameAdmin.getText(), textFieldTelephoneAdmin.getText(), textFieldEmailAdmin.getText(), textFieldLoginAdmin.getText(), valid.stringToCode(textFieldPasswordAdmin.getText()), sd.getIdUser())) {
						lblMsgAdmin.setText("<HTML>Dodano nowego administratora!</HTML>");
						textFieldLoginAdmin.setText("");
						textFieldPasswordAdmin.setText("");
						textFieldFirstNameAdmin.setText("");
						textFieldLastNameAdmin.setText("");
						textFieldTelephoneAdmin.setText("");
						textFieldEmailAdmin.setText("");
						loadTableAdminData(ubr);
					} else
						lblMsgAdmin.setText("<HTML>Nie udało się dodać nowego administratora!</HTML>");	
				}
			}
		});
		btnAddAdmin.setBounds(148, 222, 89, 23);
		panelAddOtherAdmin.add(btnAddAdmin);

		JButton btnZapiszZmianyAdmin = new JButton("Zapisz zmiany");
		btnZapiszZmianyAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblMsgAdmin.setText("");
				// Only first Admin have privileges for update other admins. 
				if(ubr.getAdminsData().get(0).getUser().getId() == sd.getIdUser()) {
					int checked = 0;
					String login = null, firstName = null, lastName = null, phoneNo = null, email = null;
					boolean active = false;
					int idAdmin = 0, idUser = 0;
					for (int i = 0; i < tableAdmin.getRowCount(); i++) {
						if (tableAdmin.getValueAt(i, 6) != null)
							if ((boolean) tableAdmin.getValueAt(i, 6) == true) {
								idAdmin = ubr.getAdminsData().get(i).getId();
								idUser = ubr.getUsersAdminData().get(i).getId();
								login = (String) tableAdmin.getValueAt(i, 0);
								active = (boolean) tableAdmin.getValueAt(i, 1);
								firstName = (String) tableAdmin.getValueAt(i, 2);
								lastName = (String) tableAdmin.getValueAt(i, 3);
								phoneNo = (String) tableAdmin.getValueAt(i, 4);
								email = (String) tableAdmin.getValueAt(i, 5);
								checked += 1;
							}
					}
					if (checked == 1) {
						if (ubr.updateAdminData(idUser, idAdmin, login, active, firstName, lastName, phoneNo, email)) {
							lblMsgAdmin.setText("<HTML>Dokonano edycji usera o loginie: " + login + "!</HTML>");
							loadTableAdminData(ubr);
						} else {
							lblMsgAdmin.setText("<HTML>Nie udało sie dokonać edeycji usera o loginie: " + login + "!</HTML>");
							loadTableAdminData(ubr);
						}
					} else
						lblMsgAdmin.setText("<HTML>Proszę zaznaczyć jeden wiersz do edycji!</HTML>");					
				} else {
					lblMsgAdmin.setText("<HTML>Brak uprawnień do edycji innych administratorów!</HTML>");
					loadTableAdminData(ubr);
				}
			}
		});
		btnZapiszZmianyAdmin.setBounds(725, 355, 120, 23);
		panelAddOtherAdmin.add(btnZapiszZmianyAdmin);
		panelAddOtherAdmin.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { textFieldLoginAdmin, textFieldPasswordAdmin, textFieldFirstNameAdmin,
						textFieldLastNameAdmin, textFieldTelephoneAdmin, textFieldEmailAdmin, btnAddAdmin }));
		
		JPanel panelSetActiveCustomer = new JPanel();
		tabbedPane.addTab("Actywacja / Deaktywacja klienta", null, panelSetActiveCustomer, null);
		panelSetActiveCustomer.setLayout(null);
		

		
		JLabel lblWyszukajWgNazwiska = new JLabel("wg fragmentu nazwiska:");
		lblWyszukajWgNazwiska.setBounds(10, 45, 143, 14);
		panelSetActiveCustomer.add(lblWyszukajWgNazwiska);
		
		JLabel lblWyszukajWgFragmentu = new JLabel("wg fragmentu pesel:");
		lblWyszukajWgFragmentu.setBounds(339, 45, 119, 14);
		panelSetActiveCustomer.add(lblWyszukajWgFragmentu);
		
		
		JScrollPane scrollPaneCustomer = new JScrollPane();
		scrollPaneCustomer.setBounds(10, 73, 571, 182);
		panelSetActiveCustomer.add(scrollPaneCustomer);
		
		String [] headerCustomerTable = {"Aktywny", "Id user", "Login", "Imię", "Nazwisko", "Pesel", "Wybierz"};
		tableModelCustomer = new DefaultTableModel(headerCustomerTable, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				switch (columnIndex) {
				case 0:
					return Boolean.class;
				case 1:
					return Integer.class;
				case 2:
					return String.class;
				case 6:
					return Boolean.class;
	
				default:
					break;
				}
								
				return super.getColumnClass(columnIndex);
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return column == 0 || column == 6;
			}
		};
		
		textFieldSerchByLastName = new JTextField();
		textFieldSerchByLastName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				textFieldSearchByPesel.setText(null);
				sizeCustomerList = 0;
				sizeCustomerList = ubr.findCustomerList(textFieldSerchByLastName.getText(), "").size();
				loadTableCustomer(ubr, sizeCustomerList);
			}
		});
		textFieldSerchByLastName.setBounds(163, 42, 113, 20);
		panelSetActiveCustomer.add(textFieldSerchByLastName);
		textFieldSerchByLastName.setColumns(10);
		
		textFieldSearchByPesel = new JTextField();
		textFieldSearchByPesel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				textFieldSerchByLastName.setText(null);
				sizeCustomerList = 0;
				sizeCustomerList = ubr.findCustomerList("", textFieldSearchByPesel.getText()).size();
				loadTableCustomer(ubr, sizeCustomerList);
			}
		});
		textFieldSearchByPesel.setBounds(468, 42, 113, 20);
		panelSetActiveCustomer.add(textFieldSearchByPesel);
		textFieldSearchByPesel.setColumns(10);
				
		tableCustomer = new JTable(tableModelCustomer);
		scrollPaneCustomer.setViewportView(tableCustomer);
		
		JLabel lblMsgCustomer = new JLabel("");
		lblMsgCustomer.setBounds(10, 266, 375, 75);
		panelSetActiveCustomer.add(lblMsgCustomer);
		
		JButton btnAktywujDeaktywuj = new JButton("Aktywuj / deaktywuj");
		btnAktywujDeaktywuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblMsgCustomer.setText("");
				int checked = 0;
				String login = "";
				boolean active = false;
				int idUser = 0;
				for (int i = 0; i < tableCustomer.getRowCount(); i++) {
					if (tableCustomer.getValueAt(i, 6) != null)
						if ((boolean) tableCustomer.getValueAt(i, 6) == true) {
							active = (boolean) tableCustomer.getValueAt(i, 0);
							idUser = (int) tableCustomer.getValueAt(i, 1);
							login = (String) tableCustomer.getValueAt(i, 2);
							checked += 1;
						}
				}
				if (checked == 1) {
					if (ubr.setActiveCustomer(idUser, active == true ? false : true)) {
						lblMsgCustomer.setText("<HTML>Dokonano edycji klienta o loginie: " + login + "!</HTML>");
						loadTableCustomer(ubr, sizeCustomerList);
					} else {
						lblMsgCustomer.setText("<HTML>Nie udało sie dokonać edeycji klienta o loginie: " + login + "!</HTML>");
						loadTableCustomer(ubr, sizeCustomerList);
					}
				} else
					lblMsgCustomer.setText("<HTML>Proszę zaznaczyć jeden wiersz do edycji!</HTML>");
			}
		});
		btnAktywujDeaktywuj.setBounds(432, 266, 149, 23);
		panelSetActiveCustomer.add(btnAktywujDeaktywuj);
		
		JLabel lblWyszukaj = new JLabel("Wyszukaj: ");
		lblWyszukaj.setBounds(10, 20, 75, 14);
		panelSetActiveCustomer.add(lblWyszukaj);

		JPanel panelSettingApp = new JPanel();
		tabbedPane.addTab("Ustawienia", null, panelSettingApp, null);
		panelSettingApp.setLayout(null);
		
		textFieldMinCharInPass = new JTextField();
		textFieldMinCharInPass.setBounds(198, 30, 86, 20);
		panelSettingApp.add(textFieldMinCharInPass);
		textFieldMinCharInPass.setColumns(10);
		
		textFieldUpperCaseCharInPass = new JTextField();
		textFieldUpperCaseCharInPass.setBounds(198, 97, 86, 20);
		panelSettingApp.add(textFieldUpperCaseCharInPass);
		textFieldUpperCaseCharInPass.setColumns(10);
		
		textFieldMaxCharInPass = new JTextField();
		textFieldMaxCharInPass.setBounds(198, 63, 86, 20);
		panelSettingApp.add(textFieldMaxCharInPass);
		textFieldMaxCharInPass.setColumns(10);
		
		JLabel lblMinCharInPass = new JLabel("Min znaków w haśle:");
		lblMinCharInPass.setBounds(10, 33, 178, 14);
		panelSettingApp.add(lblMinCharInPass);
		
		JLabel lblMaxCharInPass = new JLabel("Max znaków w haśle:");
		lblMaxCharInPass.setBounds(10, 66, 178, 14);
		panelSettingApp.add(lblMaxCharInPass);
		
		JLabel lblUpperCaseCharInPass = new JLabel("Ilość dużych liter w haśle:");
		lblUpperCaseCharInPass.setBounds(10, 100, 178, 14);
		panelSettingApp.add(lblUpperCaseCharInPass);
		
		textFieldSessionTime = new JTextField();
		textFieldSessionTime.setBounds(198, 232, 86, 20);
		panelSettingApp.add(textFieldSessionTime);
		textFieldSessionTime.setColumns(10);
		
		JLabel lblTimeOffSession = new JLabel("Czas trwania sesji (minut):");
		lblTimeOffSession.setBounds(10, 235, 178, 14);
		panelSettingApp.add(lblTimeOffSession);
		
		textFieldNumbersInPass = new JTextField();
		textFieldNumbersInPass.setBounds(198, 131, 86, 20);
		panelSettingApp.add(textFieldNumbersInPass);
		textFieldNumbersInPass.setColumns(10);
		
		JLabel lblNumbersInPass = new JLabel("Ilość liczb w haśle:");
		lblNumbersInPass.setBounds(11, 134, 177, 14);
		panelSettingApp.add(lblNumbersInPass);
		
		textFieldMinCharInLogin = new JTextField();
		textFieldMinCharInLogin.setBounds(198, 165, 86, 20);
		panelSettingApp.add(textFieldMinCharInLogin);
		textFieldMinCharInLogin.setColumns(10);
		
		textFieldMaxCharInLogin = new JTextField();
		textFieldMaxCharInLogin.setBounds(198, 198, 86, 20);
		panelSettingApp.add(textFieldMaxCharInLogin);
		textFieldMaxCharInLogin.setColumns(10);
		
		JLabel lblMinCharInLogin = new JLabel("Min ilość znaków w loginie:");
		lblMinCharInLogin.setBounds(12, 168, 176, 14);
		panelSettingApp.add(lblMinCharInLogin);
		
		JLabel lblMaxCharInLogin = new JLabel("Max ilość znaków w loginie:");
		lblMaxCharInLogin.setBounds(10, 201, 178, 14);
		panelSettingApp.add(lblMaxCharInLogin);
		
		JLabel lblMsgSettings = new JLabel("");
		lblMsgSettings.setBounds(10, 313, 274, 65);
		panelSettingApp.add(lblMsgSettings);
		
		loadSettingsApp(ubr);
				
		JButton btnSaveSettings = new JButton("Zapisz");
		btnSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validOK = true;
				Validation valid = new Validation(ubr.getSettingsApp());
				if (textFieldMinCharInPass.getText() == ""
						|| !valid.numberValidation(textFieldMinCharInPass.getText())) {
					validOK = false;
					lblMsgSettings.setText("<HTML>Pole \"Min znaków w haśle\" jest puste lub zawiera niepoprawne znaki!</HTML>");
				}
				if (textFieldMaxCharInPass.getText() == ""
						|| !valid.numberValidation(textFieldMaxCharInPass.getText())) {
					validOK = false;
					lblMsgSettings.setText("<HTML>Pole \"Max znaków w haśle\" jest puste lub zawiera niepoprawne znaki!</HTML>");
				}
				if (textFieldUpperCaseCharInPass.getText() == ""
						|| !valid.numberValidation(textFieldUpperCaseCharInPass.getText())) {
					validOK = false;
					lblMsgSettings.setText("<HTML>Pole \"Ilość dużych liter w haśle\" jest puste lub zawiera niepoprawne znaki!</HTML>");
				}
				if (textFieldNumbersInPass.getText() == ""
						|| !valid.numberValidation(textFieldNumbersInPass.getText())) {
					validOK = false;
					lblMsgSettings.setText("<HTML>Pole \"Ilość liczb w haśle\" jest puste lub zawiera niepoprawne znaki!</HTML>");
				}
				if (textFieldMinCharInLogin.getText() == ""
						|| !valid.numberValidation(textFieldMinCharInLogin.getText())) {
					validOK = false;
					lblMsgSettings.setText("<HTML>Pole \"Min znaków w loginie\" jest puste lub zawiera niepoprawne znaki!</HTML>");
				}
				if (textFieldMaxCharInLogin.getText() == ""
						|| !valid.numberValidation(textFieldMaxCharInLogin.getText())) {
					validOK = false;
					lblMsgSettings.setText("<HTML>Pole \"Max znaków w loginie\" jest puste lub zawiera niepoprawne znaki!</HTML>");
				}
				if (textFieldSessionTime.getText() == ""
						|| !valid.numberValidation(textFieldSessionTime.getText())) {
					validOK = false;
					lblMsgSettings.setText("<HTML>Pole \"Czas trwania sesji\" jest puste lub zawiera niepoprawne znaki!</HTML>");
				}
				
				if(validOK) {
					if(ubr.setSettingsApp(Integer.valueOf(textFieldMinCharInPass.getText()), Integer.valueOf(textFieldMaxCharInPass.getText()), Integer.valueOf(textFieldUpperCaseCharInPass.getText()), Integer.valueOf(textFieldNumbersInPass.getText()), Integer.valueOf(textFieldMinCharInLogin.getText()), Integer.valueOf(textFieldMaxCharInLogin.getText()), Integer.valueOf(textFieldSessionTime.getText()), sd.getIdUser())) {
						textFieldMinCharInLogin.setText("");
						lblMsgSettings.setText("<HTML>Nowe ustawienia zostały zapisane!</HTML>");						
					} else
						lblMsgSettings.setText("<HTML>Nie udało się zapisać nowych ustawień!</HTML>");					
				}
				
				loadSettingsApp(ubr);
			}
		});
		btnSaveSettings.setBounds(212, 270, 72, 23);
		panelSettingApp.add(btnSaveSettings);
		
		JLabel lblDescriptionForSession = new JLabel("<html>koresponduje z @StatefulTimeout, zmiana wymaga kompilacji. Można także ustawić w pliku ejb-jar.xml</html>");
		lblDescriptionForSession.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDescriptionForSession.setBounds(294, 220, 243, 44);
		panelSettingApp.add(lblDescriptionForSession);
		
		JLabel lblDodajRol = new JLabel("Istniejące role użytkowników:");
		lblDodajRol.setBounds(602, 30, 193, 14);
		panelSettingApp.add(lblDodajRol);
		
		JScrollPane scrollPaneRole = new JScrollPane();
		scrollPaneRole.setBounds(602, 55, 243, 111);
		panelSettingApp.add(scrollPaneRole);
		
		String [] tableRoleColumn = {"Id", "Rola"};
		tableModelRole = new DefaultTableModel(tableRoleColumn, ubr.getRoleList().size());
		roleTable = new JTable(tableModelRole);
		roleTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		roleTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		scrollPaneRole.setViewportView(roleTable);
		loadTableRole(ubr);
		
		JLabel lblDodajNowRol = new JLabel("Dodaj nową rolę:");
		lblDodajNowRol.setBounds(602, 192, 109, 14);
		panelSettingApp.add(lblDodajNowRol);
		
		textFieldNewRole = new JTextField();
		textFieldNewRole.setBounds(602, 210, 146, 20);
		panelSettingApp.add(textFieldNewRole);
		textFieldNewRole.setColumns(10);
				
		JLabel lblMsgRole = new JLabel("");
		lblMsgRole.setBounds(602, 253, 243, 40);
		panelSettingApp.add(lblMsgRole);
		
		JButton btnAddRole = new JButton("Dodaj");
		btnAddRole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean validOK = true;
				Validation valid = new Validation(ubr.getSettingsApp());

				if (textFieldNewRole.getText() == ""
						|| !valid.nameValidation(textFieldNewRole.getText())) {
					validOK = false;
					lblMsgRole.setText("<HTML>Pole rola jest puste lub zawiera niepoprawne znaki!</HTML>");
				}
				
				if (validOK) {
					if(ubr.addRole(textFieldNewRole.getText())) {
						lblMsgRole.setText("<HTML>Dodano nową rolę!</HTML>");
						textFieldNewRole.setText("");
						loadTableRole(ubr);
					} else
						lblMsgRole.setText("<HTML>Nie udało się dodać nowej roli!</HTML>");	
				};
				
				loadTableRole(ubr);
			}
		});
		btnAddRole.setBounds(766, 209, 79, 23);
		panelSettingApp.add(btnAddRole);
	
		JLabel lblZalogowanyJako = new JLabel("Zalogowany jako: ");
		contentPane.add(lblZalogowanyJako, BorderLayout.NORTH);
		lblZalogowanyJako.setText("Zalogowany jako: " + sd.getFirstName() + " " + sd.getLastName() + " " + sd.getRoleName());

	}

	private void loadTableOperatorData(UserBeanRemote ubr) {
		tableModelOperator.setRowCount(ubr.getOperatorsData().size());
		for (int i = 0; i < ubr.getOperatorsData().size(); i++) {
			tableOperator.setValueAt(ubr.getUsersOperatorData().get(i).getLogin(), i, 0);
			tableOperator.setValueAt(ubr.getUsersOperatorData().get(i).getActive(), i, 1);
			tableOperator.setValueAt(ubr.getOperatorsData().get(i).getFirstName(), i, 2);
			tableOperator.setValueAt(ubr.getOperatorsData().get(i).getLastName(), i, 3);
			tableOperator.setValueAt(ubr.getOperatorsData().get(i).getPhoneNo(), i, 4);
			tableOperator.setValueAt(ubr.getOperatorsData().get(i).getEmail(), i, 5);
		}
	}

	private void loadTableAdminData(UserBeanRemote ubr) {
		tableModelAdmin.setRowCount(ubr.getAdminsData().size());
		for (int i = 0; i < ubr.getAdminsData().size(); i++) {
			tableAdmin.setValueAt(ubr.getUsersAdminData().get(i).getLogin(), i, 0);
			tableAdmin.setValueAt(ubr.getUsersAdminData().get(i).getActive(), i, 1);
			tableAdmin.setValueAt(ubr.getAdminsData().get(i).getFirstName(), i, 2);
			tableAdmin.setValueAt(ubr.getAdminsData().get(i).getLastName(), i, 3);
			tableAdmin.setValueAt(ubr.getAdminsData().get(i).getPhoneNo(), i, 4);
			tableAdmin.setValueAt(ubr.getAdminsData().get(i).getEmail(), i, 5);
		}
	}
	
	private void loadSettingsApp (UserBeanRemote ubr) {
		textFieldMinCharInPass.setText(String.valueOf(ubr.getSettingsApp().getMinCharInPass()));
		textFieldMaxCharInPass.setText(String.valueOf(ubr.getSettingsApp().getMaxCharInPass()));
		textFieldUpperCaseCharInPass.setText(String.valueOf(ubr.getSettingsApp().getUpperCaseInPass()));
		textFieldNumbersInPass.setText(String.valueOf(ubr.getSettingsApp().getNumbersInPass()));
		textFieldMinCharInLogin.setText(String.valueOf(ubr.getSettingsApp().getMinCharInLogin()));
		textFieldMaxCharInLogin.setText(String.valueOf(ubr.getSettingsApp().getMaxCharInLogin()));
		textFieldSessionTime.setText(String.valueOf(ubr.getSettingsApp().getSessionTime()));
	}
	
	private void loadTableRole(UserBeanRemote ubr) {
		tableModelRole.setRowCount(ubr.getRoleList().size());
		for(int i =0; i < ubr.getRoleList().size(); i++) {
			roleTable.setValueAt(ubr.getRoleList().get(i).getId(), i, 0);
			roleTable.setValueAt(ubr.getRoleList().get(i).getRoleName(), i, 1);
		}	
	}
	
	private void loadTableCustomer(UserBeanRemote ubr, int sizeCustomerList) {
		tableModelCustomer.setRowCount(sizeCustomerList);
		for(int i = 0; i<sizeCustomerList; i++) {
			tableModelCustomer.setValueAt(ubr.findCustomerList(textFieldSerchByLastName.getText() == null ? "" : textFieldSerchByLastName.getText(), textFieldSearchByPesel.getText() == null ? "" : textFieldSearchByPesel.getText()).get(i).getUser().getActive(), i, 0);
			tableModelCustomer.setValueAt(ubr.findCustomerList(textFieldSerchByLastName.getText() == null ? "" : textFieldSerchByLastName.getText(), textFieldSearchByPesel.getText() == null ? "" : textFieldSearchByPesel.getText()).get(i).getUser().getId(), i, 1);
			tableModelCustomer.setValueAt(ubr.findCustomerList(textFieldSerchByLastName.getText() == null ? "" : textFieldSerchByLastName.getText(), textFieldSearchByPesel.getText() == null ? "" : textFieldSearchByPesel.getText()).get(i).getUser().getLogin(), i, 2);
			tableModelCustomer.setValueAt(ubr.findCustomerList(textFieldSerchByLastName.getText() == null ? "" : textFieldSerchByLastName.getText(), textFieldSearchByPesel.getText() == null ? "" : textFieldSearchByPesel.getText()).get(i).getFirstName(), i, 3);
			tableModelCustomer.setValueAt(ubr.findCustomerList(textFieldSerchByLastName.getText() == null ? "" : textFieldSerchByLastName.getText(), textFieldSearchByPesel.getText() == null ? "" : textFieldSearchByPesel.getText()).get(i).getLastName(), i, 4);
			tableModelCustomer.setValueAt(ubr.findCustomerList(textFieldSerchByLastName.getText() == null ? "" : textFieldSerchByLastName.getText(), textFieldSearchByPesel.getText() == null ? "" : textFieldSearchByPesel.getText()).get(i).getPesel(), i, 5);
		}
	}
}
