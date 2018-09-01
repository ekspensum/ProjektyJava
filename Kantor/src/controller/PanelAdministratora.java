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
			System.out.println("Admin");				
			break;
		case "klientFirmowy":
			response.sendRedirect("http://localhost:8080/Kantor/dodajKlientaFirmowego");
			break;
		case "klientPrywatny":
			System.out.println("klient prywatny");
			break;
		case "operator":
			System.out.println("operator");
			break;
		default:
			break;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
//		doGet(request, response);
	}

}
