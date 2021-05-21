package pl.aticode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.ListDataModel;

import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.CategoryRepository;
import pl.aticode.dao.CategoryRepositoryImpl;
import pl.aticode.entity.Category;

@ManagedBean
@RequestScoped
@Getter @Setter
public class CategoryBean {

	private ListDataModel<Category> categoryModel;

	public CategoryBean() {
		categoryModel = new ListDataModel<>();
		CategoryRepository categoryRepository = CategoryRepositoryImpl.getInstance();
		categoryModel.setWrappedData(categoryRepository.findAll());
	}
	
	
}
