package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.ObslugaBD;
import model.dao.UserZalogowany;

@WebServlet("/logowanie")
public class Logowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/kantor").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			ObslugaBD bd = new ObslugaBD();
			WalidacjaKodowanie wk = new WalidacjaKodowanie();
			UserZalogowany uz = bd.logowanie(request.getParameter("login"), wk.hasloZakodowane(request.getParameter("haslo")));
			HttpSession sesja = request.getSession();
			sesja.setAttribute("userZalogowany", uz);
			sesja.setMaxInactiveInterval(2000);
			if(uz == null) {
				request.setAttribute("komunikat", "Nieprawidłowe dane logowania");
				doGet(request, response);				
			}
			else if(uz.getIdRola() == 1)
				response.sendRedirect("http://localhost:8080/Kantor/panelAdministratora");
			else if(uz.getIdRola() == 2)
				response.sendRedirect("http://localhost:8080/Kantor/panelOperatora");
			else if(uz.getIdRola() == 3)
				response.sendRedirect("http://localhost:8080/Kantor/panelKlientaFirmowego");
			else if(uz.getIdRola() == 4)
				response.sendRedirect("http://localhost:8080/Kantor/panelKlientaPrywatnego");
	}

}
