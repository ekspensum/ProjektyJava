package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.ObslugaBD;
import model.dao.UserZalogowany;
import model.encje.DaneDolar;
import model.encje.DaneEuro;
import model.encje.DaneFrank;


@WebServlet("/panelOperatora")
public class PanelOperatora extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = request.getServletContext();
			ObslugaBD bdd = new ObslugaBD();
			List<DaneDolar> listaDaneDolar = bdd.odczytListaDaneDolar();
			sc.setAttribute("listaDaneDolar", listaDaneDolar);
			ObslugaBD bde = new ObslugaBD(); // otwarcie nowego po³¹czenie z baz¹ (konstruktor) - poprzednie zamkniête jak wy¿ej "bdd"
			List<DaneEuro> listaDaneEuro = bde.odczytListaDaneEuro();
			sc.setAttribute("listaDaneEuro", listaDaneEuro);
			ObslugaBD bdf = new ObslugaBD(); // jak wy¿ej
			List<DaneFrank> listaDaneFrank = bdf.odczytListaDaneFrank();
			sc.setAttribute("listaDaneFrank", listaDaneFrank);
		} catch (ArrayIndexOutOfBoundsException a) {
			a.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		request.getRequestDispatcher("jsp/panelOperatora.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession sesja = request.getSession();
			UserZalogowany s = (UserZalogowany) sesja.getAttribute("userZalogowany");
			ObslugaBD bd = new ObslugaBD();
			if (request.getParameter("dolarBid") != null && request.getParameter("dolarAsk") != null) {
				if (bd.dodajRekordDaneDolar(Double.valueOf(request.getParameter("dolarBid")),
						Double.valueOf(request.getParameter("dolarAsk")), s.getIdOperator(), s.getImieOperatora(), s.getNazwiskoOperatora())) {
					ServletContext sc = request.getServletContext();
					sc.setAttribute("komunikat", "Dodano nowe dane waluty USD");
				}
			}
			if (request.getParameter("euroBid") != null && request.getParameter("euroAsk") != null) {
				if (bd.dodajRekordDaneEuro(Double.valueOf(request.getParameter("euroBid")),
						Double.valueOf(request.getParameter("euroAsk")), s.getIdOperator(), s.getImieOperatora(), s.getNazwiskoOperatora())) {
					ServletContext sc = request.getServletContext();
					sc.setAttribute("komunikat", "Dodano nowe dane waluty EUR");
				}
			}
			if (request.getParameter("frankBid") != null && request.getParameter("frankAsk") != null) {
				if (bd.dodajRekordDaneFrank(Double.valueOf(request.getParameter("frankBid")),
						Double.valueOf(request.getParameter("frankAsk")), s.getIdOperator(), s.getImieOperatora(), s.getNazwiskoOperatora())) {
					ServletContext sc = request.getServletContext();
					sc.setAttribute("komunikat", "Dodano nowe dane waluty CHF");
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("przypuszczalnie koniec sesji - do sprawdzenia");
		}
		doGet(request, response);
	}

}
