package pl.shopapp.web;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javax.transaction.SystemException;

import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.TransactionBeanLocal;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.Product;

/**
 * Servlet implementation class OperatorPanel
 */
@WebServlet("/OperatorPanel")
@MultipartConfig
public class OperatorPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private ProductBeanLocal pbl;
	@EJB
	private TransactionBeanLocal tbl;
	private int productIdToEdit;

//	for tests
	public OperatorPanel() {
		super();
	}
//	for tests
	public OperatorPanel(ProductBeanLocal pbl, TransactionBeanLocal tbl) {
		super();
		this.pbl = pbl;
		this.tbl = tbl;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
		request.setAttribute("SessionData", sd);
		request.setAttribute("listCat", pbl.listCategory());
		request.getRequestDispatcher("jsp/operatorPanel.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String productName = null, productDescription = null, categoryName;
		double productPrice = 0;
		int productUnitsInStock = 0;
		byte[] buffer = null;
		List<Integer> helperListCat = new ArrayList<>();

		if (request.getParameter("buttonAddProduct") != null) {
			if(validation(request)) {
				SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
				
				productName = request.getParameter("productName");
				productDescription = request.getParameter("productDescription");
				productPrice = Double.valueOf(request.getParameter("productPrice"));
				productUnitsInStock = Integer.valueOf(request.getParameter("productUnitsInStock"));
				
				try {
					Part filePart = request.getPart("imageProduct");
					buffer = new byte[(int) filePart.getSize()];
					InputStream is = filePart.getInputStream();
					is.read(buffer);
					is.close();
				} catch (EJBException e) {
					request.setAttribute("message", "Rozmiar pliku jest zbyt duży!");
					e.printStackTrace();
				}
				
				helperListCat.add(Integer.valueOf(request.getParameter("category1")));
				helperListCat.add(Integer.valueOf(request.getParameter("category2")));
				
				try {
					if (pbl.addProduct(productName, productDescription, productPrice, productUnitsInStock, buffer, helperListCat, sd.getIdUser())) {
						request.setAttribute("message", "Produkt został dodany!");
						request.setAttribute("clear", "yes");
						helperListCat.clear();
					} else
						request.setAttribute("message", "Nie udało się dodać produktu!");
				} catch (IllegalStateException | SecurityException | SystemException e) {
					e.printStackTrace();
				}
			}
		} else	if (request.getParameter("buttonAddCategory") != null) {
			boolean validOK = true;
			Validation valid = new Validation();
			if (request.getParameter("categoryName").equals("")
					|| !valid.nameValidation(request.getParameter("categoryName"))) {
				validOK = false;
				request.setAttribute("message", "Pole nazwa produktu jest puste lub zawiera niepoprawne znaki!");
			}
			if(validOK) {
				categoryName = request.getParameter("categoryName");
				Part part = request.getPart("imageCategory");
				InputStream in = part.getInputStream();
				try {
					buffer = new byte[(int) part.getSize()];
					in.read(buffer);
					SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
					if (pbl.addCategory(categoryName, buffer, sd.getIdUser())) {
						request.setAttribute("message", "Kategoria została dodana!");
						request.setAttribute("RequestAttribute", null);
					} 
				} catch (EJBException e) {
					request.setAttribute("message", "Rozmiar pliku jest zbyt duży!");
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		} else	if(request.getParameter("searchPruductButton") != null) {
			if(!request.getParameter("searchPruductByName").equals("")) {
				request.setAttribute("Products", pbl.findProduct(request.getParameter("searchPruductByName")));	
				request.getServletContext().setAttribute("button", "searchPruductButton");
			} else
				request.setAttribute("message", "Proszę wprowadzić fragment nazwy wyszukiwanego produktu!");
			
		} else	if(request.getParameter("searchQuantityButton") != null) {
				request.setAttribute("Products", pbl.findProduct(Integer.valueOf(request.getParameter("searchPruductByQuantity"))));	
				request.getServletContext().setAttribute("button", "searchQuantityButton");
				
		} else	if(request.getParameter("editButton") != null) {
			if(request.getParameter("idProduct") != null) {
				if(request.getParameterValues("idProduct").length == 1) {
					Product product = pbl.getProduct(Integer.valueOf(request.getParameter("idProduct")));
					request.setAttribute("productToEdit", product);
					request.setAttribute("categoryToEdit", pbl.getProductCategories(product));
					productIdToEdit = product.getId();
				} else
					request.setAttribute("message", "Proszę zaznaczyć jeden produkt!");
			} else
				request.setAttribute("message", "Proszę zaznaczyć jeden produkt!");	
			
			if(request.getServletContext().getAttribute("button").equals("searchPruductButton")) {
				request.setAttribute("Products", pbl.findProduct(request.getParameter("searchPruductByName")));
				request.getServletContext().setAttribute("searchPruductByName", request.getParameter("searchPruductByName"));
			} 
			if(request.getServletContext().getAttribute("button").equals("searchQuantityButton")) {
				request.setAttribute("Products", pbl.findProduct(Integer.valueOf(request.getParameter("searchPruductByQuantity"))));
				request.getServletContext().setAttribute("searchPruductByQuantity", request.getParameter("searchPruductByQuantity"));
			}
		} else	if(request.getParameter("saveButton") != null) {
			if(validation(request)) {
				SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
				
				productName = request.getParameter("productName");
				productDescription = request.getParameter("productDescription");
				productPrice = Double.valueOf(request.getParameter("productPrice"));
				productUnitsInStock = Integer.valueOf(request.getParameter("productUnitsInStock"));

				try {
					Part filePart = request.getPart("imageProduct");
					buffer = new byte[(int) filePart.getSize()];
					InputStream is = filePart.getInputStream();
					is.read(buffer);
					is.close();
				} catch (EJBException e) {
					request.setAttribute("message", "Rozmiar pliku jest zbyt duży!");
					e.printStackTrace();
				}
				
				helperListCat.add(Integer.valueOf(request.getParameter("category1")));
				helperListCat.add(Integer.valueOf(request.getParameter("category2")));
				
				try {
					if(pbl.updateProduct(productName, productDescription, productPrice, productUnitsInStock, buffer, productIdToEdit, (int)request.getPart("imageProduct").getSize(), sd.getIdUser(), helperListCat)) {
						request.setAttribute("clear", "yes");	
						request.setAttribute("message", "Aktualizacja danych produktu zakończona powodzeniem!");
						productIdToEdit = 0;
						helperListCat.clear();
					} else
						request.setAttribute("message", "Nie udało się zaktualizować produktu!");
				} catch (IllegalStateException | SecurityException | SystemException e) {
					e.printStackTrace();
				}
			} else {
				if(request.getServletContext().getAttribute("button").equals("searchPruductButton")) {
					request.setAttribute("Products", pbl.findProduct((String)request.getServletContext().getAttribute("searchPruductByName")));
					request.setAttribute("productToEdit", pbl.getProduct(productIdToEdit));
				} 
				if(request.getServletContext().getAttribute("button").equals("searchQuantityButton")) {
					request.setAttribute("Products", pbl.findProduct(Integer.valueOf((String) request.getServletContext().getAttribute("searchPruductByQuantity"))));
					request.setAttribute("productToEdit", pbl.getProduct(productIdToEdit));
				}
			}
		} else	if(request.getParameter("buttonSearchNoExecOrder") != null) {
			searchNoExecOrder(request);
			
		} else	if(request.getParameter("buttonExecOrder") != null) {
			if(request.getParameter("buttonExecOrder").equals("yes")) {
				if(request.getParameterValues("idTransaction") != null) {
						SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
						if(tbl.execOrder(request.getParameterValues("idTransaction"), sd.getIdUser())) {
							request.setAttribute("message", "Zaznaczone transacje zapisano do bazy jako zrealizowane!<br>"
									+ "Do klienta został wysłany e-mail z powiadomieniem o wysłaniu towaru.");
							searchNoExecOrder(request);
						} else
							request.setAttribute("message", "Nie udało się oznaczyć jako zrealizowane zaznaczonych transakcji!");			
				} else {
					searchNoExecOrder(request);
					request.setAttribute("message", "Proszę zaznaczyć co najmniej 1 wiersz do realizacji zamówienia!");	
				}							
			}			
		}

		
		doGet(request, response);
	}

	private boolean validation(HttpServletRequest request) throws IOException, ServletException {
		// TODO validation and setup objects for use in addProduct and editProduct area
		boolean validOK = true;
		Validation valid = new Validation();
		if (request.getParameter("productName").equals("")
				|| !valid.nameValidation(request.getParameter("productName"))) {
			validOK = false;
			request.setAttribute("message", "Pole nazwa produktu jest puste lub zawiera niepoprawne znaki, bądz ilość znaków przekrasza 100!");
		}
		if (request.getParameter("productDescription").equals("")
				|| !valid.describeValidation(request.getParameter("productDescription"))) {
			validOK = false;
			request.setAttribute("message", "Pole opis produktu jest puste lub zawiera niepoprawne znaki, bądz ilość znaków przekrasza 1000!");
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
		if(request.getParameter("category1").equals(request.getParameter("category2"))) {
			validOK = false;
			request.setAttribute("message", "Obie kategorie nie mogą być tego samego rodzaju!");
		}
		if(request.getParameter("saveButton") == null)
			if(request.getPart("imageProduct").getSize() == 0) {
				validOK = false;
				request.setAttribute("message", "Proszę wybrać zdjęcie produktu!");
			}
		
		if (validOK)
			return true;
			
		return false;
	}
	
	private void searchNoExecOrder(HttpServletRequest request) {
		String dateFrom = request.getParameter("transactionDateFrom")+" 00:00:00";
		String dateTo = request.getParameter("transactionDateTo")+" 23:59:59";
		try {
			request.setAttribute("Transactions", tbl.getNoExecOrder(LocalDateTime.parse(dateFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), LocalDateTime.parse(dateTo, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("message", "Proszę uzupełnić oba pola dat!");
			e.printStackTrace();
		}	
	}
}
