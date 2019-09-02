package pl.dentistoffice.view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import pl.dentistoffice.entity.Visit;

public class PdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition", "attachment; filename=\"visits.pdf\"");
		
		@SuppressWarnings("unchecked")
		List<Visit> visits = (List<Visit>) model.get("visitsByPatientAndStatus");
		
		document.setMargins(20, 10, 100, 10);
		document.open();
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("ZESTAWIENIE WIZYT"), document.right()/2, document.top()+80, 0);
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("Status wizyty: "+visits.get(0).getStatus().getDescription()), document.left(), document.top()+60, 0);
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("Data wydruku: "+LocalDateTime.now().toLocalDate().toString()), document.left(), document.top()+40, 0);
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("Pacjent: "+visits.get(0).getPatient().getFirstName()+" "+visits.get(0).getPatient().getLastName()), document.left(), document.top()+20, 0);
		
		PdfPTable headerVisitTable = new PdfPTable(5);
		headerVisitTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerVisitTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		headerVisitTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
		headerVisitTable.setWidths(new float [] {30, 80, 250, 150, 50});
		headerVisitTable.setWidthPercentage(100);
		headerVisitTable.addCell("L.p.");
		headerVisitTable.addCell("Data wizyty");
		headerVisitTable.addCell("Lekarz");
		headerVisitTable.addCell("Zabieg");
		headerVisitTable.addCell("Cena");
		
		PdfPTable visitTable = new PdfPTable(5);
		visitTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		visitTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		visitTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
		visitTable.setWidths(new float [] {30, 80, 100, 150, 200});
		visitTable.setWidthPercentage(100);
		
		PdfPTable [] treatmentTable = new PdfPTable[visits.size()];
		PdfPCell [] treatmentCell = new PdfPCell[visits.size()];
		PdfPCell [] priceCell = new PdfPCell[visits.size()];
		
		for (int i=0; i<visits.size(); i++) {
			visitTable.addCell(String.valueOf(i+1));
			visitTable.addCell(visits.get(i).getVisitDateTime().toLocalDate().toString()+" godz. "+visits.get(i).getVisitDateTime().toLocalTime().toString());
			visitTable.addCell(visits.get(i).getDoctor().getFirstName()+" "+visits.get(i).getDoctor().getLastName());
			visitTable.addCell(Image.getInstance(visits.get(i).getDoctor().getPhoto()));

			treatmentTable[i] = new PdfPTable(2);
			treatmentTable[i].setWidths(new float [] {150, 50});
			
			for (int j=0; j<visits.get(i).getTreatments().size(); j++) {
				if(visits.get(i).getTreatments().get(j).getId() != 1) {
					treatmentCell[j] = new PdfPCell();
					treatmentCell[j].addElement(new Phrase(visits.get(i).getTreatments().get(j).getName()));
					treatmentTable[i].addCell(treatmentCell[j]);
					
					priceCell[j] = new PdfPCell();
					priceCell[j].addElement(new Phrase(String.valueOf(visits.get(i).getTreatments().get(j).getPrice())));
					treatmentTable[i].addCell(priceCell[j]);
				}
			}
			visitTable.addCell(treatmentTable[i]);
		}
		document.add(headerVisitTable);
		document.add(visitTable);
	}

}
