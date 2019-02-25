package pl.shopapp.webservice;

import java.io.StringWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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

@Path("/ShopResource")
public class ShopResource {
	
	private ProductBeanLocal pbl;
	private String category;
	private Context ctx;
	
	@GET
	@Path("/Processors")
	@Produces(MediaType.TEXT_XML)
	public String getProcessors() {

		StringWriter stringOut = null;
		pbl = initializeProductBean();
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
				getProducts(doc, root, "Processor", String.valueOf(i), pbl.listProductByCategory(id).get(i).getName(), pbl.listProductByCategory(id).get(i).getDescription(), String.valueOf(pbl.listProductByCategory(id).get(i).getPrice()), String.valueOf(pbl.listProductByCategory(id).get(i).getUnitsInStock()));
			
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
	
	private void getProducts(Document doc, Element root, String productCategory, String id, String productName, String productDescription, String productPrice, String productUnitsInStock) {
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

	}

	private ProductBeanLocal initializeProductBean() {
		try {
			ctx = new InitialContext();
			pbl = (ProductBeanLocal) ctx.lookup("java:global/ShopApp/ShopAppBeans/ProductBean!pl.shopapp.beans.ProductBeanLocal");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return pbl;
	}
}