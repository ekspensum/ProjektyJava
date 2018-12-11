package pl.shopapp.web;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;

/**
 * Servlet implementation class OperatorPanel
 */
@WebServlet("/OperatorPanel")
@MultipartConfig
public class OperatorPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ProductBeanLocal pbl;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
		request.setAttribute("SessionData", sd);
		List<Category> listCat = pbl.listCategory();
		request.setAttribute("listCat", listCat);
		request.getRequestDispatcher("jsp/operatorPanel.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Integer> helperListCat = new ArrayList<>();
		Product p = new Product();

		if (request.getParameter("buttonAddProduct") != null) {
			if(validationAndSetup(request, p, helperListCat)) {
				SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
				if (pbl.addProduct(p, helperListCat, sd.getIdUser())) {
					request.setAttribute("message", "Produkt został dodany!");
					request.setAttribute("clear", "yes");
				} else
					request.setAttribute("message", "Nie udało się dodać produktu!");
			}
		}
		
		if (request.getParameter("buttonAddCategory") != null) {
			boolean validOK = true;
			Validation valid = new Validation();
			if (request.getParameter("categoryName").equals("")
					|| !valid.nameValidation(request.getParameter("categoryName"))) {
				validOK = false;
				request.setAttribute("message", "Pole nazwa produktu jest puste lub zawiera niepoprawne znaki!");
			}
			if(validOK) {
				Category cat = new Category();
				cat.setName(request.getParameter("categoryName"));
				cat.setDateTime(LocalDateTime.now());
				Part part = request.getPart("imageCategory");
				InputStream in = part.getInputStream();
				try {
					byte[] buffer = new byte[(int) part.getSize()];
					in.read(buffer);
					cat.setCategoryImage(buffer);
					SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
					if (pbl.addCategory(cat, sd.getIdUser())) {
						request.setAttribute("message", "Kategoria została dodana!");
						request.setAttribute("RequestAttribute", null);
					} 
				} catch (EJBException e) {
					request.setAttribute("message", "Rozmiar pliku jest zbyt duży!");
					e.printStackTrace();
				}
			}
		}
		
		if(request.getParameter("searchPruductButton") != null) {
			if(!request.getParameter("searchPruductByName").equals("")) {
				List<Product> products = pbl.findProduct(request.getParameter("searchPruductByName"));
				request.setAttribute("Products", products);				
			} else
				request.setAttribute("message", "Proszę wprowadzić fragment nazwy wyszukiwanego produktu!");
		}
		
		if(request.getParameter("editButton") != null) {
			if(request.getParameter("idProduct") != null) {
				if(request.getParameterValues("idProduct").length == 1) {
					Product pr = pbl.getProduct(Integer.valueOf(request.getParameter("idProduct")));
					List<Category> catList = pbl.getProductCategories(pr);
					request.setAttribute("productToEdit", pr);
					request.setAttribute("categoryToEdit", catList);					
				} else
					request.setAttribute("message", "Proszę zaznaczyć jeden produkt!");
			} else
				request.setAttribute("message", "Proszę zaznaczyć jeden produkt!");		
			List<Product> products = pbl.findProduct(request.getParameter("searchPruductByName"));
			request.setAttribute("Products", products);
		}
		
		if(request.getParameter("saveButton") != null) {
			if(validationAndSetup(request, p, helperListCat)) {
				
				
				request.setAttribute("clear", "yes");
			}

		}

		doGet(request, response);
	}

	private boolean validationAndSetup(HttpServletRequest request, Product p, List<Integer> helperListCat) throws IOException, ServletException {
		// TODO validation and setup objects for use in addProduct and editProduct area
		boolean validOK = true;
		Validation valid = new Validation();
		if (request.getParameter("productName").equals("")
				|| !valid.nameValidation(request.getParameter("productName"))) {
			validOK = false;
			request.setAttribute("message", "Pole nazwa produktu jest puste lub zawiera niepoprawne znaki!");
		}
		if (request.getParameter("productDescription").equals("")
				|| !valid.nameValidation(request.getParameter("productDescription"))) {
			validOK = false;
			request.setAttribute("message", "Pole nazwa produktu jest puste lub zawiera niepoprawne znaki!");
		}
		if (request.getParameter("productPrice").equals("")
				|| !valid.numberValidation(request.getParameter("productPrice"))) {
			validOK = false;
			request.setAttribute("message", "Pole cena produktu jest puste lub zawiera niepoprawne znaki!");
		}
		if (request.getParameter("productUnitsInStock").equals("")
				|| !valid.numberValidation(request.getParameter("productUnitsInStock"))) {
			validOK = false;
			request.setAttribute("message", "Pole ilość produktu jest puste lub zawiera niepoprawne znaki!");
		}
		if(request.getParameter("category1").equals("23")) {
			validOK = false;
			request.setAttribute("message", "Należy wybrać conajmniej kategorię 1!");
		}
		if(request.getParameter("saveButton") == null)
			if(request.getPart("imageProduct").getSize() == 0) {
				validOK = false;
				request.setAttribute("message", "Proszę wybrać zdjęcie produktu!");
			}
		
		if (validOK) {
			p.setName(request.getParameter("productName"));
			p.setDescription(request.getParameter("productDescription"));
			p.setPrice(Double.valueOf(request.getParameter("productPrice")));
			p.setUnitsInStock(Integer.valueOf(request.getParameter("productUnitsInStock")));

			byte[] buffer = null;
			try {
				Part filePart = request.getPart("imageProduct");
				buffer = new byte[(int) filePart.getSize()];
				InputStream is = filePart.getInputStream();
				is.read(buffer);
				is.close();
				p.setProductImage(buffer);
			} catch (EJBException e) {
				request.setAttribute("message", "Rozmiar pliku jest zbyt duży!");
				e.printStackTrace();
			}
			helperListCat.add(Integer.valueOf(request.getParameter("category1")));
			if(!request.getParameter("category2").equals("23"))
				helperListCat.add(Integer.valueOf(request.getParameter("category2")));
			p.setDateTime(LocalDateTime.now());

			return true;
		}
			
		return false;
	}
}
