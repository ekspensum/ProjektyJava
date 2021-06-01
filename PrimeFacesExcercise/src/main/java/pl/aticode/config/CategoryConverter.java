package pl.aticode.config;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import pl.aticode.dao.CategoryRepository;
import pl.aticode.dao.CategoryRepositoryImpl;
import pl.aticode.entity.Category;

public class CategoryConverter implements Converter<Category> {
	
	private CategoryRepository categoryRepository;
	

	public CategoryConverter() {
		categoryRepository = CategoryRepositoryImpl.getInstance();
	}

	@Override
	public Category getAsObject(FacesContext context, UIComponent component, String id) {
		return categoryRepository.findById(Long.valueOf(id));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Category category) {
		return category.getId().toString();
	}

}
