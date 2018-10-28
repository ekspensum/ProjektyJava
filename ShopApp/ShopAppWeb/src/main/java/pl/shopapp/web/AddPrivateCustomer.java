package pl.shopapp.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.shopapp.beans.CustomerLocal;
import pl.shopapp.dao.PrivateCustomer;

/**
 * Servlet implementation class AddPrivateCustomer
 */
@WebServlet("/AddPrivateCustomer")
public class AddPrivateCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	CustomerLocal cl;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("jsp/addPrivateCustomer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrivateCustomer pc = new PrivateCustomer();
		pc.setFirstName(request.getParameter("firstName"));
		pc.setLastName(request.getParameter("lastName"));
		pc.setPesel(request.getParameter("pesel"));
		pc.setZipCode(request.getParameter("zipCode"));
		pc.setCity(request.getParameter("city"));
		pc.setStreet(request.getParameter("street"));
		pc.setStreetNo(request.getParameter("streetNo"));
		pc.setUnitNo(request.getParameter("unitNo"));
		cl.addPrivateCustomer(pc);
			
		doGet(request, response);
	}

}
