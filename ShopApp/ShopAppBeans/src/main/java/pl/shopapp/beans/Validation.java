package pl.shopapp.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.shopapp.entites.SettingsApp;

public class Validation {
	
	private SettingsApp setting;

	public Validation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Validation(SettingsApp setting) {
		super();
		this.setting = setting;
	}

	public String passwordToCode(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(password.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < bytes.length; i++)
				sb.append(Integer.toHexString(bytes[i] & 0xff));
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean loginValidation(String login) {
		Pattern patt = Pattern.compile("^[a-zA-Z0-9]{"+setting.getMinCharInLogin()+","+setting.getMaxCharInLogin()+"}$");
		Matcher mat = patt.matcher(login);
		return mat.find();
	}
	
    public boolean passwordValidation(String pass) {
        boolean passOK = true;
        if (pass.length() < setting.getMinCharInPass() || pass.length() > setting.getMaxCharInPass())
            passOK = false;
        int occUpperCase = 0;
        for (int i = 0; i < pass.length(); i++) {
            if (pass.substring(i, i + 1).matches("[A-ZĄ-Ż]"))
                occUpperCase++;
        }
        if (occUpperCase < setting.getUpperCaseInPass())
            passOK = false;
        int howMuchNum = 0;
        for (int i = 0; i < pass.length(); i++) {
            if (pass.substring(i, i + 1).matches("[0-9]"))
            	howMuchNum++;
        }
        if (howMuchNum < setting.getNumbersInPass())
            passOK = false;
        return passOK;
    }
	
    public boolean telephoneNoValidation(String telephon) {
		Pattern patt = Pattern.compile("^[0-9+\\s]{9,16}$");
		Matcher mat = patt.matcher(telephon);
		return mat.find();
	}
    
	public boolean nameValidation(String name) {
		Pattern patt = Pattern.compile("^[^|'\":%^#~}{\\]\\[;=<>`]{3,100}$");
		Matcher mat = patt.matcher(name);
		return mat.find();
	}
	
	public boolean emailValidation(String email) {
		Pattern patt = Pattern.compile("^[a-zA-Z]{3,20}[.]?[a-zA-Z]{0,20}@[a-zA-Z]{3,16}[.]{1}[a-zA-Z]{2,16}$");
		Matcher mat = patt.matcher(email);
		return mat.find();
	}
	public boolean numberValidation(String number) {
		Pattern patt = Pattern.compile("^[0-9]{1}[0-9.]{0,9}$");
		Matcher mat = patt.matcher(number);
		return mat.find();
	}
	public boolean nipValidation(String nip) {
		Pattern patt = Pattern.compile("^[0-9]{10}$");
		Matcher mat = patt.matcher(nip);
		return mat.find();
	}

	public boolean peselValidation(String pesel) {
		Pattern patt = Pattern.compile("^[0-9]{11}$");
		Matcher mat = patt.matcher(pesel);
		return mat.find();
	}
	
	public boolean zipCodeValidation(String zipCode) {
		Pattern patt = Pattern.compile("^[0-9]{2}-[0-9]{3}$");
		Matcher mat = patt.matcher(zipCode);
		return mat.find();
	}
	public boolean locationValidation(String location) {
		Pattern patt = Pattern.compile("^[^|'\":%^#~}{\\]\\[;=<>`]{1,20}$");
		Matcher mat = patt.matcher(location);
		return mat.find();
	}
	public boolean regonValidation(String regon) {
		Pattern patt = Pattern.compile("^[0-9]{9}$");
		Matcher mat = patt.matcher(regon);
		return mat.find();
	}

}
