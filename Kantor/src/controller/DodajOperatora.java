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
import model.encje.Operator;
import model.encje.Uzytkownik;

/**
 * Servlet implementation class DodajOperatora
 */
@WebServlet("/dodajOperatora")
public class DodajOperatora extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("jsp/dodajOperatora.jsp").forward(request, response);
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
		u.setIdRola(2);
		request.setAttribute("user", u);
		
		Operator o = new Operator();
		o.setImie(request.getParameter("imie"));	
		o.setNazwisko(request.getParameter("nazwisko"));
		o.setPesel(request.getParameter("pesel"));
		o.setTelefon(request.getParameter("telefon"));
		o.setDataDodania(LocalDateTime.now());
		o.setIdAdministrator(uz.getIdAdministrator());
		request.setAttribute("operator", o);
		
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
		if(o.getImie() == "" || !kw.walidujNazwy(o.getImie())) {
			walidacjaOK = false;
			request.setAttribute("imie", "Pole Imię jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(o.getNazwisko() == "" || !kw.walidujNazwy(o.getNazwisko())) {
			walidacjaOK = false;
			request.setAttribute("nazwisko", "Pole Nazwisko jest puste bądz ilość cyfr jest różna od 9");				
		}
		if(o.getPesel() == "" || !kw.walidujPesel(o.getPesel())) {
			walidacjaOK = false;
			request.setAttribute("pesel", "Pole PESEL jest puste bądz ilość cyfr jest różna od 11");				
		}
		
		if(o.getTelefon() == "" || !kw.walidujNrTelefonu(o.getTelefon())) {
			walidacjaOK = false;
			request.setAttribute("telefon", "Pole Nr Telefonu jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		
		if(walidacjaOK) {
			u.setHaslo(kw.hasloZakodowane(u.getHaslo()));
			ObslugaBD bd = new ObslugaBD();
			WynikiDodajUzytkownika wynik = bd.dodajOperatora(u, o);
			if(wynik.isDodano()) {
				request.setAttribute("komunikat", "Dodano nowego operatora.");
				request.setAttribute("user", null);
				request.setAttribute("operator", null);
				request.setAttribute("haslo2", "");
				request.setAttribute("haslo2wartosc", "");
			} else {
				request.setAttribute("komunikat", "NIE udało się dodać nowego Operatora. Użytkownik, którego próbowano wprowadzić jest już zapisany w bazie danych!");
				request.setAttribute("wynik", wynik);
			}
		}	
			
		doGet(request, response);
	}
}
