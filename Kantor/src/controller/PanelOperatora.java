package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.Kursy;
import model.dao.ObslugaBD;
import model.dao.UserZalogowany;
import model.encje.DaneDolar;
import model.encje.DaneEuro;

@WebServlet("/panelOperatora")
public class PanelOperatora extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ObslugaBD bdd = new ObslugaBD();
			Kursy kursy = new Kursy();
			List<DaneDolar> listaDaneDolar = bdd.odczytListaDaneDolar();
			int rozmiar = listaDaneDolar.size();
			DaneDolar dd = listaDaneDolar.get(rozmiar - 1);
			ServletContext sc = request.getServletContext();
			sc.setAttribute("kurs", kursy);
			sc.setAttribute("mnoznik", dd);
			sc.setAttribute("listaDaneDolar", listaDaneDolar);
			ObslugaBD bde = new ObslugaBD(); // otwarcie nowego po³¹czenie z baz¹ (konstruktor) - poprzednie zamkniête jak wy¿ej "bdd"
			List<DaneEuro> listaDaneEuro = bde.odczytListaDaneEuro();
			sc.setAttribute("listaDaneEuro", listaDaneEuro);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
						Double.valueOf(request.getParameter("dolarAsk")), s.getIdOperator())) {
					ServletContext sc = request.getServletContext();
					sc.setAttribute("komunikat", "Dodano nowe dane waluty USD");
				}
			}
			if (request.getParameter("euroBid") != null && request.getParameter("euroAsk") != null) {
				if (bd.dodajRekordDaneEuro(Double.valueOf(request.getParameter("euroBid")),
						Double.valueOf(request.getParameter("euroAsk")), s.getIdOperator())) {
					ServletContext sc = request.getServletContext();
					sc.setAttribute("komunikat", "Dodano nowe dane waluty EUR");
				}
			}
		} catch (NumberFormatException | SQLException e) {

			e.printStackTrace();
		}
		doGet(request, response);
	}

}
