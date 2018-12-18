package pl.shopapp.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.shopapp.beans.BasketBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.TransactionBeanLocal;

/**
 * Servlet implementation class Transaction
 */
@WebServlet("/Transaction")
public class Transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BasketBeanLocal bbl;
	@EJB
	private TransactionBeanLocal tbl;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
		if(sd != null) {
			double total = 0.0;
			for(int i=0; i<sd.getBasketBeanLocal().getBasketData().size(); i++){
				total += sd.getBasketBeanLocal().getBasketData().get(i).getPrice() * sd.getBasketBeanLocal().getBasketData().get(i).getQuantity(); 
			}
			request.setAttribute("total", total);			
		}
	request.getRequestDispatcher("/jsp/transaction.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("buttonBuyNow") != null) {
			SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
			bbl = sd.getBasketBeanLocal();
			if(tbl.newTransaction(sd.getIdUser(), bbl.getBasketData())) {
				request.setAttribute("message", "Transakcja zakończona powodzeniem!");
			} else
				request.setAttribute("message", "Nie udało się zrealizować transakcji!");		
		}
		
		doGet(request, response);
	}

}