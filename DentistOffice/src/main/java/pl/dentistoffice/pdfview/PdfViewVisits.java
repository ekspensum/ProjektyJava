package pl.dentistoffice.pdfview;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import pl.dentistoffice.entity.Visit;

public class PdfViewVisits extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		response.setHeader("Content-Disposition", "attachment; filename=\"visits.pdf\"");

		if(model.get("doctorVisits") != null) {
			pdfViewDoctorVisits(model, document, writer);
		} else if(model.get("patientVisits") != null) {
			pdfViewPatientVisits(model, document, writer);
		} else {
			throw new Exception("Błąd tworzenia dokumentu PDF");
		}
		document.close();
	}

	private void pdfViewDoctorVisits(Map<String, Object> model, Document document, PdfWriter writer) throws DocumentException, IOException {
		@SuppressWarnings("unchecked")
		List<Visit> visits = (List<Visit>) model.get("doctorVisits");
		
		document.setMargins(20, 10, 60, 10);
		Phrase phrase = new Phrase();
		phrase.setFont(new Font(BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1250, BaseFont.EMBEDDED), 10));
		phrase.add("Wizyty od: "+visits.get(0).getVisitDateTime().toLocalDate()+" od godz.: "+visits.get(0).getVisitDateTime().toLocalTime()+"\n");
		phrase.add("Data wydruku: "+LocalDateTime.now().toLocalDate().toString()+"\n");
		phrase.add("Lekarz: "+visits.get(0).getDoctor().getFirstName()+" "+visits.get(0).getDoctor().getLastName());
        HeaderFooter header = new HeaderFooter(phrase, false);
        document.setHeader(header);
        
        HeaderFooter footer = new HeaderFooter(new Phrase("Strona ", new Font(BaseFont.createFont(), 8)), true);
        footer.setAlignment(Element.ALIGN_RIGHT);
        footer.setBorder(Rectangle.NO_BORDER);
        document.setFooter(footer);
        document.setPageCount(0);
		document.open();
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("ZESTAWIENIE WIZYT", 
								   new Font(BaseFont.createFont(BaseFont.COURIER_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED), 16)), document.right()/2, document.top()+26, 0);
		
		PdfPTable visitTable = new PdfPTable(5);
		visitTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		visitTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		visitTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
		visitTable.setWidths(new float [] {30, 80, 100, 150, 200});
		visitTable.setWidthPercentage(100);
		
		Font headerTableFont = new Font(BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED));
		visitTable.addCell(new Phrase("L.p.", headerTableFont));
		visitTable.addCell(new Phrase("Data wizyty", headerTableFont));
		visitTable.addCell(new Phrase("Pacjent", headerTableFont));
		visitTable.addCell(new Phrase("Zdjęcie pacjenta", headerTableFont));
		visitTable.addCell(new Phrase("Zabieg i cena", headerTableFont));
		visitTable.setHeaderRows(1);
				
		PdfPTable [] treatmentTable = new PdfPTable[visits.size()];
		PdfPCell [] treatmentCell = new PdfPCell[visits.size()*3];
		PdfPCell [] priceCell = new PdfPCell[visits.size()*3];
		
		for (int i=0; i<visits.size(); i++) {
			visitTable.addCell(String.valueOf(i+1));
			visitTable.addCell(visits.get(i).getVisitDateTime().toLocalDate().toString()+" godz. "+visits.get(i).getVisitDateTime().toLocalTime().toString());
			visitTable.addCell(new Phrase(visits.get(i).getPatient().getFirstName()+" "+visits.get(i).getPatient().getLastName(), 
							   new Font(BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1250, BaseFont.EMBEDDED), 11)));
			visitTable.addCell(Image.getInstance(visits.get(i).getPatient().getPhoto()));

			treatmentTable[i] = new PdfPTable(2);
			treatmentTable[i].setWidths(new float [] {150, 50});
			
			for (int j=0; j<visits.get(i).getTreatments().size(); j++) {
				if(visits.get(i).getTreatments().get(j).getId() != 1) {
					treatmentCell[j] = new PdfPCell();
					treatmentCell[j].addElement(new Phrase(visits.get(i).getTreatments().get(j).getName(), 
												new Font(BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1250, BaseFont.EMBEDDED), 11)));
					treatmentTable[i].addCell(treatmentCell[j]);
					
					priceCell[j] = new PdfPCell();
					priceCell[j].addElement(new Phrase(String.valueOf(visits.get(i).getTreatments().get(j).getPrice()), 
											new Font(BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1250, BaseFont.EMBEDDED), 11)));
					treatmentTable[i].addCell(priceCell[j]);
				}
			}
			visitTable.addCell(treatmentTable[i]);
		}
		document.add(visitTable);
	}
	
	private void pdfViewPatientVisits(Map<String, Object> model, Document document, PdfWriter writer) throws DocumentException, IOException {
		
		@SuppressWarnings("unchecked")
		List<Visit> visits = (List<Visit>) model.get("patientVisits");
		
		document.setMargins(20, 10, 60, 10);
		Phrase phrase = new Phrase();
		phrase.setFont(new Font(BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1250, BaseFont.EMBEDDED), 10));
		phrase.add("Status wizyty: "+visits.get(0).getStatus().getDescription()+"\n");
		phrase.add("Data wydruku: "+LocalDateTime.now().toLocalDate().toString()+"\n");
		phrase.add("Pacjent: "+visits.get(0).getPatient().getFirstName()+" "+visits.get(0).getPatient().getLastName());
        HeaderFooter header = new HeaderFooter(phrase, false);
        document.setHeader(header);
        
        HeaderFooter footer = new HeaderFooter(new Phrase("Strona ", new Font(BaseFont.createFont(), 8)), true);
        footer.setAlignment(Element.ALIGN_RIGHT);
        footer.setBorder(Rectangle.NO_BORDER);
        document.setFooter(footer);
        document.setPageCount(0);
		document.open();
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("ZESTAWIENIE WIZYT", 
								   new Font(BaseFont.createFont(BaseFont.COURIER_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED), 16)), document.right()/2, document.top()+26, 0);
		
		PdfPTable visitTable = new PdfPTable(5);
		visitTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		visitTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		visitTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
		visitTable.setWidths(new float [] {30, 80, 100, 150, 200});
		visitTable.setWidthPercentage(100);
		
		Font headerTableFont = new Font(BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED));
		visitTable.addCell(new Phrase("L.p.", headerTableFont));
		visitTable.addCell(new Phrase("Data wizyty", headerTableFont));
		visitTable.addCell(new Phrase("Lekarz", headerTableFont));
		visitTable.addCell(new Phrase("Zdjęcie lekarza", headerTableFont));
		visitTable.addCell(new Phrase("Zabieg i cena", headerTableFont));
		visitTable.setHeaderRows(1);
				
		PdfPTable [] treatmentTable = new PdfPTable[visits.size()];
		PdfPCell [] treatmentCell = new PdfPCell[visits.size()*3];
		PdfPCell [] priceCell = new PdfPCell[visits.size()*3];
		
		for (int i=0; i<visits.size(); i++) {
			visitTable.addCell(String.valueOf(i+1));
			visitTable.addCell(visits.get(i).getVisitDateTime().toLocalDate().toString()+" godz. "+visits.get(i).getVisitDateTime().toLocalTime().toString());
			visitTable.addCell(new Phrase(visits.get(i).getDoctor().getFirstName()+" "+visits.get(i).getDoctor().getLastName(), 
							   new Font(BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1250, BaseFont.EMBEDDED), 11)));
			visitTable.addCell(Image.getInstance(visits.get(i).getDoctor().getPhoto()));

			treatmentTable[i] = new PdfPTable(2);
			treatmentTable[i].setWidths(new float [] {150, 50});
			
			for (int j=0; j<visits.get(i).getTreatments().size(); j++) {
				if(visits.get(i).getTreatments().get(j).getId() != 1) {
					treatmentCell[j] = new PdfPCell();
					treatmentCell[j].addElement(new Phrase(visits.get(i).getTreatments().get(j).getName(), 
												new Font(BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1250, BaseFont.EMBEDDED), 11)));
					treatmentTable[i].addCell(treatmentCell[j]);
					
					priceCell[j] = new PdfPCell();
					priceCell[j].addElement(new Phrase(String.valueOf(visits.get(i).getTreatments().get(j).getPrice()), 
											new Font(BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1250, BaseFont.EMBEDDED), 11)));
					treatmentTable[i].addCell(priceCell[j]);
				}
			}
			visitTable.addCell(treatmentTable[i]);
		}
		document.add(visitTable);

	}
}
