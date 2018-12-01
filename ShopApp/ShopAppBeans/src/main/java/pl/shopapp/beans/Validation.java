package pl.shopapp.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

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
		Pattern patt = Pattern.compile("^[a-zA-Z0-9]{3,15}$");
		Matcher mat = patt.matcher(login);
		return mat.find();
	}
	
    public boolean passwordValidation(String pass) {
        boolean passOK = true;
        if (pass.length() < 6 || pass.length() > 15)
            passOK = false;
        int occUpperCase = 0;
        for (int i = 0; i < pass.length(); i++) {
            if (pass.substring(i, i + 1).matches("[A-ZĄ-Ż]"))
                occUpperCase++;
        }
        if (occUpperCase < 1)
            passOK = false;
        int ileLiczb = 0;
        for (int i = 0; i < pass.length(); i++) {
            if (pass.substring(i, i + 1).matches("[0-9]"))
                ileLiczb++;
        }
        if (ileLiczb < 2)
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
		Pattern patt = Pattern.compile("^[a-zA-Z.]{3,20}@[a-zA-Z.]{4,100}$");
		Matcher mat = patt.matcher(email);
		return mat.find();
	}
	public boolean numberValidation(String number) {
		Pattern patt = Pattern.compile("^[0-9.]{1,10}$");
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
