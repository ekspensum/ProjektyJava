package model.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Kursy {

	private double pln_usd;
	private double pln_eur;
	private double pln_chf;
	private double eur_usd;
	private double chf_usd;
	private double eur_chf;

	public Kursy() {

		try {
			JSONObject obECB = getKursyECB();
			JSONObject obFreeApi = getKursyFreeApi();
			this.pln_usd = 1 / Double.valueOf(getKursyNBP().getElementsByTagName("Mid").item(1).getTextContent());
			this.pln_eur = 1 / Double.valueOf(getKursyNBP().getElementsByTagName("Mid").item(7).getTextContent());
			this.pln_chf = 1 / Double.valueOf(getKursyNBP().getElementsByTagName("Mid").item(9).getTextContent());
			if (obECB != null)
				this.eur_usd = obECB.getDouble("USD");
			this.chf_usd = obFreeApi.getDouble("val");
			if (obECB != null)
				this.eur_chf = obECB.getDouble("CHF");
		} catch (NumberFormatException | DOMException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public double getPln_usd() {
		return pln_usd;
	}

	public double getPln_eur() {
		return pln_eur;
	}

	public double getPln_chf() {
		return pln_chf;
	}

	public double getEur_usd() {
		return eur_usd;
	}

	public double getChf_usd() {
		return chf_usd;
	}

	public double getEur_chf() {
		return eur_chf;
	}

	private Document getKursyNBP() {
		try {
			URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/a/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbFactory.newDocumentBuilder();
			Document doc = db.parse(conn.getInputStream());
			return doc;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private JSONObject getKursyECB() {
		try {
			URL url = new URL("https://exchangeratesapi.io/api/latest");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String sj = br.readLine();
			JSONObject ob = new JSONObject(sj);
			JSONObject rates = ob.getJSONObject("rates");
			return rates;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	Aktulizacja danych odbywa siê co 60 min. Dostêpnych jest wiele walut w tym PLN
	private JSONObject getKursyFreeApi() {
		try {
			URL url = new URL("http://free.currencyconverterapi.com/api/v5/convert?q=CHF_USD&compact=y");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String sj = br.readLine();
			JSONObject ob = new JSONObject(sj);
			JSONObject rates = ob.getJSONObject("CHF_USD");
			return rates;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
