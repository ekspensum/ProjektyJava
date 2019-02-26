package pl.shopapp.webservice;

import java.io.StringWriter;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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

	
	@GET
	@Path("/Processors")
	@Produces(MediaType.TEXT_XML)
	public String getProcessors() {

		StringWriter stringOut = null;
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
				getProducts(doc, root, "Processor", String.valueOf(i), pbl.listProductByCategory(id).get(i).getName(), pbl.listProductByCategory(id).get(i).getDescription(), String.valueOf(pbl.listProductByCategory(id).get(i).getPrice()), String.valueOf(pbl.listProductByCategory(id).get(i).getUnitsInStock()), pbl.listProductByCategory(id).get(i).getBase64Image());
			
//			write the content into response - ServletOutputStream
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			stringOut = new StringWriter();
			StreamResult result = new StreamResult(stringOut);		
					
//			Output to console for testing
//			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);	
			
		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return stringOut.toString();	
	}
	
	@GET
	@Path("/HardDisks")
	@Produces(MediaType.TEXT_XML)
	public String getHardDisks() {

		StringWriter stringOut = null;
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
				getProducts(doc, root, "HardDisk", String.valueOf(i), pbl.listProductByCategory(id).get(i).getName(), pbl.listProductByCategory(id).get(i).getDescription(), String.valueOf(pbl.listProductByCategory(id).get(i).getPrice()), String.valueOf(pbl.listProductByCategory(id).get(i).getUnitsInStock()), pbl.listProductByCategory(id).get(i).getBase64Image());
			
//			write the content into response - ServletOutputStream
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			stringOut = new StringWriter();
			StreamResult result = new StreamResult(stringOut);		
					
//			Output to console for testing
//			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);	
			
		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return stringOut.toString();		
	}
	
	@GET
	@Path("/Product/{id}")
	@Produces(MediaType.TEXT_XML)
	public String getProductById(@PathParam("id") int id) {
		
		if(pbl.getProduct(id) != null) {
				
			StringWriter stringOut = null;
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
				stringOut = new StringWriter();
				StreamResult result = new StreamResult(stringOut);		
						
	//			Output to console for testing
	//			StreamResult result = new StreamResult(System.out);
				transformer.transform(source, result);	
				
				
			} catch (ParserConfigurationException | TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			return stringOut.toString();
		} else
			return "<Product>Product not found.</Product>";
	}
	
	
	
	private void getProducts(Document doc, Element root, String productCategory, String id, String productName, String productDescription, String productPrice, String productUnitsInStock, String productImage) {
		Element category = doc.createElement(productCategory);
		category.setAttribute("id", id);
		root.appendChild(category);
		
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