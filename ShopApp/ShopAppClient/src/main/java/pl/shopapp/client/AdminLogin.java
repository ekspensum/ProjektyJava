package pl.shopapp.client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBeanRemote;
import pl.shopapp.beans.Validation;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class AdminLogin {

	private JFrame frame;
	private JTextField textFieldLogin;
	private static UserBeanRemote ubr;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLogin window = new AdminLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminLogin() {
		initialize();
	}

	public UserBeanRemote contextAppUserBean() {
		Context ctx;
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		try {
			ctx = new InitialContext(env);
			ubr = (UserBeanRemote) ctx.lookup("ejb:ShopApp/ShopAppBeans/UserBean!pl.shopapp.beans.UserBeanRemote");
			return ubr;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

//	public UserBeanRemote contextAppUserBean() {
//		Context ctx;
//	    Properties props = new Properties();  
//	    props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");  
//	    props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");  
//	    props.put("jboss.naming.client.ejb.context", true);
//	    props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
//		try {
//			ctx = new InitialContext(props);
//			ubr = (UserBeanRemote) ctx.lookup("ejb:ShopApp/ShopAppBeans/UserBean!pl.shopapp.beans.UserBeanRemote");
//			return ubr;
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(137, 93, 46, 14);
		frame.getContentPane().add(lblLogin);

		JLabel lblHaso = new JLabel("Hasło:");
		lblHaso.setBounds(137, 118, 46, 14);
		frame.getContentPane().add(lblHaso);

		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(193, 90, 120, 20);
		frame.getContentPane().add(textFieldLogin);
		textFieldLogin.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(193, 115, 120, 20);
		frame.getContentPane().add(passwordField);

		JLabel lblMsg = new JLabel("");
		lblMsg.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMsg.setBounds(117, 187, 295, 14);
		frame.getContentPane().add(lblMsg);

		JButton btnZaloguj = new JButton("Zaloguj");

		btnZaloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ubr = contextAppUserBean();
				if (!textFieldLogin.getText().equals("")) {
					if (passwordField.getPassword().length != 0) {
						Validation valid = new Validation(ubr.getSettingsApp());
						String pass = valid.passwordToCode(passwordField.getText());
						if (valid.loginValidation(textFieldLogin.getText())) {
							if (ubr.loginAdmin(textFieldLogin.getText(), pass) != null) {
								SessionData sd = ubr.loginAdmin(textFieldLogin.getText(), pass);
								frame.dispose();
								AdminPanel ap = new AdminPanel(ubr, sd);
								ap.setLocationRelativeTo(null);
								ap.setVisible(true);
							} else
								lblMsg.setText("Nieprawidłowe dane logowania lub user jest nieaktywny!");
						} else
							lblMsg.setText("Nieprawidłowe dane logowania! 3");
					} else
						lblMsg.setText("Nieprawidłowe dane logowania! 2");
				} else
					lblMsg.setText("Nieprawidłowe dane logowania! 1");
			}
		});

		btnZaloguj.setBounds(234, 146, 79, 23);
		frame.getContentPane().add(btnZaloguj);

		JLabel lblLogowanieDoPanelu = new JLabel("Logowanie do panelu administratora");
		lblLogowanieDoPanelu.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLogowanieDoPanelu.setBounds(119, 51, 213, 14);
		frame.getContentPane().add(lblLogowanieDoPanelu);

	}
}
