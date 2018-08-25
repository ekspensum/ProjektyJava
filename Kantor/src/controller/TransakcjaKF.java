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

		if (request.getParameter("sprzedajUSD") != null) {

			UserZalogowany uz = (UserZalogowany) request.getSession().getAttribute("userZalogowany");

			Kursy kurs = (Kursy) request.getServletContext().getAttribute("kurs");
			KoszykDaneWalut kdw = (KoszykDaneWalut) request.getServletContext().getAttribute("mnoznik");
			double cena = 1 / kurs.getPln_usd() * kdw.getDolarBid();
			cena *= 10000;
			cena = (double) Math.round(cena);
			cena /= 10000;

			DaneTransakcji dt = new DaneTransakcji();
			dt.setIndex(0);
			dt.setRodzaj("Sprzedaj");
			dt.setZnak("USD");
			dt.setCena(cena);
			dt.setKwota(Double.valueOf(request.getParameter("sprzedajUSD")));
			dt.setIdUzytkownika(uz.getIdUzytkownik());
			ServletContext sc = request.getServletContext();
			sc.setAttribute("transakcja", dt);

		} else if (request.getParameter("kupUSD") != null) {
			UserZalogowany uz = (UserZalogowany) request.getSession().getAttribute("userZalogowany");

			Kursy kurs = (Kursy) request.getServletContext().getAttribute("kurs");
			KoszykDaneWalut kdw = (KoszykDaneWalut) request.getServletContext().getAttribute("mnoznik");
			double cena = 1 / kurs.getPln_usd() * kdw.getDolarAsk();
			cena *= 10000;
			cena = (double) Math.round(cena);
			cena /= 10000;

			DaneTransakcji dt = new DaneTransakcji();
			dt.setIndex(0);
			dt.setRodzaj("Kup");
			dt.setZnak("USD");
			dt.setCena(cena);
			dt.setKwota(Double.valueOf(request.getParameter("kupUSD")));
			dt.setIdUzytkownika(uz.getIdUzytkownik());
			ServletContext sc = request.getServletContext();
			sc.setAttribute("transakcja", dt);
		}

		doGet(request, response);
	}

}
