package controller;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.UserZalogowany;
import model.encje.KlientFirmowy;
import model.encje.RachunekPLN;
import model.encje.Uzytkownik;

/**
 * Servlet implementation class DodajKlientaFirmowego
 */
@WebServlet("/dodajKlientaFirmowego")
public class DodajKlientaFirmowego extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("jsp/dodajKlientaFirmowego.jsp").forward(request, response);
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
		u.setIdRola(3);
		request.setAttribute("user", u);
		
		KlientFirmowy kf = new KlientFirmowy();
		kf.setNazwa(request.getParameter("nazwa"));	
		if(request.getParameter("regon") == "")
			kf.setRegon(null);
		else 
			kf.setRegon(Integer.valueOf(request.getParameter("regon")));
		if(request.getParameter("nip") == "")
			kf.setNip(null);
		else
			kf.setNip(BigInteger.valueOf(Integer.toUnsignedLong(Integer.parseInt(request.getParameter("nip")))));
		kf.setKod(request.getParameter("kod"));
		kf.setMiasto(request.getParameter("miasto"));
		kf.setUlica(request.getParameter("ulica"));
		kf.setNrDomu(request.getParameter("nrDomu"));
		kf.setNrLokalu(request.getParameter("nrLokalu"));
		kf.setImiePracownika(request.getParameter("imie"));
		kf.setNazwiskoPracownika(request.getParameter("nazwisko"));
		kf.setTelefonPracownika(request.getParameter("telefon"));
		kf.setDataDodania(LocalDateTime.now());
		kf.setIdAdministrator(uz.getIdAdministrator());
		request.setAttribute("firma", kf);
		
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
		if(kf.getNazwa() == "" || !kw.walidujNazwy(kf.getNazwa())) {
			walidacjaOK = false;
			request.setAttribute("nazwa", "Pole Nazwa firmy jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(kf.getRegon() == null || !kw.walidujRegon(kf.getRegon())) {
			walidacjaOK = false;
			request.setAttribute("regon", "Pole REGON jest puste bądz ilość cyfr jest różna od 9");				
		}
		if(kf.getNip() == null || !kw.walidujNip(kf.getNip())) {
			walidacjaOK = false;
			request.setAttribute("nip", "Pole NIP jest puste bądz ilość cyfr jest różna od 10");				
		}
		if(kf.getKod() == "" || !kw.walidujKodPocztowy(kf.getKod())) {
			walidacjaOK = false;
			request.setAttribute("kod", "Pole Kod Pocztowy jest puste bądz ilość znaków lub ich forma są niepoprawne (00-000)");				
		}
		if(kf.getMiasto() == "" || !kw.walidujNazwy(kf.getMiasto())) {
			walidacjaOK = false;
			request.setAttribute("miasto", "Pole Miasto jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(kf.getUlica() == "" || !kw.walidujNazwy(kf.getUlica())) {
			walidacjaOK = false;
			request.setAttribute("ulica", "Pole Ulica jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(kf.getNrDomu() == "" || !kw.walidujNazwy(kf.getNrDomu())) {
			walidacjaOK = false;
			request.setAttribute("nrDomu", "Pole Nr Domu jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		
		if(kf.getNrLokalu() != "") {
			if(!kw.walidujNazwy(kf.getNrLokalu())) {
				walidacjaOK = false;
				request.setAttribute("nrLokalu", "Pole Nr Lokalu jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
			}					
		} 
		
		if(kf.getImiePracownika() == "" || !kw.walidujNazwy(kf.getImiePracownika())) {
			walidacjaOK = false;
			request.setAttribute("imiePracownika", "Pole Imię Przedstawiciela jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(kf.getNazwiskoPracownika() == "" || !kw.walidujNazwy(kf.getNazwiskoPracownika())) {
			walidacjaOK = false;
			request.setAttribute("nazwiskoPracownika", "Pole Nazwisko Przedstawiciela jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(kf.getTelefonPracownika() == "" || !kw.walidujNrTelefonu(kf.getTelefonPracownika())) {
			walidacjaOK = false;
			request.setAttribute("telefonPracownika", "Pole Nr Telefonu Przedstawiciela jest puste bądz ilość znaków lub ich forma są niepoprawne (viede Polityka Bezpieczeństwa)");				
		}
		if(pln.getNrRachunku() == "" || !kw.walidujNrRachunku(pln.getNrRachunku())) {
			walidacjaOK = false;
			request.setAttribute("nrRachunku", "Pole Nr Rachunku jest puste bądz ilość znaków lub ich forma są niepoprawne (26 cyfr)");				
		}
		
		if(walidacjaOK) {
			u.setHaslo(kw.hasloZakodowane(u.getHaslo()));
			System.out.println("Walidacja OK");
			System.out.println(u.getHaslo());
		}
	 
		doGet(request, response);
	}

}
