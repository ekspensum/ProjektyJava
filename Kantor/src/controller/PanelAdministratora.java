package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/panelAdministratora")
public class PanelAdministratora extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		request.getRequestDispatcher("jsp/panelAdministratora.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String wybor = request.getParameter("opcje");

		switch (wybor) {
		case "admin":
			response.sendRedirect("/Kantor/dodajAdministratora");				
			break;
		case "klientFirmowy":
			response.sendRedirect("/Kantor/dodajKlientaFirmowego");
			break;
		case "klientPrywatny":
			response.sendRedirect("/Kantor/dodajKlientaPrywatnego");
			break;
		case "operator":
			response.sendRedirect("/Kantor/dodajOperatora");
			break;
		default:
			break;
		}
		
			
//		doGet(request, response);
	}

}
