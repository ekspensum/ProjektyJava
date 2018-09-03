package controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ObslugaBD;
import model.dao.UserZalogowany;
import model.dao.WynikiDodajUzytkownika;
import model.encje.KlientPrywatny;
import model.encje.RachunekPLN;
import model.encje.Uzytkownik;

/**
 * Servlet implementation class DodajKlientaPrywatnego
 */
@WebServlet("/dodajKlientaPrywatnego")
public class DodajKlientaPrywatnego extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("jsp/dodajKlientaPrywatnego.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserZalogowany uz = (UserZalogowany) request.getSession().getAttribute("userZalogowany");
		WalidacjaKodowanie kw = new WalidacjaKodowanie();
		
//		Tworzenie obiektów encji
		Uzytkownik u = new Uzytkownik();
		u.setLogin(request.getParameter("login"));
		u.setHaslo(request.getParameter("haslo"));	
		u.setIdRola(4);
		request.setAttribute("user", u);
		
		KlientPrywatny kp = new KlientPrywatny();
		kp.setImie(request.getParameter("imie"));	
		kp.setNazwisko(request.getParameter("nazwisko"));
		kp.setPesel(request.getParameter("pesel"));
		kp.setKod(request.getParameter("kod"));
		kp.setMiasto(request.getParameter("miasto"));
		kp.setUlica(request.getParameter("ulica"));
		kp.setNrDomu(request.getParameter("nrDomu"));
		kp.setNrLokalu(request.getParameter("nrLokalu"));
		kp.setTelefon(request.getParameter("telefon"));
		kp.setDataDodania(LocalDateTime.now());
		kp.setIdAdministrator(uz.getIdAdministrator());
		request.setAttribute("prywatny", kp);
		
		RachunekPLN pln = new RachunekPLN();
		pln.setNrRachunku(request.getParameter("pln"));
		pln.setDataUtworzenia(LocalDateTime.now());
		pln.setIdAdministrator(uz.getIdAdministrator());
		request.setAttribute("pln", pln);
		
//		Walidacja
		boolean walidacjaOK = true;
		if(u.getLogin() == "" || !kw.walidujLogin(u.getLogin())) {
			walidacjaOK = false;
			request.setAttribute("login", "Pole login jest puste lub zawiera niepoprawne znaki!");			
		}
		if(u.getHaslo() == "" || !kw.walidujHaslo(u.getHaslo())) {
			walidacjaOK = false;
			request.setAttribute("haslo", "Pole hasło jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");
		}
		String haslo2wartosc = request.getParameter("haslo2");
		request.setAttribute("haslo2wartosc", haslo2wartosc);
		if(!request.getParameter("haslo").equals(request.getParameter("haslo2"))) {
			walidacjaOK = false;
			request.setAttribute("haslo2", "Brak zgodności haseł!");
		}
		if(kp.getImie() == "" || !kw.walidujNazwy(kp.getImie())) {
			walidacjaOK = false;
			request.setAttribute("imie", "Pole Imię jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(kp.getNazwisko() == "" || !kw.walidujNazwy(kp.getNazwisko())) {
			walidacjaOK = false;
			request.setAttribute("nazwisko", "Pole Nazwisko jest puste bądz ilość cyfr jest różna od 9");				
		}
		if(kp.getPesel() == "" || !kw.walidujPesel(kp.getPesel())) {
			walidacjaOK = false;
			request.setAttribute("pesel", "Pole PESEL jest puste bądz ilość cyfr jest różna od 11");				
		}
		if(kp.getKod() == "" || !kw.walidujKodPocztowy(kp.getKod())) {
			walidacjaOK = false;
			request.setAttribute("kod", "Pole Kod Pocztowy jest puste bądz ilość znaków lub ich forma są niepoprawne (00-000)");				
		}
		if(kp.getMiasto() == "" || !kw.walidujNazwy(kp.getMiasto())) {
			walidacjaOK = false;
			request.setAttribute("miasto", "Pole Miasto jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(kp.getUlica() == "" || !kw.walidujNazwy(kp.getUlica())) {
			walidacjaOK = false;
			request.setAttribute("ulica", "Pole Ulica jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(kp.getNrDomu() == "" || !kw.walidujNrLokalizacji(kp.getNrDomu())) {
			walidacjaOK = false;
			request.setAttribute("nrDomu", "Pole Nr Domu jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		
		if(kp.getNrLokalu() != "") {
			if(!kw.walidujNrLokalizacji(kp.getNrLokalu())) {
				walidacjaOK = false;
				request.setAttribute("nrLokalu", "Pole Nr Lokalu jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
			}					
		} 
		
		if(kp.getTelefon() == "" || !kw.walidujNrTelefonu(kp.getTelefon())) {
			walidacjaOK = false;
			request.setAttribute("telefon", "Pole Nr Telefonu jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(pln.getNrRachunku() == "" || !kw.walidujNrRachunku(pln.getNrRachunku())) {
			walidacjaOK = false;
			request.setAttribute("nrRachunku", "Pole Nr Rachunku jest puste bądz ilość znaków lub ich forma są niepoprawne (26 cyfr)");				
		}
		
		if(walidacjaOK) {
			u.setHaslo(kw.hasloZakodowane(u.getHaslo()));
			ObslugaBD bd = new ObslugaBD();
			WynikiDodajUzytkownika wynik = bd.dodajKlientaPrywatnego(u, kp, pln);
			if(wynik.isDodano()) {
				request.setAttribute("komunikat", "Dodano nowego klienta prywatnego.");
				request.setAttribute("user", null);
				request.setAttribute("prywatny", null);
				request.setAttribute("pln", null);
				request.setAttribute("haslo2", "");
				request.setAttribute("haslo2wartosc", "");
			} else {
				request.setAttribute("komunikat", "NIE udało się dodać nowego klienta prywatnego. Użytkownik, którego próbowano wprowadzić jest już zapisany w bazie danych!");
				request.setAttribute("wynik", wynik);
			}
		}
			
		doGet(request, response);
	}
}
