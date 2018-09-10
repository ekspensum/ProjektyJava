package controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.DaneTransakcji;
import model.dao.KoszykDaneWalut;
import model.dao.Kursy;
import model.dao.StanyRachunkow;
import model.dao.UserZalogowany;

/**
 * Servlet implementation class TransakcjaKF
 */
@WebServlet("/transakcjaKF")
public class TransakcjaKF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("jsp/transakcjaKF.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserZalogowany uz = (UserZalogowany) request.getSession().getAttribute("userZalogowany");
		Kursy kurs = (Kursy) request.getServletContext().getAttribute("kurs");
		KoszykDaneWalut kdw = (KoszykDaneWalut) request.getServletContext().getAttribute("mnoznik");
		DaneTransakcji dt = new DaneTransakcji();
		ServletContext sc = request.getServletContext();
		StanyRachunkow sr = (StanyRachunkow) sc.getAttribute("rachunkiKF");
		
		if (request.getParameter("sprzedajUSD") != "" && request.getParameter("sprzedajUSD") != null) {

			double cena = 1 / kurs.getPln_usd() * kdw.getDolarBid();
			cena *= 10000;
			cena = (double) Math.round(cena);
			cena /= 10000;

			dt.setIndex(0);
			dt.setRodzaj("Sprzedaj");
			dt.setZnak("USD");
			dt.setCena(cena);
			dt.setKwota(Double.valueOf(request.getParameter("sprzedajUSD")));
			dt.setIdUzytkownika(uz.getIdUzytkownik());
			dt.setIdRachunkuUSD(sr.getIdRachunkuUSD());
			dt.setIdRachunkuPLN(sr.getIdRachunkuPLN());
			if (dt.getKwota() <= sr.getStanUSD()) {
				sc.setAttribute("transakcjaKF", dt);
				doGet(request, response);
			} else {
				request.setAttribute("komunikatSprzedajUSD", "Nie masz wystarczających środków do tej trnsakcji.");
				request.getRequestDispatcher("jsp/panelKlientaFirmowego.jsp").forward(request, response);
			}
		} else if (request.getParameter("kupUSD") != "" && request.getParameter("kupUSD") != null) {
			double cena = 1 / kurs.getPln_usd() * kdw.getDolarAsk();
			cena *= 10000;
			cena = (double) Math.round(cena);
			cena /= 10000;

			dt.setIndex(0);
			dt.setRodzaj("Kup");
			dt.setZnak("USD");
			dt.setCena(cena);
			dt.setKwota(Double.valueOf(request.getParameter("kupUSD")));
			dt.setIdUzytkownika(uz.getIdUzytkownik());
			dt.setIdRachunkuUSD(sr.getIdRachunkuUSD());
			dt.setIdRachunkuPLN(sr.getIdRachunkuPLN());
			if (dt.getKwota() * dt.getCena() <= sr.getStanPLN()) {
				sc.setAttribute("transakcjaKF", dt);
				doGet(request, response);
			} else {
				request.setAttribute("komunikatKupUSD", "Nie masz wystarczających środków do tej trnsakcji.");
				request.getRequestDispatcher("jsp/panelKlientaFirmowego.jsp").forward(request, response);
			}
		} else if (request.getParameter("sprzedajEUR") != "" && request.getParameter("sprzedajEUR") != null) {
			double cena = 1 / kurs.getPln_eur() * kdw.getEuroBid();
			cena *= 10000;
			cena = (double) Math.round(cena);
			cena /= 10000;

			dt.setIndex(1);
			dt.setRodzaj("Sprzedaj");
			dt.setZnak("EUR");
			dt.setCena(cena);
			dt.setKwota(Double.valueOf(request.getParameter("sprzedajEUR")));
			dt.setIdUzytkownika(uz.getIdUzytkownik());
			dt.setIdRachunkuEUR(sr.getIdRachunkuEUR());
			dt.setIdRachunkuPLN(sr.getIdRachunkuPLN());
			if (dt.getKwota() <= sr.getStanEUR()) {
				sc.setAttribute("transakcjaKF", dt);
				doGet(request, response);
			} else {
				request.setAttribute("komunikatSprzedajEUR", "Nie masz wystarczających środków do tej trnsakcji.");
				request.getRequestDispatcher("jsp/panelKlientaFirmowego.jsp").forward(request, response);
			}
		} else if (request.getParameter("kupEUR") != "" && request.getParameter("kupEUR") != null) {
			double cena = 1 / kurs.getPln_eur() * kdw.getEuroAsk();
			cena *= 10000;
			cena = (double) Math.round(cena);
			cena /= 10000;

			dt.setIndex(1);
			dt.setRodzaj("Kup");
			dt.setZnak("EUR");
			dt.setCena(cena);
			dt.setKwota(Double.valueOf(request.getParameter("kupEUR")));
			dt.setIdUzytkownika(uz.getIdUzytkownik());
			dt.setIdRachunkuEUR(sr.getIdRachunkuEUR());
			dt.setIdRachunkuPLN(sr.getIdRachunkuPLN());
			if (dt.getKwota() * dt.getCena() <= sr.getStanPLN()) {
				sc.setAttribute("transakcjaKF", dt);
				doGet(request, response);
			} else {
				request.setAttribute("komunikatKupEUR", "Nie masz wystarczających środków do tej trnsakcji.");
				request.getRequestDispatcher("jsp/panelKlientaFirmowego.jsp").forward(request, response);
			}
		} else if (request.getParameter("sprzedajCHF") != "" && request.getParameter("sprzedajCHF") != null) {
			double cena = 1 / kurs.getPln_chf() * kdw.getFrankBid();
			cena *= 10000;
			cena = (double) Math.round(cena);
			cena /= 10000;

			dt.setIndex(2);
			dt.setRodzaj("Sprzedaj");
			dt.setZnak("CHF");
			dt.setCena(cena);
			dt.setKwota(Double.valueOf(request.getParameter("sprzedajCHF")));
			dt.setIdUzytkownika(uz.getIdUzytkownik());
			dt.setIdRachunkuCHF(sr.getIdRachunkuCHF());
			dt.setIdRachunkuPLN(sr.getIdRachunkuPLN());
			if (dt.getKwota() <= sr.getStanCHF()) {
				sc.setAttribute("transakcjaKF", dt);
				doGet(request, response);
			} else {
				request.setAttribute("komunikatSprzedajCHF", "Nie masz wystarczających środków do tej trnsakcji.");
				request.getRequestDispatcher("jsp/panelKlientaFirmowego.jsp").forward(request, response);
			}
		} else if (request.getParameter("kupCHF") != "" && request.getParameter("kupCHF") != null) {
			double cena = 1 / kurs.getPln_chf() * kdw.getFrankAsk();
			cena *= 10000;
			cena = (double) Math.round(cena);
			cena /= 10000;

			dt.setIndex(2);
			dt.setRodzaj("Kup");
			dt.setZnak("CHF");
			dt.setCena(cena);
			dt.setKwota(Double.valueOf(request.getParameter("kupCHF")));
			dt.setIdUzytkownika(uz.getIdUzytkownik());
			dt.setIdRachunkuCHF(sr.getIdRachunkuCHF());
			dt.setIdRachunkuPLN(sr.getIdRachunkuPLN());
			if (dt.getKwota() * dt.getCena() <= sr.getStanPLN()) {
				sc.setAttribute("transakcjaKF", dt);
				doGet(request, response);
			} else {
				request.setAttribute("komunikatKupCHF", "Nie masz wystarczających środków do tej trnsakcji.");
				request.getRequestDispatcher("jsp/panelKlientaFirmowego.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("komunikat", "Transakcja nie powiodła się.");
			request.getRequestDispatcher("jsp/panelKlientaFirmowego.jsp").forward(request, response);
		}
//			doGet(request, response);

	}

}
