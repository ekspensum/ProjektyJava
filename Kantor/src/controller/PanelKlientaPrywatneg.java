package controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.DaneTransakcji;
import model.dao.ObslugaBD;
import model.dao.Operacja;
import model.dao.OperacjaRachCHF;
import model.dao.OperacjaRachEUR;
import model.dao.OperacjaRachUSD;
import model.dao.StanyRachunkow;
import model.dao.UserZalogowany;



@WebServlet("/panelKlientaPrywatnego")
public class PanelKlientaPrywatneg extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserZalogowany uz = (UserZalogowany) request.getSession().getAttribute("userZalogowany");
		ObslugaBD bd = new ObslugaBD();
		StanyRachunkow sr = bd.odcztRachKlientPrywatny(uz.getIdUzytkownik());
		request.getServletContext().setAttribute("rachunkiKP", sr);		
		
		request.getRequestDispatcher("jsp/panelKlientaPrywatnego.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DaneTransakcji dt = (DaneTransakcji) request.getServletContext().getAttribute("transakcjaKP");
		if (dt != null) {
			Operacja[] op = new Operacja[3];
			op[0] = new OperacjaRachUSD();
			op[1] = new OperacjaRachEUR();
			op[2] = new OperacjaRachCHF();

			if ("Sprzedaj".equals(dt.getRodzaj())) {
				if(op[dt.getIndex()].sprzedaj(dt))
					request.setAttribute("komunikatSprzedajUSD", "Transakcja sprzedaży zakończona powodzeniem.");
				else
					request.setAttribute("komunikatSprzedajUSD", "Transakcja sprzedaży nie powiodła się.");
			}
			else if ("Kup".equals(dt.getRodzaj())) {
				if(op[dt.getIndex()].kup(dt))
					request.setAttribute("komunikatKupUSD", "Transakcja kupna zakończona powodzeniem.");
				else
					request.setAttribute("komunikatKupUSD", "Transakcja kupna nie powiodła się.");	
			}
		} else 
			request.setAttribute("komunikat", "Transakcja nie powiodła się.");	
		doGet(request, response);
	}

}
