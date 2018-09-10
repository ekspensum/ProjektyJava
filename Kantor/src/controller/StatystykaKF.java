package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ObslugaBD;
import model.dao.Statystyka;
import model.dao.UserZalogowany;

/**
 * Servlet implementation class StatystykaKF
 */
@WebServlet("/statystykaKF")
public class StatystykaKF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/statystykaKF.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserZalogowany uz = (UserZalogowany) request.getSession().getAttribute("userZalogowany");
		ObslugaBD bd = new ObslugaBD();
		List<Statystyka> listaStat = bd.wyszukajTransakcjeKF(uz.getIdUzytkownik() , request.getParameter("dataOd"), request.getParameter("dataDo"), request.getParameter("opcje"));
		request.setAttribute("stat", listaStat);
		doGet(request, response);
	}

}
