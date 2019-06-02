package pl.shopapp.web;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.shopapp.beans.BasketBeanLocal;
import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;


/**
 * Servlet implementation class ProductByCategory
 */
@WebServlet("/ProductByCategory")
public class ProductByCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProductBeanLocal pbl;
	private BasketBeanLocal bbl;
	private double total = 0.0;
	
//	for tests
	public ProductByCategory() {
		super();
	}
	
//	for tests
	public ProductByCategory(ProductBeanLocal pbl, BasketBeanLocal bbl) {
		super();
		this.pbl = pbl;
		this.bbl = bbl;
	}
	
//	for tests
	public double getTotal() {
		return total;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
		if(sd != null) {
			total = 0.0;
			for(int i=0; i<sd.getBasketBeanLocal().getBasketData().size(); i++){
				total += sd.getBasketBeanLocal().getBasketData().get(i).getPrice() * sd.getBasketBeanLocal().getBasketData().get(i).getQuantity(); 
			}
			request.setAttribute("total", total);			
		}
		request.getRequestDispatcher("/jsp/productByCategory.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Category> listCategory = pbl.listCategory();

		if(request.getParameter(String.valueOf(listCategory.get(1).getId())) != null) {
			request.setAttribute("curentCategory", listCategory.get(1));
			request.setAttribute("productList", pbl.listProductByCategory(listCategory.get(1).getId()));
		}
		if(request.getParameter(String.valueOf(listCategory.get(2).getId())) != null) {
			request.setAttribute("curentCategory", listCategory.get(2));
			request.setAttribute("productList", pbl.listProductByCategory(listCategory.get(2).getId()));
		}
		if(request.getParameter(String.valueOf(listCategory.get(3).getId())) != null) {
			request.setAttribute("curentCategory", listCategory.get(3));
			request.setAttribute("productList", pbl.listProductByCategory(listCategory.get(3).getId()));
		}
		if(request.getParameter(String.valueOf(listCategory.get(4).getId())) != null) {
			request.setAttribute("curentCategory", listCategory.get(4));
			request.setAttribute("productList", pbl.listProductByCategory(listCategory.get(4).getId()));
		}
		if(request.getParameter(String.valueOf(listCategory.get(5).getId())) != null) {
			request.setAttribute("curentCategory", listCategory.get(5));
			request.setAttribute("productList", pbl.listProductByCategory(listCategory.get(5).getId()));
		}
		if(request.getParameter(String.valueOf(listCategory.get(6).getId())) != null) {
			request.setAttribute("curentCategory", listCategory.get(6));
			request.setAttribute("productList", pbl.listProductByCategory(listCategory.get(6).getId()));
		}
		if(request.getParameter(String.valueOf(listCategory.get(7).getId())) != null) {
			request.setAttribute("curentCategory", listCategory.get(7));
			request.setAttribute("productList", pbl.listProductByCategory(listCategory.get(7).getId()));
		}
		if(request.getParameter(String.valueOf(listCategory.get(8).getId())) != null) {
			request.setAttribute("curentCategory", listCategory.get(8));
			request.setAttribute("productList", pbl.listProductByCategory(listCategory.get(8).getId()));
		}
		
//		allows add product to basket
		if(request.getParameter("buttonToBasketFromCategory") != null) {
			Product product = pbl.getProduct(Integer.valueOf(request.getParameter("buttonToBasketFromCategory")));
			SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");			
			if(sd != null) {
				bbl = sd.getBasketBeanLocal();
				String quantity = "quantity"+request.getParameter("buttonToBasketFromCategory");
				bbl.addBasketRow(product.getId(), Integer.valueOf(request.getParameter(quantity)), product.getName(), product.getPrice(), bbl.getBasketData());
			} else
				request.setAttribute("message", "Aby dodać produkt do koszyka należy się zalogować!");	
		}
		
//		allows delete product from basket
		if (request.getParameter("buttonDeleteRowBasket") != null) {
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
