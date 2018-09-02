package controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WalidacjaKodowanie {

	public String hasloZakodowane(String haslo) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bajty = md.digest(haslo.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < bajty.length; i++)
				sb.append(Integer.toHexString(bajty[i] & 0xff));
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean walidujLogin(String login) {
		Pattern patt = Pattern.compile("^[a-zA-Z0-9]{3,15}$");
		Matcher mat = patt.matcher(login);
		return mat.find();
	}

    public boolean walidujHaslo(String haslo) {
        boolean hasloOK = true;
        if (haslo.length() < 6 || haslo.length() > 15)
            hasloOK = false;
        int ileDuzych = 0;
        for (int i = 0; i < haslo.length(); i++) {
            if (haslo.substring(i, i + 1).matches("[A-ZĄ-Ż]"))
                ileDuzych++;
        }
        if (ileDuzych < 1)
            hasloOK = false;
        int ileLiczb = 0;
        for (int i = 0; i < haslo.length(); i++) {
            if (haslo.substring(i, i + 1).matches("[0-9]"))
                ileLiczb++;
        }
        if (ileLiczb < 2)
            hasloOK = false;
        return hasloOK;
    }

	public boolean walidujNazwy(String nazwa) {
		Pattern patt = Pattern.compile("^[^|'\":%^#~}{\\]\\[;=<>`]{3,100}$");
		Matcher mat = patt.matcher(nazwa);
		return mat.find();
	}
	
	public boolean walidujRegon(int regon) {
		Pattern patt = Pattern.compile("^[0-9]{9}$");
		Matcher mat = patt.matcher(String.valueOf(regon));
		return mat.find();
	}
	
	public boolean walidujNip(BigInteger nip) {
		Pattern patt = Pattern.compile("^[0-9]{10}$");
		Matcher mat = patt.matcher(String.valueOf(nip));
		return mat.find();
	}
	
	public boolean walidujKodPocztowy(String kod) {
		Pattern patt = Pattern.compile("^[0-9]{2}-[0-9]{3}$");
		Matcher mat = patt.matcher(kod);
		return mat.find();
	}
	
	public boolean walidujNrTelefonu(String telefon) {
		Pattern patt = Pattern.compile("^[0-9+\\s]{9,16}$");
		Matcher mat = patt.matcher(telefon);
		return mat.find();
	}
	
	public boolean walidujNrRachunku(String nrRachunku) {
		Pattern patt = Pattern.compile("^[0-9]{26}$");
		Matcher mat = patt.matcher(nrRachunku);
		return mat.find();
	}
	
	public boolean walidujNrLokalizacji(String nazwa) {
		Pattern patt = Pattern.compile("^[^|'\":%^#~}{\\]\\[;=<>`]{1,20}$");
		Matcher mat = patt.matcher(nazwa);
		return mat.find();
	}
}
