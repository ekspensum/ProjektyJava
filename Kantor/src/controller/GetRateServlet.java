package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.dao.Kursy;

/**
 * Servlet implementation class GetRateServlet
 */
@WebServlet("/GetRateServlet")
public class GetRateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
    	Kursy kursy = new Kursy();
    	Thread t = new Thread(kursy);
//    	method run() is necessary in this case
    	t.run();
    	t.start();
		
//    	create xml document
		try {		
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder	docBuilder = docFactory.newDocumentBuilder();
		
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("currency");
			doc.appendChild(rootElement);
	
			// USD elements
			Element usd = doc.createElement("USD");
			rootElement.appendChild(usd);
	
			Element usdBid = doc.createElement("bid");
			usdBid.setTextContent(String.valueOf(kursy.getPln_usd_Bid()));
			usd.appendChild(usdBid);
			
			Element usdAsk = doc.createElement("ask");
			usdAsk.setTextContent(String.valueOf(kursy.getPln_usd_Ask()));
			usd.appendChild(usdAsk);			
	
			// EUR elements
			Element eur = doc.createElement("EUR");
			rootElement.appendChild(eur);
			
			Element eurBid = doc.createElement("bid");
			eurBid.setTextContent(String.valueOf(kursy.getPln_eur_Bid()));
			eur.appendChild(eurBid);
			
			Element eurAsk = doc.createElement("ask");
			eurAsk.setTextContent(String.valueOf(kursy.getPln_eur_Ask()));
			eur.appendChild(eurAsk);	
			
			// CHF elements
			Element chf = doc.createElement("CHF");
			rootElement.appendChild(chf);
			
			Element chfBid = doc.createElement("bid");
			chfBid.setTextContent(String.valueOf(kursy.getPln_chf_Bid()));
			chf.appendChild(chfBid);
			
			Element chfAsk = doc.createElement("ask");
			chfAsk.setTextContent(String.valueOf(kursy.getPln_chf_Ask()));
			chf.appendChild(chfAsk);
			
//			write the content into response - ServletOutputStream
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(response.getOutputStream());		
						
//			Output to console for testing
//			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);			
			
		} catch (ParserConfigurationException | TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}

}
