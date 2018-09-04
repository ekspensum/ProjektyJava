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
import model.encje.Administrator;
import model.encje.Uzytkownik;

/**
 * Servlet implementation class DodajAdministratora
 */
@WebServlet("/dodajAdministratora")
public class DodajAdministratora extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("jsp/dodajAdministratora.jsp").forward(request, response);
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
		u.setIdRola(1);
		request.setAttribute("user", u);
		
		Administrator a = new Administrator();
		a.setImie(request.getParameter("imie"));	
		a.setNazwisko(request.getParameter("nazwisko"));
		a.setPesel(request.getParameter("pesel"));
		a.setTelefon(request.getParameter("telefon"));
		a.setDataDodania(LocalDateTime.now());
		a.setIdAdministrator(uz.getIdAdministrator());
		request.setAttribute("admin", a);
		
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
		if(a.getImie() == "" || !kw.walidujNazwy(a.getImie())) {
			walidacjaOK = false;
			request.setAttribute("imie", "Pole Imię jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(a.getNazwisko() == "" || !kw.walidujNazwy(a.getNazwisko())) {
			walidacjaOK = false;
			request.setAttribute("nazwisko", "Pole Nazwisko jest puste bądz ilość cyfr jest różna od 9");				
		}
		if(a.getPesel() == "" || !kw.walidujPesel(a.getPesel())) {
			walidacjaOK = false;
			request.setAttribute("pesel", "Pole PESEL jest puste bądz ilość cyfr jest różna od 11");				
		}
		
		if(a.getTelefon() == "" || !kw.walidujNrTelefonu(a.getTelefon())) {
			walidacjaOK = false;
			request.setAttribute("telefon", "Pole Nr Telefonu jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		
		if(walidacjaOK) {
			u.setHaslo(kw.hasloZakodowane(u.getHaslo()));
			ObslugaBD bd = new ObslugaBD();
			WynikiDodajUzytkownika wynik = bd.dodajAdministratora(u, a);
			if(wynik.isDodano()) {
				request.setAttribute("komunikat", "Dodano nowego administratora.");
				request.setAttribute("user", null);
				request.setAttribute("admin", null);
				request.setAttribute("haslo2", "");
				request.setAttribute("haslo2wartosc", "");
			} else {
				request.setAttribute("komunikat", "NIE udało się dodać nowego administratora. Użytkownik, którego próbowano wprowadzić jest już zapisany w bazie danych!");
				request.setAttribute("wynik", wynik);
			}
		}	
		
		doGet(request, response);
	}

}
