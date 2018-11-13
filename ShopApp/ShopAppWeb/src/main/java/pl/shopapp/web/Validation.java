package pl.shopapp.web;

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
}
