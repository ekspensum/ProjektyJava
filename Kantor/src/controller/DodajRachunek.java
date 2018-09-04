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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ObslugaBD bd = new ObslugaBD();		
		String [] opcjaKF = request.getParameterValues("walutaKF");
		int zliczOpcjeKF = 0;
		String wybranaOpcjaKF = "";
		String[] tabBoxKF = request.getParameterValues("boxKF");
		if (request.getParameter("loginKF") != "" || request.getParameter("nazwa") != "" || request.getParameter("regon") != "" || request.getParameter("nip") != "") {
			List<KlientWyszukaj> listaKF = bd.wyszukajKlientaFirmowego(request.getParameter("loginKF"),request.getParameter("nazwa"), request.getParameter("regon"), request.getParameter("nip"));
			request.setAttribute("listaKF", listaKF);
				if (tabBoxKF != null) {
					if (tabBoxKF.length == 1) {
						if(opcjaKF != null)
							for(int i=0; i<opcjaKF.length; i++)
								if(opcjaKF[i] != "") {
									wybranaOpcjaKF = opcjaKF[i];
									zliczOpcjeKF++;									
								}
						if(zliczOpcjeKF == 1) {
							if(bd.ustawWaluteKlienta(wybranaOpcjaKF, Integer.valueOf(request.getParameter("boxKF")))) {
								request.setAttribute("komunikat", "Dodano walutę "+wybranaOpcjaKF+" dla wybranego klienta");									
							} else
								request.setAttribute("komunika", "NIE udało sie dadać waluty dla wybranego klienta");
						} else 
							request.setAttribute("komunikat", "Proszę wybrać opcję waluty dla jednego klienta");
					} else 
						request.setAttribute("komunikat", "Proszę wybrać jednego klienta");
				}			
		} else 
			request.setAttribute("komunikat", "Proszę wypełnić jedno z pól wyszukiwania.");
		
		String [] opcjaKP = request.getParameterValues("walutaKP");
		int zliczOpcjeKP = 0;
		String wybranaOpcjaKP = "";
		String[] tabBoxKP = request.getParameterValues("boxKP");
		if (request.getParameter("loginKP") != "" || request.getParameter("nazwisko") != "" || request.getParameter("pesel") != "") {
			List<KlientWyszukaj> listaKP = bd.wyszukajKlientaPrywatnego(request.getParameter("loginKP"), request.getParameter("nazwisko"), request.getParameter("pesel"));
			request.setAttribute("listaKP", listaKP);
				if (tabBoxKP != null) {
					if (tabBoxKP.length == 1) {
						if(opcjaKP != null)
							for(int i=0; i<opcjaKP.length; i++)
								if(opcjaKP[i] != "") {
									wybranaOpcjaKP = opcjaKP[i];
									zliczOpcjeKP++;									
								}
						if(zliczOpcjeKP == 1) {						
							if(bd.ustawWaluteKlienta(wybranaOpcjaKP, Integer.valueOf(request.getParameter("boxKP")))) {
								request.setAttribute("komunikat", "Dodano walutę "+wybranaOpcjaKP+" dla wybranego klienta");									
							} else
							request.setAttribute("komunika", "NIE udało sie dadać waluty dla wybranego klienta");
						} else 
							request.setAttribute("komunikat", "Proszę wybrać opcję waluty dla jednego klienta");
					} else 
						request.setAttribute("komunikat", "Proszę wybrać jednego klienta");
				}			
		} else 
			request.setAttribute("komunikat", "Proszę wypełnić jedno z pól wyszukiwania.");

		doGet(request, response);
	}

}
