package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.KlientWyszukaj;
import model.dao.ObslugaBD;
import model.dao.Operacja;
import model.dao.OperacjaRachCHF;
import model.dao.OperacjaRachEUR;
import model.dao.OperacjaRachUSD;
import model.dao.UserZalogowany;

/**
 * Servlet implementation class DodajRachunek
 */
@WebServlet("/dodajRachunek")
public class DodajRachunek extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("jsp/dodajRachunek.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserZalogowany uz = (UserZalogowany) request.getSession().getAttribute("userZalogowany");
		ObslugaBD bd = new ObslugaBD();
		Operacja[] op = new Operacja[3];
		op[0] = new OperacjaRachUSD();
		op[1] = new OperacjaRachEUR();
		op[2] = new OperacjaRachCHF();

		String[] tabBoxKF = request.getParameterValues("boxKF");
		if (request.getParameter("loginKF") != "" || request.getParameter("nazwa") != ""
				|| request.getParameter("regon") != "" || request.getParameter("nip") != "") {
			List<KlientWyszukaj> listaKF = bd.wyszukajKlientaFirmowego(request.getParameter("loginKF"),
					request.getParameter("nazwa"), request.getParameter("regon"), request.getParameter("nip"));
			request.setAttribute("listaKF", listaKF);
			if (tabBoxKF != null) {
				if (tabBoxKF.length == 1) {
					if (request.getParameter("waluta") != "") {
						if (request.getParameter("wyborOpcjiKF").equals("2")) {
							if (!bd.czyPosiadaRachunekKF(request.getParameter("waluta"),
									Integer.valueOf(request.getParameter("boxKF")))) {
								if (request.getParameter("rachunekKF") != "") {
									if (op[Integer.valueOf(request.getParameter("waluta"))].dodajRachunekKlienta(
											request.getParameter("rachunekKF"),
											Integer.valueOf(request.getParameter("boxKF")), uz.getIdAdministrator())) {
										request.setAttribute("komunikat",
												"Dodano rachunek walutowy dla wybranego klienta");
									} else
										request.setAttribute("komunikat",
												"NIE udało sie dadać rachunku dla wybranego klienta");
								} else
									request.setAttribute("komunikat", "Proszę uzupełnić pole numer rachunku.");
							} else
								request.setAttribute("komunikat",
										"Klient posiada już rachunek w wybranej walucie. Nie można dodać kolejnego.");
						} else if (request.getParameter("wyborOpcjiKF").equals("0")) {
							if (bd.czyPosiadaRachunekKF(request.getParameter("waluta"),
									Integer.valueOf(request.getParameter("boxKF")))) {
								if (op[Integer.valueOf(request.getParameter("waluta"))].ustawRachunekKlienta(
										Integer.valueOf(request.getParameter("boxKF")),
										Integer.valueOf(request.getParameter("wyborOpcjiKF")))) {
									request.setAttribute("komunikat",
											"Deaktywowano wybrany rachunek walutowy dla wybranego klienta");
								} else
									request.setAttribute("komunikat",
											"NIE udało sie deaktywować rachunku dla wybranego klienta");
							} else
								request.setAttribute("komunikat",
										"Klient NIE posiada wybranego rachunku. Nie można deaktywować.");
						} else if (request.getParameter("wyborOpcjiKF").equals("1")) {
							if (bd.czyPosiadaRachunekKF(request.getParameter("waluta"),
									Integer.valueOf(request.getParameter("boxKF")))) {
								if (op[Integer.valueOf(request.getParameter("waluta"))].ustawRachunekKlienta(
										Integer.valueOf(request.getParameter("boxKF")),
										Integer.valueOf(request.getParameter("wyborOpcjiKF")))) {
									request.setAttribute("komunikat",
											"Ponownie aktywowano wybrany rachunek walutowy dla wybranego klienta");
								} else
									request.setAttribute("komunikat",
											"NIE udało sie aktywować wybranego rachunku dla wybranego klienta");
							} else
								request.setAttribute("komunikat",
										"Klient NIE posiada wybranego rachunku. Nie można aktywować.");
						}
					} else
						request.setAttribute("komunikat", "Proszę wybrać opcję waluty.");
				} else
					request.setAttribute("komunikat", "Proszę wybrać jednego klienta.");
			}
//				else 	
//					request.setAttribute("komunikat", "Nie wybrano żadnego klienta.");
		} else
			request.setAttribute("komunikat", "Proszę wypełnić jedno z pól wyszukiwania.");

		String[] tabBoxKP = request.getParameterValues("boxKP");

		if (request.getParameter("loginKP") != "" || request.getParameter("nazwisko") != ""
				|| request.getParameter("pesel") != "") {
			List<KlientWyszukaj> listaKP = bd.wyszukajKlientaPrywatnego(request.getParameter("loginKP"),
					request.getParameter("nazwisko"), request.getParameter("pesel"));
			request.setAttribute("listaKP", listaKP);
			if (tabBoxKP != null) {
				if (tabBoxKP.length == 1) {

					if (request.getParameter("waluta") != "") {
						if (request.getParameter("wyborOpcjiKP").equals("2")) {
							if (!bd.czyPosiadaRachunekKP(request.getParameter("waluta"),
									Integer.valueOf(request.getParameter("boxKP")))) {
								if (request.getParameter("rachunekKP") != "") {
									if (op[Integer.valueOf(request.getParameter("waluta"))].dodajRachunekKlienta(
											request.getParameter("rachunekKP"),
											Integer.valueOf(request.getParameter("boxKP")), uz.getIdAdministrator())) {
										request.setAttribute("komunikat",
												"Dodano rachunek walutowy dla wybranego klienta");
									} else
										request.setAttribute("komunikat",
												"NIE udało sie dadać rachunku dla wybranego klienta");
								} else
									request.setAttribute("komunikat", "Proszę uzupełnić pole numer rachunku.");
							} else
								request.setAttribute("komunikat",
										"Klient posiada już rachunek w wybranej walucie. Nie można dodać kolejnego.");
						} else if (request.getParameter("wyborOpcjiKP").equals("0")) {
							if (bd.czyPosiadaRachunekKP(request.getParameter("waluta"),
									Integer.valueOf(request.getParameter("boxKP")))) {
								if (op[Integer.valueOf(request.getParameter("waluta"))].ustawRachunekKlienta(
										Integer.valueOf(request.getParameter("boxKP")),
										Integer.valueOf(request.getParameter("wyborOpcjiKP")))) {
									request.setAttribute("komunikat",
											"Deaktywowano wybrany rachunek walutowy dla wybranego klienta");
								} else
									request.setAttribute("komunikat",
											"NIE udało sie deaktywować rachunku dla wybranego klienta");
							} else
								request.setAttribute("komunikat",
										"Klient NIE posiada wybranego rachunku. Nie można deaktywować.");
						} else if (request.getParameter("wyborOpcjiKP").equals("1")) {
							if (bd.czyPosiadaRachunekKP(request.getParameter("waluta"),
									Integer.valueOf(request.getParameter("boxKP")))) {
								if (op[Integer.valueOf(request.getParameter("waluta"))].ustawRachunekKlienta(
										Integer.valueOf(request.getParameter("boxKP")),
										Integer.valueOf(request.getParameter("wyborOpcjiKP")))) {
									request.setAttribute("komunikat",
											"Ponownie aktywowano wybrany rachunek walutowy dla wybranego klienta");
								} else
									request.setAttribute("komunikat",
											"NIE udało sie aktywować wybranego rachunku dla wybranego klienta");
							} else
								request.setAttribute("komunikat",
										"Klient NIE posiada wybranego rachunku. Nie można aktywować.");
						}
					} else
						request.setAttribute("komunikat", "Proszę wybrać opcję waluty.");

				} else
					request.setAttribute("komunikat", "Proszę wybrać jednego klienta.");
			}
//				else 	
//					request.setAttribute("komunikat", "Nie wybrano żadnego klienta.");
		} else
			request.setAttribute("komunikat", "Proszę wypełnić jedno z pól wyszukiwania.");

		doGet(request, response);
	}

}
