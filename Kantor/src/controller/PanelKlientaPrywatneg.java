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
		dt.wykonajTransakcje(dt, request, response);
		
		doGet(request, response);
	}

}
