package encje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dao.Kursy;



public class Test {

	public static void main(String[] args) throws IOException, JSONException, ParserConfigurationException, SAXException {
		
		Kursy kursy = new Kursy();
		
		Waluta pln_usd = new Zloty(new Dolar(0.95, 1.1, kursy));
		System.out.println(pln_usd.getBid());
		System.out.println(pln_usd.getAsk());
		System.out.println(pln_usd.getZnakKlasyBazowej());
		System.out.println(pln_usd.getZnakKlasyKonwersji());
		
		Waluta pln_chf = new Zloty(new Frank(0.95, 1.1, kursy));
		System.out.println(pln_chf.getBid());
		System.out.println(pln_chf.getAsk());
		
		Waluta usd_pln = new Dolar(new Zloty(0.95,  1.1, kursy));
		System.out.println(usd_pln.getBid());
		System.out.println(usd_pln.getAsk());
		System.out.println(usd_pln.getZnakKlasyKonwersji());
		System.out.println(usd_pln.getZnakKlasyBazowej());
		
		Operacja opRachPLN = new OperacjaRachPLN();
		OperacjaRachPLN op = (OperacjaRachPLN) opRachPLN.kupuj(100.0, usd_pln);
		System.out.println(op.getKwotaWaluty());
		System.out.println(op.getDataOperacji());
		System.out.println(op.getTytulOperacji());
		System.out.println(op.getKurs());
		System.out.println(op.getKwotaPLN());
		
//		URL url = new URL("http://free.currencyconverterapi.com/api/v5/convert?q=CHF_USD&compact=y");
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setRequestMethod("GET");
//		conn.setRequestProperty("Accept", "application/json");
//		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//		String s = br.readLine();
//		System.out.println(s);
//		
//		System.out.println("Kurs"+kursy.getPln_eur());
//		
//		JSONObject ob = new JSONObject(s);
//		JSONObject rates = ob.getJSONObject("CHF_USD");
//		System.out.println(rates.get("val"));
		
		System.out.println(kursy.getChf_usd());
		System.out.println(kursy.getEur_chf());
		System.out.println(kursy.getEur_usd());
		System.out.println(kursy.getPln_chf());
		System.out.println(kursy.getPln_eur());
		System.out.println(kursy.getPln_usd());
	}

}
