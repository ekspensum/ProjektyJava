package pl.shopapp.webservice;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;
import pl.shopapp.webservice.model.CategoryJson;
import pl.shopapp.webservice.model.MainBoardXml;
import pl.shopapp.webservice.model.ProductDataJson;
import pl.shopapp.webservice.model.ProductsJson;
import pl.shopapp.webservice.model.RamMemoryXml;


@Stateless
@Path("/ShopResource")
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class ShopResource implements ShopResourceLocal {

	@PersistenceContext(unitName = "ShopAppEntites")
	private EntityManager em;
	@Resource
	private UserTransaction ut;
	@EJB
	private ProductBeanLocal pbl;
	@EJB
	private UserBeanLocal ubl;

//	for tests
	public ShopResource(EntityManager em, UserTransaction ut, ProductBeanLocal pbl, UserBeanLocal ubl) {
	super();
	this.em = em;
	this.ut = ut;
	this.pbl = pbl;
	this.ubl = ubl;
	}
	
	public ShopResource() {
	super();
	}

	@GET
	@Path("/ProcessorsXml")
	@Produces(MediaType.TEXT_XML)
	@Override
	public byte[] getProcessors() {

		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		String category = "Procesory";
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("Processors");
			doc.appendChild(root);

			List<Category> listCategory = pbl.listCategory();
			int id = 0;
			for (int i = 0; i < listCategory.size(); i++) {
				if (listCategory.get(i).getName().equals(category)) {
					id = listCategory.get(i).getId();	
					break;
				}
			}

			List<Product> listProductByCategory = pbl.listProductByCategory(id);
			for (int i = 0; i < listProductByCategory.size(); i++) {
				getProducts(doc, root, "Processor", String.valueOf(i),
						String.valueOf(listProductByCategory.get(i).getId()),
						listProductByCategory.get(i).getName(),
						listProductByCategory.get(i).getDescription(),
						String.valueOf(listProductByCategory.get(i).getPrice()),
						String.valueOf(listProductByCategory.get(i).getUnitsInStock()),
						listProductByCategory.get(i).getBase64Image());
			}
			
//			write the content into response - ServletOutputStream
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(ba);

//			Output to console for testing
//			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ba.toByteArray();
	}

	@GET
	@Path("/HardDisksXml")
	@Produces(MediaType.TEXT_XML)
	@Override
	public byte[] getHardDisks() {

		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		String category = "Dyski twarde";
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("HardDisks");
			doc.appendChild(root);

			List<Category> listCategory = pbl.listCategory();
			int id = 0;
			for (int i = 0; i < listCategory.size(); i++)
				if (listCategory.get(i).getName().equals(category))
					id = listCategory.get(i).getId();

			List<Product> listProductByCategory = pbl.listProductByCategory(id);
			for (int i = 0; i < listProductByCategory.size(); i++) {
				getProducts(doc, root, "HardDisk", String.valueOf(i),
						String.valueOf(listProductByCategory.get(i).getId()),
						listProductByCategory.get(i).getName(),
						listProductByCategory.get(i).getDescription(),
						String.valueOf(listProductByCategory.get(i).getPrice()),
						String.valueOf(listProductByCategory.get(i).getUnitsInStock()),
						listProductByCategory.get(i).getBase64Image());
			}
			
//			write the content into response - ServletOutputStream
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(ba);

//			Output to console for testing
//			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ba.toByteArray();
	}

	@GET
	@Path("/ProductXml/{id}")
	@Produces(MediaType.TEXT_XML)
	@Override
	public byte[] getProductById(@PathParam("id") int id) {
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		if (pbl.getProduct(id) != null) {

			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.newDocument();
				Element root = doc.createElement("Product");
				doc.appendChild(root);

				Element name = doc.createElement("productName");
				name.setTextContent(pbl.getProduct(id).getName());
				root.appendChild(name);

				Element description = doc.createElement("productDescription");
				description.setTextContent(pbl.getProduct(id).getDescription());
				root.appendChild(description);

				Element price = doc.createElement("productPrice");
				price.setTextContent(String.valueOf(pbl.getProduct(id).getPrice()));
				root.appendChild(price);

				Element unitsInStock = doc.createElement("productUnitsInStock");
				unitsInStock.setTextContent(String.valueOf(pbl.getProduct(id).getUnitsInStock()));
				root.appendChild(unitsInStock);

				Element image = doc.createElement("productImage");
				image.setTextContent(pbl.getProduct(id).getBase64Image());
				root.appendChild(image);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(ba);

				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);
				transformer.transform(source, result);

			} catch (ParserConfigurationException | TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return ba.toByteArray();
		} else
			return "<Product>Product not found.</Product>".getBytes();
	}

	@GET
	@Path("/ProductsXml/{idFrom}/{idTo}")
	@Produces(MediaType.TEXT_XML)
	@Override
	public byte[] getProductsById(@PathParam("idFrom") int idFrom, @PathParam("idTo") int idTo) {

		List<Product> listProduct = em.createNamedQuery("productsByIdRange", Product.class).setParameter("idFrom", idFrom)
				.setParameter("idTo", idTo).getResultList();

		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		if (listProduct != null && listProduct.size() > 0) {

			try {
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document doc = builder.newDocument();
				Element root = doc.createElement("Products");
				doc.appendChild(root);

				for (int i = 0; i < listProduct.size(); i++)
					getProductsById(doc, root, String.valueOf(i), String.valueOf(listProduct.get(i).getId()),
							listProduct.get(i).getName(), listProduct.get(i).getDescription(),
							String.valueOf(listProduct.get(i).getPrice()),
							String.valueOf(listProduct.get(i).getUnitsInStock()), listProduct.get(i).getBase64Image());

//				write the content into response - ServletOutputStream
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(ba);

//				Output to console for testing
//				StreamResult result = new StreamResult(System.out);
				transformer.transform(source, result);

			} catch (ParserConfigurationException | TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return ba.toByteArray();
		} else
			return "<Product>Product not found.</Product>".getBytes();
	}

	@GET
	@Path("/MainBoardsXml")
	@Produces(MediaType.APPLICATION_XML)
	@Override
	public List<MainBoardXml> getMainBoardXmls() {
		Category singleResult = em.createNamedQuery("getAllMainBoardXml", Category.class).getSingleResult();
		List<Product> productsList = singleResult.getProduct();
		List<MainBoardXml> mainBoardXmlList  = new ArrayList<>();
		MainBoardXml mainBoardXml;
		for (int i = 0; i < productsList.size(); i++) {
			mainBoardXml = new MainBoardXml();
			mainBoardXml.setId(productsList.get(i).getId());
			mainBoardXml.setName(productsList.get(i).getName());
			mainBoardXml.setDescription(productsList.get(i).getDescription());
			mainBoardXml.setPrice(productsList.get(i).getPrice());
			mainBoardXml.setUnitsInStock(productsList.get(i).getUnitsInStock());
			mainBoardXml.setBase64Image(productsList.get(i).getBase64Image());
			mainBoardXmlList.add(mainBoardXml);
		}
		return mainBoardXmlList;
	}
	
	@GET
	@Path("/RamMemoryXml")
	@Produces(MediaType.APPLICATION_XML)
	@Override
	public List<RamMemoryXml> getRamMemoryXml() {
		Category singleResult = em.createNamedQuery("getAllRamMemoryXml", Category.class).getSingleResult();
		List<Product> productsList = singleResult.getProduct();
		List<RamMemoryXml> ramMemoryXmlList  = new ArrayList<>();
		RamMemoryXml ramMemoryXml;
		for (int i = 0; i < productsList.size(); i++) {
			ramMemoryXml = new RamMemoryXml();
			ramMemoryXml.setId(productsList.get(i).getId());
			ramMemoryXml.setName(productsList.get(i).getName());
			ramMemoryXml.setDescription(productsList.get(i).getDescription());
			ramMemoryXml.setPrice(productsList.get(i).getPrice());
			ramMemoryXml.setUnitsInStock(productsList.get(i).getUnitsInStock());
			ramMemoryXml.setBase64Image(productsList.get(i).getBase64Image());
			ramMemoryXmlList.add(ramMemoryXml);
		}
		return ramMemoryXmlList;
	}

	@GET
	@Path("/ProcessorsJson")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ProductsJson> getAllProcessorsJson() {
		Category singleResult = em.createNamedQuery("getAllProcessors", Category.class).getSingleResult();
		return createProductList(singleResult);
	}

	@GET
	@Path("/MainBoardsJson")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ProductsJson> getAllMainBoardJson() {
		Category singleResult = em.createNamedQuery("getAllMainBoard", Category.class).getSingleResult();
		return createProductList(singleResult);
	}

	@GET
	@Path("/RamMemoryJson")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ProductsJson> getAllRamMemoryJson() {
		Category singleResult = em.createNamedQuery("getAllRamMemory", Category.class).getSingleResult();
		return createProductList(singleResult);
	}

	@GET
	@Path("/HardDisksJson")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ProductsJson> getAllHardDisksJson() {
		Category singleResult = em.createNamedQuery("getAllHardDisks", Category.class).getSingleResult();
		return createProductList(singleResult);
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/AllCategoryJson")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<CategoryJson> getAllCategory(){
		return em.createNamedQuery("listCategoryJson").getResultList();
	}
	
	@GET
	@Path("/ProductByIdJson/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ProductsJson getProductJsonById(@PathParam("id") int id) {
		Product pr = pbl.getProduct(id);
		ProductsJson productsJson = new ProductsJson();
		productsJson.setId(pr.getId());
		productsJson.setName(pr.getName());
		productsJson.setDescription(pr.getDescription());
		productsJson.setPrice(pr.getPrice());
		productsJson.setUnitsInStock(pr.getUnitsInStock());
		productsJson.setImage(pr.getProductImage());
		return productsJson;
	}

	@GET
	@Path("/ProductsByNameJson/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ProductDataJson> getProductJsonByName(@PathParam("name") String name) {
		List<Product> productList = pbl.findProduct(name);
		ProductDataJson pdj;
		List<ProductDataJson> productListJson = new ArrayList<>();	
		for(int i=0; i<productList.size(); i++) {			
			pdj = new ProductDataJson();
			pdj.setIdProduct(productList.get(i).getId());
			pdj.setName(productList.get(i).getName());
			pdj.setDescription(productList.get(i).getDescription());
			pdj.setPrice(productList.get(i).getPrice());
			pdj.setUnitsInStock(productList.get(i).getUnitsInStock());
			pdj.setImage(productList.get(i).getProductImage());
			pdj.setCategory1Id(productList.get(i).getCategories().get(0).getId());
			pdj.setCategory2Id(productList.get(i).getCategories().get(1).getId());
			productListJson.add(pdj);
		}
		return productListJson;
	}
	
	@GET
	@Path("/ProductsJson/{idFrom}/{idTo}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Map<String, List<ProductsJson>> getProductsJsonByIdRange(@PathParam("idFrom") int idFrom, @PathParam("idTo") int idTo) {
		ProductsJson productsJson;
		List<ProductsJson> listProcessor = new ArrayList<>();
		List<ProductsJson> listMainBoard = new ArrayList<>();
		List<ProductsJson> listRamMemory = new ArrayList<>();
		List<ProductsJson> listHardDisk = new ArrayList<>();
		Map<String, List<ProductsJson>> mapProductJson = new HashMap<>();

		List<Product> products = em.createNamedQuery("productsByIdRangeJson", Product.class).setParameter("idFrom", idFrom).setParameter("idTo", idTo).getResultList();

		for (int i = 0; i < products.size(); i++) {

			productsJson = new ProductsJson();
			productsJson.setId(products.get(i).getId());
			productsJson.setName(products.get(i).getName());
			productsJson.setDescription(products.get(i).getDescription());
			productsJson.setPrice(products.get(i).getPrice());
			productsJson.setUnitsInStock(products.get(i).getUnitsInStock());
			productsJson.setBase64Image(products.get(i).getBase64Image());

			boolean forward = false;
			for (int j = 0; j < products.get(i).getCategories().size(); j++) {
				if (products.get(i).getCategories().get(j).getName().equals("Płyty główne")) {
					listMainBoard.add(productsJson);
					mapProductJson.put(products.get(i).getCategories().get(j).getName(), listMainBoard);
					forward = true;
				}				
			}
			if(forward)
				continue;
			
			for (int j = 0; j < products.get(i).getCategories().size(); j++) {
				if (products.get(i).getCategories().get(j).getName().equals("Pamięci RAM")) {
					listRamMemory.add(productsJson);
					mapProductJson.put(products.get(i).getCategories().get(j).getName(), listRamMemory);
					forward = true;
				}				
			}
			if(forward)
				continue;
			
			for (int j = 0; j < products.get(i).getCategories().size(); j++) {
				if (products.get(i).getCategories().get(j).getName().equals("Dyski twarde")) {
					listHardDisk.add(productsJson);
					mapProductJson.put(products.get(i).getCategories().get(j).getName(), listHardDisk);
					forward = true;
				}				
			}
			if(forward)
				continue;

			for (int j = 0; j < products.get(i).getCategories().size(); j++) {
				if (products.get(i).getCategories().get(j).getName().equals("Procesory")) {
					listProcessor.add(productsJson);
					mapProductJson.put(products.get(i).getCategories().get(j).getName(), listProcessor);
				}				
			}
		}
		return mapProductJson;
	}

	@POST
	@Path("/ProductAddFormParam")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Override
	public String addNewProductFormParam(@FormParam("login") String login, @FormParam("password") String password,
			@FormParam("productName") String productName, @FormParam("productDescription") String productDescription,
			@FormParam("productPrice") double productPrice, @FormParam("productUnitsInStock") int productUnitsInStock,
			@FormParam("category1Id") String category1Id,
			@FormParam("category2Id") String category2Id,
			@FormParam("base64Image") String base64Image) throws IllegalStateException, SecurityException, SystemException {

		SessionData sd = ubl.loginUser(login, password);
		if (sd != null && sd.getIdRole() == 3) {
			
			List<Integer> helperListCat = new ArrayList<>();
			helperListCat.add(Integer.valueOf(category1Id));
			if(category2Id != null)
				helperListCat.add(Integer.valueOf(category2Id));

			if (pbl.addProduct(productName, productDescription, productPrice, productUnitsInStock,	Base64.getDecoder().decode(base64Image), helperListCat, sd.getIdUser()))
				return "Produkt został dodany do bazy danych.";
			else
				return "Nie udało się dodać produktu do bazy danych.";
		} else
			return "Brak autoryzacji lub autentykacji";
	}
	
    @POST
    @Path("/ProductAddFormParamMap")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Override
    public String addNewProductFormParamMap(Map<String, String> mapParam) throws NumberFormatException, IllegalStateException, SecurityException, SystemException {
      
		SessionData sd = ubl.loginUser(mapParam.get("login"), mapParam.get("password"));
		if (sd != null && sd.getIdRole() == 3) {

			List<Integer> helperListCat = new ArrayList<>();
			helperListCat.add(Integer.valueOf(mapParam.get("category1Id")));
			if(mapParam.get("category2Id") != null)
				helperListCat.add(Integer.valueOf(mapParam.get("category2Id")));

			byte [] buffer = Base64.getDecoder().decode(mapParam.get("image"));
			
			if (pbl.addProduct(mapParam.get("productName"), mapParam.get("productDescription"), Double.valueOf(mapParam.get("productPrice")), Integer.valueOf(mapParam.get("productUnitsInStock")),	buffer, helperListCat, sd.getIdUser()))
				return "Produkt został dodany do bazy danych.";
			else
				return "Nie udało się dodać produktu do bazy danych.";
		} else
			return "Brak autoryzacji lub autentykacji";     
    }

    @POST
    @Path("/ProductAddJson")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public String addNewProductJson(ProductDataJson pdj) throws IllegalStateException, SecurityException, SystemException {
    	
		SessionData sd = ubl.loginUser(pdj.getLogin(), pdj.getPassword());
		if (sd != null && sd.getIdRole() == 3) {

			List<Integer> helperListCat = new ArrayList<>();
			helperListCat.add(pdj.getCategory1Id());
			helperListCat.add(pdj.getCategory2Id());
			
			if (pbl.addProduct(pdj.getName(), pdj.getDescription(), pdj.getPrice(), pdj.getUnitsInStock(),	pdj.getImage(), helperListCat, sd.getIdUser()))
				return "Produkt został dodany do bazy danych.";
			else
				return "Nie udało się dodać produktu do bazy danych.";
		} else
			return "Brak autoryzacji lub autentykacji";
    }
       
	@PUT
	@Path("/ProductUpdateJson")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public String updateProductData(ProductDataJson pdj) throws IllegalStateException, SecurityException, SystemException {

		SessionData sd = ubl.loginUser(pdj.getLogin(), pdj.getPassword());
		if (sd != null && sd.getIdRole() == 3) {
			
//			it is new category
			List<Integer> helperListCat = new ArrayList<>();
			helperListCat.add(pdj.getCategory1Id());
			helperListCat.add(pdj.getCategory2Id());

//			it is previous category
			int [] categoryToEdit = new int [2];
			categoryToEdit[0] = pdj.getPreviousCategory1Id();
			categoryToEdit[1] = pdj.getPreviousCategory2Id();

			if (pbl.updateProduct(pdj.getName(), pdj.getDescription(), pdj.getPrice(), pdj.getUnitsInStock(), pdj.getImage(), pdj.getIdProduct(), pdj.getImageSize(), sd.getIdUser(), helperListCat))
				return "Dane produktu id = "+pdj.getIdProduct()+" zostały zaktualizowane w bazie danych.";
			else
				return "Nie udało się zaktualizować danych produktu id = "+pdj.getIdProduct()+" w bazie danych.";
		} else
			return "Brak autoryzacji lub autentykacji";
    }


	
	public List<ProductsJson> createProductList(Category category) {
		List<Product> productsList = category.getProduct();
		List<ProductsJson> productWebServicesList  = new ArrayList<>();
		ProductsJson productsJson;
		for (int i = 0; i < productsList.size(); i++) {
			productsJson = new ProductsJson();
			productsJson.setId(productsList.get(i).getId());
			productsJson.setName(productsList.get(i).getName());
			productsJson.setDescription(productsList.get(i).getDescription());
			productsJson.setPrice(productsList.get(i).getPrice());
			productsJson.setUnitsInStock(productsList.get(i).getUnitsInStock());
			productsJson.setBase64Image(productsList.get(i).getBase64Image());
			productsJson.setCategoryName(category.getName());
			productWebServicesList.add(productsJson);
		}
		return productWebServicesList;
	}
	
	private void getProducts(Document doc, Element root, String productCategory, String id, String productId,
			String productName, String productDescription, String productPrice, String productUnitsInStock,
			String productImage) {
		Element category = doc.createElement(productCategory);
		category.setAttribute("id", id);
		root.appendChild(category);

		Element idProduct = doc.createElement("idPproduct");
		idProduct.setTextContent(productId);
		category.appendChild(idProduct);

		Element name = doc.createElement("productName");
		name.setTextContent(productName);
		category.appendChild(name);

		Element description = doc.createElement("productDescription");
		description.setTextContent(productDescription);
		category.appendChild(description);

		Element price = doc.createElement("productPrice");
		price.setTextContent(productPrice);
		category.appendChild(price);

		Element unitsInStock = doc.createElement("productUnitsInStock");
		unitsInStock.setTextContent(productUnitsInStock);
		category.appendChild(unitsInStock);

		Element image = doc.createElement("productImage");
		image.setTextContent(productImage);
		category.appendChild(image);

	}

	private void getProductsById(Document doc, Element root, String id, String productId, String productName,
			String productDescription, String productPrice, String productUnitsInStock, String productImage) {
		Element category = doc.createElement("Product");
		category.setAttribute("id", id);
		root.appendChild(category);

		Element idProduct = doc.createElement("idPproduct");
		idProduct.setTextContent(productId);
		category.appendChild(idProduct);

		Element name = doc.createElement("productName");
		name.setTextContent(productName);
		category.appendChild(name);

		Element description = doc.createElement("productDescription");
		description.setTextContent(productDescription);
		category.appendChild(description);

		Element price = doc.createElement("productPrice");
		price.setTextContent(productPrice);
		category.appendChild(price);

		Element unitsInStock = doc.createElement("productUnitsInStock");
		unitsInStock.setTextContent(productUnitsInStock);
		category.appendChild(unitsInStock);

		Element image = doc.createElement("productImage");
		image.setTextContent(productImage);
		category.appendChild(image);

	}
}