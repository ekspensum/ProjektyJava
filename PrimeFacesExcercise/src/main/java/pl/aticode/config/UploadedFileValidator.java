package pl.aticode.config;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.model.UploadedFile;

public class UploadedFileValidator implements Validator<UploadedFile> {

	@Override
	public void validate(FacesContext context, UIComponent component, UploadedFile file) throws ValidatorException {
		
		if(file.getSize() == 0) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dodanie pliku jest wymagane!", "detail"));	
		} else if (file.getSize() > 300000) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rozmiar pliku: "+file.getFileName()+" został przekroczony!", "detail"));			
		}
	}

}
