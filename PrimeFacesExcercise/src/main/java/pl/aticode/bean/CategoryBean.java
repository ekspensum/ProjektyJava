package pl.aticode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ListDataModel;

import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.CategoryRepository;
import pl.aticode.dao.CategoryRepositoryImpl;
import pl.aticode.entity.Category;

@SuppressWarnings("deprecation")
@ManagedBean
@RequestScoped
@Getter @Setter
public class CategoryBean {

	private ListDataModel<Category> categoryModel;

	public CategoryBean() {
		categoryModel = new ListDataModel<>();
		CategoryRepository categoryRepository = new CategoryRepositoryImpl();
		categoryModel.setWrappedData(categoryRepository.findAll());
	}
	
	
}
