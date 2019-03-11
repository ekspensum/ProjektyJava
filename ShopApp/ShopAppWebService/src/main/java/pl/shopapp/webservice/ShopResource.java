package pl.shopapp.webservice;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;
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

@Stateless
@Path("/ShopResource")
public class ShopResource {
	
	@EJB
	private ProductBeanLocal pbl;
	private String category;
	private List<MainBoard> listMainBoard;
	private MainBoard mb;
	private List<RamMemory> listRamMemory;
	private RamMemory rm;
	
	@GET
	@Path("/Processors")
	@Produces(MediaType.TEXT_XML)
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
	@Path("/HardDisks")
	@Produces(MediaType.TEXT_XML)
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
	@Path("/Product/{id}")
	@Produces(MediaType.TEXT_XML)
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
	@Path("/Products/{idFrom}/{idTo}")
	@Produces(MediaType.TEXT_XML)
	public byte [] getProductsById(@PathParam("idFrom") int idFrom, @PathParam("idTo") int idTo) {

		ByteArrayOutputStream ba = new ByteArrayOutputStream();			
		if(pbl.listProductByIdRange(idFrom, idTo) != null && pbl.listProductByIdRange(idFrom, idTo).size() > 0) {

			try {
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document doc = builder.newDocument();
				Element root = doc.createElement("Products");
				doc.appendChild(root);
				
				for(int i=0; i<pbl.listProductByIdRange(idFrom, idTo).size(); i++)
					getProductsById(doc, root, String.valueOf(i), String.valueOf(pbl.listProductByIdRange(idFrom, idTo).get(i).getId()), pbl.listProductByIdRange(idFrom, idTo).get(i).getName(), pbl.listProductByIdRange(idFrom, idTo).get(i).getDescription(), String.valueOf(pbl.listProductByIdRange(idFrom, idTo).get(i).getPrice()), String.valueOf(pbl.listProductByIdRange(idFrom, idTo).get(i).getUnitsInStock()), pbl.listProductByIdRange(idFrom, idTo).get(i).getBase64Image());
				
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
	@Path("/MainBoards")
	@Produces(MediaType.APPLICATION_XML)
	public List<MainBoard> getMainBoards() throws NamingException {
		listMainBoard = new ArrayList<>();	
		for (int i = 0; i < pbl.listAllMainBoard().size(); i++) {
			mb = new MainBoard();
			mb.setId(pbl.listAllMainBoard().get(i).getId());
			mb.setName(pbl.listAllMainBoard().get(i).getName());
			mb.setDescription(pbl.listAllMainBoard().get(i).getDescription());
			mb.setUnitsInStock(pbl.listAllMainBoard().get(i).getUnitsInStock());
			mb.setPrice(pbl.listAllMainBoard().get(i).getPrice());
			mb.setBase64Image(pbl.listAllMainBoard().get(i).getBase64Image());
			listMainBoard.add(mb);
		}
		return listMainBoard;
	}
	
	@GET
	@Path("/RamMemory")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RamMemory> getAllRamMemory(){
		listRamMemory = new ArrayList<>();
		for (int i = 0; i < pbl.listAllRamMemory().size(); i++) {
			rm = new RamMemory();
			rm.setId(pbl.listAllRamMemory().get(i).getId());
			rm.setName(pbl.listAllRamMemory().get(i).getName());
			rm.setDescription(pbl.listAllRamMemory().get(i).getDescription());
			rm.setUnitsInStock(pbl.listAllRamMemory().get(i).getUnitsInStock());
			rm.setPrice(pbl.listAllRamMemory().get(i).getPrice());
			rm.setBase64Image(pbl.listAllRamMemory().get(i).getBase64Image());
			listRamMemory.add(rm);
		}
		return listRamMemory;
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