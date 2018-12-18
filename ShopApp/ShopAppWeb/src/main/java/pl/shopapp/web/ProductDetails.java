package pl.shopapp.web;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.shopapp.beans.BasketBeanLocal;
import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;

/**
 * Servlet implementation class ProductDetail
 */
@WebServlet("/ProductDetails")
public class ProductDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private ProductBeanLocal pbl;

	private BasketBeanLocal bbl;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
		if(sd != null) {
			double total = 0.0;
			for(int i=0; i<sd.getBasketBeanLocal().getBasketData().size(); i++){
				total += sd.getBasketBeanLocal().getBasketData().get(i).getPrice() * sd.getBasketBeanLocal().getBasketData().get(i).getQuantity(); 
			}
			request.setAttribute("total", total);			
		}
		request.getRequestDispatcher("/jsp/productDetails.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if (request.getParameter("buttonToProductDetailsFromCategory") != null) {
			request.setAttribute("pd", pbl.getProduct(Integer.valueOf(request.getParameter("buttonToProductDetailsFromCategory"))));
		}
		if (request.getParameter("buttonToProductDetailsFromMain") != null) {
			request.setAttribute("pd", pbl.getProduct(Integer.valueOf(request.getParameter("buttonToProductDetailsFromMain"))));
		}
		if (request.getParameter("buttonToBasketFromDetails") != null) {
//			allows holds the same searching category result
			request.setAttribute("pd", pbl.getProduct(Integer.valueOf(request.getParameter("buttonToBasketFromDetails"))));

			SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
			if (sd != null) {
				bbl = sd.getBasketBeanLocal();
				String quantity = "quantity" + request.getParameter("buttonToBasketFromDetails");
				bbl.addBasketRow(pbl.getProduct(Integer.valueOf(request.getParameter("buttonToBasketFromDetails"))).getId(), Integer.valueOf(request.getParameter(quantity)), pbl.getProduct(Integer.valueOf(request.getParameter("buttonToBasketFromDetails"))).getName(), pbl.getProduct(Integer.valueOf(request.getParameter("buttonToBasketFromDetails"))).getPrice(),	bbl.getBasketData());
			} else
				request.setAttribute("message", "Aby dodać produkt do koszyka należy się zalogować!");
		}

		if (request.getParameter("buttonDeleteRowBasket") != null) {
//			allows holds the same searching category result
			request.setAttribute("pd", pbl.getProduct(Integer.valueOf(request.getParameter("buttonDeleteRowBasket"))));

			SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
			bbl = sd.getBasketBeanLocal();
			String[] deletedId = request.getParameterValues("chbxDeleteRow");
			if (deletedId != null) {
				for (int i = 0; i < deletedId.length; i++) {
					for (int j = 0; j < bbl.getBasketData().size(); j++)
						if (bbl.getBasketData().get(j).getProductId() == Integer.valueOf(deletedId[i])) {
							bbl.getBasketData().remove(j);
							break;
						}
				}
			} else
				request.setAttribute("message", "Proszę zaznaczyć co njmniej jeden produkt do usunięcia!");
		}

		doGet(request, response);
	}

}
