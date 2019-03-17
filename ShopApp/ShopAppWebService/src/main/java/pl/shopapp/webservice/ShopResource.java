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
import pl.shopapp.entites.Product;
import pl.shopapp.entites.ProductCategory;
import pl.shopapp.webservice.model.CategoryJson;
import pl.shopapp.webservice.model.HardDisksJson;
import pl.shopapp.webservice.model.MainBoardJson;
import pl.shopapp.webservice.model.MainBoardXml;
import pl.shopapp.webservice.model.ProcessorsJson;
import pl.shopapp.webservice.model.ProductDataJson;
import pl.shopapp.webservice.model.ProductsJson;
import pl.shopapp.webservice.model.RamMemoryJson;
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
	private String category;
	private List<Product> listProduct;
	private List<MainBoardXml> listMainBoardXml;
	private MainBoardXml mb;
	private List<RamMemoryXml> listRamMemoryXml;
	private RamMemoryXml rm;

	@GET
	@Path("/ProcessorsXml")
	@Produces(MediaType.TEXT_XML)
	@Override
	public byte[] getProcessors() {

		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		category = "Procesory";
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("Processors");
			doc.appendChild(root);

			int id = 0;
			for (int i = 0; i < pbl.listCategory().size(); i++)
				if (pbl.listCategory().get(i).getName().equals(category))
					id = pbl.listCategory().get(i).getId();

			for (int i = 0; i < pbl.listProductByCategory(id).size(); i++)
				getProducts(doc, root, "Processor", String.valueOf(i),
						String.valueOf(pbl.listProductByCategory(id).get(i).getId()),
						pbl.listProductByCategory(id).get(i).getName(),
						pbl.listProductByCategory(id).get(i).getDescription(),
						String.valueOf(pbl.listProductByCategory(id).get(i).getPrice()),
						String.valueOf(pbl.listProductByCategory(id).get(i).getUnitsInStock()),
						pbl.listProductByCategory(id).get(i).getBase64Image());

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
		category = "Dyski twarde";
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("HardDisks");
			doc.appendChild(root);

			int id = 0;
			for (int i = 0; i < pbl.listCategory().size(); i++)
				if (pbl.listCategory().get(i).getName().equals(category))
					id = pbl.listCategory().get(i).getId();

			for (int i = 0; i < pbl.listProductByCategory(id).size(); i++)
				getProducts(doc, root, "HardDisk", String.valueOf(i),
						String.valueOf(pbl.listProductByCategory(id).get(i).getId()),
						pbl.listProductByCategory(id).get(i).getName(),
						pbl.listProductByCategory(id).get(i).getDescription(),
						String.valueOf(pbl.listProductByCategory(id).get(i).getPrice()),
						String.valueOf(pbl.listProductByCategory(id).get(i).getUnitsInStock()),
						pbl.listProductByCategory(id).get(i).getBase64Image());

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

		listProduct = em.createNamedQuery("productsByIdRange", Product.class).setParameter("idFrom", idFrom)
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
		listProduct = em.createNamedQuery("getAllMainBoardXml", Product.class).getResultList();
		listMainBoardXml = new ArrayList<>();
		for (int i = 0; i < listProduct.size(); i++) {
			mb = new MainBoardXml();
			mb.setId(listProduct.get(i).getId());
			mb.setName(listProduct.get(i).getName());
			mb.setDescription(listProduct.get(i).getDescription());
			mb.setPrice(listProduct.get(i).getPrice());
			mb.setUnitsInStock(listProduct.get(i).getUnitsInStock());
			mb.setBase64Image(listProduct.get(i).getBase64Image());
			listMainBoardXml.add(mb);
		}
		return listMainBoardXml;
	}

	@GET
	@Path("/RamMemoryXml")
	@Produces(MediaType.APPLICATION_XML)
	@Override
	public List<RamMemoryXml> getRamMemoryXml() {
		listProduct = em.createNamedQuery("getAllRamMemoryXml", Product.class).getResultList();
		listRamMemoryXml = new ArrayList<>();
		for (int i = 0; i < listProduct.size(); i++) {
			rm = new RamMemoryXml();
			rm.setId(listProduct.get(i).getId());
			rm.setName(listProduct.get(i).getName());
			rm.setDescription(listProduct.get(i).getDescription());
			rm.setUnitsInStock(listProduct.get(i).getUnitsInStock());
			rm.setPrice(listProduct.get(i).getPrice());
			rm.setBase64Image(listProduct.get(i).getBase64Image());
			listRamMemoryXml.add(rm);
		}
		return listRamMemoryXml;
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/ProcessorsJson")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ProcessorsJson> getAllProcessorsJson() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("getAllProcessorsJson").getResultList();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/MainBoardsJson")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<MainBoardJson> getAllMainBoardJson() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("getAllMainBoardJson").getResultList();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/RamMemoryJson")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<RamMemoryJson> getAllRamMemoryJson() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("getAllRamMemoryJson").getResultList();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/HardDisksJson")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<HardDisksJson> getAllHardDisksJson() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("getAllHardDisksJson").getResultList();
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub		
		List<Product> productList = pbl.findProduct(name);
		ProductDataJson pdj;
		List<ProductDataJson> productListJson = new ArrayList<>();	
		List<ProductCategory> pcList = em.createNamedQuery("getAllProductCategory", ProductCategory.class).getResultList();
		int cat = 0;		
		for(int i=0; i<productList.size(); i++) {			
			pdj = new ProductDataJson();
			pdj.setIdProduct(productList.get(i).getId());
			pdj.setName(productList.get(i).getName());
			pdj.setDescription(productList.get(i).getDescription());
			pdj.setPrice(productList.get(i).getPrice());
			pdj.setUnitsInStock(productList.get(i).getUnitsInStock());
			pdj.setImage(productList.get(i).getProductImage());
			productListJson.add(pdj);
			cat = 0;
			for(int j=0; j<pcList.size(); j++) {
				if(productList.get(i).getId() == pcList.get(j).getProduct().getId() && cat == 0) {
					productListJson.get(i).setCategory1Name(pcList.get(j).getCategory().getId());
					cat++;
					continue;
				}
				if(productList.get(i).getId() == pcList.get(j).getProduct().getId() && cat == 1) {
					productListJson.get(i).setCategory2Name(pcList.get(j).getCategory().getId());
					break;
				}					
			}
		}
		return productListJson;
	}
	
	@GET
	@Path("/ProductsJson/{idFrom}/{idTo}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Map<String, List<ProductsJson>> getProductsJsonByIdRange(@PathParam("idFrom") int idFrom,
			@PathParam("idTo") int idTo) {
		// TODO Auto-generated method stub
		ProductsJson productsJson;
		List<ProductsJson> listProcessor = new ArrayList<>();
		List<ProductsJson> listMainBoard = new ArrayList<>();
		List<ProductsJson> listRamMemory = new ArrayList<>();
		List<ProductsJson> listHardDisk = new ArrayList<>();
		Map<String, List<ProductsJson>> mapProductJson = new HashMap<>();

		List<ProductCategory> lpc = em.createNamedQuery("productsByIdRangeJson", ProductCategory.class)
				.setParameter("idFrom", idFrom).setParameter("idTo", idTo).getResultList();

		for (int i = 0; i < lpc.size(); i++) {

			productsJson = new ProductsJson();
			productsJson.setId(lpc.get(i).getProduct().getId());
			productsJson.setName(lpc.get(i).getProduct().getName());
			productsJson.setDescription(lpc.get(i).getProduct().getDescription());
			productsJson.setPrice(lpc.get(i).getProduct().getPrice());
			productsJson.setUnitsInStock(lpc.get(i).getProduct().getUnitsInStock());
			productsJson.setBase64Image(lpc.get(i).getProduct().getBase64Image());

			if (lpc.get(i).getCategory().getName().equals("Płyty główne")) {
				listMainBoard.add(productsJson);
				mapProductJson.put(lpc.get(i).getCategory().getName(), listMainBoard);
				continue;
			}
			if (lpc.get(i).getCategory().getName().equals("Pamięci RAM")) {
				listRamMemory.add(productsJson);
				mapProductJson.put(lpc.get(i).getCategory().getName(), listRamMemory);
				continue;
			}
			if (lpc.get(i).getCategory().getName().equals("Dyski twarde")) {
				listHardDisk.add(productsJson);
				mapProductJson.put(lpc.get(i).getCategory().getName(), listHardDisk);
				continue;
			}
			if (lpc.get(i).getCategory().getName().equals("Procesory")) {
				listProcessor.add(productsJson);
				mapProductJson.put(lpc.get(i).getCategory().getName(), listProcessor);
				continue;
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
			@FormParam("category1Name") String category1Id,
			@FormParam("category2Name") String category2Id,
			@FormParam("base64Image") String base64Image) {

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
    public String addNewProductFormParamMap(Map<String, String> mapParam) {
      
		SessionData sd = ubl.loginUser(mapParam.get("login"), mapParam.get("password"));
		if (sd != null && sd.getIdRole() == 3) {

			List<Integer> helperListCat = new ArrayList<>();
			helperListCat.add(Integer.valueOf(mapParam.get("category1Name")));
			if(mapParam.get("category2Name") != null)
				helperListCat.add(Integer.valueOf(mapParam.get("category2Name")));

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
    public String addNewProductJson(ProductDataJson pdj) {
    	
		SessionData sd = ubl.loginUser(pdj.getLogin(), pdj.getPassword());
		if (sd != null && sd.getIdRole() == 3) {

			List<Integer> helperListCat = new ArrayList<>();
			helperListCat.add(pdj.getCategory1Name());
			if(pdj.getCategory2Name() != 0)
				helperListCat.add(pdj.getCategory2Name());
			
			if (pbl.addProduct(pdj.getName(), pdj.getDescription(), pdj.getPrice(), pdj.getUnitsInStock(),	pdj.getImage(), helperListCat, sd.getIdUser()))
				return "Produkt został dodany do bazy danych.";
			else
				return "Nie udało się dodać produktu do bazy danych.";
		} else
			return "Brak autoryzacji lub autentykacji";
    }
    
    
    
	@PUT
	@Path("/ProductUpdate/{idProduct}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public int updateProductData(@PathParam("idProduct") int idproduct) {
		if (idproduct <= 0)
			return -1;
		System.out.println("Update " + idproduct);
		return idproduct * 2;
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