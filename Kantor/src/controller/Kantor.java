package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.KoszykDaneWalut;
import model.dao.Kursy;
import model.dao.ObslugaBD;

@WebServlet(urlPatterns = { "/kantor", "/index" })
public class Kantor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObslugaBD bd = new ObslugaBD();
		Kursy kursy = new Kursy();
		KoszykDaneWalut kdw = bd.daneBidAskWalut();
		ServletContext sc = request.getServletContext();
		sc.setAttribute("kurs", kursy);
		sc.setAttribute("mnoznik", kdw);
		response.setContentType("text/html;charset=UTF-8");
		request.getRequestDispatcher("jsp/logowanie.jsp").include(request, response);
		request.getRequestDispatcher("jsp/index.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
