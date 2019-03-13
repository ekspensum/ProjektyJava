package pl.shopapp.webservice;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
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
import pl.shopapp.entites.Product;


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
	private String category;
	List<Product> listProduct;
	private List<MainBoardXml> listMainBoardXml;
	private MainBoardXml mb;
	private List<RamMemoryXml> listRamMemoryXml;
	private RamMemoryXml rm;
	

	
	@GET
	@Path("/ProcessorsXml")
	@Produces(MediaType.TEXT_XML)
	@Override
	public byte [] getProcessors() {
		
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		category = "Procesory";
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("Processors");
			doc.appendChild(root);

			int id = 0;
			for(int i=0; i<pbl.listCategory().size(); i++)
				if(pbl.listCategory().get(i).getName().equals(category))
					id = pbl.listCategory().get(i).getId();
			
			for(int i=0; i<pbl.listProductByCategory(id).size(); i++)
				getProducts(doc, root, "Processor", String.valueOf(i), String.valueOf(pbl.listProductByCategory(id).get(i).getId()), pbl.listProductByCategory(id).get(i).getName(), pbl.listProductByCategory(id).get(i).getDescription(), String.valueOf(pbl.listProductByCategory(id).get(i).getPrice()), String.valueOf(pbl.listProductByCategory(id).get(i).getUnitsInStock()), pbl.listProductByCategory(id).get(i).getBase64Image());
			
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
	public byte [] getHardDisks() {

		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		category = "Dyski twarde";
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("HardDisks");
			doc.appendChild(root);

			int id = 0;
			for(int i=0; i<pbl.listCategory().size(); i++)
				if(pbl.listCategory().get(i).getName().equals(category))
					id = pbl.listCategory().get(i).getId();
			
			for(int i=0; i<pbl.listProductByCategory(id).size(); i++)
				getProducts(doc, root, "HardDisk", String.valueOf(i), String.valueOf(pbl.listProductByCategory(id).get(i).getId()), pbl.listProductByCategory(id).get(i).getName(), pbl.listProductByCategory(id).get(i).getDescription(), String.valueOf(pbl.listProductByCategory(id).get(i).getPrice()), String.valueOf(pbl.listProductByCategory(id).get(i).getUnitsInStock()), pbl.listProductByCategory(id).get(i).getBase64Image());
			
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
	public byte [] getProductById(@PathParam("id") int id) {
		ByteArrayOutputStream ba = new ByteArrayOutputStream();			
		if(pbl.getProduct(id) != null) {

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
						
	//			Output to console for testing
	//			StreamResult result = new StreamResult(System.out);
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
	public byte [] getProductsById(@PathParam("idFrom") int idFrom, @PathParam("idTo") int idTo) {
		
		listProduct = em.createNamedQuery("productsByIdRange", Product.class).setParameter("idFrom", idFrom).setParameter("idTo", idTo).getResultList();

		ByteArrayOutputStream ba = new ByteArrayOutputStream();			
		if(listProduct != null && listProduct.size() > 0) {

			try {
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document doc = builder.newDocument();
				Element root = doc.createElement("Products");
				doc.appendChild(root);
				
				for(int i=0; i<listProduct.size(); i++)
					getProductsById(doc, root, String.valueOf(i), String.valueOf(listProduct.get(i).getId()), listProduct.get(i).getName(), listProduct.get(i).getDescription(), String.valueOf(listProduct.get(i).getPrice()), String.valueOf(listProduct.get(i).getUnitsInStock()), listProduct.get(i).getBase64Image());
				
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

	
	
	
	
	
	
	private void getProducts(Document doc, Element root, String productCategory, String id, String productId, String productName, String productDescription, String productPrice, String productUnitsInStock, String productImage) {
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

	private void getProductsById(Document doc, Element root, String id, String productId, String productName, String productDescription, String productPrice, String productUnitsInStock, String productImage) {
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