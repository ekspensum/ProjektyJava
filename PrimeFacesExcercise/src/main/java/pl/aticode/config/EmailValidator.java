package pl.aticode.config;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class EmailValidator implements Validator<String> {

	private Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	
	@Override
	public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
		if(!pattern.matcher(value).matches()) {
			throw new ValidatorException(new FacesMessage("Adres email jest niepoprawny!"));
		}
	}

}
