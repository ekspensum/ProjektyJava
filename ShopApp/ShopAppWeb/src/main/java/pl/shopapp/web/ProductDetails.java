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
import pl.shopapp.entites.Product;

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
		request.getRequestDispatcher("/jsp/productDetails.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if (request.getParameter("buttonToProductDetails") != null) {
			Product p = pbl.getProduct(Integer.valueOf(request.getParameter("buttonToProductDetails")));
			request.setAttribute("pd", p);
		}
		if (request.getParameter("buttonToBasketFromDetails") != null) {
//			allows holds the same searching category result
			Product p = pbl.getProduct(Integer.valueOf(request.getParameter("buttonToBasketFromDetails")));
			request.setAttribute("pd", p);

			SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
			if (sd != null) {
				bbl = sd.getBasketBeanLocal();
				String quantity = "quantity" + request.getParameter("buttonToBasketFromDetails");
				bbl.addBasketRow(p.getId(), Integer.valueOf(request.getParameter(quantity)), p.getName(), p.getPrice(),	bbl.getBasketData());
			} else
				request.setAttribute("message", "Aby dodać produkt do koszyka należy się zalogować!");
		}

		if (request.getParameter("buttonDeleteRowBasket") != null) {
//			allows holds the same searching category result
			Product p = pbl.getProduct(Integer.valueOf(request.getParameter("buttonDeleteRowBasket")));
			request.setAttribute("pd", p);

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
